package client;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import model.UserData;

import static io.restassured.RestAssured.given;

public class UserClient {

    private final String AUTH = "/auth";
    private final String REGISTER = AUTH + "/register";
    private final String LOGIN = AUTH + "/login";
    private final String USER = AUTH + "/user";

    protected String URL = "https://stellarburgers.nomoreparties.site/api";

    protected RequestSpecification reqSpec() {
        return given()
                .log().all()
                .header("Content-Type", "application/json")
                .baseUri(URL);
    }

    public ValidatableResponse register(UserData user) {
        return reqSpec()
                .body(user)
                .when()
                .post(REGISTER)
                .then().log().all();
    }

    public ValidatableResponse login(UserData user) {
        return reqSpec()
                .body(user)
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    public void delete(String token) {
        reqSpec()
                .header("Authorization", token)
                .when()
                .delete(USER)
                .then().log().all()
                .assertThat()
                .statusCode(202)
                .extract()
                .path("success");
    }
}
