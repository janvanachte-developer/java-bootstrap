create sequence SEQ_COUNTRY_ID INCREMENT BY 50;
create table COUNTRIES (
id number NOT NULL,
identifier varchar,
PRIMARY KEY (id)
);

create sequence SEQ_ADDRESS_ID INCREMENT BY 1;
create table ADDRESSES (
id number NOT NULL,
identifier varchar,
country_id number,
PRIMARY KEY (id),
FOREIGN KEY (country_id) REFERENCES COUNTRIES(id)
);

create sequence SEQ_PERSON_ID INCREMENT BY 1;
create table PERSONS (
id number NOT NULL,
identifier varchar,
firstname varchar,
lastname varchar,
status VARCHAR,
active VARCHAR,
createdOn TIMESTAMP,
keepUntil DATE
);

CREATE TABLE person_address (
person_id NUMBER NOT NULL,
address_id NUMBER NOT NULL,
PRIMARY KEY (person_id, address_id),
FOREIGN KEY (person_id) REFERENCES persons(id),
FOREIGN KEY (address_id) REFERENCES ADDRESSES(id)
);

