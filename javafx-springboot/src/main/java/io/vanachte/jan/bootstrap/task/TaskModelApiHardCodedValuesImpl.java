package io.vanachte.jan.bootstrap.task;

import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

@Named
public class TaskModelApiHardCodedValuesImpl implements TaskModelApi {

    private Map<String,TaskModel> taskservice = new HashMap<>();

    public TaskModelApiHardCodedValuesImpl() {
        taskservice.put("Muster", new TaskModel("Do first task","Sed iaculis et justo eget volutpat. "));
        taskservice.put("Dubois",new TaskModel("Do Dubois task","Mauris molestie egestas faucibus. Suspendisse potenti. "));
        taskservice.put("Kurz",new TaskModel("Do second task","Praesent at est leo. Sed placerat enim nec turpis condimentum."));
        taskservice.put("Meier",new TaskModel("Do third task","Nunc fermentum leo nec pretium gravida. Nam nec imperdiet mauris."));
        taskservice.put("Meyer",new TaskModel("Do task number 5","Vestibulum auctor facilisis nisi vitae iaculis. Quisque ornare, orci nec."));
        taskservice.put("Best",new TaskModel("Do last but one task","Fusce placerat nunc ipsum, at interdum risus vestibulum non. Vivamus."));
        taskservice.put("Meier",new TaskModel("Do last task","nteger vel auctor turpis, nec cursus ligula. Proin quis nibh."));
        taskservice.put("Kunz",new TaskModel("Do second task","Praesent at est leo. Sed placerat enim nec turpis condimentum."));
    }

    @Override
    public TaskModel findByPerson(String lastName) {
        return taskservice.get(lastName);
    }
}
