package io.vanachte.jan.bootstrap.person;

public interface PersonMapper {
    Person map(PersonType personType, Class<? extends Person> clazz);
}
