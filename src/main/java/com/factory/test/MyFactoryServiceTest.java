package com.factory.test;


import com.factory.AnimalContext;
import com.factory.AnimalFactory;
import com.factory.Cat;
import com.factory.MyFactoryService;
import com.factory.data.CatData;
import com.factory.data.DogData;
import com.word.dataSource.vo.CompAssetBaseInfoChangeVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class MyFactoryServiceTest implements ApplicationContextAware {

    ApplicationContext ac;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ac=applicationContext;

    }
//    @Autowired
//    private MyFactoryService myFactoryService;
    @Autowired
    private AnimalFactory<CatData> factory;
    @Autowired
    private AnimalContext<DogData> context;

    @Test
    public  void test() throws Exception{
//        CompAssetBaseInfoChangeVO changeVO=new CompAssetBaseInfoChangeVO();
//        changeVO.setAsset_company("33");
//        //cat.catEat(changeVO);
//        cat.eat("22");

//        String type = myFactoryService.factoryMode("cat");
//        System.out.println(type);

//        System.out.println(factory.animal(new CatData()));
        System.out.println(context.should(new DogData()));
    }


}
