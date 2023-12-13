package sw_10.p3_backend.Logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import sw_10.p3_backend.Logic.TokenLogic;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TokenLogicTest {

    private JwtEncoder jwtEncoder;
    private AuthenticationManager authenticationManager;
    private TokenLogic tokenLogic;

    @BeforeEach
    public void setup() {
        jwtEncoder = Mockito.mock(JwtEncoder.class);
        authenticationManager = Mockito.mock(AuthenticationManager.class);
        tokenLogic = new TokenLogic(jwtEncoder, authenticationManager);
    }


    @Test
    public void generateTokenShouldReturnExpectedToken() {
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getName()).thenReturn("testUser");

        Jwt jwt = Jwt.withTokenValue("testToken").header("alg", "none").claim("sub", "testUser").build();
        when(jwtEncoder.encode(Mockito.any())).thenReturn(jwt);

        String token = tokenLogic.generateToken(authentication);

        assertEquals("testToken", token);
    }



    @Test
    public void authenticateShouldReturnExpectedAuthentication() {
        String username = "testUser";
        String password = "testPassword";

        Authentication authentication = Mockito.mock(Authentication.class);
        when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);

        Authentication result = tokenLogic.authenticate(username, password);

        assertEquals(authentication, result);
    }

    @Test
    public void authenticateShouldThrowExceptionWhenAuthenticationFails() {
        String username = "testUser";
        String password = "wrongPassword";

        when(authenticationManager.authenticate(Mockito.any())).thenThrow(new UsernameNotFoundException("User not found"));

        assertThrows(UsernameNotFoundException.class, () -> tokenLogic.authenticate(username, password));
    }
}