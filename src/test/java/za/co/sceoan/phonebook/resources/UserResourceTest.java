package za.co.sceoan.phonebook.resources;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class UserResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/C:/FNB/Tools/cmder/vendor/git-for-windows/user")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

}