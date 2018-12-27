package io.vanachte.jan.bootstrap.person;

public interface PersonXmlMapper {
    Person map(PersonType personType, Class<? extends Person> clazz);
}
