package com.weiwan.support.common.options;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.weiwan.support.common.utils.ReflectUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/8/13 13:36
 * @Package: com.weiwan.common.options.OptionParser
 * @ClassName: OptionParser
 * @Description:
 **/
public class OptionParser {

    private String[] args;
    private JCommander jCommander;

    public OptionParser(String[] args) {
        jCommander = new JCommander();
        this.args = args;
    }


    public <T> T parse(Class<T> tClass) {
        T options = null;
        try {
            options = tClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        jCommander.addObject(options);
        jCommander.parse(args);
        return options;
    }


    public static <T> String[] optionToArgs(T options) throws Exception {
        Map<String, Object> optionToMap = optionToMap(options);
        List<String> argsList = new ArrayList<>();
        for (String key : optionToMap.keySet()) {
            String var = String.valueOf(optionToMap.get(key));
            if(StringUtils.isEmpty(var) || "null".equalsIgnoreCase(var)){
                continue;
            }
            if (StringUtils.isNotEmpty(var)) {
                argsList.add(key);
                if ("false".equalsIgnoreCase(var) || "true".equalsIgnoreCase(var)) {
                    continue;
                }
                argsList.add(var);
            }
        }

        String[] argsAll = argsList.toArray(new String[argsList.size()]);
        return argsAll;
    }


    public static <T> Map<String, Object> optionToMap(T options) throws Exception {
        Field[] declaredFields = options.getClass().getDeclaredFields();
        Map<String, Object> res = new HashMap();
        for (Field field : declaredFields) {
            Object fieldValue = ReflectUtil.getObjectValue(options, field);
            Parameter optionField = field.getAnnotation(Parameter.class);
            if (optionField != null) {
                String[] oweKeys = optionField.names();
                if (oweKeys.length > 0) {
                    //取oweKeys的数据
                    res.put(oweKeys[0], fieldValue);
                }
            }
        }
        return res;
    }


//        public static void main(String[] args) {
//        OptionParser objectOptionParserV2 = new OptionParser(args);
//        JCommanderTest parse = objectOptionParserV2.parse(JCommanderTest.class);
//        System.out.println(parse);
//    }
}
