import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class App {

    private static void login(WebDriver driver, String username, String password) {
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.clear();
        usernameField.sendKeys(username);
        passwordField.clear();
        passwordField.sendKeys(password);
        loginButton.click();
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Jules\\Desktop\\Java\\CodeChallenge\\lib\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); 

        try {
            // Scenario 1
            driver.get("https://www.saucedemo.com/");
            login(driver, "standard_user", "secret_sauce");

            WebElement productsTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title")));
            if (productsTitle.getText().equals("Products")) {
                System.out.println("Scenario 1: Login successful, navigated to home page.");
            } else {
                System.out.println("Scenario 1: Login failed, did not navigate to home page");
            }

            // Log out
            WebElement menuButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-burger-menu-btn")));
            menuButton.click();
            WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));
            logoutLink.click();

            // Verify
            WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
            if (loginButton.isDisplayed()) {
                System.out.println("Scenario 1: Logout successful, navigated to login page.");
            } else {
                System.out.println("Scenario 1: Logout failed.");
            }

            // Scenario 2
            driver.get("https://www.saucedemo.com/");
            login(driver, "locked_out_user", "secret_sauce");

            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));
            if (errorMessage.getText().contains("Sorry, this user has been locked out.")) {
                System.out.println("Scenario 2: Correct error message displayed.");
            } else {
                System.out.println("Scenario 2: Error message verification failed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
