package shortflow;

import client.UserClient;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.UserData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pageobject.user.LoginPage;
import pageobject.main.MainPage;
import pageobject.user.RegisterPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.webdriver;
import static org.junit.Assert.*;

public class RegisterTest {

    private UserData userData;
    private MainPage mainPage;

    @Before
    @Step("Set up test data")
    public void setUp(){
        userData = UserData.getRandomRegisterData();

        Configuration.pageLoadTimeout = 6000;
        mainPage = Selenide.open(MainPage.URL, MainPage.class);
        webdriver().driver().getWebDriver().manage().window().maximize();
    }

    @After
    @Step("Clean cookies and Local Storage")
    public void clean(){
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        Selenide.webdriver().driver().close(); //нужно для закрытия яндекс браузера
    }

    @Test
    @DisplayName("Register user forward to login page")
    @Description("Button 'Enter to account' -- Correct user registration and check the transition to the login page and check that user exists in the system")
    public void registerUserThrowBntAccForwardToLoginPage(){
        LoginPage page = mainPage
                .clickBtnAccount()
                .clickRegister()
                .fillRegisterForm(userData)
                .clickBtnRegister();

        checkLoginPage(page);
        checkSuccessUserRegistration(userData);
    }

    @Test
    @DisplayName("Register user forward to login page -")
    @Description("Link 'Personal Account' in the Header -- Correct user registration and check the transition to the login page and check that user exists in the system")
    public void registerUserThrowLinkAccForwardToLoginPage(){
        LoginPage page = mainPage
                .clickAccountLinkNoAuthorized()
                .clickRegister()
                .fillRegisterForm(userData)
                .clickBtnRegister();

        checkLoginPage(page);
        checkSuccessUserRegistration(userData);
    }

    @Test
    @DisplayName("Get error when register user with 5 symbol password")
    @Description("Try to register user with password less than 6 characters has to return error")
    public void tryToRegisterUserWithPasswordLessThanSixCharactersReturnError(){
        userData.setPassword("12345");

        RegisterPage errorRegister = mainPage
                .clickBtnAccount()
                .clickRegister()
                .fillRegisterForm(userData)
                .clickIncorrectRegister();

        SelenideElement error = errorRegister.getErrorPassword().shouldBe(visible);
        check(error);
    }

    @Test
    @DisplayName("Get error when register user with someone's else email")
    @Description("Register one user and try to register new user with the first user's email has to return error")
    public void tryToRegisterUserWithSomeonesElseEmailReturnError(){
        String token = new UserClient().register(userData).extract().path("accessToken");

        RegisterPage errorRegister = mainPage
                .clickAccountLinkNoAuthorized()
                .clickRegister()
                .fillRegisterForm(userData)
                .clickIncorrectRegister();

        SelenideElement error = errorRegister.getErrorUser().shouldBe(visible);
        check(error);
        clean(token);
    }

    //Проверить, что наc вернуло на страницу логина
    @Step("Check the login page")
    public void checkLoginPage(LoginPage page){
        page.getLoginFormHeader().shouldBe(visible);
        assertTrue("После успешной регистрации мы должны были увидеть заголовок формы авторизации 'Вход' оказавшись на странице логина", page.getLoginFormHeader().isDisplayed());
    }

    //Проверить что пользователь появился в системе
    @Step("Check success user registration - users exists in the system")
    public void checkSuccessUserRegistration(UserData userData){
        UserClient userClient = new UserClient();
        ValidatableResponse response = userClient.login(userData);
        String token = response.extract().path("accessToken");

        assertEquals("В ответе вернулся некорретный код состояния на попытку залогиться", 200, response.extract().statusCode());
        assertTrue("Поле success в ответе на попытку залогиться должно быть true", response.extract().path("success"));
        assertFalse("В ответе вернулся пустой accessToken", token.isBlank());
        clean(token);
    }

    //Проверяем, что на странице появилось сообщение об ошибке
    @Step("Check error")
    public void check(SelenideElement error){
        assertTrue("На экране должно было появиться сообщение об ошибке", error.isDisplayed());
     }

    //Чистим тестовый данные
    @Step("Clean test data")
    public void clean(String token){
        new UserClient().delete(token);
    }
}
