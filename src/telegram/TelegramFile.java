package telegram;

import java.io.File;

public class TelegramFile
{
    private File file;
    private String id;

    public TelegramFile(File file)
    {
        this.file = file;
    }

    public TelegramFile(String id)
    {
        this.id = id;
    }

    public File getFile()
    {
        return file;
    }

    public void setFile(File file)
    {
        this.file = file;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
}
