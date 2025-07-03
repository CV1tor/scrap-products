package br.com.seleniumStudy.scrap;

import br.com.seleniumStudy.model.Product;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class AmazonScrap {

    static WebDriver driver = new ChromeDriver();
    static Dotenv dotenv = Dotenv.load();
    static Logger logger = LogManager.getLogger();

    public static List<Product> findProducts(String query) throws InterruptedException {
        driver.get(dotenv.get("BASE_URL"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        Thread.sleep(1000);
        searchBox.sendKeys(query, Keys.ENTER);

        List<WebElement> searchResults = driver.findElements(By.cssSelector(".a-section.a-spacing-small.puis-padding-left-small.puis-padding-right-small"));
        int maxResultsConf = Integer.parseInt(dotenv.get("MAX_RESULTS"));
        int maxResults = Math.min(searchResults.size(), maxResultsConf);

        List<Product> products = new java.util.ArrayList<>();
        for (int i = 0; i < maxResults; i++) {
            WebElement result = searchResults.get(i);
            String name = productName(result);

            if (name == null) {
                maxResults++;
                continue;
            }
            String price = "R$" +
                    result.findElement(By.cssSelector(".a-price-whole")).getText() +
                    "," +
                    result.findElement(By.cssSelector(".a-price-fraction")).getText();

            System.out.println(name + " - " + price);
            products.add(new Product(name, price));
            Thread.sleep(1500);
        }

        return products;
    }

    private static String productName(WebElement productDiv) {
        WebElement productName = productDiv.findElement(By.cssSelector(".s-title-instructions-style > a > h2 > span"));
        if (!productName.getText().contains("Mesa") && !productName.getText().contains("Escrivaninha")) {
            return null;
        }
        return productName.getText();
    }
}


