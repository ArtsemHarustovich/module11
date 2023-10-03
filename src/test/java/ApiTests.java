import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Execution(ExecutionMode.CONCURRENT)
public class ApiTests {
    String endpoint = "https://jsonplaceholder.typicode.com/users";

    @Test
    @DisplayName("Test Status code is 200")
    public void getStatusCode() {
        given()
        .when()
                .get(endpoint)
        .then()
                .assertThat()
                    .statusCode(200)
                    .log()
                    .body();
    }

    @Test
    @DisplayName("Test Header \"Content-Type\" exist")
    public void getResponseHeader() {
        given().
        when().
                get(endpoint).
        then().
                log().
                headers().
                assertThat().
                    statusCode(200).
                    header("Content-Type", equalTo("application/json; charset=utf-8"));
    }

    @Test
    @DisplayName("Test response body: size is 10")
    public void getResponseBody() {
        given().
        when().
                get(endpoint).
        then().
                log().
                body().
                assertThat().
                    statusCode(200).
                    body("size()", equalTo(10)).
                    body("id", everyItem(notNullValue()));
    }
}
