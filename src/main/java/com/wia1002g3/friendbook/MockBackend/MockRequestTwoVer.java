package com.wia1002g3.friendbook.MockBackend;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockRequestTwoVer {

    @PostMapping("/testing/")
    public ResponseEntity<BackEndResponse> processTexts(@RequestBody FrontEndReq requestObject) {
        String variable1 = requestObject.getVariable1();
        String variable2 = requestObject.getVariable2();

        // Process the variables and prepare the response
        String serverResponse = "Received: " + variable1 + ", " + variable2;

        // Create a response object with the server response
        BackEndResponse responseObject = new BackEndResponse(serverResponse);

        // Return the response with HTTP status 200 (OK)
        return ResponseEntity.ok(responseObject);
    }

    private static class FrontEndReq {
        private String variable1;
        private String variable2;

        public String getVariable1() {
            return variable1;
        }

        public String getVariable2() {
            return variable2;
        }
    }




    private static class BackEndResponse {
        private String message; //we will use response.message in jquery to retrieve this
        public int WillbeIgnored = 56;
        public BackEndResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

}
