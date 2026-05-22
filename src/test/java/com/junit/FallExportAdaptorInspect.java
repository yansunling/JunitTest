package com.junit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FallExportAdaptorInspect {
    private static void sleep(long ms) { try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); } }
    private static void setInputByJs(WebDriver driver, String id, String value) {
        WebElement el = driver.findElement(By.id(id));
        ((JavascriptExecutor) driver).executeScript("arguments[0].focus();arguments[0].value=arguments[1];arguments[0].dispatchEvent(new Event('input',{bubbles:true}));arguments[0].dispatchEvent(new Event('change',{bubbles:true}));arguments[0].blur();", el, value);
    }
    public static void main(String[] args) throws Exception {
        System.setProperty("webdriver.chrome.driver", "E:\\idea2026\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        WebDriver driver = new ChromeDriver(options);
        try {
            WebDriverWait wait = new WebDriverWait(driver, 20);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            driver.get("https://fall.wlhn.com/fallapp-main-welcome/ext/pLogin?areaId=88a93c7727944754ab2e1ff01934cf70");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("uname"))).sendKeys("008驮龙");
            driver.findElement(By.id("pwd")).sendKeys("123456");
            driver.findElement(By.id("sub")).click();
            wait.until(ExpectedConditions.urlContains("/sys/admin"));
            sleep(5000);
            js.executeScript("arguments[0].click();", wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='装货发车']"))));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("exportExcelByColumn-1265")));
            sleep(4000);
            setInputByJs(driver, "datetimefield-1184-inputEl", "2026-05-06 00:00");
            setInputByJs(driver, "datetimefield-1185-inputEl", "2026-05-12 23:59");
            js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[text()='查询']")));
            sleep(8000);
            Object result = js.executeScript(
                "var grid=Ext.getCmp('exportExcelByColumn-1265').up('jgrid');" +
                "var params=FALL_ASK.adaptor(grid.store.config);" +
                "params['$_url']=_.getServerUrl((grid.store.config.url||FALL_ASK.uri));" +
                "return JSON.stringify(params);"
            );
            System.out.println(result);
        } finally {
            driver.quit();
        }
    }
}
