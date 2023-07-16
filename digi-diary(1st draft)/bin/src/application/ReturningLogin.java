package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.media.MediaPlayer;

public class ReturningLogin implements ProgramMethodInt{
	
	//audio for click sound when button is pressed
	MediaPlayer click;
	
	//password/textfield toggles
	PasswordField textBox;
	TextField textBoxShow;
	
	//value to determine what show password does
	boolean isHidden;
	boolean isHidden2;
	String filePass;			//the actual password on file
	String securityQuestion;	//the actual security question on file
	String securityAnswer;		//the actual security answer on file
	
	@Override
	public void add(Object obj) {
		container.getChildren().add((Node) obj);
	}
	
	/**
	 * Method retrieves the actual password/securityQ/securityAns on file for verification later on
	 */
	public void getFilePass() {
		try {
			FileReader fr = new FileReader(passwordFile);
			BufferedReader br = new BufferedReader(fr);
			
			filePass = br.readLine();
			securityQuestion = br.readLine();
			securityAnswer = br.readLine();
			
			br.close();
			fr.close();
		}
		catch (IOException e) {e.printStackTrace();}
		
		isHidden = true;
		isHidden2 = true;
	}
	
	/**
	 * Method setUpRL sets up the login page for returning user with set unique password.
	 * RL = Returning Login (acronym)
	 * After successfully signing in, it will call method setUpJW from class JournalWorkspace.
	 * @param tF		true=display label and option1 display; false=display option2 display
	 */
	public void setUpRL(String yesNoBack) {
		if (yesNoBack.equals("yes")) 		//plays display for changed password forced back to log in page
			resetPage("src/background display/REVERSEPASS.mp4");
		else if (yesNoBack.equals("no"))	//plays display for opening login/launch application
			resetPage("src/background display/OPENING.mp4");
		else if (yesNoBack.equals("back"))	//plays display if back button was pressed
			resetPage("src/background display/REVERSEPASS.mp4");
		else if (yesNoBack.equals("cancel") || yesNoBack.equals("logout"))	//plays display if user log out or canceled entry
			resetPage("src/background display/REVERSETOPPASSPAGE.mp4");
		
		getFilePass();		//get password file content
		
		
		//RECTANGLE/BORDER resize unbinded
		root.getChildren().add(rectangle(imgWidth/4,imgHeight/3,"rect1_1",imgWidth/2,imgHeight/2,20.0,10.0,null,null
				,root.widthProperty().divide(2).subtract(imgWidth/2),root.heightProperty().divide(2).subtract(imgHeight/1.75)));
		root.getChildren().add(rectangle(imgWidth/4,imgHeight/3,"rect3",imgWidth/2,imgHeight/2,20.0,10.0,null,null
				,root.widthProperty().divide(2).subtract(imgWidth/2),root.heightProperty().divide(2).subtract(imgHeight/1.75)));
		root.getChildren().add(container);

		
		//set up nodes
		Label successful = label("New password created successfully!",0,0,"label2_1",0,0,550,100,null,null,null,null); successful.setStyle("-fx-underline: true;");
		Label enterPass = label("Enter chosen password: ",0,0,"label2",0,0,550,100,null,null,null,null);
		Label invalidPass = label("Password was incorrect.",0,0,"labelInvalidPass1",0,0,550,100,null,null,null,null);
		textBox = passwordField("", 300,50,300,50,"textfield1");
		textBoxShow = textField("", 300,50,300,50,"textfield1");
		Button showPass = button("Show",0,0,"button1_",120,50,120,50,null,null,null,null);
		Button signIn = button("Sign in", 0,0,"button4",150,50,150,50,null,null,null,null);
		Button changePass = button("Change password",0,0,"button4",200,50,200,50,null,null,null,null);
		Button forgotPass = button("Forgot password",0,0,"button1_1",166,50,166,50,null,null,null,null);
		
		//button functions
		showPass.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent event) {
				click = mediaPlayer("src/music/click.mp3", 0.25);	//click sound/audio
				click.play();
			}
		});
		signIn.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent event) {
				click = mediaPlayer("src/music/click.mp3", 0.25);	//click sound/audio
				click.play();
			}
		});
		changePass.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent event) {
				click = mediaPlayer("src/music/click.mp3", 0.25);	//click sound/audio
				click.play();
			}
		});
		forgotPass.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent event) {
				click = mediaPlayer("src/music/click.mp3", 0.25);	//click sound/audio
				click.play();
			}
		});
		
		//edit location
		successful.setTranslateX(80);
		successful.setTranslateY(100);
		enterPass.setTranslateX(-50);
		enterPass.setTranslateY(200);
		textBox.setTranslateX(100);
		textBox.setTranslateY(138);	//set both textBox and textBoxShow in same location
		textBoxShow.setTranslateX(100);
		textBoxShow.setTranslateY(63);	//set both textBox and textBoxShow in same location
		showPass.setTranslateX(295);
		showPass.setTranslateY(-15);
		signIn.setTranslateX(25);
		signIn.setTranslateY(-35);
		invalidPass.setTranslateX(225);
		invalidPass.setTranslateY(-30);
		changePass.setTranslateX(225);
		changePass.setTranslateY(-110);
		forgotPass.setTranslateX(-150);
		forgotPass.setTranslateY(-185);
		
		
		//add to containers
		add(successful);
		add(enterPass);
		add(textBox);
		add(textBoxShow);	
		add(showPass);
		add(invalidPass);	
		add(signIn);
		add(changePass);
		add(forgotPass);
		
		
		//conditional/temporary invisible nodes
		textBoxShow.setVisible(false);		//initially set invisible until user presses show pass
		invalidPass.setVisible(false);  //initially set invisible until if user gets it wrong
		if (!yesNoBack.equals("yes")) {
			successful.setVisible(false); //added label but only set visible if tF == true
			stage.setTitle("Welcome Back To Digi-Diary!"); //change the title of application
		}
	}
}
