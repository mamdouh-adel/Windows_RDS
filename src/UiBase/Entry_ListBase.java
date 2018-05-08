package UiBase;


import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class Entry_ListBase extends FlowPane {

    private final FlowPane flowPane;
    private final Label label;
    private final Lighting lighting;
    private final FlowPane flowPane0;
    private final Pane list_pane;
    private final ListView listView_hosts;
    private final FlowPane flowPane1;
    private final Button new_btn;
    private final Button open_btn;
    private final Button remove_btn;

    private FlowPane flowPane_list_top;

    private final Label host_lbl, user_lbl, status_lbl;


    public ListView getListView_hosts() {
        return listView_hosts;
    }

    public Button getNew_btn() {
        return new_btn;
    }

    public Button getOpen_btn() {
        return open_btn;
    }

    public Button getRemove_btn() {
        return remove_btn;
    }

    public Entry_ListBase() {

        flowPane = new FlowPane();
        label = new Label();
        lighting = new Lighting();
        flowPane0 = new FlowPane();
        list_pane = new Pane();
        listView_hosts = new ListView();
        flowPane1 = new FlowPane();
        new_btn = new Button();
        open_btn = new Button();
        remove_btn = new Button();

        flowPane_list_top = new FlowPane();

        host_lbl = new Label();
        user_lbl = new Label();
        status_lbl = new Label();

        setAlignment(javafx.geometry.Pos.TOP_CENTER);
        setColumnHalignment(javafx.geometry.HPos.CENTER);
        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setOrientation(javafx.geometry.Orientation.VERTICAL);
        setPrefHeight(400.0);
        setPrefWidth(350.0);

        flowPane.setAlignment(javafx.geometry.Pos.CENTER);
        flowPane.setColumnHalignment(javafx.geometry.HPos.CENTER);
        FlowPane.setMargin(flowPane, new Insets(10.0, 0.0, 0.0, 0.0));

        label.setText("Remote Desktop Service Servers:");
        label.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        label.setTextFill(javafx.scene.paint.Color.valueOf("#47658f"));
        label.setFont(new Font("System Bold", 14.0));
        label.setOpaqueInsets(new Insets(0.0));

        label.setEffect(lighting);

        flowPane0.setAlignment(javafx.geometry.Pos.CENTER);
        flowPane0.setColumnHalignment(javafx.geometry.HPos.CENTER);
        FlowPane.setMargin(flowPane0, new Insets(10.0, 0.0, 0.0, 0.0));

        list_pane.setPrefHeight(290.0);
        list_pane.setPrefWidth(263.0);
        list_pane.setStyle("-fx-border-color: #617785; -fx-border-width: 4px;");

        listView_hosts.setLayoutY(26.0);
        listView_hosts.setPrefHeight(270.0);
        listView_hosts.setPrefWidth(263.0);

        flowPane_list_top.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        flowPane_list_top.setColumnHalignment(javafx.geometry.HPos.CENTER);
        flowPane_list_top.setHgap(65.0);
        flowPane_list_top.setLayoutX(2.0);
        flowPane_list_top.setLayoutY(2.0);
        flowPane_list_top.setPrefHeight(25.0);
        flowPane_list_top.setPrefWidth(259.0);
        flowPane_list_top.setStyle("-fx-background-color: #E5EAEC; -fx-border-color: #24aad7;");

        host_lbl.setText("Host");
        host_lbl.setFont(new Font("System Bold", 13.0));

        user_lbl.setAlignment(javafx.geometry.Pos.CENTER);
        user_lbl.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        user_lbl.setText("User");
        user_lbl.setFont(new Font("System Bold", 13.0));

        status_lbl.setText("Status");
        status_lbl.setFont(new Font("System Bold", 13.0));
        flowPane_list_top.setPadding(new Insets(0.0, 5.0, 0.0, 5.0));

        flowPane1.setAlignment(javafx.geometry.Pos.CENTER);
        flowPane1.setColumnHalignment(javafx.geometry.HPos.CENTER);
        flowPane1.setHgap(30.0);
        FlowPane.setMargin(flowPane1, new Insets(20.0, 0.0, 0.0, 0.0));

        new_btn.setAlignment(javafx.geometry.Pos.CENTER);
        new_btn.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        new_btn.setMnemonicParsing(false);
        new_btn.setText("New");
        new_btn.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        new_btn.setTextFill(javafx.scene.paint.Color.valueOf("#359a1e"));
        new_btn.setFont(new Font("Droid Serif Bold", 16.0));

        open_btn.setAlignment(javafx.geometry.Pos.CENTER);
        open_btn.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        open_btn.setLayoutX(204.0);
        open_btn.setLayoutY(10.0);
        open_btn.setMnemonicParsing(false);
        open_btn.setText("Open");
        open_btn.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        open_btn.setTextFill(javafx.scene.paint.Color.valueOf("#204361"));
        open_btn.setFont(new Font("Droid Serif Bold", 16.0));

        remove_btn.setAlignment(javafx.geometry.Pos.CENTER);
        remove_btn.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        remove_btn.setLayoutX(151.0);
        remove_btn.setLayoutY(10.0);
        remove_btn.setMnemonicParsing(false);
        remove_btn.setText("Remove");
        remove_btn.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        remove_btn.setTextFill(javafx.scene.paint.Color.valueOf("#bb2a2a"));
        remove_btn.setFont(new Font("Droid Serif Bold", 16.0));

        flowPane.getChildren().add(label);
        getChildren().add(flowPane);
        list_pane.getChildren().add(listView_hosts);

        flowPane_list_top.getChildren().add(host_lbl);
        flowPane_list_top.getChildren().add(user_lbl);
        flowPane_list_top.getChildren().add(status_lbl);
        list_pane.getChildren().add(flowPane_list_top);

        flowPane0.getChildren().add(list_pane);
        getChildren().add(flowPane0);
        flowPane1.getChildren().add(new_btn);
        flowPane1.getChildren().add(open_btn);
        flowPane1.getChildren().add(remove_btn);
        getChildren().add(flowPane1);

    }
}

