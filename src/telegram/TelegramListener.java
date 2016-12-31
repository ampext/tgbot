package telegram;

import telegram.event.CommandHandler;
import telegram.event.UpdateHandler;
import telegram.objects.Message;
import telegram.objects.Update;
import telegram.objects.User;

import java.util.*;
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

    private Set<Long> allowedChats = new HashSet<>();

    public TelegramListener(String token)
    {
        this(token, 30);
    }

    public TelegramListener(String token, int timeout)
    {
        this.timeout = timeout;
        telegramAccess = new TelegramAccess(token);
    }

    public void start() throws Exception
    {
        Vector<Update> updates = telegramAccess.getUpdates(0, updatesLimit, null);

        if (updates != null && !updates.isEmpty())
            lastUpdateId = updates.get(updates.size() - 1).getUpdateId();

        me = telegramAccess.getMe();

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
        return telegramAccess;
    }

    public void addAllowedChat(long chatId)
    {
        allowedChats.add(chatId);
    }

    public void clearAllowedChats()
    {
        allowedChats.clear();
    }

    public Integer[] getAllowedChats()
    {
        return allowedChats.toArray(new Integer[0]);
    }

    private void updateReceived(Update update)
    {
        for (UpdateHandler listener: updateListeners)
            listener.handle(update);

        Message message = update.getMessage();

        if (message != null && message.getText() != null && message.getText().startsWith("/"))
        {
            String commandLine = message.getText();

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

            if (message.getChat().isGroupChat())
            {
                if (command.endsWith("@" + me.getUsername()))
                {
                    command = command.substring(0, command.length() - me.getUsername().length() - 1);
                }
                else command = "";
            }

            if (command.isEmpty())
                return;

            if (!allowedChats.isEmpty() && !allowedChats.contains(message.getChat().getId()))
            {
                System.out.print("skipped command from chat " + message.getChat().getId());
                return;
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
            System.err.println("checkUpdates error: " + e.getMessage());
        }

        scheduler.schedule(() -> this.checkUpdates(), 0, TimeUnit.MILLISECONDS);
    }
}
