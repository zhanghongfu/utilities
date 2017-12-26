import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.io.FileUtils.*;

/**
 * @author dolf
 * @description
 * @date 2017/4/18
 */
public class Test {

    public static void main(String[] args) throws IOException {
        List<String> strings = readLines(new File("D:\\data\\_tmp.log"), "utf-8");

        for (int i = 0 ; i < strings.size() ; i++){
            System.out.println(i);
        }
    }

    public static void main2(String[] args) throws IOException {
        List<String> ipDataList = readLines(new File("/Users/admaster/ip.txt"));
        StringBuffer sb = new StringBuffer();
        for (String s : ipDataList) {
            String[] split = s.split(",");
            if (split[3].equals("全球")) {
                IP.enableFileWatch = true; // 默认值为：false，如果为true将会检查ip库文件的变化自动reload数据
                IP.load("/Users/admaster/Downloads/17monipdb4ecdz/tinyipdata_utf8.dat");
                String[] strings = IP.find(s.split(",")[0]);//返回字符串数组["GOOGLE","GOOGLE"]
                String are = "";
                for (String _s : strings)
                    are = _s;
                sb.append(s.split(",")[0]);
                sb.append(",");
                sb.append(s.split(",")[1]);
                sb.append(",");
                sb.append(are);

            }else sb.append(s);
            sb.append("\r");

        }
        writeStringToFile(new File("/Users/admaster/ip1.txt"),sb.toString(),"utf-8");
    }

    public static void main1(String[] args) throws IOException {
        List<String> ipDataList = readLines(new File(Test.class.getResource("/").getPath() + "ipData.txt"));
        Map<String, String> ipDataMap = new HashMap<>();
        for (String str : ipDataList) {
            String[] split = str.split(",");
            ipDataMap.put(split[2], split[0] + "," + split[1]);
        }
        System.out.println(ipDataMap);
        Map<String, String> ipRegionMap = new HashMap<>();
        List<String> ipRegionList = readLines(new File(Test.class.getResource("/").getPath() + "ipRegion.txt"));
        StringBuffer sb = new StringBuffer();

        for (String str : ipRegionList) {
            String[] split = str.split(",");

//            split[0],split[1],split[2];

            String key = split[2];
//            if (key.contains("1156")) {
            String are = MapUtils.getString(ipDataMap, key, "未知");
            String[] split1 = are.split(",");
//                System.out.println(are);
            if (split1[1].equals("全球")) {
                System.out.println(str);
            }

            String s = ipDataMap.get(split[2]);
            sb.append(split[0]);
            sb.append(",");
            sb.append(split[1]);
            sb.append(",");
            sb.append(are);
            sb.append("\r");

//            }

        }

        writeStringToFile(new File("/Users/admaster/ip.txt"), sb.toString(), "utf-8");
        System.out.println("------");
    }
}
