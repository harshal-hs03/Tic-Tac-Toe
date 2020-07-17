package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserNameController {

    @FXML
    public TextField tF1, tF2;
    public Label lbl2;
    public boolean flag2P;

    @FXML
    public void startGame(ActionEvent event) throws Exception{

//              loading the contents of new scene
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Design.fxml"));
        Parent root = (Parent) fxmlLoader.load();

//        getting the access to main controller to pass the player names
        DesignController controller = (DesignController) fxmlLoader.getController();

        if(tF1.getText().isEmpty() && tF2.getText().isEmpty()){
            controller.setNames("Player 1", "Player 2");
        } else if(tF1.getText().isEmpty() && !tF2.getText().isEmpty()) {
            controller.setNames("Player 1", tF2.getText());
        } else if(tF2.getText().isEmpty() && !tF1.getText().isEmpty()) {
            controller.setNames(tF1.getText(), "Player 2");
        } else{
            controller.setNames(tF1.getText(), tF2.getText());
        }

        controller.setMode(flag2P);     //setting the game mode

        //creating new scene obj
        Scene newScene = new Scene(root);

        //getting control of the main window (stage)
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        //setting the new scene to the window
        window.setScene(newScene);
        window.show();
    }

    @FXML
    public void backBtn(ActionEvent event) throws Exception{

        //loading the contents of new scene
        Parent root = FXMLLoader.load(getClass().getResource("Pilot.fxml"));

        //creating new scene obj
        Scene newScene = new Scene(root);

        //getting control of the main window (stage)
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        //setting the new scene to the window
        window.setScene(newScene);
        window.show();
    }

}
