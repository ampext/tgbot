package telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TelegramErrorResponse
{
    @JsonProperty("ok") private boolean ok;
    @JsonProperty("error_code") private int errorCode;
    @JsonProperty("description") private String description;

    public boolean isOk()
    {
        return ok;
    }

    public int getErrorCode()
    {
        return errorCode;
    }

    public String getDescription()
    {
        return description;
    }
}
