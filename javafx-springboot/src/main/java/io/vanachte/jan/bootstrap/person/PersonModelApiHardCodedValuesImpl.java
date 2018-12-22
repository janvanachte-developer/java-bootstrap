package io.vanachte.jan.bootstrap.person;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//@Service
public class PersonModelApiHardCodedValuesImpl implements PersonModelApi {

    @Override
    public ObservableList<PersonModel> findAll() {
        final ObservableList<PersonModel> personData = FXCollections.observableArrayList();

        // Add some sample data
        personData.add(new PersonModel("Hans", "Muster"));
        personData.add(new PersonModel("Ruth", "Dubois"));
        personData.add(new PersonModel("Heinz", "Kurz"));
        personData.add(new PersonModel("Cornelia", "Meier"));
        personData.add(new PersonModel("Werner", "Meyer"));
        personData.add(new PersonModel("Lydia", "Kunz"));
        personData.add(new PersonModel("Anna", "Best"));
        personData.add(new PersonModel("Stefan", "Meier"));
        personData.add(new PersonModel("Martin", "Dubois"));
        return personData;
    }
}
