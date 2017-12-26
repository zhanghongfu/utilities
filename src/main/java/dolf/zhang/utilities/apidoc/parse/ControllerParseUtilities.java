package dolf.zhang.utilities.apidoc.parse;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import dolf.zhang.utilities.apidoc.entity.ApiEntity;
import dolf.zhang.utilities.json.JackSonUtilities;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 说明: TODO
 * @author zhanghongfu
 * @version
 * @date 2017/12/26
 */
public class ControllerParseUtilities {

    private ControllerParseUtilities(){}

    public static void main(String[] args) {

        System.out.println(System.getenv("USERNAME"));
        String path = "D:\\code\\intellij\\huaxia-common\\huaxia-common-core\\src\\main\\java\\com\\huaxia\\common\\core\\apidoc\\TestController.java";
//        path = "D:\\code\\intellij\\huaxia-common\\huaxia-common-core\\src\\main\\java\\com\\huaxia\\common\\core\\apidoc\\parse\\ApiParseUtilities.java";
        read(path);
    }

    public static List<ApiEntity> read(String path) {
        List<ApiEntity> apis = new ArrayList<>(10);
        try {
            CompilationUnit parse = JavaParser.parse(FileUtils.readFileToString(new File(path), "utf-8"));
            ApiDocCommentVisitor apiDocCommentVisitor = new ApiDocCommentVisitor();
            parse.accept(apiDocCommentVisitor, parse);
            apis = apiDocCommentVisitor.getApis();
            System.out.println(JackSonUtilities.toString(apis));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return apis;
    }
}
