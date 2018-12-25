create sequence SEQ_PERSON_ID INCREMENT BY 50;
create table PERSONS (
id number,
identifier varchar,
firstname varchar,
lastname varchar
);


insert into PERSONS (id, identifier, firstName, lastName) values (SEQ_PERSON_ID.NEXTVAL, 'id1','Hans','Muster');
insert into PERSONS (id, identifier, firstName, lastName) values (SEQ_PERSON_ID.NEXTVAL, 'id2','Jeff','Bezos');