package utils;


import AppStart.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbAuthException;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;

public class Share_Controller implements Initializable {

    Image folderImg ;

    private static final String url_top_level = "smb://" + Main.getIp();

    private String current_url;

    @FXML
    FlowPane flow_of_dirs;

    @FXML
    Button up_btn;

    @FXML
    Label current_dir_lbl;

    @FXML
    Button select_share_btn;

    private void setSelectEffect(Node node){

        int depth = 70;//Setting the uniform variable for the glow width and height

        DropShadow borderGlow= new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);

       // borderGlow.setColor(Color.valueOf("#6CA8D6"));
        borderGlow.setColor(Color.valueOf("#FF3A00"));

        borderGlow.setWidth(depth);
        borderGlow.setHeight(depth);

        node.setEffect(borderGlow);
    }

    private void build_share_dirs(String url){

        ArrayList<Group> list_grp = new ArrayList<>();

        String domain = (Main.getDomain().equals("")) ? null : Main.getDomain();

        Set<SmbFile> dirs = Helper.getInstance ().getShares(url, Main.getUser(), Main.getPass(), domain);

        for(SmbFile smbFile : dirs){

            ImageView folderView = new ImageView(folderImg);
            folderView.setFitWidth(80.0);
            folderView.setFitHeight(50.0);
            folderView.setPickOnBounds(true);
            folderView.setPreserveRatio(true);

            Label folderLbl = new Label(smbFile.getName().replace("/", ""));

            folderLbl.setLayoutX(3.0);
            folderLbl.setLayoutY(50.0);

            Group group = new Group();

            group.getChildren().addAll(folderView, folderLbl);
            FlowPane flowPane = new FlowPane();

            flowPane.setRowValignment(VPos.CENTER);
            flowPane.setColumnHalignment(HPos.CENTER);
            flowPane.setAlignment(Pos.CENTER);
            flowPane.setOrientation(Orientation.VERTICAL);
            flowPane.setPrefWidth(100.0);
            flowPane.setPrefHeight(80.0);
            flowPane.getChildren().add(group);

            flow_of_dirs.getChildren().add(flowPane);

            group.setOnMouseClicked(event -> {

                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() >= 2) {

                    flow_of_dirs.getChildren().clear();

                    build_share_dirs(smbFile.getCanonicalPath());

                    current_url = smbFile.getCanonicalPath();

                    current_dir_lbl.setText(smbFile.getCanonicalPath().replace("smb://", ""));

                //    System.out.println("here......................");

                    if(current_url.equals(url_top_level + "/")){

                        up_btn.setVisible(false);

                        select_share_btn.setVisible(false);

                    }else {

                        up_btn.setVisible(true);

                        select_share_btn.setVisible(true);
                    }
                }

                setSelectEffect(group);
            });

            list_grp.add(group);
        }

        //-----------------------------

        flow_of_dirs.setOnMouseClicked(event -> {

            for(Group grp : list_grp){

                if(!grp.isHover()){

                    grp.setEffect(null);
                }
            }
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        current_url = url_top_level;

        current_dir_lbl.setText(Main.getIp() + "/");

        select_share_btn_action();

        try {
            folderImg = new Image(String.valueOf(new File("res/folder.png").toURI().toURL()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        URL imageUrl = null;
        try {
            imageUrl = new File("res/up_arrow.png").toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        up_btn.setVisible(false);

        select_share_btn.setVisible(false);

        up_btn.setText("");

        up_btn.setStyle("-fx-background-image: url('"+imageUrl+"');" +
                "-fx-background-size: cover;" +
                "-fx-background-repeat: no-repeat;");

        up_btn.setPrefWidth(25.0);
        up_btn.setPrefHeight(25.0);

        Tooltip add_toolTip = new Tooltip("Up");
        up_btn.setTooltip(add_toolTip);

        up_btn_action();

        flow_of_dirs.getChildren().clear();

        build_share_dirs(url_top_level);
    }

    //----------------------------------------------

    private void up_btn_action(){

        up_btn.setOnAction(event -> {

            flow_of_dirs.getChildren().clear();

            current_url = Up_Url(current_url);

            current_dir_lbl.setText(current_url.replace("smb://", ""));

            build_share_dirs(current_url);

            if(current_url.equals(url_top_level + "/")){

                up_btn.setVisible(false);

                select_share_btn.setVisible(false);

            }else {

                up_btn.setVisible(true);

                select_share_btn.setVisible(true);
            }

        });
    }

    private String Up_Url(String current_url){

        String[] tmp = current_url.split("/");

        StringBuilder rest = new StringBuilder();

        for(int i=0; i<tmp.length; i++){

            if(i>2){

                if(i < tmp.length - 1){

                    rest.append("/" + tmp[i]);
                }
            }
        }

        current_url = url_top_level + rest + "/";

        return current_url;
    }

    private void select_share_btn_action(){

        select_share_btn.setOnAction(event -> {

            String domain = (Main.getDomain().equals("")) ? null : Main.getDomain();

            NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(domain, Main.getUser(), Main.getPass());

            SmbFile testSmb = null;

            try {

                 testSmb = new SmbFile(current_url, auth);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {

                for (SmbFile smbFile : testSmb.listFiles());

                Main.select_share_property.set(current_dir_lbl.getText());

                SelectShareDialog.close_select_share_dialog();
            }
            catch (SmbAuthException ee){

                global_alert(ee);

            } catch (SmbException e) {
                e.printStackTrace();
            }

            current_url = url_top_level;
        });
    }

    //-----  global Alert ----

    private void global_alert(Exception error) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error.getLocalizedMessage());
        alert.setHeaderText("Error");
        String s = error.getMessage();
        alert.setContentText(s);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }
}
