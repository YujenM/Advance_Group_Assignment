module com.example.advance_group {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.advance_group to javafx.fxml;
    exports com.example.advance_group;
}