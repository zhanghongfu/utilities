package dolf.zhang.utilities.string;

import dolf.zhang.utilities.constant.ConstantUtilities;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dolf
 * @version V1.0
 * @Description: 字符串帮助类
 * @date 16/1/8
 */
public class StringUtilities {

    private StringUtilities() {
    }


    /**
     * byte 转 string
     *
     * @param data
     * @return
     */
    public static String convertString(byte[] data, int offset, int length) {
        if (data == null) {
            return null;
        } else {
            try {
                return new String(data, offset, length, "utf-8");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * string 转 byte
     *
     * @param data
     * @return
     */
    public static byte[] convertBytes(String data) {
        if (data == null) {
            return null;
        } else {
            try {
                return data.getBytes(ConstantUtilities.CHARSET_UTF8);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * string 转 list
     *
     * @param str
     * @return
     */
    public static List<Long> splitStrToList(String str) {
        List<Long> list = new ArrayList<Long>();
        if (StringUtils.isEmpty(str)) {
            return new ArrayList<Long>(0);
        }
        String[] split = StringUtils.split(str, ConstantUtilities.SEGMENTATION);
        for (String string : split) {
            list.add(NumberUtils.toLong(string, -1l));
        }
        return list;
    }


    /**
     * string 转 list
     *
     * @param str
     * @return
     */
    public static List<Integer> splitStrToListInt(String str) {
        List<Integer> list = new ArrayList<Integer>();
        if (StringUtils.isEmpty(str)) {
            return new ArrayList<Integer>(0);
        }
        String[] split = StringUtils.split(str, ConstantUtilities.SEGMENTATION);
        for (String string : split) {
            list.add(NumberUtils.toInt(string, -1));
        }
        return list;
    }


    /**
     * string 转 list
     *
     * @param str
     * @return
     */
    public static List<String> splitStrToListString(String str) {
        List<String> list = new ArrayList<String>();
        if (StringUtils.isEmpty(str)) {
            return new ArrayList<String>(0);
        }
        String[] split = StringUtils.split(str, ConstantUtilities.SEGMENTATION);
        for (String string : split) {
            list.add(string);
        }
        return list;
    }


    /**
     * 将string 转换为 long
     *
     * @param str
     * @return 空 list
     */
    public static List<Long> strLongList(String str, String splitChars) {
        if (StringUtils.isEmpty(str)) {
            return new ArrayList<Long>(0);
        }
        return splitStrLongList(str, splitChars);
    }

    /**
     * 拆分字符串，为long 的list类型
     *
     * @param str
     * @param splitChars
     * @return
     */
    public static List<Long> splitStrLongList(String str, String splitChars) {
        if (StringUtils.isEmpty(str))
            return new ArrayList<Long>(0);
        String[] strs = str.split(splitChars);
        List<Long> list = new ArrayList<Long>(strs.length);
        for (String item : strs) {
            list.add(NumberUtils.toLong(item));
        }
        return list;
    }

    /**
     * 拆分字符串，为Integer 的list类型
     *
     * @param str
     * @param splitChars
     * @return
     */
    public static List<Integer> splitStrIntList(String str, String splitChars) {
        if (StringUtils.isEmpty(str))
            return new ArrayList<Integer>(0);
        String[] strs = str.split(splitChars);
        List<Integer> list = new ArrayList<Integer>(strs.length);
        for (String item : strs) {
            list.add(NumberUtils.toInt(item));
        }
        return list;
    }

    /**
     * 将string 转换为 long
     * 默认分割符为,
     *
     * @param strs
     * @return 空 list
     */
    public static List<Long> strLongList(String strs) {
        return strLongList(strs, ConstantUtilities.SEGMENTATION);
    }

    /**
     * 将string 转换为 long
     *
     * @param str
     * @return 为 null
     */
    public static List<Long> strLongNullList(String str, String splitChars) {
        if (StringUtils.isEmpty(str)) {
            return new ArrayList<Long>(0);
        }
        return splitStrLongList(str, splitChars);
    }

    /**
     * 将string 转换为 long
     * 默认分割符为,
     *
     * @param strs
     * @return 为 null
     */
    public static List<Long> strLongNullList(String strs) {
        return strLongNullList(strs, ConstantUtilities.SEGMENTATION);
    }

    /**
     * 将string 转为list
     *
     * @param str
     * @return 为 null
     */
    public static List<String> strNullList(String str, String splitChars) {
        if (StringUtils.isEmpty(str)) {
            return new ArrayList<String>(0);
        }
        return splitStrList(str, splitChars);
    }

    /**
     * 将string 转为list
     *
     * @param str
     * @param splitChars
     * @return
     */
    public static List<String> splitStrList(String str, String splitChars) {
        if (StringUtils.isEmpty(str)) {
            return new ArrayList<String>(0);
        }
        String[] strs = str.split(splitChars);
        List<String> list = new ArrayList<String>(strs.length);
        for (String item : strs) {
            list.add(item);
        }
        return list;
    }

    /**
     * 将string 转为list
     * 默认分割符为,
     *
     * @param strs
     * @return 为 null
     */
    public static List<String> strNullList(String strs) {
        return strNullList(strs, ConstantUtilities.SEGMENTATION);
    }

    /**
     * 将string 转为list
     *
     * @param str
     * @return 为 空list
     */
    public static List<String> strList(String str, String splitChars) {
        if (StringUtils.isEmpty(str)) {
            return new ArrayList<String>(0);
        }
        return splitStrList(str, splitChars);
    }

    /**
     * 将string 转为list
     * 默认分割符为,
     *
     * @param strs
     * @return 为 空list
     */
    public static List<String> strList(String strs) {
        return strList(strs, ConstantUtilities.SEGMENTATION);
    }


    /**
     * 将string 转为list
     *
     * @param strList
     * @param splitChars
     * @return
     */
    public static String listToStr(List<?> strList, String splitChars) {
        StringBuffer str = new StringBuffer();
        if (CollectionUtils.isEmpty(strList)) {
            return str.toString();
        }
        for (Object object : strList) {
            str.append(object);
            str.append(splitChars);
        }
        String string = str.toString();
        if (StringUtils.isNotEmpty(string)) {
            string = string.substring(0, string.lastIndexOf(","));
        }
        return string;
    }

    /**
     * list 拼接
     *
     * @param strList
     * @return
     */
    public static String listToStr(List<?> strList) {
        return listToStr(strList, ConstantUtilities.SEGMENTATION);
    }


    /**
     * 字符插入
     *
     * @param str      源内容
     * @param strLater 需要插入在那个文件之后
     * @param insert   需要插入的内容
     * @return
     */
    public static String insert(String str, String strLater, String insert) {
        int indexOf = str.indexOf(strLater);
        if (indexOf == -1) {
            return str;
        } else {

        }
        StringBuffer sb = new StringBuffer(str);
        sb.insert(indexOf + strLater.length(), "\r\n" + insert);
        return sb.toString();
    }

    /**
     * 中文转 Unicode
     *
     * @param str
     * @return
     */
    public static String chinaToUnicode(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            int chr1 = (char) str.charAt(i);
            if (chr1 >= 19968 && chr1 <= 171941) {//汉字范围 \u4e00-\u9fa5 (中文)
                result += "\\u" + Integer.toHexString(chr1);
            } else {
                result += str.charAt(i);
            }
        }
        return result;
    }

    /**
     * 判断是否为中文字符
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }


    /**
     * 字符替换
     *
     * @param str   abc
     * @param key   b
     * @param value a
     * @return aac
     */
    public static String join(String str, String key, String value) {
        if (StringUtils.isEmpty(str))
            return "";
        StringBuffer sb = new StringBuffer();

        String[] split = str.split(key);
        for (int i = 0; i < split.length; i++) {
            if (split.length == (i + 1)) {
                sb.append(split[i]);
            } else {
                sb.append(split[i]);
                sb.append(value);
            }
        }
        return sb.toString();
    }


    public static String dbColumnToProperty(String str) {
        if (StringUtils.isEmpty(str))
            return "";
        else {

            return "";
        }
    }

    //字符第一个大写
    public static String initcap(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    /**
     * 骆驼命名转db字段
     * @param name
     * @return
     */
    public static String camelToDb(String name){
        String[] split = name.split("");
        StringBuffer sb = new StringBuffer();
        for (String s : split) {
            if(!StringUtils.isAllUpperCase(s)){
                sb.append(s);
            }else{
                sb.append("_");
                sb.append(s.toLowerCase());
            }

        }
        return  sb.toString();
    }

    /**
     * db字段转骆驼命名
     * @param name
     * @return
     */
    public static String dbToCamel(String name){
        StringBuffer sb = new StringBuffer();
        if(StringUtils.isNotEmpty(name)){
            String[] split = name.split("_");
            if(split.length > 1 ){
                sb.append(split[0]);
                for (int i = 1; i < split.length ; i++ ){
                    sb.append(initcap(split[i]));
                }
            }else{
                sb.append(name);
            }
        }
        return  sb.toString();
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {

        System.out.println(dbToCamel("ab_c_a_d_sadas"));
    }


}
