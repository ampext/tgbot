package telegram.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Sticker
{
    @JsonProperty("file_id") private String fileId;
    @JsonProperty("width") private int width;
    @JsonProperty("height") private int height;
    @JsonProperty("thumb") private PhotoSize thumb;
    @JsonProperty("emoji") private String emoji;
    @JsonProperty("file_size") private int fileSize;

    public String getFileId()
    {
        return fileId;
    }

    public void setFileId(String fileId)
    {
        this.fileId = fileId;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public PhotoSize getThumb()
    {
        return thumb;
    }

    public void setThumb(PhotoSize thumb)
    {
        this.thumb = thumb;
    }

    public int getFileSize()
    {
        return fileSize;
    }

    public void setFileSize(int fileSize)
    {
        this.fileSize = fileSize;
    }

    public String getEmoji()
    {
        return emoji;
    }

    public void setEmoji(String emoji)
    {
        this.emoji = emoji;
    }
}
