package tests;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class TestSelectFolder extends Application {

    //String default_dir_str = "smb://douha:pipo300@10.0.0.55";

    String default_dir_str = "/home/douha";

    private String select_dir(){

        File defaultDirectory = new File(default_dir_str);

        DirectoryChooser share_dir_Chooser = new DirectoryChooser();

        share_dir_Chooser.setTitle("JavaFX Projects");

        share_dir_Chooser.setInitialDirectory(defaultDirectory);

        File selectedDirectory = share_dir_Chooser.showDialog(null);

        if (selectedDirectory != null) {

            return selectedDirectory.getAbsolutePath();

        }else{

           return "nothing";
        }
    }


    //----

    public static void main(String[] args) {

        TestSelectFolder app = new TestSelectFolder();

        Platform.runLater(()->{

            System.out.println(app.select_dir());
        });


    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

}
