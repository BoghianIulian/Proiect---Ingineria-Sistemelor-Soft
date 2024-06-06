module com.example.issapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens com.example.issapp to javafx.fxml;
    opens Domain to javafx.base;
    exports com.example.issapp;
}