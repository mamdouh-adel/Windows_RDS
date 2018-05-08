package UiBase;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class ConnectBase extends FlowPane {

    private final Label label;
    private final Lighting lighting;
    private final Pane pane;
    private final FlowPane flowPane;
    private final Label label0;
    private final TextField ip_text;
    private final DropShadow dropShadow;
    private final Label label1;
    private final TextField port_txt;
    private final DropShadow dropShadow0;
    private final FlowPane flowPane0;
    private final Label label2;
    private final TextField domain_txt;
    private final DropShadow dropShadow1;
    private final FlowPane flowPane1;
    private final Label label3;
    private final TextField user_text;
    private final DropShadow dropShadow2;
    private final FlowPane flowPane2;
    private final Label label4;
    private final PasswordField pass_text;
    private final DropShadow dropShadow3;
    private final Button connect_btn;
    private final FlowPane flowPane3;
    private final Label status_label;

    public TextField getIp_text() {
        return ip_text;
    }

    public TextField getUser_text() {
        return user_text;
    }

    public PasswordField getPass_text() {
        return pass_text;
    }

    public Button getConnect_btn() {
        return connect_btn;
    }

    public Label getStatus_label() {
        return status_label;
    }

    public TextField getPort_txt() {
        return port_txt;
    }

    public TextField getDomain_txt() {
        return domain_txt;
    }

    public ConnectBase() {

        label = new Label();
        lighting = new Lighting();
        pane = new Pane();
        flowPane = new FlowPane();
        label0 = new Label();
        ip_text = new TextField();
        dropShadow = new DropShadow();
        label1 = new Label();
        port_txt = new TextField();
        dropShadow0 = new DropShadow();
        flowPane0 = new FlowPane();
        label2 = new Label();
        domain_txt = new TextField();
        dropShadow1 = new DropShadow();
        flowPane1 = new FlowPane();
        label3 = new Label();
        user_text = new TextField();
        dropShadow2 = new DropShadow();
        flowPane2 = new FlowPane();
        label4 = new Label();
        pass_text = new PasswordField();
        dropShadow3 = new DropShadow();
        connect_btn = new Button();
        flowPane3 = new FlowPane();
        status_label = new Label();

        setAlignment(javafx.geometry.Pos.CENTER);
        setColumnHalignment(javafx.geometry.HPos.CENTER);
        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setOrientation(javafx.geometry.Orientation.VERTICAL);
        setPrefHeight(400.0);
        setPrefWidth(600.0);




        //---------------my edit ----------------

        port_txt.setPrefWidth(80.0);

        ip_text.setStyle("-fx-text-fill: green; -fx-font-size: 16; -fx-font-weight: bold");
        user_text.setStyle("-fx-text-fill: black; -fx-font-size: 16; -fx-font-weight: bold");
        port_txt.setStyle("-fx-text-fill: brown; -fx-font-size: 16; -fx-font-weight: bold");
        domain_txt.setStyle("-fx-text-fill: blue; -fx-font-size: 16; -fx-font-weight: bold");

        //---------------------------------------

        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        label.setText("Connect to Remote Desktop Service");
        label.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        label.setTextFill(javafx.scene.paint.Color.valueOf("#47658f"));
        label.setFont(new Font("System Bold", 18.0));
        label.setOpaqueInsets(new Insets(0.0));
        FlowPane.setMargin(label, new Insets(5.0, 0.0, 5.0, 0.0));

        label.setEffect(lighting);

        pane.setPrefHeight(226.0);
        pane.setPrefWidth(530.0);
        pane.setStyle("-fx-border-color: #617785; -fx-border-width: 4px;");
        FlowPane.setMargin(pane, new Insets(10.0, 0.0, 0.0, 0.0));

        flowPane.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        flowPane.setColumnHalignment(javafx.geometry.HPos.CENTER);
        flowPane.setHgap(10.0);
        flowPane.setLayoutY(20.0);
        flowPane.setPrefHeight(30.0);
        flowPane.setPrefWidth(522.0);

        label0.setText("IP/Hostname:");
        label0.setTextFill(javafx.scene.paint.Color.valueOf("#724428"));
        label0.setFont(new Font("System Bold", 14.0));

        ip_text.setEffect(dropShadow);

        label1.setLayoutX(40.0);
        label1.setLayoutY(16.0);
        label1.setText("Port:");
        label1.setTextFill(javafx.scene.paint.Color.valueOf("#724428"));
        label1.setFont(new Font("System Bold", 14.0));

        port_txt.setLayoutX(157.0);
        port_txt.setLayoutY(12.0);

        port_txt.setEffect(dropShadow0);
        flowPane.setPadding(new Insets(0.0, 0.0, 0.0, 30.0));

        flowPane0.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        flowPane0.setColumnHalignment(javafx.geometry.HPos.CENTER);
        flowPane0.setHgap(51.0);
        flowPane0.setLayoutY(170.0);
        flowPane0.setPrefHeight(30.0);
        flowPane0.setPrefWidth(492.0);

        label2.setText("Domain:");
        label2.setTextFill(javafx.scene.paint.Color.valueOf("#724428"));
        label2.setFont(new Font("System Bold", 14.0));

        domain_txt.setEffect(dropShadow1);
        flowPane0.setPadding(new Insets(0.0, 0.0, 0.0, 30.0));

        flowPane1.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        flowPane1.setHgap(31.0);
        flowPane1.setLayoutY(70.0);
        flowPane1.setPrefHeight(30.0);
        flowPane1.setPrefWidth(492.0);

        label3.setText("Username:");
        label3.setTextFill(javafx.scene.paint.Color.valueOf("#724428"));
        label3.setFont(new Font("System Bold", 14.0));

        user_text.setEffect(dropShadow2);
        flowPane1.setPadding(new Insets(0.0, 0.0, 0.0, 30.0));

        flowPane2.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        flowPane2.setColumnHalignment(javafx.geometry.HPos.CENTER);
        flowPane2.setHgap(35.0);
        flowPane2.setLayoutY(120.0);
        flowPane2.setPrefHeight(30.0);
        flowPane2.setPrefWidth(492.0);

        label4.setText("Password:");
        label4.setTextFill(javafx.scene.paint.Color.valueOf("#724428"));
        label4.setFont(new Font("System Bold", 14.0));

        pass_text.setPrefHeight(26.0);
        pass_text.setPrefWidth(203.0);

        pass_text.setEffect(dropShadow3);
        flowPane2.setPadding(new Insets(0.0, 0.0, 0.0, 30.0));

        connect_btn.setAlignment(javafx.geometry.Pos.CENTER);
        connect_btn.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        connect_btn.setMnemonicParsing(false);
        connect_btn.setText("Connect");
        connect_btn.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        connect_btn.setTextFill(javafx.scene.paint.Color.valueOf("#204361"));
        FlowPane.setMargin(connect_btn, new Insets(20.0, 0.0, 0.0, 0.0));
        connect_btn.setFont(new Font("Droid Serif Bold", 19.0));

        flowPane3.setAlignment(javafx.geometry.Pos.CENTER);
        flowPane3.setColumnHalignment(javafx.geometry.HPos.CENTER);
        flowPane3.setPrefHeight(50.0);
        flowPane3.setPrefWidth(530.0);

        status_label.setText("Cannot connect to RDS system");
        status_label.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        status_label.setTextFill(javafx.scene.paint.Color.valueOf("#e40707"));
        FlowPane.setMargin(status_label, new Insets(0.0));
        status_label.setFont(new Font(17.0));
        FlowPane.setMargin(flowPane3, new Insets(10.0, 0.0, 0.0, 0.0));

        getChildren().add(label);
        flowPane.getChildren().add(label0);
        flowPane.getChildren().add(ip_text);
        flowPane.getChildren().add(label1);
        flowPane.getChildren().add(port_txt);
        pane.getChildren().add(flowPane);

        flowPane1.getChildren().add(label3);
        flowPane1.getChildren().add(user_text);
        pane.getChildren().add(flowPane1);

        flowPane2.getChildren().add(label4);
        flowPane2.getChildren().add(pass_text);
        pane.getChildren().add(flowPane2);

        flowPane0.getChildren().add(label2);
        flowPane0.getChildren().add(domain_txt);
        pane.getChildren().add(flowPane0);

        getChildren().add(pane);
        getChildren().add(connect_btn);
        flowPane3.getChildren().add(status_label);
        getChildren().add(flowPane3);

    }
}


     