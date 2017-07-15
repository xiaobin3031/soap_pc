alter table SOAP_USERS add duty varchar2(2);
COMMENT ON COLUMN SOAP_USERS.duty is '职务';
alter table SOAP_USERS add is_trip char(1) default '0';
COMMENT ON COLUMN SOAP_USERS.IS_TRIP is '是否出差;0:否';