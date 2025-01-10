module com.example.javainventorytracker {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javainventorytracker to javafx.fxml;
    exports com.example.javainventorytracker;
}