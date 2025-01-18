package com.chavindu.c_chat.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, String > {

    @Query(name = ChatConstants.FIND_CHAT_BY_SENDER_ID)
    List<Chat> findChatBySenderId(@Param("senderId") String currentUserId);

    @Query(name = ChatConstants.FIND_CHAT_BY_SENDER_ID_AND_RECEIVER_ID)
    Optional<Chat> findChatBySenderIdAndReceiverId(@Param("senderId") String senderId, @Param("recipientId") String receiverId);
}
