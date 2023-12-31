package sw_10.p3_backend.Controller;


import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
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
     * This method checks whether the passed AuthRequest fits with a user in the system and then creates a JWT if it does
     * @param authRequest contains a username and a password
     * @return a String that is either the bearer token or an error message
     */
    @PostMapping("/authenticate")
    public  ResponseEntity<String> authenticate(@RequestBody AuthRequest authRequest) {
        try {
            //Authenticate and return an Authentication object
            Authentication authentication = tokenLogic.authenticate(authRequest.username(), authRequest.password());

            //Generate a token from the Authentication object
            String token = tokenLogic.generateToken(authentication);
            return ResponseEntity.ok(token); // Return the bearer token with an OK status
        } catch (AuthenticationException e) {
            // Handle authentication failure
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
        }
        catch (Exception e) {
            // Handle any other errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal error: " + e.getMessage());
        }
    }

    private record AuthRequest(String username, String password) {
    }
}
