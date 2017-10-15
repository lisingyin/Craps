/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s147545;


import com.sun.rowset.JdbcRowSetImpl;
import java.awt.Point;
import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import static javafx.application.Platform.exit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 *
 * @author singyinli
 */
public class FXMLDocumentController{
    
    protected String stID;
    private enum Status{CONTINUE,WON,LOST};
    Status GS;
    
    private int sumOfDice;
    private int myPoint;
    private static final int SNAKE_EYES = 2;
    private static final int TREY =3;
    private static final int SEVEN = 7;
    private static final int YO_LEVEN =11;
    private static final int BOX_CARS =12;
    private int die1;
    private int die2;
    private String result;
    private Timestamp time;
    private int count;
    protected static int Win = 0;
    protected static int Lose = 0;
    
    WIN_LOSEController showresult = new WIN_LOSEController();
    
    
    
    
    
    Image dice_1 = new Image(S147545.class.getResourceAsStream("dice_1.png")); 
    Image dice_2 = new Image(S147545.class.getResourceAsStream("dice_2.png")); 
    Image dice_3 = new Image(S147545.class.getResourceAsStream("dice_3.png")); 
    Image dice_4 = new Image(S147545.class.getResourceAsStream("dice_4.png"));
    Image dice_5 = new Image(S147545.class.getResourceAsStream("dice_5.png")); 
    Image dice_6 = new Image(S147545.class.getResourceAsStream("dice_6.png")); 
            
    @FXML
    private TextField STID;
    
    @FXML
    private Label STATUS;
    
    @FXML
    private Label Ready;
   
    @FXML
    private Button Dice_Rolling;
    
    @FXML
    private Button CTB;
    
    @FXML
    private Button New_Game;
    
    @FXML
    private Button Statistic;
    
    @FXML
    private Button Exit;
    
    @FXML
    private TextArea Result;
    
    @FXML
    private Label Point;
    
    @FXML
    private ImageView d1;
    
    @FXML
    private ImageView d2;
    
    @FXML
    private Label countResult;
    
    
    @FXML
    public void Dice_Roll(ActionEvent event)
    {
   
   time = new Timestamp(System.currentTimeMillis());
   System.out.println(time);
    this.stID = STID.getText();
    STID.setDisable(true);
    countResult.setText("");
    Statistic.setDisable(true);
    this.sumOfDice = rollDice();
    
    insert("INSERT INTO CRAPS(STUDENT_ID,SUBMIT_DATETIME,DICE_1,DICE_2)VALUES(?,?,?,?)",stID,time,die1,die2);
    Ready.setText("GO!");
    STATUS.setText("");
    Result.setText("");
    
    //int sumOfDice = rollDice();
    
    switch(sumOfDice)
    {   
        case SEVEN: 
            
            STATUS.setText("SEVEN, You WIN!");
            Result.setText(result);
            GS = Status.WON;
            Win = Win + 1;
            Dice_Rolling.setDisable(true);
            New_Game.setDisable(false);
            break;
        
        case YO_LEVEN: 
            
            STATUS.setText("YO_LEVEN, You WIN!");
            Result.setText(result);
            GS = Status.WON;
            Win = Win + 1;
            Dice_Rolling.setDisable(true);
            New_Game.setDisable(false);
            break;
            
        case SNAKE_EYES: 
            STATUS.setText("SNAKE_EYES, You LOSE!");
            Result.setText(result);
            GS = Status.LOST;
            Lose = Lose +1;
            Dice_Rolling.setDisable(true);
            New_Game.setDisable(false);
            break;
        
        case TREY:
            STATUS.setText("TREY, You LOSE!");
            Result.setText(result);
            GS = Status.LOST;
            Lose = Lose +1;
            Dice_Rolling.setDisable(true);
            New_Game.setDisable(false);
            break;
       
        case BOX_CARS:
            STATUS.setText("BOX_CARS, You LOSE!");
            Result.setText(result);
            GS = Status.LOST;
            Lose = Lose +1;
            Dice_Rolling.setDisable(true);
            New_Game.setDisable(false);
            break;
            
        default:
            GS = Status.CONTINUE;
            this.myPoint = sumOfDice;
            Result.setText(result);
            Point.setText("Your Point is "+myPoint);
            
            STATUS.setText("Continue~ ");
            Dice_Rolling.setDisable(true);
            CTB.setDisable(false);
            
            break;
            
        
   
    }
    }
    
    
    
    
    @FXML
    public void CT(ActionEvent event)
    {
        time = new Timestamp(System.currentTimeMillis());
        System.out.println(time);
        this.sumOfDice = rollDice();
        insert("INSERT INTO CRAPS(STUDENT_ID,SUBMIT_DATETIME,DICE_1,DICE_2)VALUES(?,?,?,?)",stID,time,die1,die2);
        STATUS.setText("Continue~");
        
        Result.setText(result+"\n"+Result.getText());

        if(sumOfDice == myPoint)
        {GS = Status.WON;
        STATUS.setText("You Win!");
        Win = Win + 1;
        CTB.setDisable(true);
        New_Game.setDisable(false);
        }

        else

        if(sumOfDice == SEVEN)
        {GS = Status.LOST;
        Lose = Lose +1;
        STATUS.setText("You Lose!"); 
        CTB.setDisable(true);
        New_Game.setDisable(false);
        }    
    }
    
    @FXML
    public void NewGame(ActionEvent event)
    {
    Dice_Rolling.setDisable(false);
    New_Game.setDisable(true);
    Statistic.setDisable(false);
    STID.setDisable(false);
    STATUS.setText("");
    Result.setText("");
    Point.setText("");
    Ready.setText("Ready?");
    }
    
    @FXML
    public void Exit(ActionEvent event) throws IOException
    {
    
         
         FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("WIN_LOSE.fxml"));
         Parent root2 = (Parent) fxmlLoader2.load();
         Stage showresult = new Stage();
         showresult.setTitle("Results");
         showresult.setScene(new Scene(root2)); 
         
         WIN_LOSEController SH = fxmlLoader2.getController();
         SH.SHOWRESULT();
         showresult.show();
         
         Stage stage = (Stage) Exit.getScene().getWindow();
         stage.close();
    
    }
    
    public int rollDice()
    {
    SecureRandom RNs = new SecureRandom();
    int die1 = 1 + RNs.nextInt(6);
    int die2 = 1 + RNs.nextInt(6);
    
    int sum = die1 + die2;
    this.die1 = die1;
    this.die2 = die2;
    this.result = "Player Rolled: "+die1 + "+" + die2 + " = " + sum;
    //System.out.println(die1 +"+"+ die2); 
   
            switch(die1)
            {
                case 1:
                    d1.setImage(dice_1);
                    break;
                case 2:
                    d1.setImage(dice_2);
                    break;
                case 3:
                    d1.setImage(dice_3);
                    break;
                case 4:
                    d1.setImage(dice_4);
                    break;
                case 5:
                    d1.setImage(dice_5);
                    break;
                case 6:
                    d1.setImage(dice_6);
                    break;
            }

            switch(die2)
            {
                case 1:
                    d2.setImage(dice_1);
                    break;
                case 2:
                    d2.setImage(dice_2);
                    break;
                case 3:
                    d2.setImage(dice_3);
                    break;
                case 4:
                    d2.setImage(dice_4);
                    break;
                case 5:
                    d2.setImage(dice_5);
                    break;
                case 6:
                    d2.setImage(dice_6);
                    break;
            }
            Rotate();
            return sum;
    
            }
    
    private void Rotate()
    {
    RotateTransition Dice1 =new RotateTransition(Duration.millis(300),d1);
    Dice1.setByAngle(360);
    Dice1.setCycleCount(2);
    Dice1.play();
    RotateTransition Dice2 =new RotateTransition(Duration.millis(300),d2);
    Dice2.setByAngle(360);
    Dice2.setCycleCount(2);
    Dice2.play();
    }
    
    @FXML
    public void Statistic(ActionEvent event)
    {
    countResult.setText("");
    String stID = STID.getText();
    int dice_11 = select("Select count(*) from CRAPS where STUDENT_ID = ? AND DICE_1=?",stID,1);
    int dice_21 = select("Select count(*) from CRAPS where STUDENT_ID = ? AND DICE_2=?",stID,1);
    int dice_T1 = dice_11 + dice_21;
    countResult.setText("The record of 1: "+ dice_T1);
    int dice_12 = select("Select count(*) from CRAPS where STUDENT_ID = ? AND DICE_1=?",stID,2);
    int dice_22 = select("Select count(*) from CRAPS where STUDENT_ID = ? AND DICE_2=?",stID,2);
    int dice_T2 = dice_12 + dice_22;
    countResult.setText("The record of 1: "+ dice_T1);
    int dice_13 = select("Select count(*) from CRAPS where STUDENT_ID = ? AND DICE_1=?",stID,3);
    int dice_23 = select("Select count(*) from CRAPS where STUDENT_ID = ? AND DICE_2=?",stID,3);
    int dice_T3 = dice_13 + dice_23;
    countResult.setText("The record of 1: "+ dice_T1);
    int dice_14 = select("Select count(*) from CRAPS where STUDENT_ID = ? AND DICE_1=?",stID,4);
    int dice_24 = select("Select count(*) from CRAPS where STUDENT_ID = ? AND DICE_2=?",stID,4);
    int dice_T4 = dice_14 + dice_24;
    countResult.setText("The record of 1: "+ dice_T1);
    int dice_15 = select("Select count(*) from CRAPS where STUDENT_ID = ? AND DICE_1=?",stID,5);
    int dice_25 = select("Select count(*) from CRAPS where STUDENT_ID = ? AND DICE_2=?",stID,5);
    int dice_T5 = dice_15 + dice_25;
    countResult.setText("The record of 1: "+ dice_T1);
    int dice_16 = select("Select count(*) from CRAPS where STUDENT_ID = ? AND DICE_1=?",stID,6);
    int dice_26 = select("Select count(*) from CRAPS where STUDENT_ID = ? AND DICE_2=?",stID,6);
    int dice_T6 = dice_16 + dice_26;
    countResult.setText("The record of 1: "+ dice_T1+
                    "\n"+"The record of 2: "+ dice_T2+
                    "\n"+"The record of 3: "+ dice_T3+
                    "\n"+"The record of 4: "+ dice_T4+
                    "\n"+"The record of 5: "+ dice_T5+
                    "\n"+"The record of 6: "+ dice_T6);
    
        
    }
    
    
    
    
    
    
    
    
    
    public void insert(String InsertSQL,String stID,Timestamp time,int die1,int die2)
    {
    try{
        Class.forName("oracle.jdbc.driver.OracleDriver");
        }
    catch(ClassNotFoundException e)
    {
        System.out.println("Where is your Driver?");
        e.printStackTrace();
        return;
    }
    
    Connection connection = null;
    PreparedStatement pstatement = null;
    
    try
    {
    connection = DriverManager.getConnection("jdbc:oracle:thin:@10.244.0.245:1521:mobile","system","HSMCnew0");
    pstatement = connection.prepareStatement(InsertSQL);
    pstatement.setString(1, stID);
    pstatement.setTimestamp(2,time);
    pstatement.setInt(3,die1);
    pstatement.setInt(4,die2);
    pstatement.executeUpdate();
    
    
    }
    
    catch(SQLException e)
    {
    System.out.println("Connection Failed!");
    e.printStackTrace();
    return;
    }
    
    finally
    {
    try{
    pstatement.close();
    connection.close();
    }
    
    catch(Exception e)
    {
    System.out.println("\"Failed to make connection!");
    e.printStackTrace();
    }
    }
    }

    
    public int select(String Select,String stID,int dice)
    {
            count =0;
    try{
        Class.forName("oracle.jdbc.driver.OracleDriver");
        }
    catch(ClassNotFoundException e)
    {
        System.out.println("Where is your Driver?");
        e.printStackTrace();
        
    }
    
    Connection connection = null;
    PreparedStatement pstatement = null;
    
    try
    {
    connection = DriverManager.getConnection("jdbc:oracle:thin:@10.244.0.245:1521:mobile","system","HSMCnew0");
    pstatement = connection.prepareStatement(Select);
    pstatement.setString(1, stID);
    pstatement.setInt(2, dice);
    ResultSet rset = pstatement.executeQuery();
    
    
    ResultSetMetaData metaData = pstatement.getMetaData();
    while(rset.next())
    count = rset.getInt(1);
    return count;
    }
    
    catch(SQLException e)
    {
    System.out.println("Connection Failed!");
    e.printStackTrace();
    
    }
    
    finally
    {
    try{
    pstatement.close();
    connection.close();
    }
    
    catch(Exception e)
    {
    System.out.println("\"Failed to make connection!");
    e.printStackTrace();
    }
    }return count;}

public static String NoOFWIN()
{
        String WINS =String.valueOf(Win);
        return WINS;
}
public static String NoOFLOSE()
{
String LOSES =String.valueOf(Lose);
        return LOSES;
}

}
        
    
    



     
    
