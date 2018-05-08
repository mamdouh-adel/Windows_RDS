package utils;

import AppStart.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SelectShareDialog extends Stage implements Initializable
{
    private static SelectShareDialog selectShareDialog;

    private FXMLLoader fxmlLoader ;

    public static SelectShareDialog getInstance(){

        if(selectShareDialog == null){

            selectShareDialog = new SelectShareDialog();
        }

        return selectShareDialog;
    }

    private SelectShareDialog() {

         fxmlLoader = new FXMLLoader(getClass().getResource("Select_Share_Dir.fxml"));

    }

    public void new_share_dialog(String defaultName, String defaultAddress, Parent parent){

        setTitle(defaultName);

        // Nice to have this in a load() method instead of constructor, but this seems to be de-facto standard.
        try
        {
            setScene(new Scene((Parent) fxmlLoader.load()));
        }

        catch (LoadException ee){}
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }


    public static void close_select_share_dialog(){

        selectShareDialog.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }
}