package io.vanachte.jan.bootstrap.task;

import javafx.beans.property.*;

import java.time.LocalDate;

public class TaskModel {

    private final StringProperty firstName;
    private final StringProperty description;
    private final StringProperty street;
    private final IntegerProperty postalCode;
    private final StringProperty city;
    private final ObjectProperty<LocalDate> birthday;

    /**
     * Default constructor.
     */
    public TaskModel() {
        this(null, null);
    }

    /**
     * Constructor with some initial data.
     *
     * @param firstName
     * @param description
     */
    public TaskModel(String firstName, String description) {
        this.firstName = new SimpleStringProperty(firstName);
        this.description = new SimpleStringProperty(description);

        // Some initial dummy data, just for convenient testing.
        this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        this.street = new SimpleStringProperty("some location");
        this.postalCode = new SimpleIntegerProperty(3456);
        this.city = new SimpleStringProperty("some city");
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getStreet() {
        return street.get();
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public StringProperty streetProperty() {
        return street;
    }

    public int getPostalCode() {
        return postalCode.get();
    }

    public void setPostalCode(int postalCode) {
        this.postalCode.set(postalCode);
    }

    public IntegerProperty postalCodeProperty() {
        return postalCode;
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public StringProperty cityProperty() {
        return city;
    }

    public LocalDate getBirthday() {
        return birthday.get();
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }

    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }
}
