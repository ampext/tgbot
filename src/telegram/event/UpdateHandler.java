package telegram.event;

import telegram.objects.Update;

import java.util.EventListener;

public interface UpdateHandler extends EventListener
{
    void handle(Update update);
}
