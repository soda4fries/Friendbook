package com.wia1002g3.friendbook.mapping;

import com.wia1002g3.friendbook.entity.Notification;
import com.wia1002g3.friendbook.entity.User;
import com.wia1002g3.friendbook.repository.NotificationRepository;
import com.wia1002g3.friendbook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    @GetMapping("/api/notifications/{userId}/")
    public List<Notification> getUserNotifications(@PathVariable Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getNotifications();
    }

    @PostMapping("/api/notifications/{userid1}/{userid2}/")
    public ResponseEntity<String> SendFriendRequest(@PathVariable Integer userid1, @PathVariable Integer userid2) {
        User user1 = userRepository.findById(userid1)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User user2 = userRepository.findById(userid2)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification newNotification = new Notification();
        newNotification.setMessage("userID:" + userid1.toString() + ",userName:" + user1.getUsername().toString());
        newNotification.setType("FRIEND_REQ");
        user2.getNotifications().add(newNotification);
        notificationRepository.save(newNotification);
        userRepository.save(user2);
        return ResponseEntity.ok("Saved");
    }


}
