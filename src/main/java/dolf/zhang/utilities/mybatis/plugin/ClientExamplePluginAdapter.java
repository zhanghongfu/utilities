package dolf.zhang.utilities.mybatis.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientExamplePluginAdapter extends PluginAdapter{

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}





	public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method,
			Interface interfaze, IntrospectedTable introspectedTable) {
		List<Parameter> parameters = method.getParameters();
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" * 根据主键删除");
		for (Parameter parameter : parameters) {
			method.addJavaDocLine(" * @param " +parameter.getName());
		}
		method.addJavaDocLine(" * @return 受影响条数");
		method.addJavaDocLine(" */");
		return true;
	}

	public boolean clientInsertSelectiveMethodGenerated(Method method,
			Interface interfaze, IntrospectedTable introspectedTable) {
		List<Parameter> parameters = method.getParameters();
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" * 新增数据");
		for (Parameter parameter : parameters) {
			method.addJavaDocLine(" * @param " +parameter.getName());
		}
		method.addJavaDocLine(" * @return 受影响条数");
		method.addJavaDocLine(" */");
		return true;
	}


	public boolean clientSelectByPrimaryKeyMethodGenerated(Method method,
			Interface interfaze, IntrospectedTable introspectedTable) {
		List<Parameter> parameters = method.getParameters();
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" * 根据主键查询数据");
		for (Parameter parameter : parameters) {
			method.addJavaDocLine(" * @param " +parameter.getName());
		}
		method.addJavaDocLine(" * @return " );
		method.addJavaDocLine(" */");
		return true;
	}


	public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method,
			Interface interfaze, IntrospectedTable introspectedTable) {
		List<Parameter> parameters = method.getParameters();
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" * 根据主键修改数据");
		for (Parameter parameter : parameters) {
			method.addJavaDocLine(" * @param " +parameter.getName());
		}
		method.addJavaDocLine(" * @return 受影响条数" );
		method.addJavaDocLine(" */");
		return true;
	}


	public boolean clientCountByExampleMethodGenerated(Method method,
			Interface interfaze, IntrospectedTable introspectedTable) {
		return false;
	}

	public boolean clientCountByExampleMethodGenerated(Method method,
			TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		return false;
	}

	public boolean clientDeleteByExampleMethodGenerated(Method method,
			Interface interfaze, IntrospectedTable introspectedTable) {
		return false;
	}

	public boolean clientDeleteByExampleMethodGenerated(Method method,
			TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		return false;
	}



	public boolean clientInsertMethodGenerated(Method method,
			TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		return false;
	}

	public boolean clientInsertMethodGenerated(Method method, Interface interfaze,
			IntrospectedTable introspectedTable) {
		return false;
	}



	public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method,
			Interface interfaze, IntrospectedTable introspectedTable) {
		return false;
	}

	public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method,
			TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		return false;
	}

	public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method,
			Interface interfaze, IntrospectedTable introspectedTable) {
		return false;
	}

	public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method,
			TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		return false;
	}

	public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method,
			Interface interfaze, IntrospectedTable introspectedTable) {
		return false;
	}

	public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method,
			TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		return false;
	}



	public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method,
			Interface interfaze, IntrospectedTable introspectedTable) {
		return false;
	}

	public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method,
			TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		return false;
	}

	public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(
			Method method, Interface interfaze,
			IntrospectedTable introspectedTable) {
		return false;
	}

	public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(
			Method method, TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		return false;
	}


	public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method,
			Interface interfaze, IntrospectedTable introspectedTable) {
		return false;
	}

	public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method,
			TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		return false;
	}

	public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method,
			Interface interfaze, IntrospectedTable introspectedTable) {
		return false;
	}

	public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method,
			TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		return false;
	}

	public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(
			XmlElement element, IntrospectedTable introspectedTable) {
		return false;
	}

	public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(
			XmlElement element, IntrospectedTable introspectedTable) {
		return false;
	}

	public boolean sqlMapUpdateByExampleSelectiveElementGenerated(
			XmlElement element, IntrospectedTable introspectedTable) {
		return false;
	}

	public boolean sqlMapUpdateByExampleWithBLOBsElementGenerated(
			XmlElement element, IntrospectedTable introspectedTable) {
		return false;
	}

	public boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(
			XmlElement element, IntrospectedTable introspectedTable) {
		return false;
	}



	public boolean sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(
			XmlElement element, IntrospectedTable introspectedTable) {
		return false;
	}

	public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(
			XmlElement element, IntrospectedTable introspectedTable) {
		return false;
	}

	public boolean sqlMapExampleWhereClauseElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		return false;
	}

	public boolean sqlMapDeleteByExampleElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		return false;
	}

	public boolean sqlMapInsertElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		return false;
	}

	public boolean sqlMapCountByExampleElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		return false;
	}

	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		return false;
	}

	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		generateToString(introspectedTable, topLevelClass);
		return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
	}


	private void generateToString(IntrospectedTable introspectedTable, TopLevelClass topLevelClass) {
		List<Field> fields = topLevelClass.getFields();
		Map<String, Field> map = new HashMap<String, Field>();
		for (Field field : fields) {
			map.put(field.getName(), field);
		}
		List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
		for (IntrospectedColumn column : columns) {
			Field f = map.get(column.getJavaProperty());
			if (f != null) {
				f.getJavaDocLines().clear();
				if (column.getRemarks() != null && column.getRemarks().trim().length() > 0) {
					f.addJavaDocLine("/** " + column.getRemarks() + " */");
				}
			}
		}
	}

}
