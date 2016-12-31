package telegram.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Update
{
    @JsonProperty("update_id") private int updateId;
    @JsonProperty("message") private Message message;
    @JsonProperty("edited_message") private Message editedMessage;

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

    public Message getEditedMessage()
    {
        return editedMessage;
    }

    public void setEditedMessage(Message editedMessage)
    {
        this.editedMessage = editedMessage;
    }
}
