alter table SOAP_USERS add duty varchar2(2);
COMMENT ON COLUMN SOAP_USERS.duty is '职务';
alter table SOAP_USERS add is_trip char(1) default '0';
COMMENT ON COLUMN SOAP_USERS.IS_TRIP is '是否出差;0:否';

alter table SOAP_COMPANY add status char(2) default '00';
COMMENT ON COLUMN SOAP_COMPANY.STATUS is '状态';

alter table SOAP_PROJECT_INFO modify EXPECT_START_TIME varchar2(10);
alter table SOAP_PROJECT_INFO modify START_TIME varchar2(10);
alter table SOAP_PROJECT_INFO MODIFY END_TIME varchar2(10);

alter table SOAP_PROJECT_FUNCTIONS add notes varchar2(500);
COMMENT ON COLUMN SOAP_PROJECT_FUNCTIONS.notes is '备注';