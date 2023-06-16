package com.wia1002g3.friendbook.mapping;

import com.wia1002g3.friendbook.entity.Post;
import com.wia1002g3.friendbook.entity.User;
import com.wia1002g3.friendbook.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;


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
        userProfileDTO.setHobbies(user.getHobbies().toString());
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
        private Boolean gender;
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
        user.setHobbies(updatedUser.getHobbiesAsArrayList());
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
        private Boolean gender;
        private String bio;
        private String hobbies;
        private String relationStatus;

        private ArrayList<String> getHobbiesAsArrayList() {
            String[] hobbyArr = hobbies.split(",");
            ArrayList<String> hobbiesVector = new ArrayList<>();
            for(int i = 0; i < hobbyArr.length; i++) {
                hobbiesVector.add(hobbyArr[i]);
            }
            return hobbiesVector;
        }

    }

    @PostMapping("api/UserProfile/addjob/{userId}/{newJob}")
    public boolean addJob(@PathVariable Integer userId, @PathVariable String newJob) {
            User user = userRepository.findById(userId).orElseThrow();
            List<String> jobs = user.getJobExperiences();
            jobs.add(newJob);
            userRepository.save(user);
            return true;
        }

    @GetMapping("api/UserProfile/hobbylist/")
    public ArrayList<String> getHobbylist(){
        ArrayList<String> hobbies = new ArrayList<>();

        //List of allowed Hobbies
        hobbies.add("Reading");
        hobbies.add("Writing");
        hobbies.add("Painting");
        hobbies.add("Photography");
        hobbies.add("Cooking");
        hobbies.add("Gardening");
        hobbies.add("Playing a musical instrument");
        hobbies.add("Singing");
        hobbies.add("Dancing");
        hobbies.add("Yoga");
        hobbies.add("Meditation");
        hobbies.add("Collecting items");
        hobbies.add("Knitting");
        hobbies.add("Woodworking");
        hobbies.add("DIY crafts");
        hobbies.add("Sports");
        hobbies.add("Hiking");
        hobbies.add("Fishing");
        hobbies.add("Birdwatching");
        hobbies.add("Home brewing");
        hobbies.add("Board games");
        hobbies.add("Video gaming");
        hobbies.add("Learning a new language");
        hobbies.add("Volunteering");
        hobbies.add("Model building");
        hobbies.add("Coin collecting");
        hobbies.add("Surfing");
        hobbies.add("Pottery");
        hobbies.add("Scrapbooking");
        hobbies.add("Martial arts");
        hobbies.add("Traveling");
        return hobbies;
    }
}
