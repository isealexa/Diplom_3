package shortflow;

import client.UserClient;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import model.UserData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pageobject.main.MainPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.webdriver;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginTest {

    private UserData userData;
    private UserClient userClient;
    private String token;
    private MainPage mainPage;

    @Before
    @Step("Set up test data")
    public void setUp(){
        userData = UserData.getRandomRegisterData();
        userClient = new UserClient();
        token = userClient.register(userData).extract().path("accessToken");

        Configuration.pageLoadTimeout = 6000;
        mainPage = Selenide.open(MainPage.URL, MainPage.class);
        webdriver().driver().getWebDriver().manage().window().maximize();
    }

    @After
    @Step("Clean test data, cookies and Local Storage")
    public void clean(){
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        userClient.delete(token);
    }

    @Test
    @DisplayName("Login existent user from the Main page should be success and the order button")
    @Description("Button 'Enter to account' - Get random created user data, go to the login page, login and check changing the enter to account button to the order button in the Main page")
    public void loginUserThrowBntAccShouldBeBtnOrderVisible(){
        MainPage login = mainPage
                .clickBtnAccount()
                .fillLoginForm(userData)
                .clickBtnLogin();

        checkButtons(login);
    }

    @Test
    @DisplayName("Login existent user from the Header should be success and the order button")
    @Description("Link 'Personal Account' in the Header - Get random created user data, go to the login page, login and check changing the enter to account button to the order button in the Main page")
    public void loginUserThrowLinkAccShouldBeBtnOrderVisible(){
        MainPage login = mainPage
                .clickAccountLinkNoAuthorized()
                .fillLoginForm(userData)
                .clickBtnLogin();

        checkButtons(login);
    }

    @Test
    @DisplayName("Login existent user from the Register Page should be success and the order button")
    @Description("Link 'Enter' in the Register Page - Get random created user data, go to the login page, login and check changing the enter to account button to the order button in the Main page")
    public void loginUserThrowEnterLinkShouldBeBtnOrderVisible(){
        MainPage login = mainPage
                .clickAccountLinkNoAuthorized()
                .clickRegister()
                .clickEnterLink()
                .fillLoginForm(userData)
                .clickBtnLogin();

        checkButtons(login);
    }

    @Test
    @DisplayName("Login existent user from the Forgot Password Page should be success and the order button")
    @Description("Link 'Enter' in the Forgot Password Page - Get random created user data, go to the login page, login and check changing the enter to account button to the order button in the Main page")
    public void loginUserThrowEnterLinkAtForgotPasswordPageShouldBeBtnOrderVisible(){
        MainPage login = mainPage
                .clickBtnAccount()
                .clickForgotPasswordLink()
                .clickEnterLink()
                .fillLoginForm(userData)
                .clickBtnLogin();

        checkButtons(login);
    }

    //Проверить, что на главной странице вместо кнопки войти в аккаунт появилась кнопка оформить заказ
    @Step("Check changing the enter to account button to the order button in the Main page")
    public void checkButtons(MainPage page){
        assertFalse("Для авторизованного пользователя не должно быть видно кнопку Войти в аккаунт", page.getBtnAccount().is(visible));
        assertTrue("Для авторизованного пользователя должна была появиться кнопка Оформить заказ", page.getBtnOrder().shouldBe(visible).is(visible));
    }
}
