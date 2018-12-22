package io.vanachte.jan.bootstrap.person;

import javafx.collections.ObservableList;

public interface PersonModelApi {

    ObservableList<PersonModel> findAll();
}
