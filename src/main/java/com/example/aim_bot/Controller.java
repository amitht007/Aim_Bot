package com.example.aim_bot;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.scene.media.AudioClip;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Thread.sleep;

public class Controller extends Thread implements Initializable {
    @FXML
    private Pane guidePane;
    @FXML
    private Label scoreLabel;
    @FXML
    private Circle target0;
    @FXML
    private Circle target1;
    @FXML
    private Circle target2;
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    int count=0;
    private AudioClip audioClip;

    @FXML
    private Button playNext,playAgain;

    public void targetMousePressed(MouseEvent event){
        Circle target=(Circle)event.getTarget();
        double locateX = ThreadLocalRandom.current().nextDouble(0,600);
        double locateY = ThreadLocalRandom.current().nextDouble(0,400);
        target.setCenterX(locateX);
        target.setCenterY(locateY);
        int dummy=getCount();
        setCount(dummy+=2);
        scoreLabel.setText(String.valueOf(getCount()));
       //audioClip = new AudioClip(getClass().getResource("/resources/com/example/aim_bot/BellSound.mp3").toString());
      // audioClip.play();

    }
    public void paneMousePressed(){
        int dummy=getCount();
        setCount(dummy-=1);
        scoreLabel.setText(String.valueOf(getCount()));
    }
    @FXML
    private Label levelLabel;
    @FXML
    public Label timeLabel;

    public void initialize(URL location, ResourceBundle resources) {
        gameConsole();
        }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    int currentLevel=1;

    //this is the level animation

       Thread animate = new Thread(()->{
            Platform.runLater(() -> {
                FadeTransition fade = new FadeTransition(Duration.seconds(2), levelLabel);
                fade.setFromValue(1.0); // Set the initial opacity to 1.0 (fully visible)
                fade.setToValue(0.0); // Set the final opacity to 0.0 (fully transparent)
                fade.setCycleCount(1); // Set the number of cycles to 1 (play once)
                fade.play();
                level(currentLevel);
            });
        });
    @FXML
    private AnchorPane anchorPane;

    //taking the ball as variable
    public void level(int levels){
        switch (levels){
        case 1:
            Platform.runLater(() -> {
            target0.setVisible(true);//ball1 is visible
            target1.setVisible(false);//ball1 is visible
            target2.setVisible(false);
            });
            break;
            //background color ix x
            //score is some y
            case 2:
                Platform.runLater(() -> {
                target0.setVisible(true);//ball1 is visible
                target1.setVisible(true);//ball1 is visible
                    target1.setFill(Color.web("#FFA154"));
                    anchorPane.setStyle("-fx-background-color:#2a0022;");
                    target2.setVisible(false);
                         });
                break;
        case 3:
            Platform.runLater(() -> {
            target0.setVisible(true);//ball1 is visible
            target1.setVisible(true);//ball1 is visible
            target2.setVisible(true);
                target0.setFill(Color.web("#FFA154"));
                target1.setFill(Color.web("#FFA154"));
                target2.setFill(Color.web("#FFA154"));
            anchorPane.setStyle("-fx-background-color:#2a002f;");
            });
            break;
        }
    }
    //this is timer
    Thread timer=new Thread(()->{

            for(int i = 5;i!=-1;i--){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                int finalI = i;
                Platform.runLater(() -> {
                            timeLabel.setText(String.valueOf(finalI));
                        });
                    System.out.println(i);
        }
        Platform.runLater(this::checkScore);
    });
    //Game Console
    public void gameConsole() {
        guidePane.setVisible(false);
            setCount(0);

        System.out.println("GameConsole"+animate.isAlive());
        System.out.println("GameConsole"+timer.isAlive());

        if (animate.getState() == Thread.State.NEW || timer.getState() == Thread.State.NEW) {
            animate.start();
            try {
                animate.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            timer.start();
        }
        if(animate.getState()== Thread.State.TERMINATED || timer.getState()== Thread.State.TERMINATED ){
            animate=new Thread(animate::run);
            animate.start();
            try {
                animate.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            timer=new Thread(timer::run);
            timer.start();
        }

    }
        @FXML
        private Label higherScoreLabel;
    public void checkScore() {
        System.out.println(timer.getState()+"Its the new Thread");
        if (getCount() >= 1) {
            System.out.println("Upto next level!");
            higherScoreLabel.setText(String.valueOf(getCount()));
            guidePane.setVisible(true);
        }
        else {
            System.out.println("Start again!");
            level(currentLevel);
            levelLabel.setText(" LEVEL-0"+String.valueOf(getCurrentLevel()));
            higherScoreLabel.setText(String.valueOf(getCount()));
            guidePane.setVisible(true);

                }
        if(currentLevel==3){
            higherScoreLabel.setText("You have won!");
            playNext.setVisible(false);
        }
            }
            public void nextLevel(){
                currentLevel = currentLevel + 1;
                level(currentLevel);
                setCurrentLevel(currentLevel);
                System.out.println("New Level is "+getCurrentLevel());
                System.out.println("CheckScore"+timer.getState());
                System.out.println("CheckScore"+animate.getState());
                levelLabel.setText(" LEVEL-0"+String.valueOf(getCurrentLevel()));
                higherScoreLabel.setText(String.valueOf(getCount()));
                gameConsole();
            }
            public void playAgain(){
                level(currentLevel);
                levelLabel.setText(" LEVEL-0"+String.valueOf(getCurrentLevel()));
                higherScoreLabel.setText(String.valueOf(getCount()));
                gameConsole();
            }
}