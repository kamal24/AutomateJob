import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.ArrayList;
import java.util.List;

public class AutomateJob {
    private static String emailId = "eLogin";
    private static String passwordId = "pLogin";
    private static String notifiXpath = "//*[@id=\"blobId\"]";
    private static String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,Keys.RETURN);

    public static void automateRecruiterMsg(WebDriver driver){
        driver.get("https://my.naukri.com/Inbox/viewRecruiterMails");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        List<WebElement> inboxList = driver.findElements(By.className("inbLnk"));

        int count=0;
        while(count!=inboxList.size()) {
            inboxList.get(count).click();

          /*  ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
              driver.switchTo().window(tabs2.get(1));*/

            driver.findElement(By.className("mainPrevColor")).click();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<WebElement> updateList = driver.findElements(By.id("qupSubmit"));

            if(updateList.size()>0)
                driver.findElement(By.id("qupSubmit")).click();

            driver.get("https://my.naukri.com/Inbox/viewRecruiterMails");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            inboxList = driver.findElements(By.className("inbLnk"));
            count++;

            /*driver.switchTo().window(tabs2.get(0));*/
        }
    }

    public static void automateRecommandJobs(WebDriver driver){
        driver.get("https://www.naukri.com/recommendedjobs");
        List<WebElement> jobList = driver.findElements(By.cssSelector("a[class='content']"));
        jobList.stream().forEach(idx-> {
            System.out.println(idx.getAttribute("href"));
            idx.click();
            
            ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs2.get(1));
            
            List<WebElement> list= driver.findElements(By.id("loginApply"));
            List<WebElement> applyList = driver.findElements(By.id("trig1"));
            System.out.println(list.size()+" "+applyList.size());

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            if(list.size()>0)
                list.get(0).click();
            if(applyList.size()>0)
                applyList.get(0).click();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            List<WebElement> updateEle = driver.findElements(By.id("qupSubmit"));
            if(updateEle.size()>0)
                updateEle.get(0).click();
            driver.close();
            driver.switchTo().window(tabs2.get(0));
        });
    }


    public static  void main(String args[]){
        try {
            System.setProperty("webdriver.chrome.driver","/usr/lib/chromium-browser/chromedriver");
            WebDriver driver = new ChromeDriver();
            WebDriverWait wait = new WebDriverWait(driver,1000);

            driver.get("http://naukri.com");

            String title = driver.getTitle();
            System.out.println("test inside" + title);

            wait.until(ExpectedConditions.elementToBeClickable(By.id("login_Layer")));
            WebElement ele = driver.findElement(By.id("login_Layer"));
            ele.click();
            Thread.sleep(5000);

            // wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"lgnFrm\"]/div[8]/button")));
            int ok_size =  driver.findElements(By.id(emailId+"New")).size();
            if(ok_size!=0)
                driver.findElement(By.id(emailId+"New")).sendKeys("kmlgrg2425@gmail.com");
            else
                driver.findElement(By.id(emailId)).sendKeys("email");

            driver.findElement(By.id(passwordId)).sendKeys("password");
            driver.findElement(By.cssSelector("button[class='blueBtn']")).click();

            Thread.sleep(3000);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(notifiXpath)));

            driver.findElement(By.xpath(notifiXpath)).click();

            automateRecommandJobs(driver);
            //automateRecruiterMsg(driver);

            //driver.quit();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
