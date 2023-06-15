package com.wia1002g3.friendbook.mapping;

import com.wia1002g3.friendbook.entity.User;
import com.wia1002g3.friendbook.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserProfileService {
    private final UserRepository userRepository;

    @GetMapping("api/UserProfile/{userId}/getProfile")
    public ResponseEntity<UserProfileDTO> getUserProfile(@PathVariable Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 Not Found
        }

        User user = userOptional.get();

        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setUsername(user.getUsername());
        userProfileDTO.setFirstName(user.getFirstName());
        userProfileDTO.setLastName(user.getLastName());
        userProfileDTO.setAge(user.getAge());
        userProfileDTO.setAddress(user.getAddress());
        userProfileDTO.setPhoneNumber(user.getPhoneNumber());
        userProfileDTO.setGender(user.getGender());
        userProfileDTO.setBio(user.getBio());
        userProfileDTO.setHobbies(user.getHobbies());
        userProfileDTO.setRelationStatus(user.getRelationStatus());
        return ResponseEntity.ok(userProfileDTO);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class UserProfileDTO {
        private String username;
        private String firstName;
        private String lastName;
        private Integer age;
        private String address;
        private String phoneNumber;
        private String gender;
        private String bio;
        private String hobbies;
        private String relationStatus;
    }

    @PostMapping("api/UserProfile/{userId}/editProfile")
    public boolean updateUserProfile(@PathVariable Integer userId, @RequestBody UserEditReq updatedUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update the user profile information with the provided values
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setAge(updatedUser.getAge());
        user.setAddress(updatedUser.getAddress());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        user.setGender(updatedUser.getGender());
        user.setBio(updatedUser.getBio());
        user.setHobbies(updatedUser.getHobbies());
        user.setRelationStatus(updatedUser.getRelationStatus());

        // Save the updated user object
        userRepository.save(user);

        return true;
    }

    @Data
    private class UserEditReq {
        private String firstName;
        private String lastName;
        private Integer age;
        private String address;//research geospatial tools
        private String phoneNumber;
        private String gender;
        private String bio;
        private String hobbies;
        private String relationStatus;
        private Object partner;

    }
}
