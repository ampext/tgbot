package telegram;

import java.io.File;
import java.io.InputStream;

public class TelegramFile
{
    private File file;
    private String id;

    private InputStream stream;
    private String filename;
    private String contentType;

    public TelegramFile(File file)
    {
        this.file = file;
    }

    public TelegramFile(String id)
    {
        this.id = id;
    }

    public TelegramFile(InputStream stream, String filename, String contentType)
    {
        this.stream = stream;
        this.filename = filename;
        this.contentType = contentType;
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

    public InputStream getStream()
    {
        return stream;
    }

    public void setStream(InputStream stream)
    {
        this.stream = stream;
    }

    public String getContentType()
    {
        return contentType;
    }

    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }
}
