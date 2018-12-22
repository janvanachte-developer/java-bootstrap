package io.vanachte.jan.bootstrap;

import io.vanachte.jan.bootstrap.person.PersonJavaFxController;
import io.vanachte.jan.bootstrap.person.PersonModel;
import io.vanachte.jan.bootstrap.task.TaskJavaFxController;
import org.springframework.stereotype.Component;

@Component
public class JavaFxControllerMediatorImpl implements JavaFxControllerMediator {

    private PersonJavaFxController personOverviewController;
    private TaskJavaFxController personTaskController;

    @Override
    public void registerPersonController(PersonJavaFxController controller) {
        this.personOverviewController = controller;
    }

    @Override
    public void registerTaskController(TaskJavaFxController controller) {
        this.personTaskController = controller;

    }

    @Override
    public void updatePersonModel(PersonModel data) {
        personTaskController.update(data);

    }
}
