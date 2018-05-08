package AppStart;

import UiBase.AddBase;
import UiBase.ConnectBase;
import UiBase.Entry_ListBase;
import UiBase.Windows_rdsBase;
import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utils.SelectShareDialog;
import utils.Helper;
import utils.Host_Cell;

import java.io.*;
import java.net.*;
import java.util.*;

public class Main extends Application {
    
    private static final String program_name = "RDS Apps Client";
    
    //----------------------

    private ConnectBase connectBase = new ConnectBase();
    private Windows_rdsBase windows_rdsBase = new Windows_rdsBase();
    private AddBase addBase = new AddBase();
    private Entry_ListBase listBase = new Entry_ListBase();

    //---------

    private File selectedIconFile;

    private File folderIconFile = new File("res/folder.png");

    private final ToggleGroup rd_group = new ToggleGroup();
    private String new_app_or_share_val = "application";

    private static String ip;
    private static String user;
    private static String pass;
    private static String domain = "";

    private final int default_port = 3389;
    private int port = default_port;

    private String host_in_db;
    private String user_in_db;

    private Host_Cell host_cell_current;

    private ArrayList<Map> appArrList;

    private HashSet<String> appNameList = new HashSet<>();

    private HashSet<String> tmp_appNameList = new HashSet<>();

    private Map<String, Object> U_map;

    private String old_app_name;

    private boolean is_already_exists;

    private boolean isNewConnection;

    private StringProperty status_property = new SimpleStringProperty();

    public static StringProperty select_share_property = new SimpleStringProperty();

    //------ main thread -----

    public static Thread main_thread = Thread.currentThread();

    //------------------

    private Stage primaryStage;
    private Scene mainScene;
    private Scene addScene;
    private Scene listScene;
    private Scene loginScene;

    private String loginScene_str;


    //----- entry list ----------

    private ListView listView_hosts;
    private Button list_new_btn, list_open_btn, list_remove_btn;

    //---connectBase elements------------

    private Button connect_btn;
    private TextField ip_text, user_text, domain_txt, port_txt;
    private PasswordField pass_text;
    private Label status_label;


    //---Windows_rdsBase elements------------

    private Button donate_btn, add_btn, rdp_btn;
    private GridPane rds_GridPane;
    private Label main_status_label;

    //-----addBase elements------

    private Label add_name_lbl, add_script_lbl, add_icon_lbl, add_status_lbl, warn_name_lbl,warn_script_lbl,warn_icon_lbl, warn_share_lbl;
    private Button add_back_btn, add_test_btn, add_save_btn, add_icon_chooser_btn, add_share_chooser_btn;
    private RadioButton add_app_rd, add_share_rd;
    private TextField add_name_text, add_script_text;
    private ImageView add_icon_file_imageView;
    private FlowPane icon_flowPane, flowPane_app, flowPane_share, primeFlowPane, add_status_flowPane, add_test_save_flowPane, add_empty_flowPane_for_share, flowPane_name;


    //------------ getter & setter --------------

    private String getCurrentScene_str(){

        return primaryStage.getScene().toString();
    }

    private String getMainScene_str(){

        return mainScene.toString();
    }

    public static String getIp() {
        return ip;
    }

    public static String getUser() {
        return user;
    }

    public static String getPass() {
        return pass;
    }

    public static String getDomain() {
        return domain;
    }


    //-------------- end of getter & setter ------------

    @Override
    public void start(Stage primaryStage) throws Exception{

        app_init();

        Scene loginScene = new Scene(connectBase);
        Scene mainScene = new Scene(windows_rdsBase);
        Scene addScene = new Scene(addBase);
        Scene listScene = new Scene(listBase);

        this.listScene = listScene;
        this.mainScene = mainScene;
        this.primaryStage = primaryStage;
        this.addScene = addScene;
        this.loginScene = loginScene;

        loginScene_str = loginScene.toString();

        //------- for all scene and stage ----------

        primaryStage.setResizable(false);

        //--------- custom close button event -----------

        Platform.setImplicitExit(false);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {

                if (primaryStage.getScene().equals(listScene)){

                    Helper.getInstance().kill_cmd();

                    System.exit(0);

                }else{

                    Platform.runLater(()->{

                        isNewConnection = false;

                        primaryStage.setScene(listScene);
                        primaryStage.show();

                    });
                }

            }
        });

        //---- Set Application Icon -------------

        primaryStage.getIcons().add(new Image(("res/app_icon.png")));

        //--------- entryList elements init --------

        listBase_init();

        //---connectBase elements init------------

        connectBase_init();

        //---windows elements init------------

        windowsBase_init();

        //----------- AddBase elements init---------

        addBase_init();


        //--------windowsBase part-----------------------------

        donate_btn.setOnAction(e->{

            System.out.println("Donate Action url...");
        });

        add_btn.setOnAction(e->{

            empty_add_fields();

            primaryStage.setScene(addScene);
        });

        //----------------------------------

        //--------- addBase part --------------------------------

        add_back_btn.setOnAction(e->{

            add_app_rd.setSelected(true);

            add_name_text.setStyle(null);

            add_share_rd.setDisable(false);
            add_app_rd.setDisable(false);

            primaryStage.setScene(mainScene);
        });

        add_icon_chooser_btn.setOnAction(e->{

            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter(

                   "images:  .jpg  .png .bmp .xpm", "*.jpg", "*.png", "*.bmp", "*.xpm");

            fileChooser.getExtensionFilters().add(fileExtensions);

            selectedIconFile = fileChooser.showOpenDialog(null);

            if (selectedIconFile != null) {

                Image image = new Image(selectedIconFile.toURI().toString());
                add_icon_file_imageView.setImage(image);

            }else{

                if(add_save_btn.getText().equalsIgnoreCase("update")){

                    add_icon_file_imageView.setImage(null);
                }
            }
        });

        //-radio--------

        rd_group.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (rd_group.getSelectedToggle() != null) {

                empty_add_fields();

                new_app_or_share_val = rd_group.getSelectedToggle().getUserData().toString();

                if(new_app_or_share_val.equals("application")){

                    add_name_lbl.setText("Application name:");
                    add_script_lbl.setText("Application name exactly as in RDS:");
                    add_icon_lbl.setText("Application icon:");

                    app_order_PrimeFlowPane();


                }else if(new_app_or_share_val.equals("share")){

                    add_name_lbl.setText("Share name:");
                    add_script_lbl.setText("Shared directory path:");

                    share_order_PrimeFlowPane();

                  //  add_icon_lbl.setText("Share icon:");


                }
            }
        });

        //---------save button----------------------

        add_save_btn.setOnAction((ActionEvent e) -> {

            if(new_app_or_share_val.equals("application")){

                save_application();
            }
            else if (new_app_or_share_val.equals("share")){

                save_share();
            }

        });

        //------------------ Test connection Button --------------------------

        add_test_btn.setOnAction(e->{

            if(new_app_or_share_val.equalsIgnoreCase("share")){

                // -- do share test ----

                    test_App_Share(warn_share_lbl.getText().trim());

                    Helper.getInstance().exec_test_file();

            }else{

                if(add_script_text.getText().isEmpty()){

                    alert_RDS();

                }else {

                    String test_app_rds = add_script_text.getText().trim();

                    test_App_Share(test_app_rds);

                    add_test_btn.setDisable(true);

                    Task task = new Task<Void>() {
                        @Override public Void call() {

                            Helper.getInstance().exec_test_file();

                            return null;
                        }
                    };

                    new Thread(task).start();

                    app_daemon_thread(add_test_btn, null);
                }
            }


        });

        //-------connectBase part-------------------------------

        connect_btn.setDisable(true);

       // status_label.setText("");

        connect_btn.setOnAction(e->{

            connect_btn.setDisable(true);

            status_property.set("wait...");

            status_label.setText("wait...");

            status_label.setVisible(true);

            Task task = new Task() {
                @Override
                protected Object call() throws Exception {

                    connect_action();

                    return null;
                }
            };

            new Thread(task).start();

        });

        ip_text.textProperty().addListener((observable, oldValue, newValue) -> {

            checkConnectFields();
        });

        user_text.textProperty().addListener((observable, oldValue, newValue) -> {
            checkConnectFields();
        });

        pass_text.textProperty().addListener((observable, oldValue, newValue) -> {
            checkConnectFields();
        });

        port_txt.textProperty().addListener((observable, oldValue, newValue) -> {

            checkConnectFields();
        });


        //---------- gui start -------------***

        add_text_effect();

        //--- add to listview ---

        build_listView_hosts();

        primaryStage.setTitle(program_name);

        primaryStage.setResizable(false);

        primaryStage.setScene(listScene);

        primaryStage.show();

        //------ entry list buttons action ------------

        //---- new button ---

        list_new_btn.setOnAction(e->{

            empty_connection_fields();

            new_connection();
        });

        //----- open button ------

        list_open_btn.setOnAction(e->{

            status_property.set("");

            open_connection();
        });

        //----- del button ----

        list_remove_btn.setOnAction(e->{

            remove_connection();
        });

        //*************************************

        //---------auto connect--------------------

    }

    //------------------- end of start ----------------********-----------------

    //--- save application ---

    private void save_application(){

        // ------------- Save ------------------------

        if (add_save_btn.getText().equalsIgnoreCase("save")) {

            if (appNameList.stream().anyMatch(str -> str.trim().equalsIgnoreCase(add_name_text.getText().trim()))) {

                alert_already_exists((add_name_text.getText().trim()));

            } else {

                boolean dbOperation = Helper.getInstance().insert(ip, user, add_name_text.getText().trim(), add_script_text.getText().trim(), selectedIconFile.getAbsolutePath(), new_app_or_share_val);
                if (dbOperation) {

                    init_stored_apps();

                    primaryStage.setScene(mainScene);

                }
            }

            // ------------- Update ------------------------
        }else{

            if (tmp_appNameList.stream().anyMatch(str -> str.trim().equalsIgnoreCase(add_name_text.getText().trim()))) {

                alert_already_exists(add_name_text.getText().trim());

            } else {

                int U_app_id = (int) U_map.get("app_id");

                boolean dbOperation = Helper.getInstance().update(ip, user, U_app_id, add_name_text.getText().trim(), add_script_text.getText().trim(), selectedIconFile.getAbsolutePath(), new_app_or_share_val);

                if (dbOperation) {

                    File script_file = new File("scripts/" + ip + "_" + user + "_" + old_app_name);
                    File icon_file = new File("app_icons/" + ip + "_" + user + "_" + old_app_name);

                    script_file.delete();
                    icon_file.delete();

                    init_stored_apps();

                    primaryStage.setScene(mainScene);
                }

                //--------- else update end ------------
            }

        }
    }

    //------ save share ---

    private void save_share(){

        // ------------- Save ------------------------

        if (add_save_btn.getText().equalsIgnoreCase("save")) {

            if (appNameList.stream().anyMatch(str -> str.trim().equalsIgnoreCase(add_name_text.getText().trim()))) {

                alert_already_exists((add_name_text.getText().trim()));

            } else {

                boolean dbOperation = Helper.getInstance().insert(ip, user, add_name_text.getText().trim(), warn_share_lbl.getText().trim(), folderIconFile.getAbsolutePath(), new_app_or_share_val);
                if (dbOperation) {

                    init_stored_apps();

                    primaryStage.setScene(mainScene);

                }
            }

            // ------------- Update ------------------------
        }else{

            if (tmp_appNameList.stream().anyMatch(str -> str.trim().equalsIgnoreCase(add_name_text.getText().trim()))) {

                alert_already_exists(add_name_text.getText().trim());

            } else {

                int U_app_id = (int) U_map.get("app_id");

                boolean dbOperation = Helper.getInstance().update(ip, user, U_app_id, add_name_text.getText().trim(), warn_share_lbl.getText().trim(), folderIconFile.getAbsolutePath(), new_app_or_share_val);

                if (dbOperation) {

                    File script_file = new File("scripts/" + ip + "_" + user + "_" + old_app_name);
                    File icon_file = new File("app_icons/" + ip + "_" + user + "_" + old_app_name);

                    script_file.delete();
                    icon_file.delete();

                    init_stored_apps();

                    primaryStage.setScene(mainScene);
                }

                //--------- else update end ------------
            }

        }
    }

    private void build_listView_hosts(){

        List<Host_Cell> list = new ArrayList<>();

        if(listView_hosts.getItems().size() == 0){

            for (String host_user : Helper.getInstance().get_hosts_users()) {

                String[] tmp = host_user.split("_");

                String host = tmp[0];
                String user = tmp[1];

                list.add(new Host_Cell(host, user, "wait... "));
            }

            ObservableList<Host_Cell> myObservableList = FXCollections.observableList(list);

            Platform.runLater(()->{

                listView_hosts.getItems().addAll(myObservableList);

            });

        }else{

            ArrayList<Host_Cell> hc_list = new ArrayList<>();

            for(int i=0; i<listView_hosts.getItems().size();i++){

                Host_Cell hc = (Host_Cell) listView_hosts.getItems().get(i);

                hc_list.add(hc);
            }

            for (String host_user : Helper.getInstance().get_hosts_users()) {

                String[] tmp = host_user.split("_");

                String host = tmp[0];
                String user = tmp[1];

                if((!hc_list.stream().anyMatch(hc-> hc.getHost().getText().equalsIgnoreCase(host)))

                        || !hc_list.stream().anyMatch(hc-> hc.getUser().getText().equalsIgnoreCase(user))) {

                    list.add(new Host_Cell(host, user, "wait... "));
                }

            }

            ObservableList<Host_Cell> myObservableList = FXCollections.observableList(list);

            Platform.runLater(()->{

                listView_hosts.getItems().addAll(myObservableList);

            });
        }

    }

    private void connect_action(){

        ip = ip_text.getText();
        user = user_text.getText();
        pass = pass_text.getText();
        port = Integer.parseInt(port_txt.getText());

        domain = domain_txt.getText();

        int output = Helper.getInstance().execCmd_2nd(Helper.FreeRDP_command + " /cert-ignore +auth-only /v:" + ip + ":" + port + " /u:" + user + " /p:" + pass + " /d:" + domain);

        Platform.runLater(()->{

        if(output == 0){

                primaryStage.setTitle(ip + " user " + user);

                primaryStage.setScene(mainScene);

                check_server_connection(ip, port);

            //------- if new connection ----------

            if(Helper.getInstance().get_hosts_users().stream().anyMatch(str->str.trim().equalsIgnoreCase(ip_text.getText()+"_"+user_text.getText()))){

                alert_host_already_exists(ip_text.getText() + " with user " + user_text.getText());

            }else {

                if(alert_save_to_list()){

                    Helper.getInstance().create_new_host_table(ip, user);
                }
            }

            isNewConnection = false;

            init_stored_apps();

        }else{

                status_label.setText("cannot connect to Remote Server!");
        }

            connect_btn.setDisable(false);

        });

    }


    private void new_connection(){

            isNewConnection = true;

            Platform.runLater(() -> {

                primaryStage.setScene(loginScene);

                status_label.setText("");

                primaryStage.show();
            });
    }

    private void remove_connection(){

        if(remove_alert("host " + host_in_db + "for user " + user_in_db)){

            Helper.getInstance().drop_host_table(host_in_db + "_" + user_in_db);

            Helper.getInstance().remove_host_from_Settings(host_in_db + "_" + user_in_db);

            Platform.runLater(()->{

                listView_hosts.getItems().remove(host_cell_current);

            });
        }

    }

    private void open_connection(){

        Map<String, Object> host_map = (Map<String, Object>) Helper.getInstance().loadSettings(host_in_db + "_" + user_in_db);

        ip = (String) host_map.get("ip");
        user = (String) host_map.get("username");
        pass = (String) host_map.get("password");
        port = (int) host_map.get("port");
        domain = (String) host_map.get("domain");

        ip_text.setText(ip);
        user_text.setText(user);
        pass_text.setText(pass);
        port_txt.setText(String.valueOf(port));
        domain_txt.setText(domain);

        int output = Helper.getInstance().execCmd_2nd(Helper.FreeRDP_command + " /cert-ignore +auth-only /v:" + ip + ":" + port + " /u:" + user + " /p:" + pass + " /d:" + domain);

        Platform.runLater(()->{

        if(output == 0){

            //-------------- check server connection --

            check_server_connection(ip, port);

            primaryStage.setScene(mainScene);

        }else{

            if(output == 1){

                System.out.println("Level 2 ---------------");

                String output_str = Helper.getInstance().execCmd(Helper.FreeRDP_command + " /u:" + user + " /p:"+ pass +" /app:\"||fakeApp\" /v:" +ip + ":" + port);

                boolean success = (output_str.contains("initialized POSIX"));

                if(success){

                    primaryStage.setScene(mainScene);
                }

            }

            else {

                primaryStage.setScene(loginScene);

            }

        }
        //-----------------------------------------

        //-----------get apps from db--------------

        init_stored_apps();

        //---------------------------------------

        if(getCurrentScene_str().equals(loginScene_str)){

            primaryStage.setTitle(program_name);

        }else{

            primaryStage.setTitle(ip + " user " + user);
        }

        //primaryStage.show();

        });
    }


    //----------- empty fields ------------

    private void empty_add_fields() {

        add_save_btn.setText("Save");

        add_name_text.setText("");
        add_script_text.setText("");
        add_icon_file_imageView.setImage(null);

        //---- share part ---

        select_share_property.set("*");

        add_share_rd.setDisable(false);
        add_app_rd.setDisable(false);
    }

    private void empty_connection_fields() {

        ip_text.setText("");
        user_text.setText("");
        pass_text.setText("");
        domain_txt.setText("");

        port_txt.setText(String.valueOf(default_port));
    }

    private boolean alert_save_to_list(){

        rds_GridPane.getChildren().clear();

        ButtonType save = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,

                "do you want to save this connection to the connections list",
                save,
                cancel);

        alert.setTitle("Save The Connection");
        alert.setHeaderText(" Save this connection to the connections list");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == save) {

            Map<String, Object> app_map = new HashMap<>();

            app_map.put("ip", ip);
            app_map.put("username", user);
            app_map.put("password", pass);
            app_map.put("port", port);
            app_map.put("domain", domain);

            Helper.getInstance().saveSettings(ip + "_" + user, app_map);

            Platform.runLater(()->{

                build_listView_hosts();
            });

            return true;
        }

        return false;
    }

    private void alert_RDS() {

        String type = rd_group.getSelectedToggle().getUserData().toString();

        String info = type.equalsIgnoreCase("Application") ? "\"Application name exactly as in RDS\"" : "\"Shared directory path\"";

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Required Field");
        alert.setHeaderText(type + " Required Field!");
        String s ="to test " + type + " connection please set " + info + " field";
        alert.setContentText(s);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }

    private void alert_already_exists(String app_name) {

        is_already_exists = true;

        add_name_text.setStyle("-fx-background-color: #ED9B9B");

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Name already exists");
        alert.setHeaderText("The name \"" + app_name + "\" is already exists");
        alert.setContentText("Please enter a different name");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }

    private void alert_host_already_exists(String host) {

        is_already_exists = true;

        add_name_text.setStyle("-fx-background-color: #ED9B9B");

        Alert alert = new Alert(Alert.AlertType.WARNING);

        alert.setTitle("Host already exists");
        alert.setHeaderText("The host \"" + host + "\" is already exists in the connections list");
        //alert.setContentText("Please enter a different name");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }

    private boolean remove_alert(String app_name){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Remove " + app_name);

        alert.setHeaderText(app_name + " will be removed !");

        alert.setContentText("Are you sure ?");

        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        Optional<ButtonType> result = alert.showAndWait();

        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {

            return true;
        }

        return false;
    }

    private void app_init() {

        File db_Dir = new File("db");

        File db_file = new File("db/windows_rds.db");

        File scriptsDir = new File("scripts");

        File app_iconsDir = new File("app_icons");

        File app_TestsDir = new File("test");

        if(!db_Dir.exists()){

            db_Dir.mkdir();
        }

        if(!db_file.exists()){

            try {
                db_file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(!scriptsDir.exists()){

            scriptsDir.mkdir();
        }

        if(!app_iconsDir.exists()){

            app_iconsDir.mkdir();
        }

        if(!app_TestsDir.exists()){

            app_TestsDir.mkdir();
        }

    }

    private void init_stored_apps(){

        //---- get apps from db -------------

        appArrList = Helper.getInstance().select_apps(ip, user, null);

        int col = 0;
        int row = 0;

        rds_GridPane.getChildren().clear();

        for (Map app_map : appArrList){

            int app_id = (int) app_map.get("app_id");
            String app_name = (String) app_map.get("app_name");
            String app_script = (String) app_map.get("app_script");

            String type = (String) app_map.get("type");

            //--------- add to app name list -------------

            appNameList.add(app_name);

            //--------------------------------------------

            String app_name_fixer = app_name.replaceAll("\\s+", "_");

            File appFile = new File("scripts/"+ ip +"_" + user + "_" + app_name_fixer);

            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(appFile), "utf-8"))) {

                try {

                    writer.write(app_command(app_script, type));

                    appFile.setExecutable(true);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            Button new_app_Btn = new Button();

            if(col == 5){

                row++;
                col=0;
            }

            if(type.equals("application")){

                GridPane.setColumnIndex(new_app_Btn, col);
                GridPane.setRowIndex(new_app_Btn, row);

                col++;
            }


            Tooltip tooltip = new Tooltip(app_name);

            new_app_Btn.setTooltip(tooltip);

            new_app_Btn.setOnAction(en->{

                new_app_Btn.setDisable(true);

                Task task = new Task<Void>() {
                    @Override public Void call() {

                        Helper.getInstance().exec_file(ip + "_" + user + "_" + app_name_fixer);

                        return null;
                    }
                };

                Thread thread = new Thread(task);

                thread.setName(app_name);

                thread.start();

                app_daemon_thread(new_app_Btn, thread);

            });

            //-------------- change button image programatically --------

            // new Image(url)
            Image image = null;
            try {

                image = new Image(String.valueOf(new File("app_icons/" + ip +"_" + user + "_" + app_name).toURI().toURL()));

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
// new BackgroundSize(width, height, widthAsPercentage, heightAsPercentage, contain, cover)

            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);

            // new BackgroundImage(image, repeatX, repeatY, position, size)

            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

            // new Background(images...)

            Background background = new Background(backgroundImage);

            new_app_Btn.setBackground(background);

            new_app_Btn.setPrefHeight(40.0);
            new_app_Btn.setPrefWidth(40.0);

            //--- update tmp_app_list ------------

            tmp_appNameList.addAll(appNameList);

            //---- add right click menu -----------

            right_click_menu(new_app_Btn, app_name);

            //---------------- add to grid pane --------

            if(type.equals("share")){

              //  System.out.println("share...");

                Label folderLbl = new Label(app_name);

                folderLbl.setLayoutX(1.0);
                folderLbl.setLayoutY(35.0);

                Group group = new Group();

                group.getChildren().addAll(new_app_Btn, folderLbl);

                GridPane.setColumnIndex(group, col);
                GridPane.setRowIndex(group, row);

                col++;

                rds_GridPane.getChildren().add(group);

            }else{

                rds_GridPane.getChildren().add(new_app_Btn);
            }
        }

        //---------------------------------
    }

    private void listBase_init(){

        listView_hosts = listBase.getListView_hosts();
        list_new_btn = listBase.getNew_btn();
        list_open_btn = listBase.getOpen_btn();
        list_remove_btn = listBase.getRemove_btn();

        list_open_btn.setDisable(true);
        list_remove_btn.setDisable(true);

        listView_hosts.setOnMouseClicked(event -> {

             try{

            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 &&
                    (event.getTarget() instanceof LabeledText || ((GridPane) event.getTarget()).getChildren().size() > 0)) {

                    open_connection();
            }

        }catch(ClassCastException e){}

        });
    }


    private void connectBase_init(){

        connect_btn = connectBase.getConnect_btn();
        status_label = connectBase.getStatus_label();
        ip_text = connectBase.getIp_text();
        user_text = connectBase.getUser_text();
        pass_text = connectBase.getPass_text();
        domain_txt = connectBase.getDomain_txt();
        port_txt = connectBase.getPort_txt();

        port_txt.setText(String.valueOf(default_port));

            port_txt.textProperty().addListener((observable, oldValue, newValue) -> {

                try {
                    if (newValue.matches("^[1-9]\\d*") || newValue.matches("")) {

                        int value = Integer.parseInt(newValue);

                    } else {

                        port_txt.setText(oldValue);
                    }
                }catch (NumberFormatException e){}
            });


        KeyCode enter_key = KeyCode.ENTER;

        ip_text.setOnKeyPressed(event -> {

            if(!connect_btn.isDisabled()){

                if(event.getCode() == enter_key){

                    connect_action();
                }
            }
        });

        user_text.setOnKeyPressed(event -> {

            if(!connect_btn.isDisabled()){

                if(event.getCode() == enter_key){

                    connect_action();
                }
            }
        });

        pass_text.setOnKeyPressed(event -> {

            if(!connect_btn.isDisabled()){

                if(event.getCode() == enter_key){

                    connect_action();
                }
            }
        });

        port_txt.setOnKeyPressed(event -> {

            if(!connect_btn.isDisabled()){

                if(event.getCode() == enter_key){

                    connect_action();
                }
            }
        });

        domain_txt.setOnKeyPressed(event -> {

            if(!connect_btn.isDisabled()){

                if(event.getCode() == enter_key){

                    connect_action();
                }
            }
        });

    }

    private void windowsBase_init(){

        donate_btn = windows_rdsBase.getDonate_btn();

        rdp_btn = windows_rdsBase.getRdp_btn();

        rds_GridPane = windows_rdsBase.getGrid_pane();

        main_status_label = windows_rdsBase.getStatus_label();

        main_status_label.setStyle("-fx-font-weight: bold");

        Tooltip disconnect_toolTip = new Tooltip("Disconnect the current RDS session");
        donate_btn.setTooltip(disconnect_toolTip);

        add_btn = windows_rdsBase.getAdd_btn();

        //--- custom add_btn ------------

        add_btn.setText("");

        Tooltip add_toolTip = new Tooltip("Add new Application or Share");
        add_btn.setTooltip(add_toolTip);

        URL imageUrl = null;
        try {
            imageUrl = new File("res/add-button.png").toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

           add_btn.setStyle("-fx-background-image: url('"+imageUrl+"');" +
                   "-fx-background-size: cover;" +
                   "-fx-background-repeat: no-repeat;");

        //--- custom rdp_btn ------------

        rdp_btn.setText("");

        Tooltip rdp_toolTip = new Tooltip("Normal Remote Desktop Connection");
        rdp_btn.setTooltip(rdp_toolTip);

        URL imageUrl_rdp = null;
        try {
            imageUrl_rdp = new File("res/rdp.png").toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        rdp_btn.setStyle("-fx-background-image: url('"+imageUrl_rdp+"');" +
                "-fx-background-size: cover;" +
                "-fx-background-repeat: no-repeat;");


        rdp_btn.setOnAction(e->{

            normal_rdp();

            rdp_btn.setDisable(true);

            Task task = new Task<Void>() {
                @Override public Void call() {

                    Helper.getInstance().exec_file(ip + "_" + user);

                    return null;
                }
            };

            Thread thread = new Thread(task);

            thread.start();

            app_daemon_thread(rdp_btn, thread);
        });

    }

    private void addBase_init(){

      add_name_lbl = addBase.getApp_share_name_label();
      add_script_lbl = addBase.getApp_share_script_label();
      add_icon_lbl = addBase.getApp_share_icon_label();
      add_status_lbl = addBase.getStatus_label_add();
      add_back_btn = addBase.getBack_btn();
      add_test_btn = addBase.getTest_btn();
      add_save_btn = addBase.getSave_btn();
      add_icon_chooser_btn = addBase.getIcon_chooser_btn();
      add_app_rd = addBase.getApp_radio();
      add_share_rd = addBase.getShare_radio();
      add_name_text = addBase.getApp_name_text();
      add_script_text = addBase.getApp_share_script_text();
      add_icon_file_imageView = addBase.getIcon_file_imageView();

      warn_icon_lbl = addBase.getWarn_icon_label();
      warn_name_lbl = addBase.getWarn_name_label();
      warn_script_lbl = addBase.getWarn_script_label();

      icon_flowPane = addBase.getIcon_flowPane();
      flowPane_app = addBase.getFlowPane_app();
      flowPane_share = addBase.getFlowPane_share();

      primeFlowPane = addBase.getPrimeFlowPane();

      add_status_flowPane = addBase.getStatus_flowPane();

      add_test_save_flowPane = addBase.getTest_save_flowPane();

      add_empty_flowPane_for_share = addBase.getEmpty_flowPane_for_share();

      flowPane_name = addBase.getFlowPane_name();

      add_share_chooser_btn = addBase.getChoose_share_btn();
      warn_share_lbl = addBase.getWarn_share_folder();

      add_status_lbl.setText("* fields is required");


      add_share_rd.setToggleGroup(rd_group);
      add_app_rd.setToggleGroup(rd_group);

      add_app_rd.setSelected(true);

      add_share_rd.setUserData("share");
      add_app_rd.setUserData("application");

      add_name_lbl.setText("Application name:");
      add_script_lbl.setText("Application name exactly as in RDS:");
      add_icon_lbl.setText("Application icon:");

      add_back_btn.setText("");

      add_save_btn.setDisable(true);

        Tooltip back_toolTip = new Tooltip("Back to Host Screen");
        add_back_btn.setTooltip(back_toolTip);

        URL imageUrl = null;
        try {
            imageUrl = new File("res/back-button.png").toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        add_back_btn.setStyle("-fx-background-image: url('"+imageUrl+"');" +
                "-fx-background-size: cover;" +
                "-fx-background-repeat: no-repeat;");


        init_order_PrimeFlowPane();

        add_share_chooser_btn.setOnAction(event -> {

            try{

                SelectShareDialog.getInstance().new_share_dialog(host_in_db +" shares for " + user_in_db, "", null);

                SelectShareDialog.getInstance().showAndWait();

            } catch (IllegalStateException ex){}
        });
    }

    private void checkConnectFields(){

       boolean ip_isEmpty = ip_text.getText().isEmpty();
       boolean user_isEmpty = user_text.getText().isEmpty();
       boolean pass_isEmpty = pass_text.getText().isEmpty();
       boolean port_isEmpty = port_txt.getText().isEmpty();

       if(!ip_isEmpty && !user_isEmpty && !pass_isEmpty && !port_isEmpty){

           connect_btn.setDisable(false);

       }else {

           connect_btn.setDisable(true);
       }

    }

    //-------- textFields effect ---------------

    private void add_text_effect(){

        add_name_text.textProperty().addListener((observable, oldValue, newValue) -> {

            checkAddFields();

            if(is_already_exists){

                String old_name = oldValue;

                if (!newValue.equalsIgnoreCase(old_name)){

                    add_name_text.setStyle(null);

                }else {

                    add_name_text.setStyle("-fx-background-color: #ED9B9B");
                }

            }
        });

        add_script_text.textProperty().addListener((observable, oldValue, newValue) -> {

            checkAddFields();
        });

        add_icon_file_imageView.imageProperty().addListener((observable, oldValue, newValue) -> {

            checkAddFields();

        });

        warn_share_lbl.textProperty().addListener((observable, oldValue, newValue) -> {

            checkAddFields();

        });


        //---------- status property -----------

        status_label.textProperty().addListener((observable, oldValue, newValue) -> {

            Platform.runLater(()->{

                if(status_label.getText().contains("Offline")){

                    status_label.setTextFill(Color.valueOf("#D12525"));

                }else if(status_label.getText().contains("Online")){

                    status_label.setTextFill(Color.valueOf("#2B911D"));

                }else if(status_label.getText().contains("cannot")){

                    status_label.setTextFill(Color.valueOf("#e40707"));

                }else{

                    status_label.setTextFill(Color.valueOf("#26342C"));
                }
            });

        });

        main_status_label.textProperty().addListener((observable, oldValue, newValue) -> {

            Platform.runLater(()->{

                if(main_status_label.getText().contains("Offline")){

                    main_status_label.setTextFill(Color.valueOf("#D12525"));

                }else{

                    main_status_label.setTextFill(Color.valueOf("#BDFFB7"));
                }
            });

        });

        status_property.addListener((observable1, oldValue1, newValue1) -> {

            Platform.runLater(()->{

                main_status_label.setText(status_property.getValue());

                status_label.setText(status_property.getValue());

              //  System.out.println("change: " + status_property.getValue());
            });
        });

        //---- share property ----

        select_share_property.addListener((observable1, oldValue1, newValue1) -> {

            Platform.runLater(()->{


                if(newValue1.equals("*")){

                    warn_share_lbl.setTextFill(javafx.scene.paint.Color.valueOf("#E02800"));
                    warn_share_lbl.setFont(new Font("System Bold", 18.0));

                }else{

                    add_name_text.setText(rename_share(select_share_property.getValue()));

                    warn_share_lbl.setTextFill(javafx.scene.paint.Color.valueOf("#635848"));
                    warn_share_lbl.setFont(new Font("System Regular", 13.0));
                }

                warn_share_lbl.setText(select_share_property.getValue());

                 // System.out.println("change-----: " + select_share_property.getValue());
            });
        });


        //------------ entry list button and listview of hosts ------

        listView_hosts.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            host_cell_current = (Host_Cell) newValue;

            try{

                host_in_db = host_cell_current.getHost().getText();

                user_in_db = host_cell_current.getUser().getText();

                list_open_btn.setDisable(false);
                list_remove_btn.setDisable(false);

            }catch (NullPointerException e){

                list_open_btn.setDisable(true);
                list_remove_btn.setDisable(true);
            }
        });
    }

    private void checkAddFields() {

        boolean add_name_isEmpty = add_name_text.getText().isEmpty();
        boolean add_script_isEmpty = add_script_text.getText().isEmpty();
        boolean add_icon_isEmpty = add_icon_file_imageView.getImage()==null;

        boolean add_select_share_isEmpty = warn_share_lbl.getText().equals("*");

        if(!add_name_isEmpty){

            warn_name_lbl.setVisible(false);

        }else {

            warn_name_lbl.setVisible(true);
        }

        //---

        if(!add_script_isEmpty){

            warn_script_lbl.setVisible(false);

        }else {

            warn_script_lbl.setVisible(true);
        }

        //--

        if(!add_icon_isEmpty){

            warn_icon_lbl.setVisible(false);

        }else {

            warn_icon_lbl.setVisible(true);
        }

        //----

        String rd_val = rd_group.getSelectedToggle().getUserData().toString();

        if(rd_val.equals("application")){

            if(!add_name_isEmpty && !add_script_isEmpty && !add_icon_isEmpty){

                add_status_lbl.setVisible(false);

                add_save_btn.setDisable(false);

            }else{

                add_status_lbl.setVisible(true);

                add_save_btn.setDisable(true);

            }
        }
        else if(rd_val.equals("share")){

            if(!add_name_isEmpty && !add_select_share_isEmpty){

                add_status_lbl.setVisible(false);

                add_save_btn.setDisable(false);

            }else{

                add_status_lbl.setVisible(true);

                add_save_btn.setDisable(true);

            }
        }


    }

    private void app_daemon_thread(Button app_button, Thread app_thread){

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {

                Thread.sleep(5000);

                app_button.setDisable(false);

                return null;
            }
        };

        new Thread(task).start();
    }


    //---------------------test app/share method------------

    private void test_App_Share(String test_app_share) {

        File appFile = new File("test/test_app_share");

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(appFile), "utf-8"))) {

            try {

                writer.write(app_command(test_app_share,new_app_or_share_val));

                appFile.setExecutable(true);

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //------ right click menu on button -----------

    private void right_click_menu(Button app_button, String app_name){

        // Create ContextMenu
        ContextMenu contextMenu = new ContextMenu();

        //------------- item1 Edit -----------------------

        MenuItem item1 = new MenuItem("Edit " + app_name);
        item1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                Platform.runLater(()->{

                    add_share_rd.setDisable(true);
                    add_app_rd.setDisable(true);

                    add_save_btn.setText("Update");
                });


                tmp_appNameList.addAll(appNameList);

                ArrayList<Map> list = new ArrayList<>();

                list = Helper.getInstance().select_apps(ip, user, app_name);

                U_map = list.get(0);

                String app_type = (String) U_map.get("type");

                if(app_type.equalsIgnoreCase("application")){

                    add_app_rd.setSelected(true);

                    add_name_text.setText((String) U_map.get("app_name"));

                    add_script_text.setText((String) U_map.get("app_script"));

                    //- set image icon ---------------------

                    File imgFile = new File("app_icons/" + ip + "_" + user + "_" + app_name );
                    Image image = new Image(imgFile.toURI().toString());
                    add_icon_file_imageView.setImage(image);
                    selectedIconFile = imgFile;

                }else{
                        add_share_rd.setSelected(true);

                        select_share_property.setValue((String) U_map.get("app_script"));
                }


                primaryStage.setScene(addScene);

                tmp_appNameList.remove(app_name);

                old_app_name = (String) U_map.get("app_name");

            }
        });

        //-------------item2 remove ----------------------

        MenuItem item2 = new MenuItem("Remove " + app_name);
        item2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                if(remove_alert(app_name)){

                    boolean del_op = Helper.getInstance().delete(ip, user, app_name);

                    if(del_op){

                        success_del(app_name);

                        Platform.runLater(() -> {

                            init_stored_apps();

                        });

                        String app_name_fixer = app_name.replaceAll("\\s+", "_");

                        File script_file = new File("scripts/" + ip + "_" + user + "_" + app_name_fixer );
                        File icon_file = new File("app_icons/" + ip + "_" + user +"_" + app_name );

                        script_file.delete();
                        icon_file.delete();

                        appNameList.remove(app_name);


                    }else{

                        failed_del(app_name);
                    }
                }

            }
        });

        // Add MenuItem to ContextMenu

        contextMenu.getItems().addAll(item1, item2);

        // When user right-click on Circle
        app_button.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

            @Override
            public void handle(ContextMenuEvent event) {

                contextMenu.show(app_button, event.getScreenX(), event.getScreenY());
            }
        });
    }

    //----------- alert for sucess or failed remove ---------

    private void success_del(String app_name) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(app_name + " removed");

        alert.setHeaderText(app_name + " was successfully removed");

        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }


    private void failed_del(String app_name) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Operation Failed !");

        alert.setHeaderText("failed to remove " + app_name + " !");

        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }

    //--------- check server connection -----------------

    private void check_server_connection(String host, int port) {

        Task check_rdp_task = new Task() {
            @Override
            protected Object call() throws Exception {

                Socket rdp_socket = null;

                while (main_thread.isAlive() && getCurrentScene_str().equalsIgnoreCase(getMainScene_str())){

                    //--------- time task part --------

                    TimerTask timeTask = new TimerTask(){
                        public void run(){

                            status_property.set(host + " is Offline");

                            return;
                        }
                    };

                    Timer timer = new Timer();
                    timer.schedule( timeTask, 10*1000 );

                    //----------------connection test part --------------------

                    try {

                        rdp_socket = new Socket(host, port);

                        if(rdp_socket.isConnected()){

                            status_property.set(host + " is Online");

                           // System.out.println(host + " is Online---");

                        }

                    }

                    catch (ConnectException e){

                        System.out.println("ConnectException.........");
                    }
                    catch (NoRouteToHostException e){

                        status_property.set(host + " is Offline");

                       // System.out.println(host + " is Offline----");
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }

                    Thread.sleep(5000);

                    timer.cancel();
                }

                //------------ end of connection test part -----------

                return null;
            }
        };

        new Thread(check_rdp_task).start();
    }

    //------------ start app ----------------------app

    private String app_command(String app_script, String type){

        if(type.equals("application")){

            return Helper.FreeRDP_command + " +clipboard /t:'"+ip+" user " +user+ "' /u:" + user + " /p:"+ pass +" /app:\"||"+app_script+"\" /v:" +ip + ":" + port;
        }

        return Helper.SYS_FILE_MANAGER + " smb://" + user + ":" + pass + "@" + app_script;
    }


    //------ normal rdp -------------------

    private String rdp_command(){

       return (Helper.FreeRDP_command + " +clipboard /cert-tofu /t:'"+ip+ " user " +user+ "' /u:" + user + " /p:" + pass + " /d:" + domain + " /v:" + ip + ":" + port);
    }


    private void normal_rdp(){

        File appFile = new File("scripts/"+ ip + "_" + user);

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(appFile), "utf-8"))) {

            try {

                writer.write(rdp_command());

                appFile.setExecutable(true);

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    //----

    private void share_order_PrimeFlowPane(){

        primeFlowPane.getChildren().remove(add_status_flowPane);
        primeFlowPane.getChildren().remove(add_test_save_flowPane);
        primeFlowPane.getChildren().remove(icon_flowPane);
        primeFlowPane.getChildren().remove(flowPane_app);
        primeFlowPane.getChildren().remove(flowPane_name);


        primeFlowPane.getChildren().add(flowPane_share);
        primeFlowPane.getChildren().add(flowPane_name);
        primeFlowPane.getChildren().add(add_empty_flowPane_for_share);
        primeFlowPane.getChildren().add(add_test_save_flowPane);
        primeFlowPane.getChildren().add(add_status_flowPane);

    }

    private void app_order_PrimeFlowPane(){

        primeFlowPane.getChildren().remove(add_status_flowPane);
        primeFlowPane.getChildren().remove(add_test_save_flowPane);
        primeFlowPane.getChildren().remove(add_empty_flowPane_for_share);
        primeFlowPane.getChildren().remove(flowPane_share);
        primeFlowPane.getChildren().remove(flowPane_name);


        primeFlowPane.getChildren().add(flowPane_name);
        primeFlowPane.getChildren().add(flowPane_app);
        primeFlowPane.getChildren().add(icon_flowPane);
        primeFlowPane.getChildren().add(add_test_save_flowPane);
        primeFlowPane.getChildren().add(add_status_flowPane);

    }

    private void init_order_PrimeFlowPane(){

        primeFlowPane.getChildren().remove(flowPane_share);
        primeFlowPane.getChildren().remove(add_empty_flowPane_for_share);

    }

  //----

    private String rename_share(String url){

        String tmp[] = url.split("/");

        return tmp[tmp.length - 1];
    }

    //------------- Start The Program ------------------

    public static void main(String[] args) {
        launch(args);
    }
}
