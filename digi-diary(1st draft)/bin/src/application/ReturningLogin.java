package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.media.MediaPlayer;

/**
 * Class ReturningLogin contains methods that will be called to edit the GUI with features regarding the user's returning login.
 */
public class ReturningLogin implements ProgramMethodInt{
	
	//audio for click sound when button is pressed
	MediaPlayer click;
	
	//password/textfield toggles
	PasswordField textBox;
	TextField textBoxShow;
	PasswordField textBox2;
	TextField textBoxShow2;
	Button confirm;
	Button back;
	Button showPass;
	Button showPass2;
	Label setPass;
	Label reenterPass;
	Label passwordInvalid;
	
	double width;
	double width2;
	
	//value to determine what show password does
	boolean isHidden;
	boolean isHidden2;
	String str;					//passwordField value
	String str2;
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
	 * @param yesNoBack		"yes", "no", "back", "cancel" will all generate different background displays appropriate to the prior decision
	 */
	public void setUpRL(String yesNoBack) {
		if (yesNoBack.equals("yes")) 		//plays display for changed password forced back to log in page
			resetPage("src/background display/REVERSEPASS.mp4");
		else if (yesNoBack.equals("no"))	//plays display for opening login/launch application
			resetPage("src/background display/OPENING.mp4");
		else if (yesNoBack.equals("back"))	//plays display if back button was pressed
			resetPage("src/background display/REVERSEPASS.mp4");
		else if (yesNoBack.equals("cancel"))	//plays display if user log out or canceled entry
			resetPage("src/background display/REVERSETOPPASSPAGE.mp4");
		else if (yesNoBack.equals("logouthome"))	//plays the display if user logged out directly from the home page
			resetPage("src/background display/REVERSEWORKSPACE.mp4");
		else if (yesNoBack.equals("logout"))		//plays the display if user logged out during workspace editing, searching, anything other than home page
			resetPage("src/background display/LOGOUTEDIT.mp4");
		
		stage.setTitle("Welcome Back To Digi-Diary!"); //change the title of application
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
		textBox = passwordField("", 300,50,300,50,"textfield3");
		textBoxShow = textField("", 300,50,300,50,"textfield3");
		Button showPass = button("Show",0,0,"button1_",120,50,120,50,null,null,null,null);
		Button signIn = button("Sign in", 0,0,"button4_3",150,50,150,50,null,null,null,null);
		Button changePass = button("Change password",0,0,"button4_3",200,50,200,50,null,null,null,null);
		Button forgotPass = button("Forgot password",0,0,"button1_1",166,50,166,50,null,null,null,null);
		//button functions
		showPass.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent event) {
				click = mediaPlayer("src/music/click.mp3", 0.25);	//click sound/audio
				click.play();
				
				if (showPass.getText().equals("Show")) {
					showPass.setText("Hide");
				}
				else {
					showPass.setText("Show");
				}
				
				showPassword(1);
			}
		});
		signIn.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent event) {
				click = mediaPlayer("src/music/click.mp3", 0.25);	//click sound/audio
				click.play();
				
				//get the password string from either box
				if (isHidden == true)
					str = textBox.getText();
				else 
					str = textBoxShow.getText();
				
				//correct default pass or else set red text
				if (str.equals(filePass)) {
					isHidden = true;	//reset show password indicator
					JW.initializeObjects();		//set up journal workspace nodes
					JW.bindObjectValues();		//set up journal workspace nodes
					JW.setObjectListeners();		//set up journal workspace nodes
					JW.setUpJW();		//set up journal workspace for returning user
				}
				else {
					//keep the incorrect answer in the textbox but reset to hidden
					textBox.setText(str);
					textBox.setVisible(true);
					textBoxShow.setVisible(false);
					isHidden = true;
					showPass.setText("Show");
					
					invalidPass.setVisible(true);	//show red text label
				}
			}
		});
		changePass.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent event) {
				click = mediaPlayer("src/music/click.mp3", 0.25);	//click sound/audio
				click.play();
				
				//get the password string from either box
				if (isHidden == true)
					str = textBox.getText();
				else 
					str = textBoxShow.getText();
				
				//correct default pass or else set red text
				if (str.equals(filePass)) {
					str = "";
					isHidden = true;	//reset show password indicator
					changePass(true);	//set up journal workspace for returning user
				}
				else {
					//keep the incorrect answer in the textbox but reset to hidden
					textBox.setText(str);
					textBox.setVisible(true);
					textBoxShow.setVisible(false);
					isHidden = true;
					showPass.setText("Show");
					
					invalidPass.setVisible(true);	//show red text label
				}
			}
		});
		forgotPass.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent event) {
				click = mediaPlayer("src/music/click.mp3", 0.25);	//click sound/audio
				click.play();
				str = "";
				
				forgotPass();
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
		}
	}
	
	/**
	 * Method changePass will allow user to change the password on file after correctly inputting old password.
	 */
	public void changePass(boolean tF) {
		if (tF == true) {		//set whichever display depending on boolean value
			resetPage("src/background display/PASSPAGE.mp4");
		}
		else {					//false means that changePass is called through forgotPass = different display
			resetPage("src/background display/PASSPAGE2.mp4");
		}
		
		
		//RECTANGLE/BORDER resize unbinded
		root.getChildren().add(rectangle(imgWidth/4,imgHeight/3,"rect1_1",imgWidth/2,imgHeight/2,20.0,10.0,null,null
				,root.widthProperty().divide(2).subtract(imgWidth/2),root.heightProperty().divide(2).subtract(imgHeight/1.75)));
		root.getChildren().add(rectangle(imgWidth/4,imgHeight/3,"rect3",imgWidth/2,imgHeight/2,20.0,10.0,null,null
				,root.widthProperty().divide(2).subtract(imgWidth/2),root.heightProperty().divide(2).subtract(imgHeight/1.75)));
		root.getChildren().add(container);
		
		
		//make nodes
		setPass = label("Set new password: ",0,0,"label2",0,0,550,100,null,null,null,null);
		reenterPass = label("Confirm password: ",0,0,"label2",0,0,550,100,null,null,null,null);
		textBoxShow = textField("", 300,50,300,50,"textfield3");
		textBoxShow2 = textField("", 300,50,300,50,"textfield3");
		textBox = passwordField("", 300,50,300,50,"textfield3");
		textBox2 = passwordField("", 300,50,300,50,"textfield3");
		confirm = button("Confirm", 0,0,"button4_3",150,50,150,50,null,null,null,null);
		back = button("Back", 0,0,"button4_3",125,50,125,50,null,null,null,null);
		showPass = button("Show",0,0,"button1_",120,50,120,50,null,null,null,null);
		showPass2 = button("Show",0,0,"button1_",120,50,120,50,null,null,null,null);
		passwordInvalid = label("Invalid password combination.",0,0,"labelInvalidPass1",0,0,550,100,null,null,null,null);
		
		//button functionality
		back.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent event) {
				click = mediaPlayer("src/music/click.mp3", 0.25);	//click sound/audio
				click.play();
				
				RL.setUpRL("back");
			}
		});
		confirm.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent event) {
				click = mediaPlayer("src/music/click.mp3", 0.25);	//click sound/audio
				click.play();
				
				String snp = "";
				String cp = "";
				if (isHidden == true)
					snp = textBox.getText();
				else 
					snp = textBoxShow.getText();
				if (isHidden2 == true)
					cp = textBox2.getText();
				else 
					cp = textBoxShow2.getText();
				
				
				if (cp.equals("p") || !cp.equals(snp)) {		//again don't know if "P" is allowed as a password or not; instructions unclear
					//keep the incorrect answer in the textbox but reset to hidden
					str = snp;
					str2 = cp;
					textBox.setText(str);
					textBox.setVisible(true);
					textBoxShow.setVisible(false);
					isHidden = true;
					showPass.setText("Show");
					//keep the incorrect answer in the textbox2 but reset to hidden
					textBox2.setText(str2);
					textBox2.setVisible(true);
					textBoxShow2.setVisible(false);
					isHidden2 = true;
					showPass2.setText("Show");
					
					passwordInvalid.setVisible(true);
				}
				else {
					try {
						FileWriter fw = new FileWriter(passwordFile);
						BufferedWriter bw = new BufferedWriter(fw);
						PrintWriter pw = new PrintWriter(bw);
						
						clearFile(passwordFile);
						
						pw.println(cp);
						pw.println(securityQuestion);
						pw.println(securityAnswer);
						pw.println(",");
						
						pw.close();
						bw.close();
						fw.close();
					}
					catch (IOException e) {e.printStackTrace();}
					
					RL.setUpRL("yes");		//return user to login page to attempt normal login
				}
			}
		});
		showPass.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent event) {
				click = mediaPlayer("src/music/click.mp3", 0.25);	//click sound/audio
				click.play();
				
				if (showPass.getText().equals("Show")) {
					showPass.setText("Hide");
				}
				else {
					showPass.setText("Show");
				}
				
				showPassword(1);
			}
		});
		showPass2.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent event) {
				click = mediaPlayer("src/music/click.mp3", 0.25);	//click sound/audio
				click.play();
				
				if (showPass2.getText().equals("Show")) {
					showPass2.setText("Hide");
				}
				else {
					showPass2.setText("Show");
				}
				
				showPassword(2);
			}
		});
		
		
		//translate nodes
		setPass.setTranslateX(-25);
		setPass.setTranslateY(290);
		reenterPass.setTranslateX(-25);
		reenterPass.setTranslateY(315);
		textBoxShow.setTranslateX(75);
		textBoxShow.setTranslateY(175);
		textBoxShow2.setTranslateX(75);
		textBoxShow2.setTranslateY(175);
		textBox.setTranslateX(75);
		textBox.setTranslateY(25);
		textBox2.setTranslateX(75);
		textBox2.setTranslateY(25);
		showPass.setTranslateX(275);
		showPass.setTranslateY(-275);
		showPass2.setTranslateX(275);
		showPass2.setTranslateY(-275);
		confirm.setTranslateY(40);
		passwordInvalid.setTranslateX(200);
		passwordInvalid.setTranslateY(-295);
		back.setTranslateX(160);
		back.setTranslateY(-35);
		
		
		//add nodes
		add(setPass);
		add(reenterPass);
		add(textBoxShow);
		add(textBoxShow2);
		add(textBox);
		add(textBox2);
		add(confirm);
		add(back);
		add(showPass);
		add(showPass2);
		add(passwordInvalid);
		
		
		//nodes temporarily set invisible
		passwordInvalid.setVisible(false);
		textBoxShow.setVisible(false);
		textBoxShow2.setVisible(false);
	}
	
	/**
	 * Method forgotPass will first ask user to answer security question before calling changePass.
	 */
	public void forgotPass() {
		resetPage("src/background display/PASSPAGE.mp4");
		
		
		//RECTANGLE/BORDER resize unbinded
		root.getChildren().add(rectangle(imgWidth/4,imgHeight/3,"rect1_1",imgWidth/2,imgHeight/2,20.0,10.0,null,null
				,root.widthProperty().divide(2).subtract(imgWidth/2),root.heightProperty().divide(2).subtract(imgHeight/1.75)));
		root.getChildren().add(rectangle(imgWidth/4,imgHeight/3,"rect3",imgWidth/2,imgHeight/2,20.0,10.0,null,null
				,root.widthProperty().divide(2).subtract(imgWidth/2),root.heightProperty().divide(2).subtract(imgHeight/1.75)));
		root.getChildren().add(container);
		
		
		//nodes
		Label displayQuestion = label(securityQuestion,0,0,"label2",0,0,550,300,null,null,null,null);
		Label wrongAnswer = label("Answer is incorrect.",0,0,"labelInvalidPass1",0,0,550,100,null,null,null,null);
		textBoxShow = textField("", 350,50,350,50,"textfield3");
		Button submit = button("Submit", 0,0,"button4_3",150,50,150,50,null,null,null,null);
		back = button("Back", 0,0,"button4_3",125,50,125,50,null,null,null,null);
		
		
		//button functions
		back.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent event) {
				click = mediaPlayer("src/music/click.mp3", 0.25);	//click sound/audio
				click.play();
				
				RL.setUpRL("back");
			}
		});
		submit.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent event) {
				click = mediaPlayer("src/music/click.mp3", 0.25);	//click sound/audio
				click.play();
				
				//if answer is correct then user is verified, call changePass so user can change the pass
				if (textBoxShow.getText().equals(securityAnswer)) {
					changePass(false);
				}
				else {		//display incorrect label if user gets answer wrong
					wrongAnswer.setVisible(true);
				}
			}
		});
		
		
		//translate/shift node location
		displayQuestion.setAlignment(Pos.CENTER);
		displayQuestion.setTranslateY(67);
		wrongAnswer.setTranslateX(100);
		wrongAnswer.setTranslateY(-115);
		textBoxShow.setTranslateY(57);
		submit.setTranslateX(-75);
		submit.setTranslateY(62);
		back.setTranslateX(75);
		back.setTranslateY(-13);
		
		
		//add nodes
		add(displayQuestion);
		add(textBoxShow);
		add(submit);
		add(back);
		add(wrongAnswer);
		
		
		//nodes set invisible temporarily
		wrongAnswer.setVisible(false);
	}
	
	/**
	 * Method showPassword displays or hides password on click of whatever buttons uses this method
	 * (specified usage for this program)
	 * @param num	if 1 then first textbox, if 2 then second
	 */
	public void showPassword(int num) {
		//if num is 1 then it is referring to the first pass box (the if statement is specialized for both setting up the profile and 
		//first time logging in page since they both only have 1 password box to keep track of
		if (num == 1) {
			if (isHidden == true) {
				str = textBox.getText();
				textBoxShow.setText(str);
				textBoxShow.setVisible(true);
				textBox.setVisible(false);
				isHidden = false;
			}
			else {
				str = textBoxShow.getText();
				textBox.setText(str);
				textBox.setVisible(true);
				textBoxShow.setVisible(false);
				isHidden = true;
			}
		}
		else {
			//if num isn't 1 then it is referring to the 2nd pass box (this is specialized solely for setting up profile page
			//where there are two password boxes to keep track of
			if (isHidden2 == true) {
				str = textBox2.getText();
				textBoxShow2.setText(str);
				textBoxShow2.setVisible(true);
				textBox2.setVisible(false);
				isHidden2 = false;
			}
			else {
				str = textBoxShow2.getText();
				textBox2.setText(str);
				textBox2.setVisible(true);
				textBoxShow2.setVisible(false);
				isHidden2 = true;
			}
		}
	}
}
