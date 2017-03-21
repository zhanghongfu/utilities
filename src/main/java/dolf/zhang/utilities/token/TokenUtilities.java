package dolf.zhang.utilities.token;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author dolf
 * @description    加密
 * @date 17/3/21
 */
public class TokenUtilities {

    /**
     * The Logger.
     */
    static Logger logger = LoggerFactory.getLogger(TokenUtilities.class);

    /**
     * The constant default_secret_key.
     */
    final static String default_secret_key = "default_secret_key";

    final static TimeUnit default_time_unit = TimeUnit.SECONDS;


    final static int  default_amount = 1000;

    private TokenUtilities() {
    }


    /**
     * Encrypt string.
     *
     * @param userId     the user id
     * @param secret_key the secret key  秘钥
     * @param unit       the unit   不支持(MICROSECONDS,NANOSECONDS)
     * @param amount     the amount
     * @return the string
     * @author dolf
     * @date 2016年12月29日 15:05:12
     * @description 生成token(加密规则md5 userId秘钥时间戳)token规则:32+userId+时间戳)
     * @responseBody
     * @requestPayLoad
     */
    public static String generate(String userId, String secret_key, TimeUnit unit, int amount) {
        if (unit == null) {
            logger.warn("time unit is empty ,set time unit is seconds");
            unit = default_time_unit;
        }

        //当前时间
        Date currentTime = new Date();
        switch (unit) {

            case DAYS: {
                currentTime = DateUtils.addDays(currentTime, amount);
            }
            case HOURS: {
                currentTime = DateUtils.addHours(currentTime, amount);
            }
            //分钟
            case MINUTES: {
                currentTime = DateUtils.addMinutes(currentTime, amount);
            }
            //秒
            case SECONDS: {
                currentTime = DateUtils.addSeconds(currentTime, amount);
            }
            case MILLISECONDS: {
                currentTime = DateUtils.addMilliseconds(currentTime, amount);
            }
            default: {
                logger.warn("time unit is default ,set time unit is seconds");
                currentTime = DateUtils.addSeconds(currentTime, amount);
            }
        }

        if (StringUtils.isEmpty(secret_key)) {
            secret_key = default_secret_key;
            logger.warn("secret_key is empty ,set secret_key is default_secret_key");
        }
        String data = userId + secret_key + currentTime.getTime();
        return DigestUtils.md5Hex(data).toLowerCase() + userId + currentTime.getTime();
    }




    /**
     * Encrypt string.
     *
     * @param userId     the user id
     * @param secret_key the secret key  秘钥
     * @param amount     the amount
     * @return the string
     * @author dolf
     * @date 2016年12月29日 15:05:12
     * @description 生成token(加密规则md5 userId秘钥时间戳)token规则:32+userId+时间戳)
     * @responseBody
     * @requestPayLoad
     */
    public static String generate(String userId, String secret_key, int amount) {
        return generate(userId , secret_key,default_time_unit , amount);
    }


    /**
     * Encrypt string.
     *
     * @param userId     the user id
     * @param amount     the amount
     * @return the string
     * @author dolf
     * @date 2016年12月29日 15:05:12
     * @description 生成token(加密规则md5 userId秘钥时间戳)token规则:32+userId+时间戳)
     * @responseBody
     * @requestPayLoad
     */
    public static String generate(String userId, int amount) {
        return generate(userId , default_secret_key,default_time_unit , amount);
    }


    /**
     * Encrypt string.
     *
     * @param userId     the user id
     * @return the string
     * @author dolf
     * @date 2016年12月29日 15:05:12
     * @description 生成token(加密规则md5 userId秘钥时间戳)token规则:32+userId+时间戳)
     * @responseBody
     * @requestPayLoad
     */
    public static String generate(String userId) {
        return generate(userId , default_secret_key,TimeUnit.SECONDS , default_amount);
    }


    /**
     * Check token boolean.
     *
     * @param token the token
     * @return 有效 true 反之
     * @author dolf
     * @date 2016年12月29日 15:23:59
     * @description 验证token是否有效
     * @responseBody
     * @requestPayLoad
     */
    public static boolean checkToken(String token, String secret_key) {
        boolean bln = false;
        if (StringUtils.isEmpty(token))
            return bln;
        else {
            if (token.length() >= 46) {
                long time = NumberUtils.toLong(token.substring(token.length() - 13, token.length()), 0);
                if (StringUtils.isEmpty(secret_key)) secret_key = default_secret_key;
                String userId = token.substring(32, token.length() - 13);
                String data = userId + secret_key + time;
                String _token = DigestUtils.md5Hex(data).toLowerCase() + userId + time;
                if (!_token.equals(token)) return bln;
                Date today = new Date();
                return time >= today.getTime();
            }
            return bln;
        }
    }



    /**
     * Check token boolean.
     *
     * @param token the token
     * @return 有效 true 反之
     * @author dolf
     * @date 2016年12月29日 15:23:59
     * @description 验证token是否有效
     * @responseBody
     * @requestPayLoad
     */
    public static boolean checkToken(String token) {
        return checkToken(token , default_secret_key);
    }

    /**
     * Gets user id.
     *
     * @param token the token
     * @param secret_key the secret_key
     * @return the user id
     * @author dolf
     * @date 2016年12月29日 15:23:59
     * @description token中抽取用户信息
     * @responseBody
     * @requestPayLoad
     */
    public static String decrypt(String token, String secret_key) {
        if (checkToken(token, secret_key)) {//检查token是否有效
            return token.substring(32, token.length() - 13);
        } else {
            logger.warn("token is failure {token:" + token + "}");
        }
        return null;
    }

    /**
     * Gets user id.
     *
     * @param token the token
     * @return the user id
     * @author dolf
     * @date 2016年12月29日 15:23:59
     * @description token中抽取用户信息
     * @responseBody
     * @requestPayLoad
     */
    public static String decrypt(String token) {
        return  decrypt(token , default_secret_key);
    }
}
