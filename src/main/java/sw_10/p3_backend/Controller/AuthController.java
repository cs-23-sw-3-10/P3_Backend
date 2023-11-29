package sw_10.p3_backend.Controller;


import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import sw_10.p3_backend.Logic.TokenLogic;

@Controller
public class AuthController {
    private final TokenLogic tokenLogic;


    public AuthController(TokenLogic tokenLogic) {
        this.tokenLogic = tokenLogic;
    }

    @Secured("ROLE_USER")
    @QueryMapping
    public String authenticate(Authentication authentication) {

        return tokenLogic.generateToken(authentication);
    }
}
