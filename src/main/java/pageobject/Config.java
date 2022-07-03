package pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Map;

import static com.codeborne.selenide.WebDriverRunner.setWebDriver;

public class Config {

    public static void initWebDriver() {

        final String driverEnvVarName = "WEBDRIVER_PATH";
        Map<String, String> env = System.getenv();

        String webDriverPath = env.get(driverEnvVarName);

        if (webDriverPath != null) {
            System.setProperty("webdriver.chrome.driver", webDriverPath);
            WebDriver driver = new ChromeDriver();
            setWebDriver(driver);
        }
    }
}
//        Путь до Yandex браузера на моей машине: "D:/IdeaProjects/Praktikum/Diplom/Diplom_3/src/main/resources/yandexdriver-22.5.0.1816-win/yandexdriver.exe"
//        Путь до Yandex браузера в проекте: "/Diplom_3/src/main/resources/yandexdriver-22.5.0.1816-win/yandexdriver.exe"
//
//        Переменная окружения для установки в настройках прогона теста
//        WEBDRIVER_PATH=D:/IdeaProjects/Praktikum/Diplom/Diplom_3/src/main/resources/yandexdriver-22.5.0.1816-win/yandexdriver.exe

