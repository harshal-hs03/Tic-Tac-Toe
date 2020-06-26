package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Controller {
    
    @FXML
    private Label tx1;
    @FXML
    private Label tx2;
    @FXML
    private Label tx3;
    @FXML
    private Label tx4;
    @FXML
    private Label tx5;
    @FXML
    private Label tx6;
    @FXML
    private Label tx7;
    @FXML
    private Label tx8;
    @FXML
    private Label tx9;
    @FXML
    private Label btmLabel;
    @FXML
    private TextField playerIp;
    @FXML
    private Button okBtn;
    @FXML
    private Button enableKeyIpBtn;
    @FXML
    private Button modeBtn;
    @FXML
    private BorderPane mainBorderPane;

    private static List<Integer> playerPositions = new ArrayList<>();
    private static List<Integer> cpuPositions = new ArrayList<>();
    private boolean flag = true;
    private boolean multiplayer = false;

    @FXML
    public void tx1Click(){
        modeBtn.setVisible(false);
       placeX(1);

    }
    @FXML
    public void tx2Click(){
        modeBtn.setVisible(false);
        placeX(2);
    }
    @FXML
    public void tx3Click(){
        modeBtn.setVisible(false);
        placeX(3);
    }
    @FXML
    public void tx4Click(){
        modeBtn.setVisible(false);
        placeX(4);
    }
    @FXML
    public void tx5Click(){
        modeBtn.setVisible(false);
        placeX(5);
    }
    @FXML
    public void tx6Click(){
        modeBtn.setVisible(false);
        placeX(6);
    }
    @FXML
    public void tx7Click(){
        modeBtn.setVisible(false);
        placeX(7);
    }
    @FXML
    public void tx8Click(){
        modeBtn.setVisible(false);
        placeX(8);
    }
    @FXML
    public void tx9Click(){
        modeBtn.setVisible(false);
        placeX(9);
    }

    @FXML
    public void newGame(){
        clearScene();
        modeBtn.setVisible(true);
    }

    @FXML
    public void modeChange(){
        multiplayer = !multiplayer;
        if(multiplayer){
            modeBtn.setText("Switch to Single-Player");
        } else {
            modeBtn.setText("Switch to Two-Player");
        }
        clearScene();
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

    public void enableKeyboardIp(){
        if(enableKeyIpBtn.getText().equals("Enable Keyboard Input")) {
            btmLabel.setVisible(true);
            playerIp.setVisible(true);
            okBtn.setVisible(true);
            enableKeyIpBtn.setText("Disable Keyboard Input");
        } else if(enableKeyIpBtn.getText().equals("Disable Keyboard Input")){
            btmLabel.setVisible(false);
            playerIp.setVisible(false);
            okBtn.setVisible(false);
            enableKeyIpBtn.setText("Enable Keyboard Input");
        }
    }

    //This function is for processing the input number(1 to 9) provided in the textField
    @FXML
    public void onBtnClick(){
        modeBtn.setVisible(false);
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
        modeBtn.setText((multiplayer) ? "Switch to Single-Player" : "Switch to Two-Player");
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
            modeBtn.setVisible(true);
        } else if(alert.getResult() == ButtonType.NO) {
            clearScene();
            Platform.exit();
        }
    }

    public static String checkWinner(){
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
                return "Congrats, you won!";
            } else if(cpuPositions.containsAll(l)){
                return "Oops, you lose. Better luck next time!";
            }
        }

        if((playerPositions.size() + cpuPositions.size()) == 9){
            return "it's a tie!";
        }else
            return "";
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
}
