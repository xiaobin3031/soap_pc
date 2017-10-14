--soap_product
insert into SOAP_PRODUCT(prod_id,prod_name,parent_id)
values('sys','系统设置',null);
insert into SOAP_PRODUCT(prod_id,prod_name,parent_id)
values('users','人员设置','sys');
insert into SOAP_PRODUCT(prod_id,prod_name,parent_id)
    values('calendar','日历','sys');

insert into SOAP_PRODUCT(prod_id,prod_name,parent_id)
values('workspace','工作空间',null);
insert into SOAP_PRODUCT(prod_id,prod_name,parent_id)
values('weekreport','周报','workspace');
insert into SOAP_PRODUCT(prod_id,prod_name,parent_id)
    values('task','任务','workspace');

insert into SOAP_PRODUCT(prod_id,prod_name,parent_id)
    values('projectManage','项目管理',null);
insert into SOAP_PRODUCT(prod_id,prod_name,parent_id)
    values('projectInfo','项目信息','projectManage');
insert into SOAP_PRODUCT(prod_id,prod_name,parent_id)
    values('projectFunctions','项目功能','projectManage');
insert into SOAP_PRODUCT(prod_id,prod_name,parent_id)
    values('projectUsers','项目人员','projectManage');
insert into SOAP_PRODUCT(prod_id,prod_name,parent_id)
values('workload','工作量评估','projectManage');

insert into SOAP_PRODUCT(prod_id,prod_name,parent_id)
    values('baseInfo','基础信息',null);
insert into SOAP_PRODUCT(prod_id,prod_name,parent_id)
    values('company','公司','baseInfo');