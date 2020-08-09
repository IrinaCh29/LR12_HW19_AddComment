import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriverFactory {

  private static WebDriver webDriver;

  public static WebDriver getDriver() {
    return webDriver;
  }

  public static void setWebDriver(WebDriver driver){
    webDriver = driver;
  }

  public static void closeDriver(){
    webDriver.quit();
  }

  public static void createInstance(String browserName) {

    DesiredCapabilities capability = null;
    WebDriver driver = null;

    if (browserName.toLowerCase().contains("firefox")) {
      /*GeckoDriver физически подтягивается (сохраняли/клали ранее в папку и ссылались),
      то есть подтягивается сейчас автоматически
      WebDriver - подключенная библиотека  */
      WebDriverManager.firefoxdriver().setup(); // Аналог - System.setProperty("webdriver.chrome.driver","D:\List_of_Jar\chromedriver.exe"); и руками не кладем фафлик в папку
      // создаем конкретный webDriver - что является = Подключись к браузеру к такому-то
      driver = new FirefoxDriver();
    } else if (browserName.toLowerCase().contains("internet")) {
      WebDriverManager.iedriver().setup();
      driver = new InternetExplorerDriver();
    } else if (browserName.toLowerCase().contains("chrome")) {
      // WebDriverManager.chromedriver().version("84.0.4147.30").setup();
      WebDriverManager.chromedriver().setup();
      driver = new ChromeDriver();
    } else {
      driver = new ChromeDriver();
    }

    driver.manage().window().maximize();
    webDriver = driver;
  }
}
