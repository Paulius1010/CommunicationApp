package lt.example.communication.payloads.responses;

import java.time.LocalDateTime;

public class UserMessageStatistic {
    
    private String userEmail;
    private Integer messagesAmount;
    private LocalDateTime firstMessageTime;
    private LocalDateTime lastMessageTime;
    private Integer averageMessageLength;
    private String lastMessageText;

    public UserMessageStatistic(String userEmail, Integer messagesAmount, LocalDateTime firstMessageTime, LocalDateTime lastMessageTime, Integer averageMessageLength, String lastMessageText) {
        this.userEmail = userEmail;
        this.messagesAmount = messagesAmount;
        this.firstMessageTime = firstMessageTime;
        this.lastMessageTime = lastMessageTime;
        this.averageMessageLength = averageMessageLength;
        this.lastMessageText = lastMessageText;
    }

    public UserMessageStatistic() {
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setMessagesAmount(Integer messagesAmount) {
        this.messagesAmount = messagesAmount;
    }

    public void setFirstMessageTime(LocalDateTime firstMessageTime) {
        this.firstMessageTime = firstMessageTime;
    }

    public void setLastMessageTime(LocalDateTime lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public void setAverageMessageLength(Integer averageMessageLength) {
        this.averageMessageLength = averageMessageLength;
    }

    public void setLastMessageText(String lastMessageText) {
        this.lastMessageText = lastMessageText;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Integer getMessagesAmount() {
        return messagesAmount;
    }

    public LocalDateTime getFirstMessageTime() {
        return firstMessageTime;
    }

    public LocalDateTime getLastMessageTime() {
        return lastMessageTime;
    }

    public Integer getAverageMessageLength() {
        return averageMessageLength;
    }

    public String getLastMessageText() {
        return lastMessageText;
    }
}
