module com.mycompany.cloudstorage {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires java.desktop;

    opens com.mycompany.cloudstorage to javafx.fxml;
    exports com.mycompany.cloudstorage;
}
