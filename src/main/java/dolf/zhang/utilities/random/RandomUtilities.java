package dolf.zhang.utilities.random;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * The type Random utilities.
 *
 * @author dolf  
 * @date 16 /10/18
 * @description 随机函数
 */
public class RandomUtilities {
    private RandomUtilities() {
    }

    public static void main(String[] args) {
        System.out.println(randomLetter(10));
        System.out.println(RandomStringUtils.randomAlphabetic(10));
        System.out.println(RandomStringUtils.randomAlphanumeric(10));
    }


    /**
     * Random letter string.
     *
     * @param length the length
     * @return the string
     * @author dolf
     * @date 2016年10月18日 17:31:59
     * @description 随机生成字母
     * @responseBody
     * @requestPayLoad
     */
    public static String randomLetter(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append((char) (Math.random() * 26 + 'A') + "");
        }
        return sb.toString();
    }


    /**
     * Random chinese string.
     *
     * @param length the length
     * @return the string
     * @author dolf
     * @date 2016年10月18日 17:32:19
     * @description 随机生成中文
     * @responseBody
     * @requestPayLoad
     */
    public static String randomChinese(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int hightPos, lowPos; // 定义高低位
            Random random = new Random();
            hightPos = (176 + Math.abs(random.nextInt(39)));//获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93)));//获取低位值
            byte[] b = new byte[2];
            b[0] = (new Integer(hightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            String str = "";//转成中文
            try {
                str = new String(b, "GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            sb.append(str);
        }
        return sb.toString();
    }

    public static String randomMobile() {
        String[] s = {"133", "153", "180", "181", "189", "177", "173", "149", "130", "131", "132", "155", "156", "145", "185", "186", "176", "134", "135", "136", "137", "138", "139", "150", "151", "152", "157", "158", "159", "182", "183", "184", "187", "188", "147", "178"};
        return s[getNum(0,s.length-1)]+ RandomStringUtils.randomNumeric(8);
    }

    public static int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }
}
