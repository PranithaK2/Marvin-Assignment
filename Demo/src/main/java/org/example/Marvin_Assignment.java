package org.example;

import com.google.common.io.Files;
import dev.failsafe.internal.util.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

public class Marvin_Assignment {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webDriver.chrome.driver", "/Users/pranithak/Downloads/chromedriver_mac_arm64 (1)/chromedriver");
        WebDriver driver = new EdgeDriver();
        try{

            //Open "Amazon" website
            driver.get("https://www.amazon.in/");
            driver.manage().window().maximize();

            //Get the locator of the search field
            WebElement search = driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));
            search.sendKeys("women dresses");
            search.sendKeys(Keys.RETURN);

            //Switching from one window to another window with the help of locator
            driver.findElement(By.xpath("(//span[@class='rush-component'])[4]")).click();
            Set<String> set1 = driver.getWindowHandles();
            ArrayList ar1 =  new ArrayList<>(set1);
            driver.switchTo().window((String)ar1.get(1));

            //Select the size of the dress from dropdown
            WebElement sizeDropDown = driver.findElement(By.xpath("(//select[@id='native_dropdown_selected_size_name'])[1]"));
            Select s = new Select(sizeDropDown);
            s.selectByIndex(1);

            //Finding the price and storing it in a variable
            WebElement priceElement = driver.findElement(By.xpath("(//span[@class=\"a-price-whole\"])[1]"));
            float price = Float.parseFloat(priceElement.getText());

            Thread.sleep(2000);
            //Switching to a new window
            Set<String> set = driver.getWindowHandles();
            ArrayList ar =  new ArrayList<>(set);
            driver.switchTo().window((String)ar.get(1));

            //Adding item to the cart
            driver.findElement(By.xpath("//span[@id='submit.add-to-cart']")).click();

            //Click on "Go to cart button"
            driver.findElement(By.linkText("Go to Cart")).click();

            //Getting quantity and storing in a variable
            WebElement quantity = driver.findElement(By.xpath("(//span[@class='a-dropdown-prompt'])[1]"));
            String qty = quantity.getText();

            //Switching to a new window
            driver.switchTo().window((String)ar.get(1));

            //Finding the price of the item at Proceed to pay and storing it in a variable
            WebElement priceAtProceed = driver.findElement(By.xpath("//span[@id='sc-subtotal-amount-buybox']//span[@class='a-size-medium a-color-base sc-price sc-white-space-nowrap']"));
            float priceAtProceedToPay = Float.parseFloat(priceAtProceed.getText());

            //Asserting Price & Quantity
            Assert.isTrue(Integer.parseInt(qty) == 1,"Quantity mismatched");
            Assert.isTrue(price == priceAtProceedToPay,"Price mismatces");

            //click on "Proceed to pay" button
            driver.findElement(By.xpath("(//input[@name='proceedToRetailCheckout'])[1]")).click();

            //Taking the Screenshot of login page
            TakesScreenshot scrShot =((TakesScreenshot)driver);
            File source = scrShot.getScreenshotAs(OutputType.FILE);
            File dest = new File("src/main/resources/screenshots/login_page_screenshot.jpg");
            Files.copy(source, dest);
        }
        catch (Exception e){
            System.out.println(String.format("Exception occured with error message: %s", e.getMessage()));
        }
        finally {
            driver.quit();//close the browser windows opened by selenium
        }
    }
}