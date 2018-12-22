package io.vanachte.jan.bootstrap;

import io.vanachte.jan.bootstrap.country.Country;
import javafx.fxml.FXML;
import javafx.util.StringConverter;

import javax.inject.Named;

@Named
public class ApplicationController {

    @FXML
    public void initialize() {
//        countriesComboBox.setConverter(new CountryNameStringConverter());
//        countriesComboBox.setItems(FXCollections.observableArrayList(countryService.findAll()));
    }

    private static class CountryNameStringConverter extends StringConverter<Country> {
        @Override
        public String toString(Country item) {
            return item.getName();
        }

        @Override
        public Country fromString(String string) {
            return null;
        }
    }
}
