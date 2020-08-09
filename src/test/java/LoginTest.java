import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static org.testng.Assert.*;

public class LoginTest {
  WebDriver driver = null;

  @BeforeMethod
  public void setUp() {
    WebDriverFactory.createInstance("Chrome");
    driver = WebDriverFactory.getDriver();
  }

  @Test
  public void createIssueTest() {
    driver.get("https://jira.hillel.it/secure/Dashboard.jspa");
    driver.findElement(By.id("login-form-username")).sendKeys("IrinaChub");
    driver.findElement(By.id("login-form-password")).sendKeys("IrinaChub");
    driver.findElement(By.id("login")).click();

    //Explicit Wait for element to appear
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30).getSeconds());
    boolean elementIsPresent = wait.until(presenceOfElementLocated(By.id("create_link"))).isEnabled();
    assertEquals(elementIsPresent, true);

    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    driver.findElement(By.id("create_link")).click();

    wait.until(presenceOfElementLocated(By.id("project-field"))).isDisplayed();
    driver.findElement(By.id("project-field")).clear();
    driver.findElement(By.id("project-field")).sendKeys("Webinar (WEBINAR)");
    driver.findElement(By.id("project-field")).sendKeys(Keys.TAB);

    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    driver.findElement(By.id("issuetype-field")).clear();
    driver.findElement(By.id("issuetype-field")).sendKeys("Task");
    driver.findElement(By.id("issuetype-field")).sendKeys(Keys.TAB);

    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    driver.findElement(By.id("summary")).sendKeys("summary");
    driver.findElement(By.id("reporter-field")).clear();
    driver.findElement(By.id("reporter-field")).sendKeys("IrinaChub");
    driver.findElement(By.id("reporter-field")).sendKeys(Keys.TAB);

    wait.until(presenceOfElementLocated(By.xpath("//*[@id='description-wiki-edit']//child::a[text()='Text']"))).isEnabled();
    driver.findElement(By.xpath("//*[@id='description-wiki-edit']//child::a[text()='Text']")).click();
    driver.findElement(By.id("description")).sendKeys("some text of description");

    driver.findElement(By.id("create-issue-submit")).click();

    //Explicit Wait for element to appear
    boolean popUpIsPresent = wait.until(presenceOfElementLocated(By.className("aui-message-success"))).isDisplayed();
    assertEquals(popUpIsPresent, true);

    WebElement projectNameIsPresent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("aui-message-success")));
    wait.until(ExpectedConditions.textToBePresentInElement(projectNameIsPresent, "WEBINAR"));
  }

  @AfterMethod
  public void tearDown() {
    driver.quit();
  }
}