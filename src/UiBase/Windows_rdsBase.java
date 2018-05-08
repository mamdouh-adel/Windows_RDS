package UiBase;


import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

public class Windows_rdsBase extends FlowPane {

    private final FlowPane flowPane;
    private final Button donate_btn;
    private final FlowPane flowPane0;
    private final Button add_btn;
    private final GridPane grid_pane;
    private final ColumnConstraints columnConstraints;
    private final ColumnConstraints columnConstraints0;
    private final ColumnConstraints columnConstraints1;
    private final ColumnConstraints columnConstraints2;

    private final ColumnConstraints columnConstraints3;
    private final ColumnConstraints columnConstraints4;
    private final ColumnConstraints columnConstraints5;

    private final RowConstraints rowConstraints;
    private final RowConstraints rowConstraints0;
    private final RowConstraints rowConstraints1;
    private final RowConstraints rowConstraints2;

    private final RowConstraints rowConstraints3;
    private final RowConstraints rowConstraints4;
    private final RowConstraints rowConstraints5;

   // private final Button testBtn;

    private final DropShadow dropShadow;
    private final FlowPane flowPane1;
    private final Label status_label;

    private final Button rdp_btn;

    public Button getDonate_btn() {
        return donate_btn;
    }

    public Button getAdd_btn() {
        return add_btn;
    }

    public GridPane getGrid_pane() {
        return grid_pane;
    }

    public Label getStatus_label() {
        return status_label;
    }

    public Button getRdp_btn() {
        return rdp_btn;
    }

    public Windows_rdsBase() {

        flowPane = new FlowPane();
        donate_btn = new Button();
        flowPane0 = new FlowPane();
        add_btn = new Button();
        grid_pane = new GridPane();
        columnConstraints = new ColumnConstraints();
        columnConstraints0 = new ColumnConstraints();
        columnConstraints1 = new ColumnConstraints();
        columnConstraints2 = new ColumnConstraints();

        columnConstraints3 = new ColumnConstraints();
        columnConstraints4 = new ColumnConstraints();
        columnConstraints5 = new ColumnConstraints();

        rowConstraints = new RowConstraints();
        rowConstraints0 = new RowConstraints();
        rowConstraints1 = new RowConstraints();
        rowConstraints2 = new RowConstraints();

        rowConstraints3 = new RowConstraints();
        rowConstraints4 = new RowConstraints();
        rowConstraints5 = new RowConstraints();


        rdp_btn = new Button();

        dropShadow = new DropShadow();
        flowPane1 = new FlowPane();
        status_label = new Label();

        setAlignment(javafx.geometry.Pos.TOP_RIGHT);
        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(428.0);
        setPrefWidth(600.0);
        setStyle("-fx-background-color: #E6F4FA;");
        setVgap(3.0);

        grid_pane.setVgap(20.0);

        flowPane.setAlignment(javafx.geometry.Pos.TOP_RIGHT);
        flowPane.setPrefHeight(31.0);
        flowPane.setPrefWidth(602.0);
        flowPane.setStyle("-fx-background-color: B0C2CB;");

        donate_btn.setMnemonicParsing(false);
        donate_btn.setText("Donate");
        donate_btn.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        donate_btn.setTextFill(javafx.scene.paint.Color.valueOf("#2B90CD"));
        donate_btn.setFont(new Font(14.0));

        flowPane0.setPrefHeight(38.0);
        flowPane0.setPrefWidth(598.0);

        add_btn.setMnemonicParsing(false);
        add_btn.setPrefHeight(50.0);
        add_btn.setPrefWidth(50.0);
        add_btn.setText("ADD");


        rdp_btn.setMnemonicParsing(false);
        rdp_btn.setPrefHeight(50.0);
        rdp_btn.setPrefWidth(50.0);
        rdp_btn.setText("RDP");

        grid_pane.setAlignment(javafx.geometry.Pos.CENTER);
        grid_pane.setPrefHeight(280.0);
        grid_pane.setPrefWidth(559.0);

        columnConstraints.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints.setMinWidth(10.0);
        columnConstraints.setPrefWidth(100.0);

        columnConstraints0.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints0.setMinWidth(10.0);
        columnConstraints0.setPrefWidth(100.0);

        columnConstraints1.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints1.setMinWidth(10.0);
        columnConstraints1.setPrefWidth(100.0);

        columnConstraints2.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints2.setMinWidth(10.0);
        columnConstraints2.setPrefWidth(100.0);

        rowConstraints.setMinHeight(10.0);
        rowConstraints.setPrefHeight(30.0);
        rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints0.setMinHeight(10.0);
        rowConstraints0.setPrefHeight(30.0);
        rowConstraints0.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints1.setMinHeight(10.0);
        rowConstraints1.setPrefHeight(30.0);
        rowConstraints1.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints2.setMinHeight(10.0);
        rowConstraints2.setPrefHeight(30.0);
        rowConstraints2.setVgrow(javafx.scene.layout.Priority.SOMETIMES);
        FlowPane.setMargin(grid_pane, new Insets(20.0, 20.0, 5.0, 20.0));

      /*  GridPane.setColumnIndex(testBtn, 7);
        GridPane.setRowIndex(testBtn, 7);
        testBtn.setAlignment(javafx.geometry.Pos.CENTER);
        testBtn.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        testBtn.setMnemonicParsing(false);
        testBtn.setText("Button");
        testBtn.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);*/

        grid_pane.setEffect(dropShadow);

        flowPane1.setLayoutX(10.0);
        flowPane1.setLayoutY(370.0);
        flowPane1.setPrefHeight(31.0);
        flowPane1.setPrefWidth(602.0);
        flowPane1.setStyle("-fx-background-color: #A2B3BB;");

        status_label.setText("status");
        status_label.setTextFill(javafx.scene.paint.Color.valueOf("#d01e1e"));
        status_label.setFont(new Font(16.0));
        FlowPane.setMargin(status_label, new Insets(5.0, 0.0, 0.0, 10.0));


        flowPane0.setHgap(230.0);

        flowPane.getChildren().add(donate_btn);
        getChildren().add(flowPane);

        flowPane0.getChildren().add(add_btn);

        flowPane0.getChildren().add(rdp_btn);

        getChildren().add(flowPane0);
        grid_pane.getColumnConstraints().add(columnConstraints);
        grid_pane.getColumnConstraints().add(columnConstraints0);
        grid_pane.getColumnConstraints().add(columnConstraints1);
        grid_pane.getColumnConstraints().add(columnConstraints2);

        grid_pane.getColumnConstraints().add(columnConstraints3);
        grid_pane.getColumnConstraints().add(columnConstraints4);
        grid_pane.getColumnConstraints().add(columnConstraints5);

        grid_pane.getRowConstraints().add(rowConstraints);
        grid_pane.getRowConstraints().add(rowConstraints0);
        grid_pane.getRowConstraints().add(rowConstraints1);
        grid_pane.getRowConstraints().add(rowConstraints2);

        grid_pane.getRowConstraints().add(rowConstraints3);
        grid_pane.getRowConstraints().add(rowConstraints4);
        grid_pane.getRowConstraints().add(rowConstraints5);


        //grid_pane.getChildren().add(testBtn);

        getChildren().add(grid_pane);
        flowPane1.getChildren().add(status_label);
        getChildren().add(flowPane1);

    }
}
