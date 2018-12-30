create sequence SEQ_COUNTRY_ID INCREMENT BY 50;
create table COUNTRIES (
id number NOT NULL,
identifier varchar,
PRIMARY KEY (id)
);
insert into COUNTRIES (id, identifier) values (SEQ_COUNTRY_ID.NEXTVAL, 'BE');
insert into COUNTRIES (id, identifier) values (SEQ_COUNTRY_ID.NEXTVAL, 'NL');
insert into COUNTRIES (id, identifier) values (SEQ_COUNTRY_ID.NEXTVAL, 'FR');
insert into COUNTRIES (id, identifier) values (SEQ_COUNTRY_ID.NEXTVAL, 'DE');
insert into COUNTRIES (id, identifier) values (SEQ_COUNTRY_ID.NEXTVAL, 'PL');
insert into COUNTRIES (id, identifier) values (SEQ_COUNTRY_ID.NEXTVAL, 'UK');
insert into COUNTRIES (id, identifier) values (SEQ_COUNTRY_ID.NEXTVAL, 'ES');
insert into COUNTRIES (id, identifier) values (SEQ_COUNTRY_ID.NEXTVAL, 'PT');

create sequence SEQ_ADDRESS_ID INCREMENT BY 50;
create table ADDRESSES (
id number NOT NULL,
identifier varchar,
country_id number,
PRIMARY KEY (id),
FOREIGN KEY (country_id) REFERENCES COUNTRIES(id)
);
insert into ADDRESSES (id, identifier, country_id) VALUES (SEQ_ADDRESS_ID.NEXTVAL,'id1', 1);
insert into ADDRESSES (id, identifier, country_id) VALUES (SEQ_ADDRESS_ID.NEXTVAL,'id2', 1);


create sequence SEQ_PERSON_ID INCREMENT BY 50;
create table PERSONS (
id number NOT NULL,
identifier varchar,
firstname varchar,
lastname varchar,
status VARCHAR,
active VARCHAR,
created TIMESTAMP,
keepUntil DATE
);
insert into PERSONS (id, identifier, firstName, lastName, status, active, created, keepUntil) values (SEQ_PERSON_ID.NEXTVAL, 'id1','Hans','Muster','STATUS_1', 'Y', SYSDATE, SYSDATE);
insert into PERSONS (id, identifier, firstName, lastName, status, active, created, keepUntil) values (SEQ_PERSON_ID.NEXTVAL, 'id2','Jeff','Bezos','STATUS_2', 'Y', SYSDATE, SYSDATE);

CREATE TABLE person_address (
person_id NUMBER NOT NULL,
address_id NUMBER NOT NULL,
PRIMARY KEY (person_id, address_id),
FOREIGN KEY (person_id) REFERENCES persons(id),
FOREIGN KEY (address_id) REFERENCES ADDRESSES(id)
);
INSERT INTO person_address (person_id, address_id) VALUES (1,1);
-- INSERT INTO person_address (person_id, address_id) VALUES  (2,2);


