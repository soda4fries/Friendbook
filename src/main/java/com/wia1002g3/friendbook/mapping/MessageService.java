package com.wia1002g3.friendbook.mapping;
import com.wia1002g3.friendbook.entity.Conversation;
import com.wia1002g3.friendbook.entity.Message;
import com.wia1002g3.friendbook.entity.User;
import com.wia1002g3.friendbook.repository.ConversationRepository;
import com.wia1002g3.friendbook.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RequiredArgsConstructor
@RestController
public class MessageService {

    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;

    @GetMapping("api/messaging/{userId}/conversations")
    public List<Integer> getUserConversationIds(@PathVariable Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<Conversation> conversations = user.getConversations();
        List<Integer> conversationIds = new ArrayList<>();

        for (Conversation conversation : conversations) {
            conversationIds.add(conversation.getId());
        }

        return conversationIds;
    }

    @GetMapping("api/messaging/{conversationId}/messages/{page}")
    public ResponseEntity<List<MessageDTO>> getConversationMessages(@PathVariable Integer conversationId, @PathVariable Integer page) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        List<MessageDTO> messageDTOs = new ArrayList<>();
        for (Message message : conversation.getAllMessages()) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setMessageId(message.getId());
            messageDTO.setMessage(message.getMessage());
            messageDTO.setTimestamp(message.getTimestamp());
            messageDTO.setSenderUserId(message.getSender().getId());
            messageDTOs.add(messageDTO);
        }

        Collections.sort(messageDTOs);

        List<MessageDTO> pageMessages = getPage(messageDTOs, page, 30);

        return new ResponseEntity<>(pageMessages, HttpStatus.OK);
    }

    public static List<MessageDTO> getPage(List<MessageDTO> list, int pageNumber, int pageSize) {
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, list.size());

        if (startIndex >= list.size()) {
            return new ArrayList<>(); // Empty list if page number is out of range
        }

        return list.subList(startIndex, endIndex);
    }

    @Data
    public class MessageDTO implements Comparable<MessageDTO> {
        private Integer messageId;
        private String message;
        private Date timestamp;
        private Integer senderUserId;

        @Override
        public int compareTo(MessageDTO o) {
            return this.timestamp.compareTo(o.getTimestamp());
        }
    }





}
