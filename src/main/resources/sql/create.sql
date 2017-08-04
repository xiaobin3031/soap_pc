--用户表
create table soap_users(
  user_id varchar2(32) primary key,
  user_name varchar2(100) not null,
  status char(2) default '00',
  name varchar2(200),
  mobile varchar2(20),
  office_time TIMESTAMP,
  leave_time TIMESTAMP,
  practice_over_time TIMESTAMP,
  create_user varchar2(32) not null,
  create_time TIMESTAMP default sysdate,
  update_user varchar2(32),
  update_time TIMESTAMP
);
comment on table soap_users is '用户表';
comment on column soap_users.user_id is '操作员ID';
comment on column soap_users.user_name is '登录名';
comment on column soap_users.status is '状态';
comment on column soap_users.name is '用户名';
comment on column soap_users.mobile is '手机号码';
COMMENT ON COLUMN soap_users.create_user is '添加人';
COMMENT ON COLUMN soap_users.create_time is '添加日期';
COMMENT ON COLUMN soap_users.update_user is '更新人';
COMMENT ON COLUMN soap_users.update_time is '更新日期';
COMMENT ON COLUMN soap_users.office_time is '任职时间';
COMMENT ON COLUMN soap_users.leave_time is '离职时间';
COMMENT ON COLUMN soap_users.practice_over_time is '实习结束时间';
--操作用户流水表
create table soap_user_operate(
  operate_id varchar2(32) PRIMARY KEY,
  user_id varchar2(32) not null,
  operate char(2) not null,
  time1 varchar2(20),
  time2 varchar2(20),
  notes varchar2(500),
  create_user varchar2(32) not null,
  create_time TIMESTAMP default sysdate,
  CONSTRAINT pk_item_user_id FOREIGN KEY (user_id) REFERENCES soap_users(user_id)
);
COMMENT ON TABLE soap_user_operate is '用户状态流水表';
COMMENT ON COLUMN soap_user_operate.operate_id is '操作ID';
COMMENT ON COLUMN soap_user_operate.user_id is '用户ID';
COMMENT ON COLUMN soap_user_operate.operate is '状态';
COMMENT ON COLUMN soap_user_operate.time1 is '时间1';
COMMENT ON COLUMN soap_user_operate.time2 is '时间2';
COMMENT ON COLUMN soap_user_operate.notes is '备注';
COMMENT ON COLUMN soap_user_operate.create_user is '修改人';
COMMENT ON COLUMN soap_user_operate.create_time is '修改时间';

--用户属性表
create table soap_user_attr(
  user_id varchar2(32) not null,
  attr_type varchar2(50) not null,
  attr_id varchar2(50) not null,
  attr_value varchar2(100) not null,
  attr_desc varchar2(100),
  CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES soap_users(user_id),
  CONSTRAINT pk_user_attr PRIMARY KEY (user_id,attr_type,attr_id)
);
COMMENT ON TABLE soap_user_attr is '用户属性表';
COMMENT ON COLUMN soap_user_attr.user_id is '用户ID';
COMMENT ON COLUMN soap_user_attr.attr_type is '属性类型';
COMMENT ON COLUMN soap_user_attr.attr_id is '属性ID';
COMMENT ON COLUMN soap_user_attr.attr_value is '属性值';
COMMENT ON COLUMN soap_user_attr.attr_desc is '属性描述';
--菜单表
create table soap_product(
  prod_id varchar2(32) primary key,
  prod_name varchar2(200) unique,
  parent_id varchar2(32),
  prod_type varchar2(20),
  prod_level varchar2(20),
  icon varchar2(32)
);
COMMENT ON TABLE soap_product is '产品表';
COMMENT ON COLUMN soap_product.prod_id is '产品ID';
COMMENT ON COLUMN soap_product.prod_name is '产品名称';
COMMENT ON COLUMN soap_product.parent_id is '父级产品';
COMMENT ON COLUMN soap_product.prod_type is '产品类别';
COMMENT ON COLUMN soap_product.prod_level is '产品等级';
COMMENT ON COLUMN soap_product.icon is '产品图标，相对路径';

--周报表
create table soap_weekreport(
  report_id varchar2(32) primary key,
  content varchar2(1000) not null,
  work_time TIMESTAMP not null,
  remain_problems varchar2(250),
  status char(2) default '00',
  is_trip char(1) default '0',
  work_suggest varchar2(250),
  notes varchar2(500),
  pmo_email varchar2(50),
  create_user varchar2(32) not null,
  create_time timestamp default sysdate,
  update_user varchar2(32),
  update_time timestamp
);
COMMENT ON TABLE soap_weekreport is '周报';
COMMENT ON COLUMN soap_weekreport.report_id is 'ID';
COMMENT ON column soap_weekreport.work_time is '工作时间';
COMMENT ON COLUMN soap_weekreport.content is '工作内容';
COMMENT ON COLUMN soap_weekreport.remain_problems is '存在的问题';
COMMENT ON COLUMN soap_weekreport.is_trip is '是否出差';
COMMENT ON COLUMN soap_weekreport.work_suggest is '工作建议';
COMMENT ON COLUMN soap_weekreport.notes is '备注';
COMMENT ON COLUMN soap_weekreport.pmo_email is 'pmo邮箱';
COMMENT ON COLUMN soap_weekreport.create_user is '添加人';
COMMENT ON COLUMN soap_weekreport.create_time is '添加日期';
COMMENT ON COLUMN soap_weekreport.update_user is '更新人';
COMMENT ON COLUMN soap_weekreport.update_time is '更新日期';
COMMENT ON COLUMN soap_weekreport.status is '状态';

create table soap_weekreport_overtime(
  work_time char(10) primary key,
  over_time_begin char(8),
  over_time_end char(8)
);
COMMENT ON TABLE soap_weekreport_overtime is '周报加班时间';
COMMENT ON COLUMN soap_weekreport_overtime.over_time_begin is '加班开始时间';
COMMENT ON COLUMN soap_weekreport_overtime.over_time_end is '加班结束时间';

--公司列表
create table soap_company(
  company_id varchar2(32) PRIMARY KEY ,
  company_name varchar2(200) not null,
  industry char(2) not null,
  address varchar2(200),
  fax varchar2(50),
  email varchar2(100),
  notes varchar2(1000),
  create_user varchar2(32) not null,
  create_time TIMESTAMP default sysdate,
  update_user varchar2(32),
  update_time TIMESTAMP
);
COMMENT ON TABLE soap_company is '公司列表';
COMMENT ON COLUMN soap_company.company_id is '公司ID';
COMMENT ON COLUMN soap_company.company_name is '公司名称';
COMMENT ON COLUMN soap_company.industry is '行业';
COMMENT ON COLUMN soap_company.address is '地址';
COMMENT ON COLUMN soap_company.fax is '传真';
COMMENT ON COLUMN soap_company.email is '邮件';
COMMENT ON COLUMN soap_company.notes is '备注';
--项目信息
create table soap_project_info(
  project_id varchar2(32) PRIMARY KEY ,
  parent_id varchar2(32),
  project_name varchar2(200) not null,
  develop_company varchar2(32) not null,
  customer varchar2(32),
  expect_start_time TIMESTAMP not null,
  start_time TIMESTAMP,
  expect_end_time TIMESTAMP not null,
  end_time TIMESTAMP,
  create_user varchar2(32) not null,
  create_time TIMESTAMP default sysdate,
  update_user varchar2(32),
  update_time TIMESTAMP
);
COMMENT ON TABLE soap_project_info is '项目信息';
COMMENT ON COLUMN soap_project_info.project_id is '项目ID';
COMMENT ON COLUMN soap_project_info.parent_id is '母项目';
COMMENT ON COLUMN soap_project_info.project_name is '项目名称';
COMMENT ON COLUMN soap_project_info.develop_company is '开发公司';
COMMENT ON COLUMN soap_project_info.customer is '客户';
COMMENT ON COLUMN soap_project_info.expect_start_time is '预计开始时间';
COMMENT ON COLUMN soap_project_info.start_time is '开始时间';
COMMENT ON COLUMN soap_project_info.expect_end_time is '预计结束时间';
COMMENT ON COLUMN soap_project_info.end_time is '结束时间';

--项目功能
create table soap_project_functions(
  function_id varchar2(32) PRIMARY KEY ,
  project_id varchar2(32) not null,
  parent_id varchar2(32),
  function_name varchar2(100) not null,
  status char(2) default '00',
  type char(2),
  create_user varchar2(32) not null,
  create_time TIMESTAMP default sysdate,
  update_user varchar2(32),
  update_time TIMESTAMP,
  CONSTRAINT pk_functions_project_id FOREIGN KEY (project_id) REFERENCES soap_project_info(project_id)
);
COMMENT ON TABLE soap_project_functions is '项目功能';
COMMENT ON COLUMN soap_project_functions.function_id is '功能ID';
COMMENT ON COLUMN soap_project_functions.project_id is '项目ID';
COMMENT ON COLUMN soap_project_functions.parent_id is '父功能ID';
COMMENT ON COLUMN soap_project_functions.function_name is '功能名称';
COMMENT ON COLUMN soap_project_functions.status is '状态';
COMMENT ON COLUMN soap_project_functions.type is '类型';
--项目人员
create table soap_project_users(
  project_id varchar2(32) not null,
  function_id varchar2(32) not null,
  user_id varchar2(32) not null,
  status char(2) default '00',
  duty char(2) not null,
  create_user varchar2(32) not null,
  create_time TIMESTAMP default sysdate,
  update_user varchar2(32),
  update_time TIMESTAMP,
  CONSTRAINT fk_pro_func_user_stat PRIMARY KEY (project_id,function_id,user_id)
);
COMMENT ON TABLE soap_project_users is '项目人员';
COMMENT ON COLUMN soap_project_users.project_id is '项目ID';
COMMENT ON COLUMN soap_project_users.function_id is '功能ID';
COMMENT ON COLUMN soap_project_users.user_id is '人员ID';
COMMENT ON COLUMN soap_project_users.status is '状态';
COMMENT ON COLUMN soap_project_users.duty is '职务';