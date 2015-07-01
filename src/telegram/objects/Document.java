package telegram.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Document
{
    @JsonProperty("file_id") private String fileId;
    @JsonProperty("thumb") private PhotoSize thumb;
    @JsonProperty("file_name") private String fileName;
    @JsonProperty("mime_type") private String mimeType;
    @JsonProperty("file_size") private int fileSize;

    public String getFileId()
    {
        return fileId;
    }

    public void setFileId(String fileId)
    {
        this.fileId = fileId;
    }

    public PhotoSize getThumb()
    {
        return thumb;
    }

    public void setThumb(PhotoSize thumb)
    {
        this.thumb = thumb;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getMimeType()
    {
        return mimeType;
    }

    public void setMimeType(String mimeType)
    {
        this.mimeType = mimeType;
    }

    public int getFileSize()
    {
        return fileSize;
    }

    public void setFileSize(int fileSize)
    {
        this.fileSize = fileSize;
    }
}
