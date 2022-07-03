package pageobject.main;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Data;
import pageobject.user.LoginPage;
import pageobject.user.AccountPage;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

@Data
public class CommonHeader {

    //логотип сайта stellar burgers
    private final SelenideElement logo = $(byAttribute("xmlns", "http://www.w3.org/2000/svg"));

    //метод клика по логитипу сайта stellar burgers
    public MainPage clickLogo(){
        logo.click();
        return page(MainPage.class);
    }

    //сслыка на главную страницу - конструктор в header
    private final SelenideElement constructorLink = $(byXpath("//p[@class = 'AppHeader_header__linkText__3q_va ml-2' and contains(text(), 'Конструктор')]"));

    //метод клика по логитипу сайта stellar burgers
    public MainPage clickConstructorLink(){
        constructorLink.click();
        return page(MainPage.class);
    }

    //сслыка на личный кабинет в header
    private final SelenideElement accountLink = $(byXpath("//p[@class = 'AppHeader_header__linkText__3q_va ml-2' and contains(text(), 'Личный Кабинет')]"));

    //метод для клика по ссылке на Личный кабинет авторизованным пользователем
    @Step("Click on the Personal Account link to go to the Profile Page")
    public AccountPage clickAccountLink(){
        accountLink.click();
        return page(AccountPage.class);
    }

    //метод для клика по ссылке на Личный кабинет неавторизованным пользователем
    @Step("Click on the Personal Account link to go to the Profile Page")
    public LoginPage clickAccountLinkNoAuthorized(){
        accountLink.click();
        return page(LoginPage.class);
    }
}
