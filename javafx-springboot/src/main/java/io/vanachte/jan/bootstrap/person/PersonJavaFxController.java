package io.vanachte.jan.bootstrap.person;

import io.vanachte.jan.bootstrap.JavaFxControllerMediator;
import io.vanachte.jan.bootstrap.date.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class PersonJavaFxController {

    @Inject
    private PersonModelApi api;

    @Inject
    private JavaFxControllerMediator mediator;

    @FXML
    private TableView<PersonModel> personTable;
    @FXML
    private TableColumn<PersonModel, String> firstNameColumn;
    @FXML
    private TableColumn<PersonModel, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    @FXML
    private void initialize() {
        // Add observable list data to the table
        personTable.setItems(api.findAll());

        // Initialize the person table with the two columns.
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        // Clear person details.
        showPersonDetails(null);

        // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, person) -> {showPersonDetails(person);mediator.updatePersonModel(person);});
    }

    private void showPersonDetails(PersonModel person) {

        if (person != null) {
            // Fill the labels with info from the person object.
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());

            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
        } else {
            // Person is null, remove all the text.
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }

    public void setMediator(JavaFxControllerMediator mediator) {
        this.mediator = mediator;
        this.mediator.registerPersonController(this);
    }
}
