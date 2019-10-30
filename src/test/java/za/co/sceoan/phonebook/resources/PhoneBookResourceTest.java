package za.co.sceoan.phonebook.resources;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class PhoneBookResourceTest {
    private static final String EMAIL = UUID.randomUUID() + "@test.com";
    private static final String PASSWORD = UUID.randomUUID().toString();
    private static final String NAME = "TEST";
    private static final String CONTACT = "{name: \"test\", email: \"test@test.com\", phone: \"0115555555\"}";
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
    public void addContact() {
        given()
                .auth()
                .preemptive()
                .basic(EMAIL, PASSWORD)
                .contentType("application/json")
                .body(CONTACT)
                .when().get("/phonebook/api/v1/phonebook")
                .then()
                .statusCode(200);
        
        
    }
}
