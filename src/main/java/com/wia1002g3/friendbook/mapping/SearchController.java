package com.wia1002g3.friendbook.mapping;

import com.wia1002g3.friendbook.entity.User;
import com.wia1002g3.friendbook.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.ArrayList;
import java.util.Collections;

@RequiredArgsConstructor
public class SearchController {
    private final UserRepository userRepository;

    @AllArgsConstructor
    public static class SearchResultDTO implements Comparable<SearchResultDTO> {
        String userName;
        String bio;
        public String fullName;

        SearchResultDTO(String userName, String bio, String firstName, String lastName) {
            this.fullName = firstName + " " + lastName;
            this.userName = userName;
            this.bio = bio;
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
            SearchResultDTO match = new SearchResultDTO(user.getUsername(), user.getBio(), user.getFirstName(), user.getLastName());
            Result.add(match);
        }

        Collections.sort(Result);
        return ResponseEntity.ok(Result);
    }

    @PostMapping("api/Search/BioFuzzySearch/{searchterm}")
    public ResponseEntity<ArrayList<SearchResultDTO>> findFuzzyBio(@PathVariable String searchterm) {
        ArrayList<User> matches= userRepository.searchByBio(searchterm);
        ArrayList<SearchResultDTO> Result= new ArrayList<>();
        for (User user : matches) {
            SearchResultDTO match = new SearchResultDTO(user.getUsername(), user.getBio(), user.getFirstName(), user.getLastName());
            Result.add(match);
        }
        return ResponseEntity.ok(Result);
    }
}
