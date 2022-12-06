module ru.vsu.cs.korotaev.cardgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens ru.vsu.cs.korotaev to javafx.fxml;
    exports ru.vsu.cs.korotaev;
    exports ru.vsu.cs.korotaev.Enums;
    opens ru.vsu.cs.korotaev.Enums to javafx.fxml;
    exports ru.vsu.cs.korotaev.ObjectClasses;
    opens ru.vsu.cs.korotaev.ObjectClasses to javafx.fxml;
    exports ru.vsu.cs.korotaev.LogicClasses;
    opens ru.vsu.cs.korotaev.LogicClasses to javafx.fxml;
}