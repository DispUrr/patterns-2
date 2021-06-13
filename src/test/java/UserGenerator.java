import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class UserGenerator {

    private static final Faker faker = new Faker(new Locale("ru"));
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    static void makeRequest(UserData userData) {
        given()
                .spec(requestSpec)
                .body(userData)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static UserData generateActiveUser() {
        UserData userData = new UserData(
                faker.name().firstName(),
                faker.internet().password(),
                Status.active);
        makeRequest(userData);
        return userData;
    }

    public static UserData generateBlockedUser() {
        UserData userData = new UserData(
                faker.name().firstName(),
                faker.internet().password(),
                Status.blocked);
        makeRequest(userData);
        return userData;
    }

    public static UserData generateActiveUserInvalidLogin() {
        String password = faker.internet().password(); // сгенерированный пароль верный
        UserData userData = new UserData(
                faker.name().firstName(),
                password,
                Status.active);
        makeRequest(userData);
        return new UserData(faker.name().firstName(), password, Status.active);
    }

    public static UserData generateActiveUserInvalidPassword() {
        String login = faker.name().firstName();
        UserData userData = new UserData(
                login,
                faker.internet().password(),
                Status.active);
        makeRequest(userData);
        return new UserData(login, faker.internet().password(), Status.active);
    }

    public static UserData generateBlockedUserInvalidLogin() {
        String password = faker.internet().password();
        UserData userData = new UserData(
                faker.name().firstName(),
                password,
                Status.blocked);
        makeRequest(userData);
        return new UserData(faker.name().firstName(), password, Status.active);
    }

    public static UserData generateBlockedUserInvalidPassword() {
        String login = faker.name().firstName();
        UserData userData = new UserData(
                login,
                faker.internet().password(),
                Status.blocked);
        makeRequest(userData);
        return new UserData(login, faker.internet().password(), Status.active);
    }

}