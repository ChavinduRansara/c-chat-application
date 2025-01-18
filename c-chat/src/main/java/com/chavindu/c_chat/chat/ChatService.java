package com.chavindu.c_chat.chat;

import com.chavindu.c_chat.user.User;
import com.chavindu.c_chat.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatMapper chatMapper;

    @Transactional(readOnly = true)
    public List<ChatResponse> getChatsByReceiverId(Authentication currentUser) {
        final String currentUserId = currentUser.getName();
        return chatRepository.findChatBySenderId(currentUserId)
                .stream()
                .map(c-> chatMapper.toChatResponse(c,currentUserId))
                .toList();
    }

    public String createChat(String senderId, String receiverId) {
        Optional<Chat> existingChat = chatRepository.findChatBySenderIdAndReceiverId(senderId, receiverId);
        if (existingChat.isPresent()) {
            return existingChat.get().getId();
        }

        User sender = userRepository.findByPublicId(senderId)
                .orElseThrow(()-> new EntityNotFoundException("User with id " + senderId + " not found"));

        User receiver = userRepository.findByPublicId(receiverId)
                .orElseThrow(()-> new EntityNotFoundException("User with id " + receiverId + " not found"));

        Chat chat = new Chat();
        chat.setSender(sender);
        chat.setRecipient(receiver);
        Chat savedChat = chatRepository.save(chat);
        return savedChat.getId();
    }

}
