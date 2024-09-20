package com.img;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class ScreenImg {

    public static void main(String[] args) {
        guge("https://kp.tuolong56.com/query/ui/out/query_request_log_detail.html?id=e467e2228af140ac801b3c773d0682f1");
//        guge("https://registry.npmmirror.com/binary.html?path=chrome-for-testing/");
    }

    //解决如下： 模拟浏览器滚动滚动条 解决懒加载问题
    public static void guge(String url){
        //这里设置下载的驱动路径，Windows对应chromedriver.exe Linux对应chromedriver，具体路径看你把驱动放在哪
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\yansunling\\Desktop\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        //ssl证书支持
        options.setCapability("acceptSslCerts", true);
        //截屏支持
        options.setCapability("takesScreenshot", true);
        //css搜索支持
        options.setCapability("cssSelectorsEnabled", true);
        options.setHeadless(true);


        ChromeDriver driver = new ChromeDriver(options);
        //设置超时，避免有些内容加载过慢导致截不到图
        driver.manage().timeouts().pageLoadTimeout(1, TimeUnit.MINUTES);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
        driver.manage().timeouts().setScriptTimeout(1, TimeUnit.MINUTES);
        try {
            //设置需要访问的地址
            driver.get(url);




            //获取高度和宽度一定要在设置URL之后，不然会导致获取不到页面真实的宽高；
            Long width = (Long)driver.executeScript("return document.documentElement.scrollWidth");
            Long height =0L;

            //这里需要模拟滑动，有些是滑动的时候才加在的
            long temp_height = 0;
            while (true) {
                //每次滚动500个像素，因为懒加载所以每次等待2S 具体时间可以根据具体业务场景去设置
                Thread.sleep(2000);
                driver.executeScript("window.scrollBy(0,500)");

                height = (Long) driver.executeScript("return document.documentElement.scrollHeight");
                System.out.println("高度："+height+"temp_height:"+temp_height);

                temp_height += 500;
                if(temp_height>=height){
                    break;
                }
            }
            //设置窗口宽高，设置后才能截全
            driver.manage().window().setSize(new Dimension(width.intValue(), height.intValue()));
            //设置截图文件保存的路径
            String screenshotPath = "C:\\Users\\yansunling\\Desktop\\imgGG.png";
            File srcFile = driver.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File(screenshotPath));
        }catch (Exception e){
            throw new RuntimeException("截图失败",e);
        }finally {
            driver.quit();
        }
    }

}
