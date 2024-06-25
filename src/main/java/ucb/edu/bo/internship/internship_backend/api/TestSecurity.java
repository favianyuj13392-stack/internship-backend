package ucb.edu.bo.internship.internship_backend.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ucb.edu.bo.internship.internship_backend.util.KeycloakProvider;

@RestController
@RequestMapping("/test")
public class TestSecurity {

    @GetMapping("/public")
    public String publicEndpoint() {
        KeycloakProvider.getRealmResource();
        return "This is a public endpoint";
    }

    @GetMapping("/admin")
    public String adminEndpoint() {
        return "This is an admin endpoint";
    }

    @GetMapping("/student")
    public String userEndpoint() {
        return "This is a student endpoint";
    }

    @GetMapping("/business")
    public String businessEndpoint() {
        return "This is a business endpoint";
    }

}
