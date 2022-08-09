package lt.example.communication.payloads.requests;

import javax.validation.constraints.NotBlank;

public class MessageRequest {

    @NotBlank
    private String recipientEmail;

    @NotBlank
    private String text;

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public String getText() {
        return text;
    }
}
