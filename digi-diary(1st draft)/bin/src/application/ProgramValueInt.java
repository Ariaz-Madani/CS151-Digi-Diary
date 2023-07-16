package application;

import java.io.File;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public interface ProgramValueInt {
	//image/audio reference objects
		public static final double imgWidth = 1500;
		public static final double imgHeight = 895;
	
		//define objects
		Stage stage = new Stage();
		//Stage stage2 = new Stage();
		Pane root = new Pane();
		Scene scene = new Scene(root,imgWidth, imgHeight);	//add to root to change around just 1 scene variable
		VBox container = new VBox(); //added to root to centralized objects; maybe not needed
		
		//other class Objects
		FirstTimeLogin FTL = new FirstTimeLogin();
		ReturningLogin RL = new ReturningLogin();
		JournalWorkspace JW = new JournalWorkspace();
		
		//file content objects
		public static final File passwordFile = new File("src/pass/password.txt");	//saved user's password	
		public static final File deletedFiles = new File("src/fileDB/deletedFiles.txt");	//saved user's files	
		public static final File savedFiles = new File("src/fileDB/savedFiles.txt");	//saved user's deleted files
}

