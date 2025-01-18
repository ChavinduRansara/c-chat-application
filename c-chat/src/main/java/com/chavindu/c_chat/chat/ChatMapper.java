package com.chavindu.c_chat.chat;

import org.springframework.stereotype.Service;

@Service
public class ChatMapper {
    public ChatResponse toChatResponse(Chat chat, String senderId) {
        return ChatResponse.builder()
                .id(chat.getId())
                .name(chat.getChatName(senderId))
                .unreadCount(chat.getUnreadMessageCount(senderId))
                .lastMessage(chat.getLastMessage())
                .isRecipientOnline(chat.getRecipient().isUserOnline())
                .senderId(chat.getSender().getId())
                .recipientId(chat.getRecipient().getId())
                .build();
    }
}
