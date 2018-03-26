CREATE TABLE `sys_user` (
`user_id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键' ,
`user_name`  varchar(256) NOT NULL COMMENT '用户名称' ,
`user_nick`  varchar(256) NOT NULL COMMENT '用户昵称' ,
`login_name`  varchar(256) NOT NULL COMMENT '登录名称' ,
`password`  varchar(256) NOT NULL COMMENT '登录密码' ,
`state`  varchar(64) NOT NULL DEFAULT  'normal'  COMMENT '状态,normal:正常',
`is_delete`  tinyint(1)  NOT NULL DEFAULT '0'  COMMENT '是否删除,0:正常,1:删除',
`created`  varchar(64) NOT NULL DEFAULT 'system' COMMENT '记录创建者' ,
`updated`  varchar(64) NOT NULL DEFAULT 'system' COMMENT '记录修改者' ,
`created_time` timestamp NULL  COMMENT '创建时间',
`updated_time` timestamp NULL  COMMENT '更新时间',
PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';


CREATE TABLE `sys_role` (
`role_id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键' ,
`role_name`  varchar(256) NOT NULL COMMENT '用户名称' ,
`parent_role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父角色id' ,
`state`  varchar(64) NOT NULL DEFAULT  'normal'  COMMENT '状态,normal:正常',
`is_delete`  tinyint(1)  NOT NULL DEFAULT '0'  COMMENT '是否删除,0:正常,1:删除',
`created`  varchar(64) NOT NULL DEFAULT 'system' COMMENT '记录创建者' ,
`updated`  varchar(64) NOT NULL DEFAULT 'system' COMMENT '记录修改者' ,
`created_time` timestamp NULL  COMMENT '创建时间',
`updated_time` timestamp NULL  COMMENT '更新时间',
PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';


CREATE TABLE `sys_user_role` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键' ,
`user_id`   bigint(20) NOT NULL DEFAULT '0'  COMMENT '用户id' ,
`role_id`   bigint(20) NOT NULL DEFAULT '0'  COMMENT '角色id' ,
`is_delete`  tinyint(1)  NOT NULL DEFAULT '0'  COMMENT '是否删除,0:正常,1:删除',
`created`  varchar(64) NOT NULL DEFAULT 'system' COMMENT '记录创建者' ,
`updated`  varchar(64) NOT NULL DEFAULT 'system' COMMENT '记录修改者' ,
`created_time` timestamp NULL  COMMENT '创建时间',
`updated_time` timestamp NULL  COMMENT '更新时间',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';




CREATE TABLE `sys_product` (
`product_id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键' ,
`product_name`  varchar(256) NOT NULL COMMENT '产品名称' ,
`state`  varchar(64) NOT NULL DEFAULT  'normal'  COMMENT '状态,normal:正常',
`dev_url`  varchar(256)   NULL  COMMENT '开发地址',
`test_url`  varchar(256)  NULL  COMMENT '测试地址',
`prod_url`  varchar(256)  NULL  COMMENT '生产地址',
`is_delete`  tinyint(1)  NOT NULL DEFAULT '0'  COMMENT '是否删除,0:正常,1:删除',
`created`  varchar(64) NOT NULL DEFAULT 'system' COMMENT '记录创建者' ,
`updated`  varchar(64) NOT NULL DEFAULT 'system' COMMENT '记录修改者' ,
`created_time` timestamp NULL  COMMENT '创建时间',
`updated_time` timestamp NULL  COMMENT '更新时间',
PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品表';


CREATE TABLE `apidoc_module` (
`module_id`   bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键' ,
`product_id`  bigint(20) NOT NULL  COMMENT '产品id' ,
`module_name` varchar(256) NOT NULL COMMENT '模块名称' ,
`module_nick_name` varchar(256) NOT NULL COMMENT '模块昵称' ,
`state`  varchar(64) NOT NULL DEFAULT  'normal'  COMMENT '状态,normal:正常',
`is_delete`  tinyint(1)  NOT NULL DEFAULT '0'  COMMENT '是否删除,0:正常,1:删除',
`created`  varchar(64) NOT NULL DEFAULT 'system' COMMENT '记录创建者' ,
`updated`  varchar(64) NOT NULL DEFAULT 'system' COMMENT '记录修改者' ,
`created_time` timestamp NULL  COMMENT '创建时间',
`updated_time` timestamp NULL  COMMENT '更新时间',
PRIMARY KEY (`module_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模块表';


CREATE TABLE `apidoc_document` (
`document_id`   bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键' ,
`module_id`   bigint(20) NOT NULL   COMMENT '模块id' ,
`product_id`  bigint(20) NOT NULL  COMMENT '产品id' ,
`document_name` varchar(256) NOT NULL COMMENT '文档名称' ,
`document_nick_name` varchar(256) NOT NULL COMMENT '文档昵称' ,
`document_url` varchar(256) NOT NULL COMMENT '文档地址' ,
`document_type` varchar(256) NOT NULL COMMENT '文档类型:dubbo,http' ,
`document_method` varchar(256) NOT NULL COMMENT '文档方法:get,post等' ,
`document_author` varchar(256) NOT NULL COMMENT '文档作者' ,
`document_version` varchar(256) NOT NULL COMMENT '文档版本' ,
`state`  varchar(64) NOT NULL DEFAULT  'normal'  COMMENT '状态,normal:正常',
`is_delete`  tinyint(1)  NOT NULL DEFAULT '0'  COMMENT '是否删除,0:正常,1:删除',
`created`  varchar(64) NOT NULL DEFAULT 'system' COMMENT '记录创建者' ,
`updated`  varchar(64) NOT NULL DEFAULT 'system' COMMENT '记录修改者' ,
`created_time` timestamp NULL  COMMENT '创建时间',
`updated_time` timestamp NULL  COMMENT '更新时间',
PRIMARY KEY (`document_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文档表';


CREATE TABLE `apidoc_document_param` (
`param_id`   bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键' ,
`document_id`   bigint(20) NOT NULL  COMMENT '模块文档id' ,
`module_id`   bigint(20) NOT NULL   COMMENT '模块id' ,
`product_id`  bigint(20) NOT NULL  COMMENT '产品id' ,
`param_name` varchar(256) NOT NULL COMMENT '参数名称' ,
`param_type` varchar(256) NOT NULL COMMENT '参数类型,String，int 等等' ,
`param_description` varchar(256) NOT NULL COMMENT '参数描述' ,
`param_default` varchar(256) NOT NULL COMMENT '参数默认值' ,
`is_required`  tinyint(1)  NOT NULL DEFAULT '0'  COMMENT '是否必填,0:必填,1:非必填',
`state`  varchar(64) NOT NULL DEFAULT  'normal'  COMMENT '状态,normal:正常',
`is_delete`  tinyint(1)  NOT NULL DEFAULT '0'  COMMENT '是否删除,0:正常,1:删除',
`created`  varchar(64) NOT NULL DEFAULT 'system' COMMENT '记录创建者' ,
`updated`  varchar(64) NOT NULL DEFAULT 'system' COMMENT '记录修改者' ,
`created_time` timestamp NULL  COMMENT '创建时间',
`updated_time` timestamp NULL  COMMENT '更新时间',
PRIMARY KEY (`param_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文档参数表';


CREATE TABLE `apidoc_document_returned` (
`returned_id`   bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键' ,
`document_id`   bigint(20) NOT NULL  COMMENT '模块文档id' ,
`module_id`   bigint(20) NOT NULL   COMMENT '模块id' ,
`product_id`  bigint(20) NOT NULL  COMMENT '产品id' ,
`returned_name` varchar(256) NOT NULL COMMENT '参数名称' ,
`returned_type` varchar(256) NOT NULL COMMENT '参数类型,String，int 等等' ,
`returned_description` varchar(256) NOT NULL COMMENT '参数描述' ,
`state`  varchar(64) NOT NULL DEFAULT  'normal'  COMMENT '状态,normal:正常',
`is_delete`  tinyint(1)  NOT NULL DEFAULT '0'  COMMENT '是否删除,0:正常,1:删除',
`created`  varchar(64) NOT NULL DEFAULT 'system' COMMENT '记录创建者' ,
`updated`  varchar(64) NOT NULL DEFAULT 'system' COMMENT '记录修改者' ,
`created_time` timestamp NULL  COMMENT '创建时间',
`updated_time` timestamp NULL  COMMENT '更新时间',
PRIMARY KEY (`returned_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文档返回值表';


