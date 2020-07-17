package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class DesignController implements Initializable {

    @FXML
    private Label tx1, tx2, tx3, tx4, tx5, tx6, tx7, tx8, tx9;
    @FXML
    private TextField playerIp;
    @FXML
    private Line str123, str456, str789, str147, str258, str369, str159, str753;

    private static List<Integer> playerPositions = new ArrayList<>();
    private static List<Integer> cpuPositions = new ArrayList<>();
    private boolean flag = true;
    private boolean multiplayer;
    private static String name1P, name2P;

    @FXML
    public void tx1Click(){
        placeX(1);

    }
    @FXML
    public void tx2Click(){
        placeX(2);
    }
    @FXML
    public void tx3Click(){
        placeX(3);
    }
    @FXML
    public void tx4Click(){
        placeX(4);
    }
    @FXML
    public void tx5Click(){
        placeX(5);
    }
    @FXML
    public void tx6Click(){
        placeX(6);
    }
    @FXML
    public void tx7Click(){
        placeX(7);
    }
    @FXML
    public void tx8Click(){
        placeX(8);
    }
    @FXML
    public void tx9Click(){
        placeX(9);
    }

    @FXML
    public void placeX(int pos){
        if(playerPositions.contains(pos) || cpuPositions.contains(pos)){
            System.out.println("Position already taken, try some other placement!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Not a valid placement");
            alert.setContentText("Position already taken, try some other placement!");
            alert.setTitle("Invalid Input");
            alert.showAndWait();
            playerIp.clear();
            return;
        }

        if(multiplayer) {
            String user = (flag) ? "player" : "cpu";
            updatePlacement(pos, user);
            flag = !flag;
            String result = checkWinner();
            if (result.length() > 0) {
                playAgainPopUp(result);
            }
        } else {
            updatePlacement(pos, "player");
            String result = checkWinner();
            if (result.length() > 0) {
                playAgainPopUp(result);
            }
            cpuTurn();
        }
    }

    @FXML
    public void cpuTurn(){
        Random random = new Random();
        int cpuPos = random.nextInt(9) + 1;
        while(playerPositions.contains(cpuPos) || cpuPositions.contains(cpuPos)){
            System.out.println("Position already taken, try some other placement!");
            cpuPos = random.nextInt(9) + 1;
        }
        System.out.println("CPU made it's placement at : "+cpuPos);
        updatePlacement(cpuPos, "cpu");

        String result = checkWinner();
        if(result.length() > 0) {
            playAgainPopUp(result);
        }
    }

    //This function is for processing the input number(1 to 9) provided in the textField
    @FXML
    public void onBtnClick(){
        int input = Integer.parseInt(playerIp.getText());
        if((input > 0) && (input <= 9)){
            playerIp.clear();
            placeX(input);
        } else{
            System.out.println("Please enter position number between 1 to 9 only!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Not a valid input");
            alert.setContentText("Only numbers between 1 to 9 are allowed as input");
            alert.setTitle("Invalid Input");
            alert.showAndWait();
            playerIp.clear();
            return;
        }
    }

    @FXML
    public void clearScene(){
        tx1.setText("");
        tx2.setText("");
        tx3.setText("");
        tx4.setText("");
        tx5.setText("");
        tx6.setText("");
        tx7.setText("");
        tx8.setText("");
        tx9.setText("");
        playerIp.clear();
        playerPositions.clear();
        cpuPositions.clear();
        str123.setVisible(false);
        str456.setVisible(false);
        str789.setVisible(false);
        str147.setVisible(false);
        str258.setVisible(false);
        str369.setVisible(false);
        str159.setVisible(false);
        str753.setVisible(false);
    }

    public void playAgainPopUp(String result){
        System.out.println(result);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, result, ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(result);
        alert.setContentText("Do you want to play again ?");
        alert.setTitle("Match Result");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            clearScene();
        } else if(alert.getResult() == ButtonType.NO) {
            clearScene();
            Platform.exit();
        }
    }

    public String checkWinner(){
//        String p1Name = name1P;
//        String p2Name = name2P;
        List topRow = Arrays.asList(1, 2, 3);
        List midRow = Arrays.asList(4, 5, 6);
        List botRow = Arrays.asList(7, 8, 9);
        List leftCol = Arrays.asList(1, 4, 7);
        List midCol = Arrays.asList(2, 5, 8);
        List rightCol = Arrays.asList(3, 6, 9);
        List diag1 = Arrays.asList(1, 5, 9);
        List diag2 = Arrays.asList(7, 5, 3);

        List<List> winOpt = new ArrayList<>();
        winOpt.add(topRow);
        winOpt.add(midRow);
        winOpt.add(botRow);
        winOpt.add(leftCol);
        winOpt.add(midCol);
        winOpt.add(rightCol);
        winOpt.add(diag1);
        winOpt.add(diag2);

        for(List l : winOpt){
            if(playerPositions.containsAll(l)){
                strikeThrough(l);
                return "Congrats "+name1P+"!, you won!";
            } else if(cpuPositions.containsAll(l)){
                strikeThrough(l);
                return "Oops "+name1P+", you lost by "+name2P+". Better luck next time!";
            }
        }

        if((playerPositions.size() + cpuPositions.size()) == 9){
            return "it's a tie!";
        }else
            return "";
    }

    @FXML
    public void strikeThrough(List list){
        if(list.equals(Arrays.asList(1, 2, 3))){
            str123.setVisible(true);
        } else if(list.equals(Arrays.asList(4, 5, 6))){
            str456.setVisible(true);
        } else if(list.equals(Arrays.asList(7, 8, 9))){
            str789.setVisible(true);
        } else if(list.equals(Arrays.asList(1, 4, 7))){
            str147.setVisible(true);
        } else if(list.equals(Arrays.asList(2, 5, 8))){
            str258.setVisible(true);
        } else if(list.equals(Arrays.asList(3, 6, 9))){
            str369.setVisible(true);
        } else if(list.equals(Arrays.asList(1, 5, 9))){
            str159.setVisible(true);
        } else if(list.equals(Arrays.asList(7, 5, 3))){
            str753.setVisible(true);
        }
    }

    public void updatePlacement(int pos, String user) {
        String symbol = " ";
        if (user.equals("player")){
            symbol = "X";
            playerPositions.add(pos);
        }
        else if(user.equals("cpu")){
            symbol = "O";
            cpuPositions.add(pos);
        }
        switch(pos){
            case 1 :
                tx1.setText(symbol);
                break;
            case 2 :
                tx2.setText(symbol);
                break;
            case 3 :
                tx3.setText(symbol);
                break;
            case 4 :
                tx4.setText(symbol);
                break;
            case 5 :
                tx5.setText(symbol);
                break;
            case 6 :
                tx6.setText(symbol);
                break;
            case 7 :
                tx7.setText(symbol);
                break;
            case 8 :
                tx8.setText(symbol);
                break;
            case 9 :
                tx9.setText(symbol);
                break;
            default:
                break;
        }
    }

    public void setNames(String p1, String p2){
        name1P = p1;
        name2P = p2;
    }

    public void setMode(boolean modeFlag){
        multiplayer = modeFlag;
    }

    @FXML
    public void backBtn(ActionEvent event) throws Exception{

        clearScene();
//        loading the contents of new scene
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UserName.fxml"));
        Parent root = (Parent) fxmlLoader.load();

//        getting the access to main controller to pass the player names
        UserNameController controller = (UserNameController) fxmlLoader.getController();

        if(!multiplayer){
            controller.tF2.setVisible(false);
            controller.lbl2.setVisible(false);
        }

        //creating new scene obj
        Scene newScene = new Scene(root);

        //getting control of the main window (stage)
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        //setting the new scene to the window
        window.setScene(newScene);
        window.show();
    }

    @FXML
    public void homeBtn(ActionEvent event) throws Exception{

        clearScene();
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

    @FXML
    public void exitBtn(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "result", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Exit Confirmation");
        alert.setContentText("Are you sure, you want to exit ?");
        alert.setTitle("Exit");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            clearScene();
            Platform.exit();
        }
    }

    //    this is needed for the previous controller to get control of this one
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}



