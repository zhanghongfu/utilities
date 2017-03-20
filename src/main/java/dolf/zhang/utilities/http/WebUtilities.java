package dolf.zhang.utilities.http;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The type Web utilities.
 *
 * @author dolf  
 * @version V1.0
 * @date 16 /7/5
 * @description
 * @Description:
 */
public class WebUtilities {

    public static final String DEFAULT_CHARSET = "utf-8";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_GET = "GET";
    static Logger logger = LoggerFactory.getLogger(WebUtilities.class);
    private static int CONNECT_TIMEOUT = 60000;
    private static int READ_TIMEOUT = 60000;
    private WebUtilities() {
    }

    public static void main(String[] args) throws IOException {
        String url = "https://account.admaster.com.cn/api/oauth/access_token";
        Map<String, String> params = new HashMap<String, String>();
        params.put("client_secret","67fbfc7ae324a96870c28f17766eefa4adc52be1");
        params.put("grant_type","authorization_code");
        params.put("code","795a486447088c562414efccef0b3a0c06bf494f");
        params.put("client_id","2f10bf7e8687625747b0");
        System.out.println( doPost(url ,DEFAULT_CHARSET, params));

//        Map<String, Object> map = new HashMap<String, Object>();
//
//        String url = "https://oauth.jd.com/oauth/token";
//
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("grant_type","refresh_token");
//        params.put("refresh_token","2f3d2b9d-13f3-44ea-af4f-72068835b0c4");
//        params.put("client_id","951D05FBFBC47B5ECA9E8437FC75C79B");
//        params.put("client_secret","5832f67a91d04188aa5d82ad9d490271");
//        try {
////            String body = WebUtilities.doPost(url,"utf-8",params );
//            String body = WebUtilities.doGet(url,params,"utf-8" );
////            String body = WebUtilities.doPost(url, params, CONNECT_TIMEOUT, READ_TIMEOUT);
//            map = JackSonUtilities.toMap(body);
//            System.out.println(body);
//            System.out.println(map);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error(e.getMessage(), e);
//        }


//        String url = "http://tag.cdnmaster.com/tmjs/tm.js";
//        Map<String, String> params = new HashMap<>();
//        params.put("id","TM-31EKD8");
//        String s = doGet(url, params, "utf-8");
//        System.out.println(s.length());
//        url = "http://tag.cdnmaster.cn/tmjs/tm.js";
//        String _s = doGet(url, params, "utf-8");
//        System.out.println(s.length());
//        url = "http://tagace.com/tmjs/tm.js";
//
//       String  s1 = doGet(url, params, "utf-8");
//        System.out.println(s.length());
//        System.out.println(s.equals(_s));
//        System.out.println(s1.equals(_s));
//        System.out.println(s);
    }

    public static String doGet(String url  ,Map<String, String> params ,String charset){
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
        String ctype = "application/json;charset=" + charset;
        try {
            conn = getConnection(buildGetUrl(url,buildQuery(params,charset) ),METHOD_GET , ctype);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            rsp = getResponseAsString(conn);
        }catch (IOException e ){
            logger.error(e.getMessage() , e );
        }finally {
            if(conn != null) {
                conn.disconnect();
            }
        }
        return rsp;
    }

    public static String doPost(String url, String ctype,Map<String, String> params ) throws IOException {
       String query =  buildQuery(params , "utf-8");
        if(StringUtils.isEmpty(query)){
            query = "";
        }
        return doPost(url , ctype ,query.getBytes() , CONNECT_TIMEOUT , READ_TIMEOUT);
    }

    public static String doPost(String url, String ctype, byte[] content) throws IOException {
        return doPost(url , ctype , content , CONNECT_TIMEOUT , READ_TIMEOUT);
    }

    public static String doPost(String url, String ctype, byte[] content, int connectTimeout, int readTimeout) throws IOException {
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;

        try {
            conn = getConnection(new URL(url), METHOD_POST, ctype);
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            out = conn.getOutputStream();
            out.write(content);
            rsp = getResponseAsString(conn);
        } finally {
            if(out != null) {
                out.close();
            }

            if(conn != null) {
                conn.disconnect();
            }

        }

        return rsp;
    }


    private static HttpURLConnection getConnection(URL url, String method, String ctype) throws  IOException{
        return getConnection(url , method , ctype , MapUtils.EMPTY_MAP);
    }

    private static HttpURLConnection getConnection(URL url, String method, String ctype, Map<String, String> headersMap)
            throws IOException {

        Object conn = null;
        if("https".equals(url.getProtocol())){
            SSLContext ctx = null;

            try {
                ctx = SSLContext.getInstance("TLS");
                ctx.init(new KeyManager[0], new DefaultTrustManager[]{new DefaultTrustManager()}, new SecureRandom());
            } catch (Exception var6) {
                throw new IOException(var6);
            }

            HttpsURLConnection connHttps = (HttpsURLConnection)url.openConnection();
            connHttps.setSSLSocketFactory(ctx.getSocketFactory());
            connHttps.setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            conn = connHttps;
        }else{
            conn = (HttpURLConnection) url.openConnection();
        }

        ((HttpURLConnection) conn).setRequestMethod(method);
        ((HttpURLConnection) conn).setDoInput(true);
        ((HttpURLConnection) conn).setDoOutput(true);
        if("post".equals(METHOD_POST.toLowerCase())){
            ((HttpURLConnection) conn).setUseCaches(false);// 忽略缓存
        }
        ((HttpURLConnection) conn).setRequestProperty("Accept", "*/*");
        ((HttpURLConnection) conn).setRequestProperty("User-Agent", "YOU SHU API Server");
        ((HttpURLConnection) conn).setRequestProperty("Content-Type", ctype);
        ((HttpURLConnection) conn).setRequestProperty("Accept-Language", "zh-CN,zh");
        if (MapUtils.isNotEmpty(headersMap)) {
            Set<Map.Entry<String, String>> entrySet = headersMap.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                ((HttpURLConnection) conn).setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        return ((HttpURLConnection) conn);
    }


    /**
     * Gets response as string.
     *
     * @param conn the conn
     * @return the response as string
     * @throws IOException the io exception
     */
    protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        InputStream es = conn.getErrorStream();
        if(es == null) {
            return getStreamAsString(conn.getInputStream(), charset);
        } else {
            String msg = getStreamAsString(es, charset);
            if(StringUtils.isEmpty(msg)) {
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
            } else {
                throw new IOException(msg);
            }
        }
    }


    private static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
            StringWriter writer = new StringWriter();
            char[] chars = new char[256];
            boolean count = false;

            int count1;
            while((count1 = reader.read(chars)) > 0) {
                writer.write(chars, 0, count1);
            }

            String var6 = writer.toString();
            return var6;
        } finally {
            if(stream != null) {
                stream.close();
            }

        }
    }

    private static String getResponseCharset(String ctype) {
        String charset = "UTF-8";
        if(!StringUtils.isEmpty(ctype)) {
            String[] params = ctype.split(";");
            String[] arr$ = params;
            int len$ = params.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String param = arr$[i$];
                param = param.trim();
                if(param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if(pair.length == 2 && !StringUtils.isEmpty(pair[1])) {
                        charset = pair[1].trim();
                    }
                    break;
                }
            }
        }

        return charset;
    }

    public static String buildQuery(Map<String, String> params, String charset) throws IOException {
        if (params == null || params.isEmpty()) {
            return null;
        }

        StringBuilder query = new StringBuilder();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        boolean hasParam = false;

        for (Map.Entry<String, String> entry : entries) {
            String name = entry.getKey();
            String value = entry.getValue();
            // 忽略参数名或参数值为空的参数
            if( StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(value) )
            {
                if (hasParam) {
                    query.append("&");
                } else {
                    hasParam = true;
                }
                query.append(name).append("=").append(URLEncoder.encode(value, charset));
            }
        }

        return query.toString();
    }

    private static URL buildGetUrl(String strUrl, String query) throws IOException {
        URL url = new URL(strUrl);
        if (StringUtils.isEmpty(query)) {
            return url;
        }

        if (StringUtils.isEmpty(url.getQuery())) {
            if (strUrl.endsWith("?")) {
                strUrl = strUrl + query;
            } else {
                strUrl = strUrl + "?" + query;
            }
        } else {
            if (strUrl.endsWith("&")) {
                strUrl = strUrl + query;
            } else {
                strUrl = strUrl + "&" + query;
            }
        }

        return new URL(strUrl);
    }

}
