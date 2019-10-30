package za.co.sceoan.phonebook.resources;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;

@QuarkusTest
public class UserResourceTest {

    private static final String EMAIL = UUID.randomUUID() + "@test.com";
    private static final String PASSWORD = UUID.randomUUID().toString();
    private static final String NAME = "TEST";
    private static final String USER = "{\"email\":\"" + EMAIL + "\",\"password\":\"" + PASSWORD + "\",\"name\":\"" + NAME + "\"}";

    @BeforeEach
    public void register() {
        given()
                .when()
                .contentType("application/json")
                .body(USER)
                .post("/phonebook/api/v1/user");
    }

    @Test
    public void login() {
        given()
                .contentType("application/json")
                .body(USER)
                .when().post("/phonebook/api/v1/user/login")
                .then()
                .statusCode(200);
    }
    
    @Test
    public void failGetAll() {
        given()
                .auth()
                .preemptive()
                .basic(EMAIL, PASSWORD)
                .contentType("application/json")
                .body(USER)
                .when().get("/phonebook/api/v1/user")
                .then()
                .statusCode(403); //not authorised
    }
    
    @Test
    public void successGetAll() {
        //TODO - better wway to handle admin user for this test
        given()
                .auth()
                .preemptive()
                .basic("sceoan@gmail.com", "bleh")
                .contentType("application/json")
                .body(USER)
                .when().get("/phonebook/api/v1/user")
                .then()
                .statusCode(200); 
    }

}
