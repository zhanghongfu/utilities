package dolf.zhang.utilities.apidoc.parse;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.javadoc.Javadoc;
import dolf.zhang.utilities.apidoc.entity.ParameterEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 说明: TODO
 *
 * @author zhanghongfu
 * @date 2017/12/25
 */
public class BeanDocCommentVisitor extends VoidVisitorAdapter {
    private List<ParameterEntity> success = new ArrayList<>(10);

    public List<ParameterEntity> getSuccess() {
        return success;
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, Object cu) {
        Optional<JavadocComment> javadocComment = n.getJavadocComment();
        NodeList<BodyDeclaration<?>> members = n.getMembers();
        for (BodyDeclaration body : members) {
            ParameterEntity apiSuccess = new ParameterEntity();
            if (body.isFieldDeclaration()) {
                FieldDeclaration fieldDeclaration = body.asFieldDeclaration();
                VariableDeclarator variable = fieldDeclaration.getVariable(0);
                apiSuccess.setField(variable.getName().asString());
                apiSuccess.setType(variable.getType().asString());
                Optional<Comment> comment = fieldDeclaration.getComment();
                Optional<Javadoc> javadoc = fieldDeclaration.getJavadoc();
                if (javadoc.isPresent()) {
                    Comment comment1 = comment.get();
                    JavadocComment javadocComment1 = comment1.asJavadocComment();
                    Javadoc parse = javadocComment1.parse();
                    apiSuccess.setDescription(parse.getDescription().toText());
                }
                success.add(apiSuccess);
            }
        }

    }
}
