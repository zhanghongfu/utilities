package dolf.zhang.utilities.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * json 反序列化工具
 */
@SuppressWarnings("deprecation")
public class JackSonUtilities {

    static Logger logger = LoggerFactory.getLogger(JackSonUtilities.class);
    private static ObjectMapper m = new ObjectMapper();


    public static <T> T toBean(String jsonAsString, Class<T> pojoClass)
            throws JsonGenerationException {
        try {
            if (StringUtils.isEmpty(jsonAsString))
                return null;
            return m.readValue(jsonAsString, pojoClass);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static String toString(Object pojo) {
        try {
            StringWriter sw = new StringWriter();
            m.writeValue(sw, pojo);
            return sw.toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }

    }

    /**
     * 转换json字符串 不包括类型信息,如果转换出现异常返回空字符串
     */
    public static final String toJsonString(Object obj) {
        String json = "";
        try {
            json = m.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error(obj.getClass().getName() + "对象转换为json出错！", e);
        }
        return json;
    }


    public static <T> Map<String, T> toMap(String jsonAsString)
            throws JsonGenerationException {
        try {
            return m.readValue(jsonAsString,
                    new TypeReference<Map<String, T>>() {
                    });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @JsonCreator
    public static <T> LinkedHashMap<String, T> toLinkedHashMap(String jsonAsString)
            throws JsonGenerationException {
        try {

            return m.readValue(jsonAsString,
                    new TypeReference<LinkedHashMap<String, T>>() {
                    });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T> Map<String, T>[] toMapArray(String jsonAsString)
            throws JsonGenerationException {
        try {
            return m.readValue(jsonAsString,
                    new TypeReference<Map<String, T>[]>() {
                    });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T> List<T> toList(String jsonAsString, Class<T> cl) {

        JavaType javaType = m.getTypeFactory().constructParametricType(List.class, cl);
        List<T> t = null;
        try {
            t = m.readValue(jsonAsString, javaType);
        } catch (IOException e) {
            e.printStackTrace();

        }
        return t;
    }

    public static void main(String[] args) throws JsonGenerationException {
        Map<String, Object>[] maps = toMapArray("[{\"id\":568096,\"name\":\"万家康\",\"description\":\"万家康\"},{\"id\":500043,\"name\":\"卡行天下\",\"description\":\"卡行天下\"},{\"id\":323141,\"name\":\"亚风快运\",\"description\":\"亚风快运\"},{\"id\":332098,\"name\":\"用户自提\",\"description\":\"用户自提\"},{\"id\":336878,\"name\":\"京东大件开放承运商\",\"description\":\"京东大件开放承运商\"},{\"id\":328977,\"name\":\"上药物流\",\"description\":\"上药物流\"},{\"id\":313214,\"name\":\"如风达\",\"description\":\"如风达\"},{\"id\":222693,\"name\":\"贝业新兄弟\",\"description\":\"贝业新兄弟\"},{\"id\":171686,\"name\":\"易宅配物流\",\"description\":\"易宅配物流\"},{\"id\":171683,\"name\":\"一智通物流\",\"description\":\"一智通物流\"},{\"id\":6012,\"name\":\"斑马物联网\",\"description\":\"斑马物联网\"},{\"id\":5419,\"name\":\"中铁物流\",\"description\":\"中铁物流\"},{\"id\":4832,\"name\":\"安能物流\",\"description\":\"安能物流\"},{\"id\":4605,\"name\":\"微特派\",\"description\":\"微特派\"},{\"id\":3046,\"name\":\"德邦快递\",\"description\":\"德邦快运\"},{\"id\":2909,\"name\":\"快行线物流\",\"description\":\"快行线物流\"},{\"id\":2462,\"name\":\"天地华宇\",\"description\":\"天地华宇\"},{\"id\":2460,\"name\":\"佳吉快运\",\"description\":\"佳吉快运\"},{\"id\":2461,\"name\":\"新邦物流\",\"description\":\"新邦物流\"},{\"id\":2465,\"name\":\"国通快递\",\"description\":\"国通快递\"},{\"id\":2171,\"name\":\"中国邮政挂号信\",\"description\":\"中国邮政挂号信\"},{\"id\":2170,\"name\":\"邮政快递包裹\",\"description\":\"邮政快递包裹\"},{\"id\":2105,\"name\":\"速尔快递\",\"description\":\"速尔快递\"},{\"id\":2101,\"name\":\"嘉里大通物流\",\"description\":\"嘉里大通物流\"},{\"id\":2100,\"name\":\"全一快递\",\"description\":\"全一快递\"},{\"id\":2096,\"name\":\"联邦快递\",\"description\":\"联邦快递\"},{\"id\":2094,\"name\":\"快捷速递\",\"description\":\"快捷速递\"},{\"id\":471,\"name\":\"龙邦快递\",\"description\":\"龙邦快递\"},{\"id\":2130,\"name\":\"德邦物流\",\"description\":\"德邦物流\"},{\"id\":2016,\"name\":\"全峰快递\",\"description\":\"全峰快递\"},{\"id\":2009,\"name\":\"天天快递\",\"description\":\"天天快递\"},{\"id\":1748,\"name\":\"百世快递\",\"description\":\"百世快递\"},{\"id\":1747,\"name\":\"优速快递\",\"description\":\"优速快递\"},{\"id\":1549,\"name\":\"宅急便\",\"description\":\"宅急便\"},{\"id\":1499,\"name\":\"中通速递\",\"description\":\"中通速递\"},{\"id\":1409,\"name\":\"宅急送\",\"description\":\"宅急送\"},{\"id\":1327,\"name\":\"韵达快递\",\"description\":\"韵达快递\"},{\"id\":1274,\"name\":\"厂家自送\",\"description\":\"厂家自送\"},{\"id\":470,\"name\":\"申通快递\",\"description\":\"申通快递\"},{\"id\":467,\"name\":\"顺丰快递\",\"description\":\"顺丰快递\"},{\"id\":465,\"name\":\"邮政EMS\",\"description\":\"邮政EMS\"},{\"id\":463,\"name\":\"圆通快递\",\"description\":\"圆通快递\"}]");
        for (Map map : maps) {
            System.out.println("insert into tb_express_shop (shop_id , name , valid , create_time , update_time , logistics_id )values ('190008','" + MapUtils.getString(map, "name") + "','1',now(),now() ,'" + MapUtils.getInteger(map, "id") + "');");
        }
    }

}
