insert into COUNTRIES (id, identifier) values (SEQ_COUNTRY_ID.NEXTVAL, 'BE');
insert into COUNTRIES (id, identifier) values (SEQ_COUNTRY_ID.NEXTVAL, 'NL');
insert into COUNTRIES (id, identifier) values (SEQ_COUNTRY_ID.NEXTVAL, 'FR');
insert into COUNTRIES (id, identifier) values (SEQ_COUNTRY_ID.NEXTVAL, 'DE');
insert into COUNTRIES (id, identifier) values (SEQ_COUNTRY_ID.NEXTVAL, 'PL');
insert into COUNTRIES (id, identifier) values (SEQ_COUNTRY_ID.NEXTVAL, 'UK');
insert into COUNTRIES (id, identifier) values (SEQ_COUNTRY_ID.NEXTVAL, 'ES');
insert into COUNTRIES (id, identifier) values (SEQ_COUNTRY_ID.NEXTVAL, 'PT');

insert into ADDRESSES (id, country_id) VALUES (SEQ_ADDRESS_ID.NEXTVAL,'BE');
insert into ADDRESSES (id, country_id) VALUES (SEQ_ADDRESS_ID.NEXTVAL,'BE');

insert into PERSONS (id, identifier, firstName, lastName, status, active, createdOn, keepUntil) values (SEQ_PERSON_ID.NEXTVAL, 'id1','Hans','Muster','STATUS_1', 'Y', SYSDATE, SYSDATE);
insert into PERSONS (id, identifier, firstName, lastName, status, active, createdOn, keepUntil) values (SEQ_PERSON_ID.NEXTVAL, 'id2','Jeff','Bezos','STATUS_2', 'Y', SYSDATE, SYSDATE);

INSERT INTO person_address (person_id, address_id) VALUES (1,1);
INSERT INTO person_address (person_id, address_id) VALUES  (2,2);


