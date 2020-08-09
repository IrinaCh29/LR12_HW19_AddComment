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
  public void addCommentToTicketTest() {
    driver.get("https://jira.hillel.it/secure/Dashboard.jspa");
    driver.findElement(By.id("login-form-username")).sendKeys("IrinaChub");
    driver.findElement(By.id("login-form-password")).sendKeys("IrinaChub");
    driver.findElement(By.id("login")).click();

    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertTrue(driver.findElement(By.id("header-details-user-fullname")).isDisplayed());

    //Explicit Wait for element to appear
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30).getSeconds());
    boolean elementIsPresent = wait.until(presenceOfElementLocated(By.id("quickSearchInput"))).isEnabled();
    assertEquals(elementIsPresent, true);

    driver.findElement(By.id("quickSearchInput")).sendKeys("WEBINAR-12467");
    driver.findElement(By.id("quickSearchInput")).sendKeys(Keys.ENTER);

    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    assertTrue(driver.findElement(By.xpath("//*[@id='stalker']//child::a[@data-issue-key='WEBINAR-12467']")).isDisplayed());

    wait.until(presenceOfElementLocated(By.xpath("//*[@id='issue-tabs']//child::*[@id='comment-tabpanel']"))).isEnabled();
    driver.findElement(By.xpath("//*[@id='issue-tabs']//child::*[@id='comment-tabpanel']")).click();

    wait.until(presenceOfElementLocated(By.id("footer-comment-button"))).isEnabled();
    driver.findElement(By.id(("footer-comment-button"))).click();
    driver.findElement(By.id("comment")).sendKeys("some comments");
    driver.findElement(By.id("issue-comment-add-submit")).click();

    WebElement addCommentTest =
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(("//*[@id='issue_actions_container']//child::*[@class='action-body flooded']"))));
    wait.until(ExpectedConditions.textToBePresentInElement(addCommentTest, "some comments"));

    WebElement popUpDeleteCommentTest =
        driver.findElement(By.xpath("//*[@id='issue_actions_container']//child::*[@class='action-links']//child::*[@class='delete-comment issue-comment-action']"));
    popUpDeleteCommentTest.click();

    boolean deleteDialogIsPresent = wait.until(presenceOfElementLocated(By.xpath("//*[@id='delete-comment-dialog']//child::*[@id='comment-delete-submit']"))).isEnabled();
    assertEquals(deleteDialogIsPresent, true);
    driver.findElement(By.xpath("//*[@id='delete-comment-dialog']//child::*[@id='comment-delete-submit']")).click();

    WebElement deletedCommentTest = wait.
        until(ExpectedConditions.visibilityOfElementLocated(By.xpath(("//*[@id='issue_actions_container']//child::*[contains(text(), 'There are no comments yet on this issue.')]"))));
    wait.until(ExpectedConditions.textToBePresentInElement(deletedCommentTest, "There are no comments yet on this issue."));
  }

  @AfterMethod
  public void tearDown() {
    driver.quit();
  }
}