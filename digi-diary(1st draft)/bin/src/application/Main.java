package application;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Class Main launches the Digi-Diary application with a GUI display specific to whether it's the user's first time or not.
 * 
 * @author 		Alex Wong
 * @author 		Hayden Tu
 * @author 		Ariaz Madani
 */
public class Main extends Application implements ProgramMethodInt {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//set up background image
			root.setStyle("-fx-background-color: gray");
			ImageView imgView = new ImageView(new File("src/background display/cozypic.jpg").toURI().toString());
			add(imgView);
			
			
			//setting up lobby music
			MediaPlayer mediaPlayer = mediaPlayer("src/music/Idealism - Controlla [1 Hour].mp3", 0.05);
			mediaPlayer.setOnEndOfMedia(new Runnable() {
			       public void run() {
			    	   mediaPlayer.seek(Duration.ZERO);
			       }
			   });
			mediaPlayer.play();
			MediaView mediaView = new MediaView(mediaPlayer);
			add(mediaView);
			
			
			//intitializing readers to search password file
			String str = "";
			try {
				FileReader fr = new FileReader(passwordFile);
				BufferedReader br = new BufferedReader(fr);
				str = br.readLine();
//				str = br.readLine();				//4th line is developer customized to distinguish new and old user
//				str = br.readLine();				//this is to target ANY edge case regarding user customizing the password
//				str = br.readLine();				//eg: keeps "p" as the pass, adds spaces, commas or any symbols
				br.close();
				fr.close();
			}
			catch(IOException e) {e.printStackTrace();}
			
			
			//setting up scene
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
//			stage.initStyle(StageStyle.TRANSPARENT);
			
			
			//setting up stage
			stage.setScene(scene);
			stage.setTitle("Welcome To Digi-Diary!");						//name of frame
			stage.getIcons().add(new Image(new File("src/background display/icon.jpg").toURI().toString()));	//top left app icon image
			stage.setMinWidth(imgWidth+40);											//minimum width the frame can resize to
			stage.setMinHeight(imgHeight+40);										//minimum height the frame can resize to
			stage.show();
			
			
			if (!str.equals("p")) {			//if file's first line doesn't contain "," then call FirstTimeLoginPage object
				RL.setUpRL("no"); }
			else {								//else if file's first line does contain "," then call ReturningLoginPage object
				FTL.setUpFTL(); }
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void add(Object obj) {
		root.getChildren().add((Node) obj);
	}

}
