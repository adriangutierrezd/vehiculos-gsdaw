module prog_10.prog_10_alt_fx {
    requires javafx.controls;
    requires javafx.fxml;


    opens prog_10.prog_10_alt_fx to javafx.fxml;
    exports prog_10.prog_10_alt_fx;
}