package fullflow;

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
import pageobject.main.MainPage;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.webdriver;
import static org.junit.Assert.assertEquals;

public class RegisterAndLoginTest {

    private UserData userData;
    private MainPage mainPage;

    @Before
    @Step("Set up test data")//Configurations browser maximized screen")
    public void setUp(){
        userData = UserData.getRandomRegisterData();

        Configuration.pageLoadTimeout = 6000;
        mainPage = Selenide.open(MainPage.URL, MainPage.class);
        webdriver().driver().getWebDriver().manage().window().maximize();
    }

    @After
    @Step("Clean cookie and Local Storage")
    public void clean(){
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
    }

    @Test
    @DisplayName("Full UI flow for user registration and authorization should be success")
    @Description("Button 'Enter to account' -- Get random user data, go to the login page, go to the register page, correct user registration, login and check the account page")
    public void fullUIFlowForRegisterThrowBntAccAndLoginUserShouldBeSuccess(){
        MainPage registerFlow = mainPage
                .clickBtnAccount()
                .clickRegister()
                .fillRegisterForm(userData)
                .clickBtnRegister()
                .fillLoginForm(userData)
                .clickBtnLogin();

        AccountPage page = registerFlow
                .clickAccountLink();

        checkProfilePage(page, userData);
    }


    @Test
    @DisplayName("Full UI flow for user registration and authorization should be success")
    @Description("Link 'Personal Account' in the Header -- Get random user data, go to the login page, go to the register page, correct user registration, login and check the account page")
    public void fullUIFlowForRegisterThrowLinkAccAndLoginUserShouldBeSuccess(){
        MainPage registerFlow = mainPage
                .clickAccountLinkNoAuthorized()
                .clickRegister()
                .fillRegisterForm(userData)
                .clickBtnRegister()
                .fillLoginForm(userData)
                .clickBtnLogin();

        AccountPage page = registerFlow
                .clickAccountLink();

        checkProfilePage(page, userData);
    }

    //Проверить, что на странице профила верно отображаются данные пользователя
    @Step("Check the profile page")
    public void checkProfilePage(AccountPage page, UserData userData){
        assertEquals("В поле имя отображается некорректное значение", userData.getName(), page.getName().getValue());
        assertEquals("В поле логин отображается некорректное значение", userData.getEmail().toLowerCase(Locale.ROOT), page.getEmail().getValue());
        assertEquals("В поле пароль отображается некорректное значение","*****", page.getPassword().getValue());
    }
}
