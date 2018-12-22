package io.vanachte.jan.bootstrap;

import io.vanachte.jan.bootstrap.person.PersonJavaFxController;
import io.vanachte.jan.bootstrap.person.PersonModel;
import io.vanachte.jan.bootstrap.task.TaskJavaFxController;

public interface JavaFxControllerMediator {

    void registerPersonController(PersonJavaFxController controller);
    void registerTaskController(TaskJavaFxController controller);
    void updatePersonModel(PersonModel data);
}
