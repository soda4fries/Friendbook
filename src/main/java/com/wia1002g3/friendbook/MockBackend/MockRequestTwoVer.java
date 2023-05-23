package com.wia1002g3.friendbook.MockBackend;


import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockRequestTwoVer {

    @PostMapping("/testing/")
    public ResponseEntity<Object> processTexts(@RequestBody YourRequestObject requestObject) {
        String variable1 = requestObject.getVariable1();
        String variable2 = requestObject.getVariable2();

        // Process the variables and prepare the response
        String serverResponse = "Received: " + variable1 + ", " + variable2;

        // Create a response object with the server response
        YourResponseObject responseObject = new YourResponseObject(serverResponse);

        // Return the response with HTTP status 200 (OK)
        return ResponseEntity.ok(responseObject);
    }

    private static class YourRequestObject {
        private String variable1;
        private String variable2;

        public String getVariable1() {
            return variable1;
        }

        public String getVariable2() {
            return variable2;
        }
    }



    private static class YourResponseObject {
        private String message;

        public YourResponseObject(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

}
