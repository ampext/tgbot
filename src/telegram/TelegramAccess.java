package telegram;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;
import telegram.objects.*;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class TelegramAccess
{
    private static String apiUrl;
    private static ObjectMapper mapper = new ObjectMapper();
    private static MimetypesFileTypeMap typeMap = new MimetypesFileTypeMap();

    public TelegramAccess(String token)
    {
        this.apiUrl = "https://api.telegram.org/bot" + token;
    }

    public void sendMessage(int chatId, String text) throws TelegramAccessException
    {
        Map<String, String> args = new HashMap<>();

        args.put("chat_id", Integer.toString(chatId));
        args.put("text", text);

        execCommand("sendMessage", args);
    }

    public Vector<Update> getUpdates(Integer offset, Integer limit, Integer timeout) throws TelegramAccessException, IOException
    {
        Map<String, String> args = new HashMap<>();

        if (offset != null) args.put("offset", offset.toString());
        if (limit != null) args.put("limit", limit.toString());
        if (timeout != null) args.put("timeout", timeout.toString());

        String data = execCommand("getUpdates", args);
        TelegramResponse<Vector<Update>> response = mapper.readValue(data, new TypeReference<TelegramResponse<Vector<Update>>>(){});

        return getResult(response);
    }

    public Message sendPhoto(int chatId, TelegramFile file) throws TelegramAccessException, IOException
    {
        return sendPhoto(chatId, file, null, null);
    }

    public Message sendPhoto(int chatId, TelegramFile file, String caption) throws TelegramAccessException, IOException
    {
        return sendPhoto(chatId, file, caption, null);
    }

    public Message sendPhoto(int chatId, TelegramFile file, String caption, Integer replyId) throws TelegramAccessException, IOException
    {
        Map<String, String> args = new HashMap<>();
        if (caption != null) args.put("caption", caption);
        if (replyId != null) args.put("reply_to_message_id", replyId.toString());

        return sendFile("sendPhoto", chatId, file, args);
    }

    public Message sendSticker(int chatId, TelegramFile file) throws TelegramAccessException, IOException
    {
        return sendPhoto(chatId, file, null);
    }

    public Message sendSticker(int chatId, TelegramFile file, Integer replyId) throws TelegramAccessException, IOException
    {
        Map<String, String> args = new HashMap<>();
        if (replyId != null) args.put("reply_to_message_id", replyId.toString());

        return sendFile("sendSticker", chatId, file, args);
    }

    public Message sendVideo(int chatId, TelegramFile file) throws TelegramAccessException, IOException
    {
        return sendPhoto(chatId, file, null);
    }

    public Message sendVideo(int chatId, TelegramFile file, Integer replyId) throws TelegramAccessException, IOException
    {
        Map<String, String> args = new HashMap<>();
        if (replyId != null) args.put("reply_to_message_id", replyId.toString());

        return sendFile("sendVideo", chatId, file, args);
    }

    public Message sendDocument(int chatId, TelegramFile file) throws TelegramAccessException, IOException
    {
        return sendPhoto(chatId, file, null);
    }

    public Message sendDocument(int chatId, TelegramFile file, Integer replyId) throws TelegramAccessException, IOException
    {
        Map<String, String> args = new HashMap<>();
        if (replyId != null) args.put("reply_to_message_id", replyId.toString());

        return sendFile("sendDocument", chatId, file, args);
    }

    private String execCommand(String command, Map<String, String> args) throws TelegramAccessException
    {
        HttpRequest request = HttpRequest
                .post(this.apiUrl + "/" + command)
                .acceptJson()
                .form(args);

        if (!request.ok())
            throw new TelegramAccessException(request.body());

        return request.body();
    }

    private String sendData(String command, Map<String, String> args, String partName, File file) throws TelegramAccessException
    {
        HttpRequest request = HttpRequest
                .post(this.apiUrl + "/" + command)
                .acceptJson();

        args.forEach((k, v) -> request.part(k, v));
        request.part(partName, file.getName(), typeMap.getContentType(file), file);

        if (!request.ok())
            throw new TelegramAccessException(request.body());

        return request.body();
    }

    private Message sendFile(String command, int chatId, TelegramFile file, Map<String, String> args) throws TelegramAccessException, IOException
    {
        args.put("chat_id", Integer.toString(chatId));

        String data;

        if (file.getFile() != null) data = sendData(command, args, getFileTypeFromCommand(command), file.getFile());
        else
        {
            args.put(getFileTypeFromCommand(command), file.getId());
            data = execCommand(command, args);
        }

        TelegramResponse<Message> response = mapper.readValue(data, new TypeReference<TelegramResponse<Message>>(){});

        return getResult(response);
    }

    private <Result> Result getResult(TelegramResponse<Result> response)
    {
        if (response.isOk()) return response.getResult();

        System.err.println("broken response");
        return null;
    }

    private String getFileTypeFromCommand(String command)
    {
        if (command.equals("sendPhoto")) return "photo";
        else if (command.equals("sendDocument")) return "document";
        else if (command.equals("sendAudio")) return "audio";
        else if (command.equals("sendSticker")) return "sticker";
        else if (command.equals("sendVideo")) return "video";

        return "";
    }
}
