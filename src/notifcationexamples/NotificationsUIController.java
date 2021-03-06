/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifcationexamples;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import taskers.*;

/**
 * FXML Controller class
 *
 * @author dalemusser
 */
public class NotificationsUIController implements Initializable, Notifiable {

    @FXML
    private TextArea textArea;
    @FXML
    public Button task1Btn;
    @FXML
    public Button task2Btn;
    @FXML
    public Button task3Btn;
    
    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (task1 != null){
                    task1.end();
                    task1Btn.setText("Start Task1");
                }
                if (task2 != null){
                    task2.end();
                    task2Btn.setText("Start Task2");
                }
                
                if (task3 != null){
                    task3.end();
                    task3Btn.setText("Start Task3");
                }
            }
        });
    }
        
    @FXML
    public void startTask1(ActionEvent event) {
        if(task1Btn.getText()=="End Task1"){
            stopTask1();
        }
        else{        
            System.out.println("start task 1");
            //if (task1 == null) {
                task1 = new Task1(2147483647, 1000000);
                task1.setNotificationTarget(this);
                task1.start();
                task1Btn.setText("End Task1");
            //}
        }
    }
    
    @Override
    public void notify(String message) {
        if (message.equals("Task1 done.")) {
            task1 = null;
            task1Btn.setText("Set Task1");
        }
        textArea.appendText(message + "\n");
    }
    
    @FXML
    public void startTask2(ActionEvent event) throws InterruptedException {
        if (task2 == null) {
            System.out.println("start task 2");
            task2 = new Task2(10000000, 1000000);
            task2.setOnNotification((String message) -> {
                textArea.appendText(message + "\n");
                if(message == "Task2 done."){
                    stopTask2();
                }
            //task2Btn.setText("Start Task2");
            });
            
            task2.start();
            task2Btn.setText("End Task2");
        }
        else{
            textArea.appendText("Task 2 Stopped\n");
            stopTask2();
        }
    }
    
    @FXML
    public void startTask3(ActionEvent event) throws InterruptedException {
        if (task3 == null) {
            System.out.println("start task 3");
            task3 = new Task3(100000000, 1000000);
            // this uses a property change listener to get messages
            task3.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                textArea.appendText((String)evt.getNewValue() + "\n");
                if(evt.getNewValue().equals("Task3 done.")){
                    task3Btn.setText("Start Task3");
                }
            //task3Btn.setText("Start Task3");
            });
            
            task3.start();
            task3Btn.setText("End Task3"); 
        }
        
        else{
            stopTask3();
        }
        
    } 

    @FXML
    public void stopTask1() {
        task1.end();
        task1 = null;
        task1Btn.setText("Start Task1");
        textArea.appendText("Task 1 Stopped\n");
    }
    
    @FXML
    public void stopTask2() {
        task2.end();
        task2 = null;
        task2Btn.setText("Start Task2");
    }
    
    @FXML
    public void stopTask3() {
        task3.end();
        task3 = null;
        task3Btn.setText("Start Task3");
        textArea.appendText("Task 3 Stopped\n");
    }    
    
}