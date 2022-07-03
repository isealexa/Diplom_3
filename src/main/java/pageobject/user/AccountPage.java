package pageobject.user;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import pageobject.main.CommonHeader;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

@Getter
public class AccountPage extends CommonHeader {

    //URL страницы Личного кабинета:
    public final static String URL = "https://stellarburgers.nomoreparties.site/account/";

    //ссылки на информацию об аккаунте: профиль и история заказов
    private final SelenideElement profile = $(byLinkText("Профиль"));
    private final SelenideElement history = $(byLinkText("История заказов"));

    //кнопка выход
    private final SelenideElement btnExit = $(byXpath("//button[contains(text(),'Выход')]"));

    //метод клика по кнопке Выход
    public LoginPage clickBtnExit(){
        btnExit.click();
        return page(LoginPage.class);
    }

    //информация о пользователе: Имя, email и пароль
    private final SelenideElement name = $(byTagAndText("label", "Имя")).parent().$(byCssSelector("input"));
    private final SelenideElement email = $(byTagAndText("label", "Логин")).parent().$(byCssSelector("input"));
    private final SelenideElement password = $(byTagAndText("label", "Пароль")).parent().$(byCssSelector("input"));
}
