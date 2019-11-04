package za.co.sceoan.phonebook.resources;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import java.util.UUID;
import java.util.logging.Logger;
import static org.hamcrest.CoreMatchers.containsString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PhoneBookResourceTest {
    private static final Logger LOG = Logger.getLogger(PhoneBookResourceTest.class.getName());
    
    private static final String EMAIL = UUID.randomUUID() + "@test.com";
    private static final String PASSWORD = UUID.randomUUID().toString();
    private static final String NAME = "TEST";
    private static final String CONTACT = "{\"name\": \"test\", \"email\": \"test@test.com\", \"phone\": \"0115555555\"}";
    private static final String USER = "{\"email\":\"" + EMAIL + "\",\"password\":\"" + PASSWORD + "\",\"name\":\"" + NAME + "\"}";
    
    private int contactId;

    @BeforeEach
    public void register() {
        given()
                .when()
                .contentType("application/json")
                .body(USER)
                .post("/phonebook/api/v1/user");
    }
    
    private void addContact() {
        given()
                .auth()
                .preemptive()
                .basic(EMAIL, PASSWORD)
                .contentType("application/json")
                .body(CONTACT)
                .when().post("/phonebook/api/v1/phonebook")
                .then()
                .statusCode(200);
        
        contactId = given()
                .auth()
                .preemptive()
                .basic(EMAIL, PASSWORD)
                .when().get("/phonebook/api/v1/phonebook/search?s=test@test.com")
                .then()
                .statusCode(200)
                .assertThat()
                .body(containsString("\"test\""))
                .extract()
                .body()
                .jsonPath().get("[0].id");
    }
    
    private void deleteContact() {
        LOG.info("Cont  " + contactId);
        given()
                .auth()
                .preemptive()
                .basic(EMAIL, PASSWORD)
                .contentType("application/json")
                .when()
                .delete("/phonebook/api/v1/phonebook/" + contactId)
                .then()
                .statusCode(200);
    }
    
    
    @Test
    public void postContact() {
        addContact();
        deleteContact();
    }
    
    @Test
    public void search() {
        addContact();
        given()
                .auth()
                .preemptive()
                .basic(EMAIL, PASSWORD)
                .when().get("/phonebook/api/v1/phonebook/search?s=test")
                .then()
                .statusCode(200)
                .assertThat()
                .body(containsString("\"test\""));
        deleteContact();
    }
    
    @Test
    public void getInitials() {
        addContact();
        given()
                .auth()
                .preemptive()
                .basic(EMAIL, PASSWORD)
                .when().get("/phonebook/api/v1/phonebook/initial")
                .then()
                .statusCode(200)
                .assertThat()
                .body(containsString("\"T\""));
        deleteContact();
    }
    
    @Test
    public void getByInitial() {
        addContact();
        given()
                .auth()
                .preemptive()
                .basic(EMAIL, PASSWORD)
                .when().get("/phonebook/api/v1/phonebook/initial/t")
                .then()
                .statusCode(200)
                .assertThat()
                .body(containsString("\"test\""));
        deleteContact();
    }
    
    @Test
    public void getAllContacts() {
        addContact();
        given()
                .auth()
                .preemptive()
                .basic(EMAIL, PASSWORD)
                .when().get("/phonebook/api/v1/phonebook")
                .then()
                .statusCode(200)
                .assertThat()
                .body(containsString("\"test\""));
        deleteContact();
    }
}
