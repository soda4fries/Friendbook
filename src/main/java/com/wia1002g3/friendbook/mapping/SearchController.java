package com.wia1002g3.friendbook.mapping;

import com.wia1002g3.friendbook.DTOs.SearchTermFuzzy;
import com.wia1002g3.friendbook.entity.User;
import com.wia1002g3.friendbook.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class SearchController {
    private final UserRepository userRepository;

    @AllArgsConstructor
    @Data
    public static class SearchResultDTO implements Comparable<SearchResultDTO> {
        public String userName;
        public String bio;
        public String fullName;
        public Integer userID;

        SearchResultDTO(String userName, String bio, String firstName, String lastName, Integer userID) {
            this.fullName = firstName + " " + lastName;
            this.userName = userName;
            this.bio = bio;
            this.userID = userID;
        }

        @Override
        public int compareTo(SearchResultDTO o) {
            return this.fullName.compareTo(o.fullName);
        }
    }

    @PostMapping("api/Search/keyterm/{searchterm}")
    public ResponseEntity<ArrayList<SearchResultDTO>> findByUsernameNameContactNumber(@PathVariable String searchterm) {
        ArrayList<User> matches = userRepository.findAllByUsernameContainingOrFirstNameContainingOrLastNameContaining(searchterm, searchterm, searchterm);
        ArrayList<SearchResultDTO> Result= new ArrayList<>();
        for (User user : matches) {
            SearchResultDTO match = new SearchResultDTO(user.getUsername(), user.getBio(), user.getFirstName(), user.getLastName(), user.getId());
            Result.add(match);
        }

        Collections.sort(Result);
        return ResponseEntity.ok(Result);
    }

    @PostMapping("api/Search/UserNamePhoneEmail/")
    public ResponseEntity<ArrayList<SearchResultDTO>> findByUsernameNameContactNumberEmail(@RequestBody SearchTermFuzzy request) {
        List<User> users = userRepository.findAll();
        ArrayList<User> matches = new ArrayList<>();
        for (User user : users) {
            String userInfo = user.getUsername() + user.getPhoneNumber() + user.getEmail();
            if(userInfo.contains(request.getSearchterm())) {
                matches.add(user);
            }
        }

        ArrayList<SearchResultDTO> Result= new ArrayList<>();
        for (User user : matches) {
            SearchResultDTO match = new SearchResultDTO(user.getUsername(), user.getBio(), user.getFirstName(), user.getLastName(), user.getId());
            Result.add(match);
        }

        Collections.sort(Result);
        return ResponseEntity.ok(Result);
    }




    //fuzzy search
    @PostMapping("api/Search/BioFuzzySearch/")
    public ResponseEntity<ArrayList<SearchResultDTO>> findFuzzyBio(@RequestBody SearchTermFuzzy request) {
        ArrayList<User> matches= userRepository.searchByBio(request.getSearchterm());
        ArrayList<SearchResultDTO> Result= new ArrayList<>();
        for (User user : matches) {
            SearchResultDTO match = new SearchResultDTO(user.getUsername(), user.getBio(), user.getFirstName(), user.getLastName(), user.getId());
            Result.add(match);
        }
        return ResponseEntity.ok(Result);
    }


}
