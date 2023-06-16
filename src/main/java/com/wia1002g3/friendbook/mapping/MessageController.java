package com.wia1002g3.friendbook.mapping;

import com.wia1002g3.friendbook.DTOs.CreateGroupRequest;
import com.wia1002g3.friendbook.DTOs.MessageDTO;
import com.wia1002g3.friendbook.DTOs.SaveMessageDTO;
import com.wia1002g3.friendbook.entity.Conversation;
import com.wia1002g3.friendbook.entity.Message;
import com.wia1002g3.friendbook.entity.User;
import com.wia1002g3.friendbook.repository.ConversationRepository;
import com.wia1002g3.friendbook.repository.MessageRepository;
import com.wia1002g3.friendbook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@RestController
public class MessageController {

    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    @PostMapping("api/message/Dm/{userid1}/{userid2}")
    public Boolean startMessage(@PathVariable Integer userid1, @PathVariable Integer userid2) {
        Conversation newConversation = new Conversation();
        User user1 = userRepository.findById(userid1).orElseThrow();
        User user2 = userRepository.findById(userid2).orElseThrow();
        newConversation.setConversationName(String.format("%s:%s,%s:%s", user1.getId().toString(), user1.getUsername(),user2.getId().toString(), user2.getUsername()));
        newConversation.setAllMessages(new ArrayList<>() {});
        user1.getConversations().add(newConversation);
        user2.getConversations().add(newConversation);
        userRepository.save(user1);
        userRepository.save(user2);
        conversationRepository.save(newConversation);
        return true;
    }

    @PostMapping("api/message/Group/{userid1}/{userid2}")
    public Boolean startMessage(@RequestBody CreateGroupRequest request) {
        Conversation newConversation = new Conversation();
        newConversation.setAllMessages(new ArrayList<>() {});
        newConversation.setConversationName(request.getGroupName());
        conversationRepository.save(newConversation);
        for (Integer memberid : request.getMembers()) {
            User user = userRepository.findById(memberid).orElseThrow();
            user.getConversations().add(newConversation);
            userRepository.save(user);
        }


        return true;
    }

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

    @PostMapping("api/messaging/sendMessage/{conversationId}/")
    public ResponseEntity<String> postMessageToConversation(@PathVariable Integer conversationId, @RequestBody SaveMessageDTO messageDTO) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        User sender = userRepository.findById(messageDTO.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Message message = new Message();
        message.setMessage(messageDTO.getMessage());
        message.setTimestamp(new Date());
        message.setSender(sender);
        conversation.getAllMessages().add(message);
        messageRepository.save(message);
        conversationRepository.save(conversation);
        return ResponseEntity.ok("Saved");
    }


}
