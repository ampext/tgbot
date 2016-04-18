package telegram.event;

import telegram.objects.User;

import java.util.EventListener;

public interface CommandHandler extends EventListener
{
    void handle(String command, String args, User chat);
}

