package shortflow;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pageobject.Config;
import pageobject.main.MainPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.webdriver;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ConstructorTest {

    private final String chooseType;

    public ConstructorTest(String chooseType){
        this.chooseType = chooseType;
    }

    @Parameterized.Parameters(name = "Test {index} checks choosing ingredient's type: {0}")
    public static Object[][] getChooseType(){
        return new Object[][] {
                {"bun"},
                {"sauce"},
                {"fillings"},
        };
    }

    private MainPage mainPage;

    @Before
    @Step("Set up web driver")
    public void setUp(){
        Config.initWebDriver();

        Configuration.pageLoadTimeout = 6000;
        mainPage = Selenide.open(MainPage.URL, MainPage.class);
        webdriver().driver().getWebDriver().manage().window().maximize();
    }

    @After
    @Step("Clean cookies and Local Storage")
    public void clean(){
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
    }

    @Test
    @DisplayName("Successful type switching changes focus")
    @Description("Click ingredient type has to focus it and make visible ingredient's list")
    public void clickIngredientTypeHasToFocusItAndMakeVisibleList(){
        MainPage page =chooseSection(chooseType);
        check(page, chooseType);
    }

    @Step("Click on the section type")
    public MainPage chooseSection(String type){
        MainPage page;

        switch (type) {
            case "sauce":
                page = mainPage.clickSauce();
                break;
            case "fillings":
                page = mainPage.clickFillings();
                break;
            default:
                page = mainPage.clickSauce()
                        .clickBuns();
        }
        return page;
    }

    @Step("Check constructor is visible in the Main page after transition to constructor")
    public void check(MainPage page, String type){
        assertTrue("На главной странице не отобразился заголовок раздела Контруктор - 'Соберите бургер'", page.getConstructorH().shouldBe(visible).is(visible));
        assertTrue("На главной странице не отобразился заголовок секциии Булки", page.getBuns().shouldBe(visible).is(visible));
        assertTrue("На главной странице не отобразился заголовок секции Соусы", page.getSauce().shouldBe(visible).is(visible));
        assertTrue("На главной странице не отобразился заголовок секции Начинки", page.getFillings().shouldBe(visible).is(visible));
        assertTrue("Типов ингридиентов должно быть не меньше 3-х", page.getIngredientsTypes().size() >= 3);
        assertTrue("Ингридиентов должно быть не меньше 3-х", page.getIngredientsList().size() >= 3);

        switch (type){
            case "sauce":
                assertFalse("Ошибочно выбран раздел Булки", page.getBuns().parent().attr("class").contains("tab_tab_type_current__2BEPc"));
                assertTrue("Должен быть выбран раздел Соусы",page.getSauce().parent().attr("class").contains("tab_tab_type_current__2BEPc"));
                assertFalse("Ошибочно выбран раздел Начинки", page.getFillings().parent().attr("class").contains("tab_tab_type_current__2BEPc"));

                assertTrue("Список Соусов должен быть виден на экране", page.getSauce().shouldBe(visible).is(visible));
                break;

            case "fillings":
                assertFalse("Ошибочно выбран раздел Булки",page.getBuns().parent().attr("class").contains("tab_tab_type_current__2BEPc"));
                assertFalse("Ошибочно выбран раздел Соусы",page.getSauce().parent().attr("class").contains("tab_tab_type_current__2BEPc"));
                assertTrue("Должен быть выбран раздел Начинки", page.getFillings().parent().attr("class").contains("tab_tab_type_current__2BEPc"));

                assertTrue("Список Начинок должен быть виден на экране",page.getFillingsH().shouldBe(visible).is(visible));
                break;

            default:
                assertTrue("Должен быть выбран раздел Булки", page.getBuns().parent().attr("class").contains("tab_tab_type_current__2BEPc"));
                assertFalse("Ошибочно выбран раздел Соусы",page.getSauce().parent().attr("class").contains("tab_tab_type_current__2BEPc"));
                assertFalse("Ошибочно выбран раздел Начинки",page.getFillings().parent().attr("class").contains("tab_tab_type_current__2BEPc"));

                assertTrue("Список Булок должен быть виден на экране",page.getBunsH().shouldBe(visible).is(visible));
        }
    }
}
