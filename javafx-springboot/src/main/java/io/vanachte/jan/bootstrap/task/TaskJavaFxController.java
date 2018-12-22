package io.vanachte.jan.bootstrap.task;

import io.vanachte.jan.bootstrap.JavaFxControllerMediator;
import io.vanachte.jan.bootstrap.date.DateUtil;
import io.vanachte.jan.bootstrap.person.PersonModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class TaskJavaFxController {

    @Inject
    private TaskModelApi taskApi;

    @Inject
    private JavaFxControllerMediator mediator;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private TextArea description;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        mediator.registerTaskController(this);
        // Add observable list data to the table
    }

    private void show(TaskModel task) {
        if (task != null) {
            // Fill the labels with info from the person object.
            firstNameLabel.setText(task.getFirstName());
            description.setText(task.getDescription());
            streetLabel.setText(task.getStreet());
            postalCodeLabel.setText(Integer.toString(task.getPostalCode()));
            cityLabel.setText(task.getCity());

            birthdayLabel.setText(DateUtil.format(task.getBirthday()));
        } else {
            // Person is null, remove all the text.
            firstNameLabel.setText("");
            description.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }

    public void update(PersonModel data) {
        this.show(taskApi.findByPerson(data.getLastName()));
    }

    public void setMediator(JavaFxControllerMediator mediator) {
        this.mediator = mediator;
        this.mediator.registerTaskController(this);
    }
}
