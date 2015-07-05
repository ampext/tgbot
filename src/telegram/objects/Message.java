package telegram.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Vector;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message
{
    @JsonProperty("message_id") private int messageId;
    @JsonProperty("from") private User from;
    @JsonProperty("date") private int date;
    @JsonProperty("chat") private User chat;
    @JsonProperty("forward_from") private User forwardFrom;
    @JsonProperty("forward_date") private int forwardDate;
    @JsonProperty("reply_to_message") private int replyToMessage;
    @JsonProperty("text") private String text;
    @JsonProperty("document") private Document document;
    @JsonProperty("photo") private Vector<PhotoSize> photo;
    @JsonProperty("sticker") private Sticker sticker;
    @JsonProperty("video") private Video video;
    @JsonProperty("location") private Location location;
    @JsonProperty("new_chat_participant") private User newChatParticipant;
    @JsonProperty("left_chat_participant") private User leftChatParticipant;
    @JsonProperty("new_chat_title") private String newChatTitle;
    @JsonProperty("new_chat_photo") private Vector<PhotoSize> newChatPhoto;
    @JsonProperty("delete_chat_photo") private boolean deleteChatPhoto;
    @JsonProperty("group_chat_created") private boolean groupChatCreated;

    public int getMessageId()
    {
        return messageId;
    }

    public void setMessageId(int messageId)
    {
        this.messageId = messageId;
    }

    public User getFrom()
    {
        return from;
    }

    public void setFrom(User from)
    {
        this.from = from;
    }

    public int getDate()
    {
        return date;
    }

    public void setDate(int date)
    {
        this.date = date;
    }

    public User getChat()
    {
        return chat;
    }

    public void setChat(User chat)
    {
        this.chat = chat;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public Vector<PhotoSize> getPhoto()
    {
        return photo;
    }

    public void setPhoto(Vector<PhotoSize> photo)
    {
        this.photo = photo;
    }

    public String getNewChatTitle()
    {
        return newChatTitle;
    }

    public void setNewChatTitle(String newChatTitle)
    {
        this.newChatTitle = newChatTitle;
    }

    public Vector<PhotoSize> getNewChatPhoto()
    {
        return newChatPhoto;
    }

    public void setNewChatPhoto(Vector<PhotoSize> newChatPhoto)
    {
        this.newChatPhoto = newChatPhoto;
    }

    public Document getDocument()
    {
        return document;
    }

    public void setDocument(Document document)
    {
        this.document = document;
    }

    public Sticker getSticker()
    {
        return sticker;
    }

    public void setSticker(Sticker sticker)
    {
        this.sticker = sticker;
    }

    public Video getVideo()
    {
        return video;
    }

    public void setVideo(Video video)
    {
        this.video = video;
    }

    public User getForwardFrom()
    {
        return forwardFrom;
    }

    public void setForwardFrom(User forwardFrom)
    {
        this.forwardFrom = forwardFrom;
    }

    public int getForwardDate()
    {
        return forwardDate;
    }

    public void setForwardDate(int forwardDate)
    {
        this.forwardDate = forwardDate;
    }

    public int getReplyToMessage()
    {
        return replyToMessage;
    }

    public void setReplyToMessage(int replyToMessage)
    {
        this.replyToMessage = replyToMessage;
    }

    public User getNewChatParticipant()
    {
        return newChatParticipant;
    }

    public void setNewChatParticipant(User newChatParticipant)
    {
        this.newChatParticipant = newChatParticipant;
    }

    public User getLeftChatParticipant()
    {
        return leftChatParticipant;
    }

    public void setLeftChatParticipant(User leftChatParticipant)
    {
        this.leftChatParticipant = leftChatParticipant;
    }

    public boolean isDeleteChatPhoto()
    {
        return deleteChatPhoto;
    }

    public void setDeleteChatPhoto(boolean deleteChatPhoto)
    {
        this.deleteChatPhoto = deleteChatPhoto;
    }

    public boolean isGroupChatCreated()
    {
        return groupChatCreated;
    }

    public void setGroupChatCreated(boolean groupChatCreated)
    {
        this.groupChatCreated = groupChatCreated;
    }

    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }
}