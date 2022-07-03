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
import pageobject.user.AccountPage;
import pageobject.user.LoginPage;
import pageobject.main.MainPage;

import java.util.Locale;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.webdriver;
import static org.junit.Assert.*;

public class GoToAccountAndExitTest {

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
        MainPage openMainPage = Selenide.open(MainPage.URL, MainPage.class);
        webdriver().driver().getWebDriver().manage().window().maximize();
        mainPage = openMainPage
                .clickBtnAccount()
                .fillLoginForm(userData)
                .clickBtnLogin();
    }

    @After
    @Step("Clean test data, Cookies and Local Storage")
    public void clean(){
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        userClient.delete(token);
    }

    @Test
    @DisplayName("Going to account via a link in the header should show the user's data")
    @Description("Link 'Personal Account' in the Header' - Get random created and authorized user and go to the account page check the account page")
    public void goToAccountThrowLinkAtHeaderShouldBeUserDataVisible(){
        AccountPage page = mainPage
                .clickAccountLink();

        checkProfilePage(page, userData);
    }

    @Test
    @DisplayName("Click on the exit button should logout and show the login page ")
    @Description("Get random created and authorized user, click on the exit button and check transition to the login page and check that the enter to account button is visible now")
    public void clickExitBtnShouldLogoutAndShowLoginPage(){
        checkButtons(mainPage, true);

        AccountPage account = mainPage
                .clickAccountLink();
        assertTrue("Не отображается кнопка выход", account.getBtnExit().shouldBe(visible).is(visible));

        LoginPage logout = account.clickBtnExit();
        checkLoginPage(logout);

        MainPage mainPageAfterExit = logout.clickLogo();
        checkButtons(mainPageAfterExit, false);
    }

    //Проверить, что на странице профиля верно отображаются данные пользователя
    @Step("Check the profile page")
    public void checkProfilePage(AccountPage page, UserData userData){
        assertTrue("Поле имя не отображается",  page.getName().shouldBe(visible).is(visible));
        assertTrue("Поле логин не отображается",  page.getEmail().shouldBe(visible).is(visible));
        assertTrue("Поле пароль не отображается",  page.getPassword().shouldBe(visible).is(visible));
        assertTrue("Не отображается вкладка история заказов",  page.getHistory().shouldBe(visible).is(visible));
        assertTrue("Не отображается кнопка выход",  page.getBtnExit().shouldBe(visible).is(visible));

        assertEquals("В поле имя отображается некорректное значение", userData.getName(), page.getName().getValue());
        assertEquals("В поле логин отображается некорректное значение", userData.getEmail().toLowerCase(Locale.ROOT), page.getEmail().getValue());
        assertEquals("В поле пароль отображается некорректное значение","*****", page.getPassword().getValue());
    }

    //Проверить, что по клику выход произошёл переход на страницу авторизации
    @Step("Check the login page")
    public void checkLoginPage(LoginPage page){
        assertTrue("После выхода из аккаунта на странице", page.getLoginFormHeader().shouldBe(visible).is(visible));
    }

    //Проверить, что на главной странице
    // для авторизованного пользователя вместо кнопки Зайти в аккаунт видна кнопка Оформить заказ
    // для разлогиненного пользователя вместо кнопки Оформить заказ теперь кнопка Зайти в аккаунт
    @Step("Check changing the order button is visible for authorized user and the enter to account button is visible for no authorized user in the Main page")
    public void checkButtons(MainPage page, boolean authorized){
        if (authorized) {
            assertFalse("Для авторизованного пользователя не должно быть видно кнопку Войти в аккаунт", page.getBtnAccount().is(visible));
            assertTrue("Для авторизованного пользователя должна была появиться кнопка Оформить заказ", page.getBtnOrder().shouldBe(visible).is(visible));
        } else {
            assertFalse("Для не авторизованного пользователя не должно быть видно кнопку Оформить заказ", page.getBtnOrder().is(visible));
            assertTrue("Для не авторизованного пользователя должна была появиться кнопка Войти в аккаунт", page.getBtnAccount().shouldBe(visible).is(visible));
        }
    }
}
