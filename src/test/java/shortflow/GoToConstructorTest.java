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

public class GoToConstructorTest {

    private MainPage mainPage;

    @Before
    @Step("Set up web driver")
    public void setUp(){
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
    @DisplayName("Click on the logo has to transfer to constructor in the main page")
    @Description("Get random created and authorized user, click on the logo in the header in the account page and check transition to constructor in the main page")
    public void clickLogoHasToGoToMainPage(){
        UserData userData = UserData.getRandomRegisterData();
        String token = new UserClient().register(userData).extract().path("accessToken");

        MainPage page = mainPage
                .clickBtnAccount()
                .fillLoginForm(userData)
                .clickBtnLogin()
                .clickAccountLink()
                .clickLogo();

        checkButtons(page);
        check(page);
        clean(token);
    }

    @Test
    @DisplayName("Click on the constructor link has to transfer to constructor in the main page")
    @Description("Get random created and authorized user, click on the constructor link in the header in the account page and check transition to constructor in the main page")
    public void clickConstructorHasToGoToMainPage(){
        UserData userData = UserData.getRandomRegisterData();
        String token = new UserClient().register(userData).extract().path("accessToken");

        MainPage page = mainPage
                .clickBtnAccount()
                .fillLoginForm(userData)
                .clickBtnLogin()
                .clickAccountLink()
                .clickConstructorLink();

        checkButtons(page);
        check(page);
        clean(token);
    }

    @Step("Check buttons in the Main page after transition to constructor")
    public void checkButtons(MainPage page){
        assertFalse("Для авторизованного пользователя не должно быть видно кнопку Войти в аккаунт", page.getBtnAccount().is(visible));
        assertTrue("Для авторизованного пользователя должна была появиться кнопка Оформить заказ", page.getBtnOrder().shouldBe(visible).is(visible));
    }

    @Step("Check constructor is visible in the Main page after transition to constructor")
    public void check(MainPage page){
        assertTrue("На главной странице не отобразился заголовок раздела Контруктор - 'Соберите бургер'", page.getConstructorH().shouldBe(visible).is(visible));
        assertTrue("На главной странице не отобразился заголовок секциии Булки", page.getBuns().shouldBe(visible).is(visible));
        assertTrue("На главной странице не отобразился заголовок секции Соусы", page.getSauce().shouldBe(visible).is(visible));
        assertTrue("На главной странице не отобразился заголовок секции Начинки", page.getFillings().shouldBe(visible).is(visible));
        assertTrue("Типов ингридиентов должно быть не меньше 3-х", page.getIngredientsTypes().size() >= 3);
        assertTrue("Ингридиентов должно быть не меньше 3-х", page.getIngredientsList().size() >= 3);
    }

    @Step("Clean test data")
    public void clean(String token){
        new UserClient().delete(token);
    }
}
