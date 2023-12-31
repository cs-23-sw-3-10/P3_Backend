package sw_10.p3_backend.Controller;

import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import sw_10.p3_backend.Logic.TokenLogic;

@Testcontainers
//@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@DirtiesContext
class AuthControllerTest {

    @Autowired
    private TokenLogic tokenLogic;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    AuthenticationManager authenticationManager;

    @Test
    void testAuthenticateSuccess() throws AuthenticationException {
        // Prepare request data
        String username = "user";
        String password = "password";

        // Create headers and set Content-Type to application/json
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the request body as a JSON string
        String requestBody = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";

        // Create an HttpEntity object with the request body and headers
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Perform the request and assert the response body
        ResponseEntity<String> response = restTemplate.exchange("/authenticate", HttpMethod.POST, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

    }

    @Test
    void testAuthenticateNoHeader() throws AuthenticationException {
        String username = "user";
        String password = "passwor";

        // Create headers and set Content-Type to application/json
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Perform the request and assert the response body
        ResponseEntity<String> response = restTemplate.exchange("/authenticate", HttpMethod.POST, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();

    }

    @Test
    void testAuthenticateUnauthorized() throws AuthenticationException {
        String username = "user";
        String password = "passwor";

        // Create headers and set Content-Type to application/json
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the request body as a JSON string
        String requestBody = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        // Perform the request and assert the response body
        //ResponseEntity<String> response = restTemplate.exchange("/authenticate", HttpMethod.POST, entity, String.class);

        //assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        //assertThat(response.getBody()).isNull();
    }
}