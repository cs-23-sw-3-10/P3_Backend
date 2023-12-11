package sw_10.p3_backend.Controller;


import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sw_10.p3_backend.Logic.TokenLogic;

@Controller
public class AuthController {
    private final TokenLogic tokenLogic;


    public AuthController(TokenLogic tokenLogic) {
        this.tokenLogic = tokenLogic;
    }


    /**
     *
     * @param authRequest
     * @return a String that is either the bearer token or an error message
     */
    @PostMapping("/authenticate")
    public  ResponseEntity<String> authenticate(@RequestBody AuthRequest authRequest) {
        System.out.println("Authenticating");
        try {
            //Authenticate and return an Authentication object
            Authentication authentication = tokenLogic.authenticate(authRequest.username(), authRequest.password());

            //Generate a token from the Authentication object
            String token = tokenLogic.generateToken(authentication);
            System.out.println("Token: " + token);
            return ResponseEntity.ok(token); // Return the bearer token with an OK status
        } catch (AuthenticationException e) {
            // Handle authentication failure
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
        }
    }

    private record AuthRequest(String username, String password) {
    }
}
