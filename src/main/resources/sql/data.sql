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