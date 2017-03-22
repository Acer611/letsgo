package com.umessage.letsgo.core.plugin;

import java.util.List;


import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.Context;

public class SwaggerPlugin extends PluginAdapter {

	@Override
	public boolean validate(List<String> arg0) {
		return true;
	}

	@Override
	public void setContext(Context context) {
		super.setContext(context);
        //设置默认的注释生成器
        //CommentGeneratorConfiguration commentCfg = new CommentGeneratorConfiguration();
        //commentCfg.setConfigurationType(SwaggerCommentGenerator.class.getCanonicalName());
        //context.setCommentGeneratorConfiguration(commentCfg);		
	}

	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		 boolean result = super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
		 processEntityClass(topLevelClass, introspectedTable);
		 return result;
	}

	@Override
	public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		boolean result = super.modelPrimaryKeyClassGenerated(topLevelClass, introspectedTable);
		 processEntityClass(topLevelClass, introspectedTable);
		return result;
	}

	@Override
	public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		boolean result = super.modelRecordWithBLOBsClassGenerated(topLevelClass, introspectedTable);
		 processEntityClass(topLevelClass, introspectedTable);		
		return result;
	}
	
	private void processEntityClass(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		// 引入JPA注解
		topLevelClass.addImportedType("io.swagger.annotations.ApiModelProperty");
	}

}
