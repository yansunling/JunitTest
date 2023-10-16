package com.javaBuild.crmx.data.constants;

import com.dy.components.annotations.CJ_column;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.yd.common.utils.RedisUtils;
import com.yd.utils.common.CollectionUtil;
import com.yd.utils.common.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

@Slf4j
public class MyCacheSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(o);
        String key=jsonGenerator.getOutputContext().getCurrentName();
        try {
            Class<?> clazz = jsonGenerator.getOutputContext().getCurrentValue().getClass();
            Field declaredField = clazz.getDeclaredField(key);
            CJ_column cjColumn = declaredField.getAnnotation(CJ_column.class);
            if(cjColumn!=null){
                String code = cjColumn.code();
                if(StringUtils.isNotBlank(code)){
                    String cacheValue = getCacheValue(code, o);
                    jsonGenerator.writeStringField(key+"_name", cacheValue);
                }
            }
        } catch (Exception e) {
            log.info("serialize error,key:"+key,e);
        }
    }

    public String getCacheValue(String code,String obj){
        if(StringUtils.isBlank(obj)){
            return "";
        }
        String result = RedisUtils.getSingleMapValue(CRMXConstant.QUERY_CACHE_REDIS_PREX +code, obj, String.class);
        if(StringUtils.isNotBlank(result)){
            return result;
        }
        String keyName=code+obj;
        //取query上数据库缓存
        String redisKey="dy:query:public:cache:cip_admin_code_crm";
        List<String> mapValue = RedisUtils.getMapValue(redisKey, String.class, keyName);
        if(CollectionUtil.isNotEmpty(mapValue)){
            String value = mapValue.get(0);
            if(StringUtils.isNotBlank(value)){
                return value;
            }

        }
        return obj;
    }


}
