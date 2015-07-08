package telegram;

import telegram.event.CommandHandler;
import telegram.event.UpdateHandler;
import telegram.objects.Message;
import telegram.objects.Update;
import telegram.objects.User;

import java.util.LinkedList;
import java.util.List;
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
    private User me;

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private List<UpdateHandler> updateListeners = new LinkedList<>();
    private List<CommandHandler> commandListeners = new LinkedList<>();

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

            me = telegramAccess.getMe();
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

    public void addUpdateListener(UpdateHandler listener)
    {
        if (!updateListeners.contains(listener))
            updateListeners.add(listener);
    }

    public void removeUpdateListener(UpdateHandler listener)
    {
        updateListeners.remove(listener);
    }

    public void addCommandListener(CommandHandler listener)
    {
        if (!commandListeners.contains(listener))
            commandListeners.add(listener);
    }

    public void removeCommandListener(CommandHandler listener)
    {
        commandListeners.remove(listener);
    }


    public TelegramAccess getTelegramAccess()
    {
        return this.telegramAccess;
    }

    private void updateReceived(Update update)
    {
        for (UpdateHandler listener: updateListeners)
            listener.handle(update);

        Message message = update.getMessage();

        if (message.getText() != null)
        {
            String commandLine = "";

            if (message.getChat().isGroupChat())
            {
                String text = message.getText().trim();

                if (text.startsWith("@" + me.getUsername()))
                    commandLine = text.substring(me.getUsername().length() + 1).trim();
            }
            else
            {
                commandLine = message.getText();
            }

            if (!commandLine.isEmpty() && commandLine.startsWith("/"))
            {
                String command;
                String args;

                int split = commandLine.indexOf(" ");

                if (split == -1 || split == commandLine.length() - 1)
                {
                    command = commandLine.substring(1);
                    args = "";
                }
                else
                {
                    command = commandLine.substring(1, split);
                    args = commandLine.substring(split + 1);
                }

                for (CommandHandler listener: commandListeners)
                    listener.handle(command, args, message.getChat());
            }
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
