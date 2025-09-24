package com.sqlFile;

import com.word.dataSource.vo.CompAssetBaseInfoPO;
import com.word.util.AssetUtil;
import com.yd.utils.common.DateUtils;
import com.yd.utils.common.StringUtils;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class CreateAssetNum implements ApplicationContextAware {
    ApplicationContext ac;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ac=applicationContext;

    }
    @Qualifier("jdbcTemplateYL")
    @Autowired
    private JdbcTemplate jdbcTemplateYL;

    @Test
    public void test() throws Exception {
        String sql="select * from comp.comp_asset_base_info main where delete_flag='N' order by main.create_time ,main.serial_no  ";
        String existNumSql="select right(asset_num,5)  from comp.comp_asset_base_info where length(asset_num)=11";
        List<String> numList = jdbcTemplateYL.queryForList(existNumSql, String.class);
        List<CompAssetBaseInfoPO> baseInfoPOS = jdbcTemplateYL.query(sql, new BeanPropertyRowMapper<>(CompAssetBaseInfoPO.class) );
        List<String> sqlList=new ArrayList<>();
        int num=1;
        for(int i=0;i<baseInfoPOS.size();i++){
            CompAssetBaseInfoPO compAssetBaseInfoPO = baseInfoPOS.get(i);
            if(compAssetBaseInfoPO.getAsset_num().length()==11){
                String updateSql="update comp.comp_asset_base_info set asset_num='"+compAssetBaseInfoPO.getAsset_num()+"',original_asset_num='"+compAssetBaseInfoPO.getAsset_num()+"' where serial_no='"+compAssetBaseInfoPO.getSerial_no()+"';";
                sqlList.add(updateSql);
            }else{
                String startNum = AssetUtil.appendZero(num, 5);
                while (numList.contains(startNum)){
                    num++;
                    startNum = AssetUtil.appendZero(num, 5);
                }
                String newAssetNum = setAssetNum(compAssetBaseInfoPO, startNum);
                String updateSql="update comp.comp_asset_base_info set asset_num='"+newAssetNum+"',original_asset_num='"+compAssetBaseInfoPO.getAsset_num()+"' where serial_no='"+compAssetBaseInfoPO.getSerial_no()+"';";
                sqlList.add(updateSql);
                num++;
            }



        }

        File allFile = new File("C:\\Users\\yansunling\\Desktop\\asset.sql");
        FileUtils.writeLines(allFile, "utf-8", sqlList);


    }



    private String setAssetNum(CompAssetBaseInfoPO infoPO,String startNum){

        String shortYear= DateUtils.format(new Date(), "yy");
        String assetSubject = infoPO.getAsset_subject().substring(0,2);
        String firstPingYin = getFirstPingYin(assetSubject);
        String assetNum=firstPingYin+"-"+shortYear+"-"+startNum;
        return assetNum;
    }
    public static String getFirstPingYin(String inputString) {
        String pinyinName = "";
        char[] nameChar = inputString.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    String[] pinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(
                            nameChar[i], defaultFormat);
                    if(pinyinStringArray!=null&&pinyinStringArray.length>0){
                        pinyinName += pinyinStringArray[0].charAt(0);
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName += nameChar[i];
            }
        }
        return pinyinName;
    }












}
