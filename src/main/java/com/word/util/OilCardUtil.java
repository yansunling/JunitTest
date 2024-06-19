package com.word.util;

import cn.hutool.core.date.DateUtil;
import com.dy.components.annotations.CJ_column;
import com.dy.components.logs.api.logerror.GlobalErrorInfoException;
import com.dy.components.logs.error.spring.GlobalErrorInfoEnum;
import com.google.common.collect.Lists;
import com.yd.common.cache.CIPRedisUtils;
import com.yd.common.data.CIPResponseMsg;
import com.yd.common.exttype.Money;
import com.yd.common.runtime.CIPErrorCode;
import com.yd.common.runtime.CIPRuntime;
import com.yd.common.runtime.CIPRuntimeOperator;
import com.yd.common.utils.RedisUtils;
import com.yd.utils.common.CollectionUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class OilCardUtil {
    //接口返回成功对象
    public static CIPResponseMsg success() {
        CIPResponseMsg msg = new CIPResponseMsg();
        msg.errorCode = CIPErrorCode.CALL_SUCCESS.code;
        msg.msg = CIPErrorCode.CALL_SUCCESS.name;
        return msg;
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set emptyNames = new HashSet();
        for (java.beans.PropertyDescriptor pd : pds) {
            //check if value of this property is null then add it to the collection
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {//特定字符写在此处过滤，收集不需要copy的字段列表。此处过滤null为例
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return (String[]) emptyNames.toArray(result);
    }



    public static boolean isNumeric(String str) {
        // 使用正则表达式匹配数字，包括整数和小数
        String pattern = "^[-+]?\\d+(\\.\\d+)?$";
        return str.matches(pattern);
    }

    public static boolean isValidDateFormat(String dateString) {
        String regex = "\\d{4}-\\d{2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dateString);
        return matcher.matches();
    }

    public static String getRedisDayNo(String busiType, int length) {
        String today = DateUtil.format(new Date(), "yyyyMMdd");
        String redisKey = "tlwl:tmsp:public:cache:numstart:" + today + ":" + busiType;
        String value = RedisUtils.get(redisKey, String.class);
        if (StringUtils.isBlank(value)) {
            value = "1";
            RedisUtils.putWithStringKey(redisKey, 1, 86400);
        }
        RedisUtils.incr(redisKey);
        //数据累加
        return busiType + today + StringUtils.leftPad(value, length, "0");
    }

    public static String asynGetRedisDayNo(String busiType, int length) {
        String today = DateUtil.format(new Date(), "yyyyMMdd");
        String redisKey = "tlwl:tmsp:public:cache:numstart:" + today + ":" + busiType;
        String value = "";
        Jedis jedis = null;
        try {
            jedis = CIPRedisUtils.getJedisResource();
            String script = "local val = redis.call('incr', KEYS[1]) return val";
            // 执行Lua脚本
            Object result = jedis.eval(script, 1, redisKey);
            value = result.toString();
        }catch (Exception e) {
           log.info("script error",e);
        } finally {
            CIPRedisUtils.returnJedisResource(jedis, false);
        }
        //数据累加
        return busiType + today + StringUtils.leftPad(value, length, "0");
    }





    public static String getRedisDayNo(String busiType,int length,int addNum) {
        String today = DateUtil.format(new Date(), "yyyyMMdd");
        String redisKey = "tlwl:tmsp:public:cache:numstart:" + today + ":" + busiType;
        String value = RedisUtils.get(redisKey, String.class);
        if (StringUtils.isBlank(value)) {
            value = "1";
            RedisUtils.putWithStringKey(redisKey, 1, 86400);
        }
        value=Integer.parseInt(value)+addNum+"";
        //数据累加
        return busiType + today + StringUtils.leftPad(value, length, "0");
    }

    public static void setRedisDayNo(String busiType,int addNum) {
        String today = DateUtil.format(new Date(), "yyyyMMdd");
        String redisKey = "tlwl:tmsp:public:cache:numstart:" + today + ":" + busiType;
        String value = RedisUtils.get(redisKey, String.class);
        if (StringUtils.isBlank(value)) {
            return;
        }
        value=Integer.parseInt(value)+addNum+"";
        RedisUtils.putWithStringKey(redisKey, value, 86400);
    }






    public static String[] removeStr(String[] array, String... values) {
        // 将原始数组转换为 ArrayList
        ArrayList<String> list = new ArrayList<>(Arrays.asList(array));

        for (String value : values) {
            list.remove(value);
        }
        // 将 ArrayList 转换回字符串数组
        return list.toArray(new String[0]);
    }

    public static Money addMoney(Money... monies) {
        Money total = Money.ofFen(0);
        for (Money money : monies) {
            if (money != null) {
                total = total.add(money);
            }
        }
        return total;
    }

    @SneakyThrows
    public static SXSSFWorkbook createWorkBook(String sheetName, Map<String, String> excelTitle, Map<Integer, String[]> selectValue) {
        SXSSFWorkbook wb = new SXSSFWorkbook();
        Sheet sheet1 = wb.createSheet(sheetName);
        Object[] title = excelTitle.keySet().toArray();
        int i = 0;
        Row row = sheet1.createRow(0);
        Object[] var8 = title;
        int indexCell = title.length;
        for (int var10 = 0; var10 < indexCell; ++var10) {
            Object s = var8[var10];
            Cell cell = row.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellStyle(getTitleStyle(wb));
            cell.setCellValue((String) excelTitle.get(s));
            sheet1.setColumnWidth(i, 5120);
            ++i;
        }
        if (selectValue != null) {
            Set<Integer> keys = selectValue.keySet();
            for (Integer key : keys) {
                String[] options = selectValue.get(key);
                CellRangeAddressList addressList = new CellRangeAddressList(0, 1000, key, key);
                DataValidationHelper validationHelper = sheet1.getDataValidationHelper();
                DataValidationConstraint constraint = validationHelper.createExplicitListConstraint(options);
                DataValidation dataValidation = validationHelper.createValidation(constraint, addressList);
                sheet1.addValidationData((XSSFDataValidation) dataValidation);
            }
        }
        return wb;
    }
    private static CellStyle getTitleStyle(Workbook workbook) {
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setBorderLeft(BorderStyle.THIN);
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setBorderTop(BorderStyle.THIN);
        titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return titleStyle;
    }

    public  static String BuildFileUrl(String host,String fileUrl){
        if(StringUtils.isBlank(fileUrl)){
            return "";
        }
        String[] files = fileUrl.split(",");
        List<String> fileList = Arrays.asList(files);
        List<List<String>> partitions = Lists.partition(fileList, 100);
        StringBuffer sb=new StringBuffer();
        for(List<String> partition:partitions){
            sb.append("<a target='_blank' href='"+host+"/fsm/preview.html?file_serial_nos="+ StringUtils.join(partition.toArray(),",")+"' style='text-decoration: none;'>相关附件</a>   ");
        }
        return sb.toString();
    }





}
