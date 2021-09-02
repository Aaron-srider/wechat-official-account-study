package com.example.wechatofficialaccountstudy.utils;

import com.example.wechatofficialaccountstudy.model.BaseXml;
import com.example.wechatofficialaccountstudy.model.TextXmlResponse;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.AnnotationTypeMismatchException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class XmlUtil {

    /**
     * 将Map形式的xml映射到指定类的对象中，该类的属性由注解标注，映射关系是：当且仅仅当Map中的key
     * 与目标类的属性注解的name属性相同且目标属性为 java.lang.String 类型时，将key对应的value
     * 映射到属性中。该方法支持的xml注解工具是 jackson-dataformat-xml，具体处理的注解是：@JacksonXmlProperty。
     *
     * @param desClazz 目标类
     * @param srcMap   源map
     * @param <T>      目标类的泛型变量
     * @return 返回生成的目标类对象
     * @throws NoSuchFieldException     在目标类中没找到指定属性
     * @throws IllegalArgumentException 目标类中指定属性类型不为String
     */
    public static <T> T mapToBean(Class<T> desClazz, Map<String, String> srcMap) throws NoSuchFieldException, IllegalArgumentException{
        Set<String> keySet = srcMap.keySet();

        T desObj = null;
        try {
            desObj = desClazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        for (String elementNode : keySet) {
            String elementValue = srcMap.get(elementNode);
            Field annotatedField = null;

            try {
                annotatedField = findFieldByAnnotation(desClazz, elementNode);
            } catch (NoSuchFieldException ex) {
                log.error("no such field annotated by name " + elementNode);
                throw ex;
            }


            annotatedField.setAccessible(true);
            if(!annotatedField.getType().getTypeName().equals("java.lang.String")) {
                log.error("des field " + annotatedField.getName() + " is not an instance of java.lang.String");
                throw new IllegalArgumentException();
            } else {
                try {
                    annotatedField.set(desObj, elementValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }
        return desObj;
    }

    /**
     * 找到指定类中，标注了注解@JacksonXmlProperty且注解的localName属性为targetLocalName的属性。
     *
     * @param desClazz        目标类
     * @param targetLocalName 目标注解localName值
     * @return 返回找到的属性
     * @throws NoSuchFieldException 若找不到目标属性，抛出该错误
     */
    public static Field findFieldByAnnotation(Class desClazz, String targetLocalName) throws NoSuchFieldException {
        List<Field> fieldList = BeanUtils.getAllDeclaredFields(desClazz);

        for (Field field : fieldList) {
            field.setAccessible(true);
            JacksonXmlProperty annotation = field.getDeclaredAnnotation(JacksonXmlProperty.class);

            if (annotation != null) {
                String localName = annotation.localName();
                if (localName.equals(targetLocalName)) {
                    return field;
                }
            }
        }

        throw new NoSuchFieldException();
    }

    public static void main(String[] args) {

        Map<String, String> map = new HashMap<>();

        map.put("CreateTime", "3333");
        map.put("FromUserName", "wc");
        map.put("ToUserName", "cc");
        map.put("MsgType", "text");
        map.put("Content", "hello");
        TextXmlResponse xml = null;
        try {
            xml = mapToBean(TextXmlResponse.class, map);
            System.out.println(xml);
        } catch (NoSuchFieldException e) {
            System.out.println("error");
        }

    }
}