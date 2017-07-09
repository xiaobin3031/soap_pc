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