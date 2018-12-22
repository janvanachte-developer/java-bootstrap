package io.vanachte.jan.bootstrap;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application extends javafx.application.Application {

    private ConfigurableApplicationContext context;
    private Parent rootNode;

    @Override
    public void init() throws Exception {
        context = SpringApplication.run(Application.class);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Application.fxml"));
        loader.setControllerFactory(context::getBean);
        rootNode = loader.load();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        double width = visualBounds.getWidth();
        double height = visualBounds.getHeight();

        primaryStage.setScene(new Scene(rootNode, width, height));
        primaryStage.centerOnScreen();

        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        context.stop();
    }

    public static void main(String[] args) {launch(Application.class, args);}


}
