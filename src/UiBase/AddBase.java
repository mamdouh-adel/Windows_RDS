package UiBase;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;


public class AddBase extends FlowPane {

    private final FlowPane flowPane;
    private final Button back_btn;
    private final FlowPane flowPane0;
    private final Label label;
    private final RadioButton app_radio;
    private final RadioButton share_radio;
    private final FlowPane flowPane_name;
    private final Label app_share_name_label;
    private final TextField app_name_text;
    private final FlowPane flowPane_app;
    private final Label app_share_script_label;
    private final TextField app_share_script_text;
    private final FlowPane icon_flowPane;

    private final FlowPane empty_flowPane_for_share;

    private final Label app_share_icon_label;

    private final Button icon_chooser_btn;

    private final Button choose_share_btn;

    private final FlowPane test_save_flowPane;
    private final Button test_btn;
    private final Button save_btn;
    private final FlowPane status_flowPane;
    private final Label status_label_add;

    private final ImageView icon_file_imageView;

    private final Label warn_name_label;
    private final Label warn_script_label;

    private final Label warn_share_folder;

    private final Label warn_icon_label;

    private final FlowPane flowPane_share;

    private final Label choose_share_label;



    public Button getBack_btn() {
        return back_btn;
    }

    public RadioButton getApp_radio() {
        return app_radio;
    }

    public RadioButton getShare_radio() {
        return share_radio;
    }

    public Label getApp_share_name_label() {
        return app_share_name_label;
    }

    public TextField getApp_name_text() {
        return app_name_text;
    }

    public Label getApp_share_script_label() {
        return app_share_script_label;
    }

    public TextField getApp_share_script_text() {
        return app_share_script_text;
    }

    public Label getApp_share_icon_label() {
        return app_share_icon_label;
    }

    public Button getIcon_chooser_btn() {
        return icon_chooser_btn;
    }

    public Button getTest_btn() {
        return test_btn;
    }

    public Button getSave_btn() {
        return save_btn;
    }

    public Label getStatus_label_add() {
        return status_label_add;
    }

    public ImageView getIcon_file_imageView() {
        return icon_file_imageView;
    }

    public Label getWarn_name_label() {
        return warn_name_label;
    }

    public Label getWarn_script_label() {
        return warn_script_label;
    }

    public Label getWarn_icon_label() {
        return warn_icon_label;
    }

    public FlowPane getIcon_flowPane() {
        return icon_flowPane;
    }

    public FlowPane getFlowPane_app() {
        return flowPane_app;
    }

    public FlowPane getFlowPane_share() {
        return flowPane_share;
    }

    public Button getChoose_share_btn() {
        return choose_share_btn;
    }

    public Label getWarn_share_folder() {
        return warn_share_folder;
    }

    public FlowPane getStatus_flowPane() {
        return status_flowPane;
    }

    public FlowPane getPrimeFlowPane() {
        return this;
    }

    public FlowPane getTest_save_flowPane() {
        return test_save_flowPane;
    }

    public FlowPane getEmpty_flowPane_for_share() {
        return empty_flowPane_for_share;
    }

    public FlowPane getFlowPane_name() {
        return flowPane_name;
    }

    //================

    public AddBase() {

        flowPane = new FlowPane();
        back_btn = new Button();
        flowPane0 = new FlowPane();
        label = new Label();
        app_radio = new RadioButton();
        share_radio = new RadioButton();
        flowPane_name = new FlowPane();
        app_share_name_label = new Label();
        app_name_text = new TextField();
        flowPane_app = new FlowPane();
        app_share_script_label = new Label();
        app_share_script_text = new TextField();
        icon_flowPane = new FlowPane();
        app_share_icon_label = new Label();
        icon_chooser_btn = new Button();

        choose_share_btn = new Button();

        test_save_flowPane = new FlowPane();
        test_btn = new Button();
        save_btn = new Button();
        status_flowPane = new FlowPane();
        status_label_add = new Label();

        flowPane_share = new FlowPane();

        empty_flowPane_for_share = new FlowPane();

        choose_share_label = new Label();

        icon_file_imageView = new ImageView();

        icon_file_imageView.setFitHeight(30.0);
        icon_file_imageView.setFitWidth(30.0);

        FlowPane.setMargin(icon_file_imageView, new Insets(0.0, 0.0, 0.0, 20.0));

        warn_name_label = new Label("*");
        warn_script_label = new Label("*");
        warn_icon_label = new Label("*");
        warn_share_folder = new Label("*");

        setAlignment(javafx.geometry.Pos.TOP_RIGHT);
        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(428.0);
        setPrefWidth(600.0);
        setStyle("-fx-background-color: #E6F4FA;");
        setVgap(20.0);


        app_share_script_text.setPrefWidth(240.0);

        flowPane.setPrefHeight(31.0);
        flowPane.setPrefWidth(602.0);
        flowPane.setStyle("-fx-background-color: #A2B3BB;");

        back_btn.setMnemonicParsing(false);
        back_btn.setText("Back");
        back_btn.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        back_btn.setTextFill(javafx.scene.paint.Color.valueOf("#1444db"));
        back_btn.setFont(new Font(14.0));

        back_btn.setPrefWidth(32.0);
        back_btn.setPrefHeight(32.0);

        FlowPane.setMargin(back_btn, new Insets(0.0, 0.0, 0.0, 3.0));

        FlowPane.setMargin(flowPane, new Insets(0.0));

        flowPane0.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        flowPane0.setColumnHalignment(javafx.geometry.HPos.CENTER);
        flowPane0.setHgap(20.0);
        flowPane0.setPrefHeight(38.0);
        flowPane0.setPrefWidth(598.0);
        flowPane0.setStyle("-fx-background-color: #dbe9ee;");

        label.setText("Choose Type:");
        label.setTextFill(javafx.scene.paint.Color.valueOf("#486a9e"));
        label.setFont(new Font("System Bold", 16.0));
        FlowPane.setMargin(label, new Insets(0.0, 0.0, 0.0, 10.0));

        //---------- warninig labels------------

        warn_name_label.setTextFill(javafx.scene.paint.Color.valueOf("#E02800"));
        warn_name_label.setFont(new Font("System Bold", 18.0));

        warn_script_label.setTextFill(javafx.scene.paint.Color.valueOf("#E02800"));
        warn_script_label.setFont(new Font("System Bold", 18.0));

        warn_icon_label.setTextFill(javafx.scene.paint.Color.valueOf("#E02800"));
        warn_icon_label.setFont(new Font("System Bold", 18.0));

        warn_share_folder.setTextFill(javafx.scene.paint.Color.valueOf("#E02800"));
        warn_share_folder.setFont(new Font("System Bold", 18.0));

        app_radio.setMnemonicParsing(false);
        app_radio.setText("Application");
        app_radio.setTextFill(javafx.scene.paint.Color.valueOf("#906086"));
        FlowPane.setMargin(app_radio, new Insets(0.0));
        app_radio.setFont(new Font("System Bold", 14.0));

        share_radio.setMnemonicParsing(false);
        share_radio.setText("Share");
        share_radio.setTextFill(javafx.scene.paint.Color.valueOf("#906086"));
        FlowPane.setMargin(share_radio, new Insets(0.0, 0.0, 0.0, 10.0));
        share_radio.setFont(new Font("System Bold", 14.0));
        FlowPane.setMargin(flowPane0, new Insets(20.0, 0.0, 0.0, 0.0));

        flowPane_name.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        flowPane_name.setColumnHalignment(javafx.geometry.HPos.CENTER);
        flowPane_name.setHgap(3.0);
        flowPane_name.setLayoutX(12.0);
        flowPane_name.setLayoutY(64.0);
        flowPane_name.setPrefHeight(38.0);
        flowPane_name.setPrefWidth(598.0);
        flowPane_name.setStyle("-fx-background-color: #dbe9ee;");

        app_share_name_label.setText("App/Share Name:");
        app_share_name_label.setTextFill(javafx.scene.paint.Color.valueOf("#486a9e"));
        app_share_name_label.setFont(new Font("System Bold", 16.0));
        FlowPane.setMargin(app_share_name_label, new Insets(0.0, 0.0, 0.0, 10.0));

        flowPane_app.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        flowPane_app.setColumnHalignment(javafx.geometry.HPos.CENTER);
        flowPane_app.setHgap(3.0);
        flowPane_app.setLayoutX(12.0);
        flowPane_app.setLayoutY(159.0);
        flowPane_app.setPrefHeight(38.0);
        flowPane_app.setPrefWidth(598.0);
        flowPane_app.setStyle("-fx-background-color: #dbe9ee;");

        flowPane_share.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        flowPane_share.setColumnHalignment(javafx.geometry.HPos.CENTER);
        flowPane_share.setHgap(3.0);
        flowPane_share.setLayoutX(12.0);
        flowPane_share.setLayoutY(159.0);
        flowPane_share.setPrefHeight(38.0);
        flowPane_share.setPrefWidth(598.0);
        flowPane_share.setStyle("-fx-background-color: #dbe9ee;");

        app_share_script_label.setText("App/Share Script:");
        app_share_script_label.setTextFill(javafx.scene.paint.Color.valueOf("#486a9e"));
        app_share_script_label.setFont(new Font("System Bold", 16.0));
        FlowPane.setMargin(app_share_script_label, new Insets(0.0, 0.0, 0.0, 10.0));

        choose_share_label.setText("Select Share Folder:");
        choose_share_label.setTextFill(javafx.scene.paint.Color.valueOf("#486a9e"));
        choose_share_label.setFont(new Font("System Bold", 16.0));
        FlowPane.setMargin(choose_share_label, new Insets(0.0, 0.0, 0.0, 10.0));

        icon_flowPane.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        icon_flowPane.setColumnHalignment(javafx.geometry.HPos.CENTER);
        icon_flowPane.setHgap(3.0);
        icon_flowPane.setLayoutX(12.0);
        icon_flowPane.setLayoutY(227.0);
        icon_flowPane.setPrefHeight(38.0);
        icon_flowPane.setPrefWidth(598.0);
        icon_flowPane.setStyle("-fx-background-color: #dbe9ee;");

        icon_flowPane.setAlignment(Pos.CENTER_LEFT);


        empty_flowPane_for_share.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        empty_flowPane_for_share.setColumnHalignment(javafx.geometry.HPos.CENTER);
        empty_flowPane_for_share.setHgap(3.0);
        empty_flowPane_for_share.setLayoutX(12.0);
        empty_flowPane_for_share.setLayoutY(227.0);
        empty_flowPane_for_share.setPrefHeight(38.0);
        empty_flowPane_for_share.setPrefWidth(598.0);

        empty_flowPane_for_share.setAlignment(Pos.CENTER_LEFT);

        app_share_icon_label.setText("App/Share Icon:");
        app_share_icon_label.setTextFill(javafx.scene.paint.Color.valueOf("#486a9e"));
        app_share_icon_label.setFont(new Font("System Bold", 16.0));
        FlowPane.setMargin(app_share_icon_label, new Insets(0.0, 0.0, 0.0, 10.0));


        icon_chooser_btn.setText("Choose Icon Image...");
        icon_chooser_btn.setPrefWidth(160.0);

        choose_share_btn.setText("Choose Share Folder...");
        choose_share_btn.setPrefWidth(170.0);

        test_save_flowPane.setAlignment(javafx.geometry.Pos.CENTER);
        test_save_flowPane.setColumnHalignment(javafx.geometry.HPos.CENTER);
        test_save_flowPane.setHgap(100.0);
        test_save_flowPane.setLayoutX(12.0);
        test_save_flowPane.setLayoutY(295.0);
        test_save_flowPane.setPrefHeight(38.0);
        test_save_flowPane.setPrefWidth(598.0);
        test_save_flowPane.setStyle("-: ;");

        test_btn.setMnemonicParsing(false);
        test_btn.setPrefHeight(33.0);
        test_btn.setPrefWidth(100.0);
        test_btn.setText("Test");
        test_btn.setTextFill(javafx.scene.paint.Color.valueOf("#bc3d32"));
        test_btn.setFont(new Font("System Bold", 16.0));

        save_btn.setMnemonicParsing(false);
        save_btn.setPrefHeight(33.0);
        save_btn.setPrefWidth(100.0);
        save_btn.setText("Save");
        save_btn.setTextFill(javafx.scene.paint.Color.valueOf("#2e895f"));
        save_btn.setFont(new Font("System Bold", 16.0));
        FlowPane.setMargin(test_save_flowPane, new Insets(19.0, 0.0, 0.0, 0.0));

        status_flowPane.setLayoutX(10.0);
        status_flowPane.setLayoutY(370.0);
        status_flowPane.setPrefHeight(31.0);
        status_flowPane.setPrefWidth(602.0);
        status_flowPane.setStyle("-fx-background-color: #A2B3BB;");

        status_label_add.setText("status");
        status_label_add.setTextFill(javafx.scene.paint.Color.valueOf("#d01e1e"));
        status_label_add.setFont(new Font(16.0));
        FlowPane.setMargin(status_label_add, new Insets(5.0, 0.0, 0.0, 10.0));
        FlowPane.setMargin(status_flowPane, new Insets(15.0, 0.0, 0.0, 0.0));

        flowPane.getChildren().add(back_btn);
        getChildren().add(flowPane);
        flowPane0.getChildren().add(label);
        flowPane0.getChildren().add(app_radio);
        flowPane0.getChildren().add(share_radio);
        getChildren().add(flowPane0);
        flowPane_name.getChildren().add(app_share_name_label);
        flowPane_name.getChildren().add(app_name_text);

        flowPane_name.getChildren().add(warn_name_label);

        getChildren().add(flowPane_name);
        flowPane_app.getChildren().add(app_share_script_label);
        flowPane_app.getChildren().add(app_share_script_text);
        flowPane_app.getChildren().add(warn_script_label);

        getChildren().add(flowPane_app);
        icon_flowPane.getChildren().add(app_share_icon_label);
        icon_flowPane.getChildren().add(icon_chooser_btn);

        icon_flowPane.getChildren().add(icon_file_imageView);

        icon_flowPane.getChildren().add(warn_icon_label);
        getChildren().add(icon_flowPane);

        getChildren().add(empty_flowPane_for_share);

        flowPane_share.getChildren().add(choose_share_label);
        flowPane_share.getChildren().add(choose_share_btn);
        flowPane_share.getChildren().add(warn_share_folder);

        getChildren().add(flowPane_share);

        test_save_flowPane.getChildren().add(test_btn);
        test_save_flowPane.getChildren().add(save_btn);
        getChildren().add(test_save_flowPane);
        status_flowPane.getChildren().add(status_label_add);
        getChildren().add(status_flowPane);

    }
}

