package telegram;

import com.fasterxml.jackson.databind.ObjectMapper;
import telegram.event.CommandHandler;
import telegram.event.UpdateHandler;
import telegram.objects.Message;
import telegram.objects.PhotoSize;
import telegram.objects.Update;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TelegramListener
{
    private TelegramAccess telegramAccess;

    private int lastUpdateId = -1;
    private int timeout = 0; // long pooling timeout
    private int updatesLimit = 50;

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private Vector<UpdateHandler> updateListeners = new Vector<>();
    private Vector<CommandHandler> commandListeners = new Vector<>();

    public TelegramListener(String token)
    {
        this(token, 30);
    }

    public TelegramListener(String token, int timeout)
    {
        this.timeout = timeout;

        telegramAccess = new TelegramAccess(token);

        try
        {
            Vector<Update> updates = telegramAccess.getUpdates(0, 1, null);

            if (updates != null && !updates.isEmpty())
                lastUpdateId = updates.get(updates.size() - 1).getUpdateId();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void start()
    {
        scheduler.schedule(() -> this.checkUpdates(), 0, TimeUnit.MILLISECONDS);
    }

    public void stop()
    {
        scheduler.shutdown();
    }

    public void addMessageListener(UpdateHandler listener)
    {
        updateListeners.add(listener);
    }

    public void addCommandListener(CommandHandler listener)
    {
        commandListeners.add(listener);
    }

    public TelegramAccess telegramAccess()
    {
        return this.telegramAccess;
    }

    private void updateReceived(Update update)
    {
        for (UpdateHandler listener: updateListeners)
            listener.handle(update);

        Message message = update.getMessage();

        if (message.getText() != null && message.getText().startsWith("/"))
        {
            String command;
            String args;

            int split = message.getText().indexOf(" ");

            if (split == -1 || split == message.getText().length() - 1)
            {
                command = message.getText().substring(1);
                args = "";
            }
            else
            {
                command = message.getText().substring(1, split);
                args = message.getText().substring(split + 1);
            }

            for (CommandHandler listener: commandListeners)
                listener.handle(command, args, message.getChat());
        }
    }

    private void checkUpdates()
    {
        try
        {
            Integer offset = lastUpdateId > 0 ? lastUpdateId + 1 : null;
            Vector<Update> updates = telegramAccess.getUpdates(offset,this.updatesLimit, this.timeout);

            if (updates != null)
            {
                for (Update update: updates)
                {
                    if (update.getUpdateId() > this.lastUpdateId)
                    {
                        updateReceived(update);
                        this.lastUpdateId = update.getUpdateId();
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        scheduler.schedule(() -> this.checkUpdates(), 0, TimeUnit.MILLISECONDS);
    }
}
