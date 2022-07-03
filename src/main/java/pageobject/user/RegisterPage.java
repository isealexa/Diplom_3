package pageobject.user;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Getter;
import model.UserData;
import pageobject.main.CommonHeader;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

@Getter
public class RegisterPage extends CommonHeader {

    //URL страницы регистрации:
    public final static String URL = "https://stellarburgers.nomoreparties.site/register";

    //заголовок формы регистрации
    private final SelenideElement registerFormHeader = $(byTagAndText("h2", "Регистрация"));

    //поле для заполнения формы регистрации: Имя, email и пароль
    private final SelenideElement inputName = $(byTagAndText("label", "Имя")).parent().$(byCssSelector("input"));
    private final SelenideElement inputEmail = $(byTagAndText("label", "Email")).parent().$(byCssSelector("input"));
    private final SelenideElement inputPassword = $(byTagAndText("label", "Пароль")).parent().$(byCssSelector("input"));

    //метод для заполнения полей на странице Регистрации
    @Step("Filling in all fields of the Registration form")
    public RegisterPage fillRegisterForm(UserData userData){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getInputName().shouldBe(visible).setValue(userData.getName());
        getInputEmail().shouldBe(visible).setValue(userData.getEmail());
        getInputPassword().shouldBe(visible).setValue(userData.getPassword());
        return this;
    }

    //кнопка Зарегистрироваться на форме регистрации
    private final SelenideElement btnRegister = $(byXpath("//button[contains(text(),'Зарегистрироваться')]"));

    //метод для клика по кнопке Зарегистрироваться
    @Step("Click on the Registration button")
    public LoginPage clickBtnRegister(){
        btnRegister.click();
        return page(LoginPage.class);
    }

    @Step("Click on the Registration button")
    public RegisterPage clickIncorrectRegister(){
        btnRegister.click();
        return this;
    }

    //Подвечивающееся сообщение о некорректном пароле
    private final SelenideElement errorPassword =$(byXpath("//p[@class = 'input__error text_type_main-default' and contains(text(), 'Некорректный пароль')]"));

    //Подвечивающееся сообщение о том, что пользоваль уже существует
    private final SelenideElement errorUser =$(byXpath("//p[@class = 'input__error text_type_main-default' and contains(text(), 'Такой пользователь уже существует')]"));

    //Ссылка на страницу авторизации
    private final SelenideElement enterLink = $(byLinkText("Войти"));

    //метод для клика по ссылке на авторизацию
    @Step("Click on the login link")
    public LoginPage clickEnterLink(){
        enterLink.scrollTo().shouldBe(visible).click();
        return page(LoginPage.class);
    }
}
