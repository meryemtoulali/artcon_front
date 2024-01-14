package com.example.artcon_test.chat.models;

import java.util.Date;

public class ChatMessage {
    public String senderId, receiverId, message, dateTime;
    public Date dateObject;
    public String conversionId, conversionFirstname, conversionLastname, conversionImage, conversionUsername;  // Update this line


    public String getConversionImage() {
        return conversionImage;
    }

    public String getConversionUsername() {
        return conversionUsername;
    }

}
