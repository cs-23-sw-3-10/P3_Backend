package sw_10.p3_backend.Controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import sw_10.p3_backend.Logic.TokenLogic;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenLogic tokenLogic;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void testAuthenticateSuccess() throws Exception {
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
}