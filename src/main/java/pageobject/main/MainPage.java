package pageobject.main;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Getter;
import pageobject.user.LoginPage;

import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;

@Getter
public class MainPage extends CommonHeader {

    //URL для главной страницы сайта stellar burgers
    public final static String URL = "https://stellarburgers.nomoreparties.site/";

    //заголовок конструктора бургеров
    private final SelenideElement constructorH = $(byTagAndText("h1", "Соберите бургер"));

    //вкладки конструктора бургеров на главной странице: Булки, Соусы, Начинки
    private final SelenideElement buns = $(byXpath("//span[@class = 'text text_type_main-default' and contains(text(), 'Булки')]"));
    private final SelenideElement sauce = $(byXpath("//span[@class = 'text text_type_main-default' and contains(text(), 'Соусы')]"));
    private final SelenideElement fillings = $(byXpath("//span[@class = 'text text_type_main-default' and contains(text(), 'Начинки')]"));

    //методы клика по вкладками конструктора бургеров на главной странице: Булки, Соусы, Начинки
    @Step("Click on the buns to open bun's Section")
    public MainPage clickBuns(){
        buns.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Step("Click on the sauce to open sauce's Section")
    public MainPage clickSauce(){
        sauce.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Step("Click on the fillings to open filling's Section")
    public MainPage clickFillings(){
        fillings.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    //Коллекция ингридиентов
    private final ElementsCollection ingredientsTypes = $$(byXpath("//ul[@class='BurgerIngredients_ingredients__list__2A-mT']"));
    private final ElementsCollection ingredientsList = $$(byXpath("//a[@class='BurgerIngredient_ingredient__1TVf6 ml-4 mr-4 mb-8']"));
    //секция булок
    private final SelenideElement bunSection = ingredientsTypes.get(0);
    private final SelenideElement bunsH = $(byXpath("//h2[@class='text text_type_main-medium mb-6 mt-10' and contains(text(), 'Булки')]"));
    //секция соусов
    private final SelenideElement sauceSection = ingredientsTypes.get(1);
    private final SelenideElement sauceH = $(byXpath("//h2[@class='text text_type_main-medium mb-6 mt-10' and contains(text(), 'Соусы')]"));
    //секция начинок
    private final SelenideElement fillingsSection = ingredientsTypes.get(2);
    private final SelenideElement fillingsH = $(byXpath("//h2[@class='text text_type_main-medium mb-6 mt-10' and contains(text(), 'Начинки')]"));

    //кнопка войти в аккаунт
    private final SelenideElement btnAccount = $(byXpath("//button[contains(text(),'Войти в аккаунт')]"));

    //метод для клика по ссылке на Личный кабинет
    @Step("Click on the 'Enter to the Personal Account' button to go to the Login page")
    public LoginPage clickBtnAccount(){
        btnAccount.click();
        return page(LoginPage.class);
    }

    //кнопка оформить заказ
    private final SelenideElement btnOrder = $(byXpath("//button[contains(text(),'Оформить заказ')]"));
}
