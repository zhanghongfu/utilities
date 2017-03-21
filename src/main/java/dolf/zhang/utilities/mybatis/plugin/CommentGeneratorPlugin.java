package dolf.zhang.utilities.mybatis.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.internal.DefaultCommentGenerator;

public class CommentGeneratorPlugin extends DefaultCommentGenerator{

	public void addJavaFileComment(CompilationUnit compilationUnit) {
		return;
	}

	public void addClassComment(InnerClass innerClass,
			IntrospectedTable introspectedTable) {
	}

	public void addGeneralMethodComment(Method method,
			IntrospectedTable introspectedTable) {
	}

	public void addFieldComment(Field field,
			IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {
	}

	public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
		
	}
	public void addSetterComment(Method method,
			IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {

	}

	public void addGetterComment(Method method,
			IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {
	}


}
