package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PilotController implements Initializable{

    @FXML
    public void changeWindowTo1P(ActionEvent event)throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UserName.fxml"));
        Parent root = (Parent) fxmlLoader.load();

//        getting the access to user name controller
        UserNameController controller = (UserNameController) fxmlLoader.getController();
        controller.tF2.setVisible(false);
        controller.lbl2.setVisible(false);
        controller.flag2P = false;


        //creating new scene obj
        Scene newScene = new Scene(root);

        //getting control of the main window (stage)
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        //setting the new scene to the window
        window.setScene(newScene);
        window.show();
    }

    @FXML
    public void changeWindowTo2P(ActionEvent event)throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UserName.fxml"));
        Parent root = (Parent) fxmlLoader.load();

//        getting the access to user name controller
        UserNameController controller = (UserNameController) fxmlLoader.getController();
        controller.tF2.setVisible(true);
        controller.lbl2.setVisible(true);
        controller.flag2P = true;


        //creating new scene obj
        Scene newScene = new Scene(root);

        //getting control of the main window (stage)
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        //setting the new scene to the window
        window.setScene(newScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
