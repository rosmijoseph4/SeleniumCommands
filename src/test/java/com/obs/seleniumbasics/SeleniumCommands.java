package com.obs.seleniumbasics;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

public class SeleniumCommands {
    public WebDriver driver;
    public void testInitialize(String browser,String url) {
        if(browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver=new ChromeDriver();
        } else if(browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver=new EdgeDriver();
        }else if(browser.equalsIgnoreCase("firefox")) {
            //System.setProperty("webdriver.gecko.driver","C:\\Users\\Rosmi Joseph\\Documents\\Selenium\\Firefoxpath\\geckodriver.exe");
           WebDriverManager.firefoxdriver().setup();
            driver=new FirefoxDriver();
        }else {
            try {
                throw new Exception("Browser value not defined");
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
       // driver.get(url);
       //driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser","url"})
    public void setUp(String browserName,String baseUrl) {
       // public void setUp(){
        testInitialize(browserName,baseUrl);
        //testInitialize("chrome","http://demowebshop.tricentis.com/");
    }


    @AfterMethod

       public void tearDown(ITestResult result) throws IOException {

            if(result.getStatus()==ITestResult.FAILURE) {
                TakesScreenshot scrshot = (TakesScreenshot) driver;
                File screenshot = scrshot.getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screenshot,new File("./Screenshots/"+result.getName()+".png"));
            }
            //driver.close();
           driver.quit();
        }


        @Test(priority = 1,enabled = true,description = "TC_001_Verify Homepage Titile",groups = {"smoke"})
    public void verifyHomePageTitle() {
       driver.get("http://demowebshop.tricentis.com/");
        String expTitle ="Demo Web Shop";
        String actualTitle=driver.getTitle();
        Assert.assertEquals(actualTitle, expTitle,"ERROR :Invalid Homepage Title Found");
    }
    @Test
    public void VerifyWindowHandle(){
        driver.get("https://demo.guru99.com/popup.php");
        String parentwindow=driver.getWindowHandle();
       // System.out.println(parentwindow);
        WebElement button=driver.findElement(By.xpath(" //a[text()='Click Here']"));
        button.click();
        Set<String> handleids=driver.getWindowHandles();
        System.out.println(handleids);
        Iterator<String> itr=handleids.iterator();
        while(itr.hasNext()){
            String child=itr.next();
            if(!parentwindow.equals(child)){
                driver.switchTo().window(child);
                WebElement textfiled=driver.findElement(By.xpath("//input[@name='emailid']"));
                textfiled.sendKeys("test@gmail.com");
                driver.findElement(By.xpath("//input[@name='btnLogin']")).click();
            }
        }
        driver.switchTo().window(parentwindow);
    }
    @Test
    public void VerifyFileUpload(){
        driver.get("https://demo.guru99.com/test/upload/");
        WebElement filelocate=driver.findElement(By.id("uploadfile_0"));
        filelocate.sendKeys("C:\\Users\\Rosmi Joseph\\Documents\\JavaNotes\\MavenNotes.docx");
        driver.findElement(By.id("terms")).click();
        driver.findElement(By.id("submitbutton")).click();

    }
    @Test
    public void verifyUrl(){
        System.out.println("rosmi");
    }
    @Test
    public void verifyFileuploadRobot() throws AWTException, InterruptedException {
        driver.get("http://demo.guru99.com/test/upload/");
        StringSelection s = new StringSelection("C:\\\\Users\\\\Rosmi Joseph\\\\Documents\\\\JavaNotes\\\\MavenNotes.docx");
        // Clipboard copy
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s,null);
        //identify element and click
        WebElement filelocate=driver.findElement(By.id("uploadfile_0"));
        filelocate.submit();
        Thread.sleep(10000);

        // Robot object creation
        Robot r = new Robot();
        Thread.sleep(10000);

        //pressing enter
        r.keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(10000);
        //releasing enter
        r.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(10000);

        //pressing ctrl+v
        r.keyPress(KeyEvent.VK_CONTROL);
        Thread.sleep(10000);
        r.keyPress(KeyEvent.VK_V);
        Thread.sleep(10000);
        //releasing ctrl+v
        r.keyRelease(KeyEvent.VK_CONTROL);
        Thread.sleep(10000);
        r.keyRelease(KeyEvent.VK_V);
        Thread.sleep(10000);
        //Thread.sleep(1000);
        //pressing enter
        r.keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(10000);
        //releasing enter
        r.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(10000);
    }
    @Test
    public void verifyJavascriptClickAndSendkeys(){
        driver.get("http://demowebshop.tricentis.com/");
        JavascriptExecutor js=(JavascriptExecutor)driver;
        String s1="document.getElementById('newsletter-email').value='test@gmail.com'";
        String s2="document.getElementById('newsletter-subscribe-button').click()";
        js.executeScript(s1);
        js.executeScript(s2);
    }

    @Test(priority = 2,enabled = true,groups = {"smoke"},description = "TC_002 verify Validlogin Excel")
    public void verifyValidloginExcel() throws IOException {
        ExcelUtility excelUtility=new ExcelUtility();
        driver.get("http://demowebshop.tricentis.com/");
        driver.findElement(By.xpath("//a[text()='Log in']")).click();
        WebElement user=driver.findElement(By.xpath("//input[@id='Email']"));
        String user_value=excelUtility.readData(1,0,"Login");
        user.sendKeys(user_value);

        WebElement pass=driver.findElement(By.xpath("//input[@id='Password']"));
        String pass_value=excelUtility.readData(1,1,"Login");
        pass.sendKeys(pass_value);
        driver.findElement(By.xpath("//input[@class='button-1 login-button']")).click();
    }
    @Test(dataProvider = "userlogindata")
    public void verifyLoginUsingDataProvider(String username, String password){
        driver.get("http://demowebshop.tricentis.com/");
        driver.findElement(By.xpath("//a[text()='Log in']")).click();
        WebElement user = driver.findElement(By.xpath("//input[@id='Email']"));
        user.sendKeys(username);
        WebElement pass = driver.findElement(By.xpath("//input[@id='Password']"));
        pass.sendKeys(password);
        driver.findElement(By.xpath("//input[@class='button-1 login-button']")).click();
    }
    @DataProvider(name="userlogindata")
    public Object[][] loginData(){
        Object[][] data=new Object[2][2];
        data[0][0]="selenium121@test.com";
        data[0][1]="12345678";
        data[1][0]="selenium111@test.com";
        data[1][1]="12345678";
        return data;
    }
    @DataProvider(name="loginExcelDataprovider")
    public Object[][] loginExcelData() throws IOException {
        ExcelUtility excelUtility=new ExcelUtility();
        String filepath = System.getProperty("user.dir") + "\\src\\main\\resources\\TestData.xlsx";
        Object[][] excelData=excelUtility.readDataFromExcel(filepath,"login");
        return excelData;
    }

}




