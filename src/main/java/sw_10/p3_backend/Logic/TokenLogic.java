package sw_10.p3_backend.Logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TokenLogic {
    private final JwtEncoder jwtEncoder;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public TokenLogic(JwtEncoder jwtEncoder, AuthenticationManager authenticationManager) {
        this.jwtEncoder = jwtEncoder;
        this.authenticationManager = authenticationManager;
    }

    /**
     * This method generates a JSON Web Token (JWT)
     * @param authentication a Spring object type
     * @return a JWT token
     */
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();// Create a JWT Claims Set instance with issuer, timestamp, subject, and scope claims
        String scope = authentication.getAuthorities().stream()// Create a space-separated string of authorities
                .map(GrantedAuthority::getAuthority)// Create a space-separated string of authorities from the authentication object
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")// Set the issuer to self (the authorization server)
                .issuedAt(now) // Set issued at to now (the current timestamp)
                .expiresAt(now.plus(1, ChronoUnit.HOURS)) // Set the expiration to 1 hour from now
                .subject(authentication.getName())// Set the subject to the user name from the authentication object
                .claim("scope", scope) // Set the scope to the space-separated string of authorities from the authentication object
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(); // Encode the claims set into a JWT and return it as a string (the token value)
    }


    /**
     * This method authenticates a user against the registered users in the system
     * @param username
     * @param password
     * @return a Authentization Spring object
     * @throws AuthenticationException
     */
    public Authentication authenticate(String username, String password) throws AuthenticationException {
        try {
            // Authenticate and return an Authentication object
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return authentication;
        } catch (AuthenticationException e) {
            // Handle authentication failure
            throw e;
        }
        catch (Exception e) {
            // Handle any other errors
            throw e;
        }
    }
}
