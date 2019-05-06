package main;

import controller.DBSyncAbstractEngine;
import controller.DataController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.SpringContainer;

/**
 * Main application class
 */
public class MainApp {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringContainer.class);
        DBSyncAbstractEngine engine = ctx.getBean(DataController.class);
        engine.updateLocalDB();
    }
}