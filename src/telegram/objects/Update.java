package telegram.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Update
{
    @JsonProperty("update_id") private int updateId;
    @JsonProperty("message") private Message message;

    public int getUpdateId()
    {
        return updateId;
    }

    public void setUpdateId(int updateId)
    {
        this.updateId = updateId;
    }

    public Message getMessage()
    {
        return message;
    }

    public void setMessage(Message message)
    {
        this.message = message;
    }
}
