package com.chavindu.c_chat.chat;

import com.chavindu.c_chat.common.BaseAuditingEntity;
import com.chavindu.c_chat.message.Message;
import com.chavindu.c_chat.message.MessageState;
import com.chavindu.c_chat.message.MessageType;
import com.chavindu.c_chat.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chat")
public class Chat extends BaseAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;

    @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER)
    @OrderBy("createdDate DESC")
    private List<Message> messages;

    @Transient
    public String getChatName(final String senderId){
        if(recipient.getId().equals(senderId)){
            return sender.getFirstName() + " " + sender.getLastName();
        }

        return recipient.getFirstName() + " " + recipient.getLastName();
    }

    @Transient
    public long getUnreadMessageCount(final String senderId){
        return messages
                .stream()
                .filter(m-> m.getReceiverId().equals(senderId))
                .filter(m-> MessageState.SENT == m.getState())
                .count();
    }

    @Transient
    public String getLastMessage(){
        if(messages != null && !messages.isEmpty()){
            if(messages.get(0).getType() != MessageType.TEXT){
                return "Attachment";
            }
            return messages.get(0).getContent();
        }
        return null;
    }

    @Transient
    public LocalDateTime getLastMessageTime(){
        if(messages != null && !messages.isEmpty()){
            return messages.get(0).getCreatedDate();
        }
        return null;
    }

}
