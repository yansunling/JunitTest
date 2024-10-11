package com.str.string;

import com.yd.utils.common.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringNumberTest {
    public static void main(String[] args) {
        String tempData="1. 23号24号邵谷俊邵帆俊妈妈已交,2. 32俞亮已交,3. 12号吴梓境妈妈 已交,4. 38号-葛沐枫-已交,5. 25号邵梓汐已交,6. 17号张淇芮已交,7. 3朱飞逸已交,8. 7号刘柏妤已交,9. 37章展译妈妈 已交,10. 35号郭楚恬妈妈,11. 14号 张米朵已交,12. 44号颜财双已交,13. 43号虞文博已交,14. 5号朱茗嘉已交,15. 39号傅茗希已交,16. 1号王靖欢妈妈已交,17. 29号金聿宬已交,18. 2号毛思辰已交,19. 9号-吴昱辰已交,20. 28金义凡已交（忘备注了）,21. 21号    陈欣妤已交,22. 19号陈宇飞已交,23. 42号-楼峻楷已交,24. 4号-朱芷萌已交,25. 18号陈旭尧(微信 忘备注了),26. 11-吴峻辰爸爸 已交,27. 27号季宸霆妈妈 已交,28. 26号欧彻已交,29. 30号周子俊已交,30. 41楼姝好已交,31. 31号胡婧雁己交,32. 22陈浠 已交,33. 40号-童熙雅 已交,34. 15号张芯瑜 已交,35. 20-陈述妈妈 已交,36. 36龚怡瑾已交,37. 33.贾铭凯，己交,38. 34-徐嘉禾爸爸  已交,39. 13号 宋欣妈妈 已交,40. 8杨子悦 已交,41. 6号朱宸逸 已交,42. 10号 吴昱衡 已交";
        String[] dataList = tempData.split(",");
        Map<String,String> map=new HashMap<>();
        for(String str:dataList){
            str=str.split("\\.")[1].trim();
            // 定义正则表达式，匹配数字
            Pattern pattern = Pattern.compile("\\d+");
            // 创建 Matcher 对象
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()) {
                // 获取第一个匹配的数字序列
                String number = matcher.group();
                map.put(number,str);
            }
        }
        for(int i=1;i<=44;i++){
            String value = map.get(i + "");
            if(StringUtils.isBlank(value)){
                System.out.println(i);
            }
        }

    }
}
