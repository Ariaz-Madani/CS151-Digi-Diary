package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;

public class FirstTimeLogin implements ProgramMethodInt{
	
	//audio for click sound when button is pressed
	MediaPlayer click;
	
	//password/textfield toggles
	PasswordField textBox;
	TextField textBoxShow;
	PasswordField textBox2;
	TextField textBoxShow2;
	
	//value to determine what show password does
	boolean isHidden = true;	
	boolean isHidden2 = true;
	boolean showingMenu = false;
	String displayInvalid = "";
	String str ="";					//passwordField value
	String str2 = "";
	
	//field nodes for setting up profile
	Label securityQuestion;
	Label securityAnswer;
	Label setPass;
	Label reenterPass;
	Label questionBlank;
	Label answerBlank;
	Label passwordInvalid;
	TextField createQuestion;
	TextField answerQuestion;
	Button confirm;
	Button showPass;
	Button showPass2;
	ChoiceBox<String> dropDownMenu;
	
	@Override
	public void add(Object obj) {
		container.getChildren().add((Node) obj);
	}
	

	/**
	 * Method setUpFTL sets up the login page for a new user whose password is unchanged from default.
	 * FTL = First Time Login (acronym)
	 */
	public void setUpFTL() {
		resetPage("src/background display/OPENING.mp4");
		
		
		//RECTANGLE/BORDER resize unbinded
		root.getChildren().add(rectangle(imgWidth/4,imgHeight/3,"rect1_1",imgWidth/2,imgHeight/2,20.0,10.0,null,null
				,root.widthProperty().divide(2).subtract(imgWidth/2),root.heightProperty().divide(2).subtract(imgHeight/1.75)));
		root.getChildren().add(rectangle(imgWidth/4,imgHeight/3,"rect3",imgWidth/2,imgHeight/2,20.0,10.0,null,null
				,root.widthProperty().divide(2).subtract(imgWidth/2),root.heightProperty().divide(2).subtract(imgHeight/1.75)));
		root.getChildren().add(container);
		

		//set up nodes
		Label enterPass = label("Enter default password: ",0,0,"label2",0,0,550,100,null,null,null,null);
		Label invalidPass = label("Password was incorrect.",0,0,"labelInvalidPass1",0,0,550,100,null,null,null,null);
		textBox = passwordField("", 300,50,300,50,"textfield1");
		textBoxShow = textField("", 300,50,300,50,"textfield1");
		Button showPass = button("Show",0,0,"button1_",120,50,120,50,null,null,null,null);
		Button signIn = button("Sign in", 0,0,"button4",150,50,150,50,null,null,null,null);
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
				if (str.equals("p")) {
					str = "";
					isHidden = true;	//reset show password indicator
					setUpProfile();		//set up profile for new user
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
		
		
		//edit location
		enterPass.setTranslateX(-50);
		enterPass.setTranslateY(150);
		textBox.setTranslateX(100);
		textBox.setTranslateY(88);	//set both textBox and textBoxShow in same location
		textBoxShow.setTranslateX(100);
		textBoxShow.setTranslateY(13);	//set both textBox and textBoxShow in same location
		showPass.setTranslateX(295);
		showPass.setTranslateY(-65);
		signIn.setTranslateX(25);
		signIn.setTranslateY(-85);
		invalidPass.setTranslateX(225);
		invalidPass.setTranslateY(-80);
		
		//add to containers
		add(enterPass);
		add(textBox);
		add(textBoxShow);	textBoxShow.setVisible(false);		//initially set invisible until user presses show pass
		add(showPass);
		add(invalidPass);	invalidPass.setVisible(false);  //initially set invisible until if user gets it wrong
		add(signIn);
	}
	
	/**
	 * Method setUpProfile brings user to page where he/she will set a password and security question.
	 * After successfully creating, it will call method setUpRL from class ReturningLogin.
	 * @throws InterruptedException 
	 */
	public void setUpProfile()  {
		resetPage("src/background display/PASSPAGE.mp4");
		stage.setTitle("Create New Password"); //change the title of application
		
		
		//RECTANGLE/BORDER resize unbinded
		//line object created so that we can toggle visibility later (cosmetic purpose)
		Rectangle line = rectangle(imgWidth/2-imgWidth/5.12,imgHeight/3.17,"rect1_2",imgWidth/2.6,imgHeight/874,20.0,10.0,null,null
				,root.widthProperty().divide(2).subtract(imgWidth/2),root.heightProperty().divide(2).subtract(imgHeight/2.11));
		Rectangle arrowBorder = rectangle(1032,imgHeight/3.23,"rect1_3",imgWidth/122,imgWidth/122,2.5,2.5,null,null
				,root.widthProperty().divide(2).subtract(imgWidth/2),root.heightProperty().divide(2).subtract(imgHeight/2.08));
		root.getChildren().add(rectangle(imgWidth/4,imgHeight/3,"rect1_1",imgWidth/2,imgHeight/1.5,20.0,10.0,null,null
				,root.widthProperty().divide(2).subtract(imgWidth/2),root.heightProperty().divide(2).subtract(imgHeight/1.5)));
		root.getChildren().add(rectangle(imgWidth/4,imgHeight/3,"rect3",imgWidth/2,imgHeight/1.5,20.0,10.0,null,null
				,root.widthProperty().divide(2).subtract(imgWidth/2),root.heightProperty().divide(2).subtract(imgHeight/1.5)));
		root.getChildren().add(line);
		root.getChildren().add(arrowBorder);
		root.getChildren().add(container);
		
		
		//set up nodes
		securityQuestion = label("Create or pick a security question: ",0,0,"label2",0,0,550,100,null,null,null,null);
		securityAnswer = label("Answer the security question: ",0,0,"label2",0,0,550,100,null,null,null,null);
		setPass = label("Set new password: ",0,0,"label2",0,0,550,100,null,null,null,null);
		reenterPass = label("Confirm password: ",0,0,"label2",0,0,550,100,null,null,null,null);
		questionBlank = label("Security question cannot be blank.",0,0,"labelInvalidPass1",0,0,550,100,null,null,null,null);
		answerBlank = label("Answer cannot be blank.",0,0,"labelInvalidPass1",0,0,550,100,null,null,null,null);
		passwordInvalid = label("Invalid password combination.",0,0,"labelInvalidPass1",0,0,550,100,null,null,null,null);
		createQuestion = textField("", 600,50,600,50,"textfield1");
		answerQuestion = textField("", 600,50,600,50,"textfield1");
		textBoxShow = textField("", 300,50,300,50,"textfield1");
		textBoxShow2 = textField("", 300,50,300,50,"textfield1");
		textBox = passwordField("", 300,50,300,50,"textfield1");
		textBox2 = passwordField("", 300,50,300,50,"textfield1");
		confirm = button("Confirm", 0,0,"button4",150,50,150,50,null,null,null,null);
		showPass = button("Show",0,0,"button1_",120,50,120,50,null,null,null,null);
		showPass2 = button("Show",0,0,"button1_",120,50,120,50,null,null,null,null);
		
		
		//button functions
		confirm.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent event) {
				click = mediaPlayer("src/music/click.mp3", 0.25);	//click sound/audio
				click.play();
				
				String cq = createQuestion.getText();
				String aq = answerQuestion.getText();
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
				//correct default pass or else set red text
				//user cannot use default password again as their new password and password can be empty or an empty space
				if (!cp.equals("p") && cp.equals(snp)) {			//dont know if should add ||cp.equals("P"); instructions are very unclear
					if (!cq.contains("?"))	//format any missing punctuation
						cq = cq + "?";
					
					try {
						FileWriter fw = new FileWriter(passwordFile);
						BufferedWriter bw = new BufferedWriter(fw);
						PrintWriter pw = new PrintWriter(bw);
						
						clearFile(passwordFile);				//reset file to reenter info line; just 1 line anyways
						
						pw.println(cp);
						pw.println(cq);
						pw.println(aq);
					//	pw.println(",");
						
						pw.close();
						bw.close();
						fw.close();
					} 
					catch(IOException e) {e.printStackTrace();}
					
							
					RL.setUpRL("yes");		//return user to login page to attempt normal login
				}
				else {
					//if an input is wrong or blank then create invalid statements to display based on field of text
					displayInvalid = "";
					if (cq.isEmpty()) {
						questionBlank.setVisible(true);
						displayInvalid = displayInvalid + "1";
					}
					else {
						questionBlank.setVisible(false);
					}
					if (aq.isEmpty()) {
						answerBlank.setVisible(true);	
						displayInvalid = displayInvalid + "2";
					}
					else {
						answerBlank.setVisible(false);	
					}
					if (cp.equals("p") || !cp.equals(snp)) {		//dont know if should add ||cp.equals("P"); instructions are very unclear
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
						displayInvalid = displayInvalid + "3";
					}
					else {
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
						
						passwordInvalid.setVisible(false);
					}
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
		
		
		//drop down menu
		dropDownMenu = new ChoiceBox<>();
		dropDownMenu.setMinSize(600, 50);	//setting dimensions
		dropDownMenu.setMaxSize(600, 50);
		
		
		dropDownMenu.setId("dropdown1");	//setting customization for menu
		
		
		dropDownMenu.getItems().addAll("What's your first pet's name?"			//adding menu options
				,"What's your childhood nickname?"
				,"What's your dream car?"
				,"What's your dream school?"
				,"What's your primary hobby?"
				,"What's your favorite color?"
				,"What's your favorite dish?"
				,"What's your favorite beverage?"
				,"What's your favorite video game?"
				,"What's your favorite movie?");
		
		
		//Separate event functions
		//ensures that textbox changes or sets new input when user selects a choice from drop down menu
		dropDownMenu.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent event) {
				createQuestion.setText(dropDownMenu.getValue());
				dropDownMenu.getItems().remove(dropDownMenu.getValue());
				dropDownMenu.getItems().add(0, dropDownMenu.getValue());
			}
		});
		//ensures that while the dropdownmenu or its choices are pressed, it will toggle visibility of other fields
		//to make the transparent style smooth and clean looking for user usage
		dropDownMenu.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				//hides other fields to make things look clean for the transparent drop down style
				if (answerQuestion.isVisible() && dropDownMenu.isShowing()) {
					toggleVisibility(false);
				}
				else if (!answerQuestion.isVisible() && !dropDownMenu.isShowing()) {
					toggleVisibility(true);
				}
			}
		});
		//ensures that the hidden fields come back even if user clicks off dropdownmenu
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (!answerQuestion.isVisible() && !dropDownMenu.isShowing()) {
					toggleVisibility(true);
				}
			}
		});
		//ensures that if a question from drop downlist is selected or if there is text, hidden fields become visible
		createQuestion.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable,
		            String oldValue, String newValue) {

		    	toggleVisibility(true);
		    }
		});
		
		
		//edit location (shift xy coordinates)
		securityQuestion.setTranslateX(-25);
		securityQuestion.setTranslateY(187);
		createQuestion.setTranslateY(172);
		dropDownMenu.setTranslateY(127);
		securityAnswer.setTranslateX(-25);
		securityAnswer.setTranslateY(122);
		answerQuestion.setTranslateY(107);
		setPass.setTranslateX(-25);
		setPass.setTranslateY(139);
		reenterPass.setTranslateX(-25);
		reenterPass.setTranslateY(89);
		showPass.setTranslateX(270);
		showPass.setTranslateY(-200);
		showPass2.setTranslateX(270);
		showPass2.setTranslateY(-200);
		textBox.setTranslateX(75);
		textBox.setTranslateY(77);
		textBox2.setTranslateX(75);
		textBox2.setTranslateY(25);
		textBoxShow.setTranslateX(75);
		textBoxShow.setTranslateY(-350);
		textBoxShow2.setTranslateX(75);
		textBoxShow2.setTranslateY(-350);
		questionBlank.setTranslateX(-25);
		questionBlank.setTranslateY(-670);
		answerBlank.setTranslateX(-25);
		answerBlank.setTranslateY(-585);
		passwordInvalid.setTranslateX(200);
		passwordInvalid.setTranslateY(-460);
		confirm.setTranslateY(45);
		
		
		//add nodes to container
		add(securityQuestion);
		add(createQuestion);
		add(dropDownMenu);
		add(securityAnswer);
		add(answerQuestion);
		add(setPass);
		add(textBox);
		add(reenterPass);
		add(textBox2);	
		add(confirm);
		add(showPass);
		add(showPass2);
		add(textBoxShow);
		add(textBoxShow2);
		add(questionBlank);
		add(answerBlank);
		add(passwordInvalid);
		
		
		//temporarily invisible nodes on start of launch
		questionBlank.setVisible(false);
		answerBlank.setVisible(false);
		passwordInvalid.setVisible(false);
		textBoxShow.setVisible(false);
		textBoxShow2.setVisible(false);
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
				str2 = textBox2.getText();
				textBoxShow2.setText(str2);
				textBoxShow2.setVisible(true);
				textBox2.setVisible(false);
				isHidden2 = false;
			}
			else {
				str2 = textBoxShow2.getText();
				textBox2.setText(str2);
				textBox2.setVisible(true);
				textBoxShow2.setVisible(false);
				isHidden2 = true;
			}
		}
	}
	
	/**
	 * Method toggleVisibility is used to toggle between showing or hiding fields of nodes
	 * @param tF	T = visible; F = invisible
	 */
	public void toggleVisibility(boolean tF) {
		if (tF == true) {
			//toggling everything visible but there are some edge cases
			securityAnswer.setVisible(true);
			answerQuestion.setVisible(true);
			setPass.setVisible(true);
			reenterPass.setVisible(true);
			confirm.setVisible(true);
			showPass.setVisible(true);
			showPass2.setVisible(true);
			
			//EDGE CASE:
			//if certain invalid statements are called; let them remain there
			if (!displayInvalid.isEmpty()) {
				if (displayInvalid.contains("1"))
					questionBlank.setVisible(true);
				if (displayInvalid.contains("2"))
					answerBlank.setVisible(true);
				if (displayInvalid.contains("3"))
					passwordInvalid.setVisible(true);
			}
			
			//EDGE CASE:
			//if we are toggling everything to be visible, either show pass box or hidden pass box should be visible
			//if hidden is true meaning we still want password to be hidden then dont set visibility to the show pass boxes
			if (isHidden == true) {
				textBox.setVisible(true);
				textBoxShow.setVisible(false);
			}
			//if hidden is not true(false) meaning we don't want password to be hidden then set visibility to the show pass boxes only
			else {
				textBox.setVisible(false);
				textBoxShow.setVisible(true);
			}
			
			//do the same for 2nd passbox's isHidden indicator
			if (isHidden2 == true) {
				textBox2.setVisible(true);
				textBoxShow2.setVisible(false);
			}
			else {
				textBox2.setVisible(false);
				textBoxShow2.setVisible(true);
			}
		}
		else {
			//since we are toggling visibility(false) off then everything can be set to false
			securityAnswer.setVisible(false);
			answerQuestion.setVisible(false);
			setPass.setVisible(false);
			reenterPass.setVisible(false);
			confirm.setVisible(false);
			showPass.setVisible(false);
			showPass2.setVisible(false);
			questionBlank.setVisible(false);
			answerBlank.setVisible(false);
			passwordInvalid.setVisible(false);
			textBox.setVisible(false);
			textBox2.setVisible(false);
			textBoxShow.setVisible(false);
			textBoxShow2.setVisible(false);
		}
	}
}
