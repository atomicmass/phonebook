package za.co.sceoan.phonebook.resources;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import java.util.UUID;
import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.BeforeAll;
import za.co.sceoan.phonebook.dto.User;

@QuarkusTest
public class UserResourceTest {

    private static final String TEST_EMAIL = UUID.randomUUID() + "@test.com";
    private static final String PASSWORD = UUID.randomUUID().toString();
    
    @BeforeAll
    private static void beforeAll() {
        
    }
    
    @Test
    public void testSuccessfulRegister() {
        User u = new User(PASSWORD, TEST_EMAIL, "TEST");
        
        given()
          .when().post("/phonebook/v1/user", u)
          .then()
             .statusCode(200);
    }
    
    @Test
    public void testSuccessfulRetrieveUsers() {
        given()
          .when().get("/phonebook/v1/user")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

}