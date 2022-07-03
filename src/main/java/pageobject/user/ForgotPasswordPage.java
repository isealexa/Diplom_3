package pageobject.user;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import pageobject.main.CommonHeader;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class ForgotPasswordPage extends CommonHeader {

    //URL страницы авторизации:
    public final static String URL = "https://stellarburgers.nomoreparties.site/forgot-password";

    //Ссылка на страницу авторизации
    private final SelenideElement enterLink = $(byLinkText("Войти"));

    //метод для клика по ссылке на авторизацию
    @Step("Click on the login link")
    public LoginPage clickEnterLink(){
        enterLink.scrollTo().shouldBe(visible).click();
        return page(LoginPage.class);
    }
}
