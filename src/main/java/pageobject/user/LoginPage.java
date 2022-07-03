package pageobject.user;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Getter;
import model.UserData;
import pageobject.main.CommonHeader;
import pageobject.main.MainPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

@Getter
public class LoginPage extends CommonHeader {

    //URL страницы авторизации:
    public final static String URL = "https://stellarburgers.nomoreparties.site/login";

    //заголовок формы авторизации
    private final SelenideElement loginFormHeader = $(byTagAndText("h2", "Вход"));

    //поле для заполнения формы авторизации: email и пароль
    private final SelenideElement inputEmail =  $(byTagAndText("label", "Email")).parent().$(byCssSelector("input"));
    private final SelenideElement inputPassword = $(byTagAndText("label", "Пароль")).parent().$(byCssSelector("input"));

    //метод для заполнения полей на странице Регистрации
    @Step("Filling in all fields of the Login form")
    public LoginPage fillLoginForm(UserData userData){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getInputEmail().shouldBe(visible).click();
        getInputEmail().shouldBe(visible).setValue(userData.getEmail());
        getInputPassword().shouldBe(visible).setValue(userData.getPassword());
        return this;
    }

    //кнопка Войти на форме авторизации
    private final SelenideElement btnLogin = $(byXpath("//button[contains(text(),'Войти')]"));

    //метод для клика по кнопке Войти
    @Step("Click on the Login button")
    public MainPage clickBtnLogin(){
        btnLogin.click();
        return page(MainPage.class);
    }

    //ссылка на страницу регистрации
    private final SelenideElement linkRegister = $(byLinkText("Зарегистрироваться"));

    //Ссылка на страницу восстановления пароля
    private final SelenideElement forgotPasswordLink = $(byLinkText("Восстановить пароль"));

    //метод для клика по ссылке на восстановление пароля
    @Step("Click on the forgot password link")
    public ForgotPasswordPage clickForgotPasswordLink(){
        forgotPasswordLink.scrollTo().shouldBe(visible).click();
        return page(ForgotPasswordPage.class);
    }

    //метод для клика по ссылке на Регистрацию
    @Step("Click on the Registration link")
    public RegisterPage clickRegister(){
        linkRegister.click();
        return page(RegisterPage.class);
    }

    //Подвечивающееся сообщение о некорректном пароле
    private final SelenideElement errorPassword =$(byXpath("//p[@class = 'input__error text_type_main-default' and contains(text(), 'Некорректный пароль')]"));
}
