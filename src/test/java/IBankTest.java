import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class IBankTest {
    private final UserGenerator userGenerator = new UserGenerator();

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    // Активный пользователь, валидные логин/пароль
    void shouldTestValidActive() {
        UserData data = userGenerator.generateActiveUser();
        $("[name=login").setValue(data.getLogin());
        $("[name=password").setValue(data.getPassword());
        $("[data-test-id=action-login]").click();
        $(withText("Личный кабинет")).waitUntil(Condition.visible, 3000);
    }

    @Test
    // Заблокированный пользователь, логин/пароль валидные
    void shouldTestValidBlocked() {
        UserData data = userGenerator.generateBlockedUser();
        $("[name=login]").setValue(data.getLogin());
        $("[name=password").setValue(data.getPassword());
        $("[data-test-id=action-login]").click();
        boolean exists = $("[data-test-id=error-notification] .notification__title").exists();
    }
    @Test
    // Активный пользователь, невалидный логин
    void shouldTestActiveInvalidLogin() {
        UserData data = userGenerator.generateActiveUserInvalidLogin();
        $("[name=login]").setValue(data.getLogin());
        $("[name=password").setValue(data.getPassword());
        $("[data-test-id=action-login]").click();
        boolean exists = $("[data-test-id=error-notification] .notification__title").exists();
    }
    @Test
    // Активный пользователь, пароль невалиден
    void shouldTestActiveInvalidPassword() {
        UserData data = userGenerator.generateActiveUserInvalidPassword();
        $("[name=login]").setValue(data.getLogin());
        $("[name=password").setValue(data.getPassword());
        $("[data-test-id=action-login]").click();
        boolean exists = $("[data-test-id=error-notification] .notification__title").exists();
    }
    @Test
    // Блокированный пользователь, невалидный логин
    void shouldTestBlockedInvalidLogin() {
        UserData data = userGenerator.generateBlockedUserInvalidLogin();
        $("[name=login]").setValue(data.getLogin());
        $("[name=password").setValue(data.getPassword());
        $("[data-test-id=action-login]").click();
        boolean exists = $("[data-test-id=error-notification] .notification__title").exists();
    }
    @Test
    // Заблокированный пользователь, невалидный пароль
    void shouldTestBlockedInvalidPassword() {
        UserData data = userGenerator.generateBlockedUserInvalidPassword();
        $("[name=login]").setValue(data.getLogin());
        $("[name=password").setValue(data.getPassword());
        $("[data-test-id=action-login]").click();
        boolean exists = $("[data-test-id=error-notification] .notification__title").exists();
    }
}