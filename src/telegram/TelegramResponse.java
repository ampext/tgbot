package telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TelegramResponse<Result>
{
    @JsonProperty("ok") private boolean ok;
    @JsonProperty("result") private Result result;

    public boolean isOk()
    {
        return ok;
    }

    public Result getResult()
    {
        return result;
    }
}
