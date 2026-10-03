module com.lifesystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;
    requires jbcrypt;

    opens com.lifesystem to javafx.fxml;
    opens com.lifesystem.controllers to javafx.fxml;

    exports com.lifesystem;
    exports com.lifesystem.controllers;
    exports com.lifesystem.database;
    exports com.lifesystem.models;
}