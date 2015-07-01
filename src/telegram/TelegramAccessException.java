package telegram;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TelegramAccessException extends Exception
{
    private TelegramErrorResponse response;
    private static ObjectMapper mapper = new ObjectMapper();

    public TelegramAccessException(String response)
    {
        try
        {
            this.response = mapper.readValue(response, TelegramErrorResponse.class);
        }
        catch (Exception e)
        {

        }
    }

    public String getMessage()
    {
        if (this.response != null) return this.response.getDescription();
        return "";
    }
}
