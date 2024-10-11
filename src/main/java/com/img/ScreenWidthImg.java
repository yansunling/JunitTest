package com.img;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ScreenWidthImg {

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
//        options.addArguments("--window-size=1920,1080");
//        options.addArguments("--start-maximized");
        ChromeDriver driver = new ChromeDriver(options);
        //设置超时，避免有些内容加载过慢导致截不到图
        driver.manage().timeouts().pageLoadTimeout(1, TimeUnit.MINUTES);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
        driver.manage().timeouts().setScriptTimeout(1, TimeUnit.MINUTES);
        try {
            //设置需要访问的地址
            driver.get(url);



            // 获取页面的宽度和高度
            JavascriptExecutor js = (JavascriptExecutor) driver;

            Long height = (Long) js.executeScript("return document.body.scrollHeight");
            Long width=(Long)driver.executeScript("let div=document.getElementsByClassName('datagrid-body'); div[1].scrollLeft+= 8000; return div[1].scrollWidth; ");
            if(width==null){
                width = (Long) js.executeScript("return document.body.scrollWidth");
            }
            // 创建一个与页面大小相同的图像

            int startWidth=0;


            List<BufferedImage> picList=new ArrayList<>();
            // 分段截取图像
            for (int x = 0; x < 2; x ++) {
                Thread.sleep(1000);
                // 截取当前可见部分的图像
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                BufferedImage img = ImageIO.read(screenshot);
                if(x==0){
                    int imgWidth = img.getWidth();
                    img = img.getSubimage(97, 0,imgWidth-97, img.getHeight());
                }
                if(x==1){
                    img = img.getSubimage(0, 0, 120+87, img.getHeight());
                    // 将当前图像写入到全图像中
                }
                ImageIO.write(img, "png", new File("C:\\Users\\yansunling\\Desktop\\"+x+".png"));
                driver.executeScript("let div=document.getElementsByClassName('datagrid-body'); div[1].scrollLeft-= 150; ");
                picList.add(img);
            }
            int totalWidth=0;
            for(BufferedImage img:picList){
                totalWidth+=img.getWidth();
            }


            BufferedImage fullImage = new BufferedImage(totalWidth, height.intValue(), BufferedImage.TYPE_INT_RGB);
            int currentX = 0;
            for(int i=picList.size()-1;i>=0;i--){
                BufferedImage img = picList.get(i);
                fullImage.getGraphics().drawImage(img, currentX, 0, null);
                currentX += img.getWidth();
            }
            // 保存完整图像
            ImageIO.write(fullImage, "png", new File("C:\\Users\\yansunling\\Desktop\\imgGG.png"));





        }catch (Exception e){
            throw new RuntimeException("截图失败",e);
        }finally {
            driver.quit();
        }
    }

}
