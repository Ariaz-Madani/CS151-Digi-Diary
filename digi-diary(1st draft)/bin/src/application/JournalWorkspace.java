package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * Class JournalWorkspace contains methods that will be called to edit the GUI with features regarding the journal workspace.
 */
public class JournalWorkspace implements ProgramMethodInt{
	
	//every node for reference that are used for journal workspace
	Rectangle searchBar;				//make this a centered with message	--> search			//doesn't have to be instance variable
	Rectangle searchBarBorder;			//make this a centered with message	--> search			//doesn't have to be instance variable
	
	//aesthetics 
	Rectangle contentBox;		
	Rectangle buttonBox;				
	Rectangle buttonBoxBorder;
	Rectangle titleBar;	
	
	//functionality
	Button createNewEntry;
	Button searchOldEntry;
	Button recoverOldEntry;
	Button recover;
	Button deleteEntry;
	Button editEntry;
	Button saveEntry;
	Button cancel;
	Button logOut;
	Button backToHome;
	Button confirmDelete;		//make this a centered with message	--> delete			//doesn't have to be instance variable
	Button volumeMinus;			//make this a centered with message	--> settings		//doesn't have to be instance variable
	Button volumePlus;			//make this a centered with message	--> settings		//doesn't have to be instance variable
	TextField date;				//user edits date from here
	TextField title;			//user edits title from here
	TextArea area;				//user picks buttons from here
	TextArea viewArea;			//user views searched entries from here
	TextField displayUserDateTime;			//shows user's local time and date
	Slider slider;				//volume slider
	ComboBox<String> dropDownMenu;
	
	//data holders
	Path entryPath;
	File file;
	String todayDate;
	String fileDate;		//date
	String fileTitle = "";		//title
	String fileContent;		//entry content
	boolean isFromHomePage = true;	//used to decide which animation to display
	
	@Override
	public void add(Object obj) {
		root.getChildren().add((Node) obj);
	}
	
	/**
	 * Method bindObjectValues creates an auto-resize system for each specified node and its font for the journal workspace.
	 */
	public void bindObjectValues() {
		createNewEntry.setMinSize(buttonBox.getWidth()*0.90813648294,50.77333333333333);				//replace with buttonBox.getHeight()/15		if you prefer the height of the buttons to also be resized accordingly
		searchOldEntry.setMinSize(buttonBox.getWidth()*0.90813648294,50.77333333333333);
		recoverOldEntry.setMinSize(buttonBox.getWidth()*0.90813648294,50.77333333333333);
		recover.setMinSize(buttonBox.getWidth()*0.90813648294,50.77333333333333);
		deleteEntry.setMinSize(buttonBox.getWidth()*0.90813648294,50.77333333333333);
		editEntry.setMinSize(buttonBox.getWidth()*0.90813648294,50.77333333333333);
		saveEntry.setMinSize(buttonBox.getWidth()*0.90813648294,50.77333333333333);
		cancel.setMinSize(buttonBox.getWidth()*0.90813648294,50.77333333333333);
		logOut.setMinSize(buttonBox.getWidth()*0.90813648294,50.77333333333333);
		backToHome.setMinSize(buttonBox.getWidth()*0.90813648294,50.77333333333333);
		area.setPrefSize(contentBox.getWidth(), contentBox.getHeight());
		area.setStyle("-fx-font-size: " + root.getHeight()/37/1.618 + "pt;");								//automatically resize the font of the textbox; font size golden rule is to /1.618
		title.setPrefSize(titleBar.getWidth()/8*5-(root.getWidth()*0.00656167979/2), titleBar.getHeight());
		title.setStyle("-fx-font-size: " + root.getHeight()/37/1.618 + "pt;");							
		date.setPrefSize(titleBar.getWidth()/8*3-(root.getWidth()*0.00656167979/2), titleBar.getHeight());
		date.setStyle("-fx-font-size: " + root.getHeight()/37/1.618 + "pt;");
		displayUserDateTime.setPrefSize(root.getWidth()/5.5, root.getHeight()/20);
		displayUserDateTime.setStyle("-fx-font-size: " + root.getHeight()/37/1.618 + "pt;");
		dropDownMenu.setPrefWidth(contentBox.getWidth()/2);
		dropDownMenu.setStyle("-fx-font-size: " + root.getHeight()/37/1.618 + "pt;");		//MUST declare css style before adding this to work
		viewArea.setPrefSize(contentBox.getWidth()/2, contentBox.getHeight());
		viewArea.setStyle("-fx-font-size: " + root.getHeight()/37/1.618 + "pt;");
		slider.setStyle("-fx-pref-width: " + root.getWidth()*250/1524 + "; -fx-pref-height: " + root.getHeight()*25/895 + ";");
	}
	
	/**
	 * Method setObjectListeners sets up the functionality of all the buttons of the journal workspace.
	 */
	public void setObjectListeners() {
		root.heightProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				bindObjectValues();		//refreshes and resizes all nodes currently visible
			}
			
		});
		root.widthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				bindObjectValues();		//refreshes and resizes all nodes currently visible
			}
			
		});
		buttonBoxBorder.setOnMousePressed(new EventHandler<  MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (buttonBoxBorder.getWidth() == 15 || buttonBox.getWidth() == 15) {
					buttonBox.setWidth(217.71428571428572);
					buttonBoxBorder.setWidth(217.71428571428572);
					buttonBoxBorder.setOpacity(1);
					area.setMinSize(contentBox.getWidth(), contentBox.getHeight());
					for (int i = root.getChildren().indexOf(date) + 1; i < root.getChildren().size(); i++) {
						root.getChildren().get(i).setVisible(true);
					}
				}
				else {
					buttonBox.setWidth(15);
					buttonBoxBorder.setWidth(15);
					buttonBoxBorder.setOpacity(0.25);
					area.setMinSize(contentBox.getWidth(), contentBox.getHeight());
					for (int i = root.getChildren().indexOf(date) + 1; i < root.getChildren().size(); i++) {
						root.getChildren().get(i).setVisible(false);
					}
				}
				bindObjectValues();		//refreshes and resizes all nodes currently visible
			}

		});
		//Separate event functions
		//ensures that textbox changes or sets new input when user selects a choice from drop down menu
		dropDownMenu.setOnAction(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent event) {
				if (stage.getTitle().contains("Old Entry") && dropDownMenu.getValue()!=null) {		//if user is using drop down menu on search page
					//get the text from selected file and display it on resized text area
					fileTitle = dropDownMenu.getValue();							//gets the selected file name
					entryPath = Paths.get("src/fileDB/savedFiles/" + fileTitle);	//gets the path to that file
					file = new File(entryPath.toString());							//make a new file object that links to the content of selected file path
					fileContent = getFileContent(file);								//get the file content
					
					viewArea.setText(fileContent);					//display the file's content
				}
				else if (stage.getTitle().contains("Recovery") && dropDownMenu.getValue()!=null) {		//if user is using drop down menu on recovery page
					fileTitle = dropDownMenu.getValue();							//gets the selected file name
					entryPath = Paths.get("src/fileDB/deletedFiles/" + fileTitle);	//gets the path to that file
					file = new File(entryPath.toString());							//make a new file object that links to the content of selected file path
					fileContent = getFileContent(file);								//get the file content
					
					viewArea.setText(fileContent);					//display the file's content
				}
			}
		});
		dropDownMenu.showingProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (dropDownMenu.isShowing()) {
					area.setVisible(false);
					viewArea.setVisible(false);
				}
				else {
					area.setVisible(true);
					viewArea.setVisible(true);
				}
			}
		});
		dropDownMenu.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (dropDownMenu.getValue() == null) {
					viewArea.setText("");
					if (stage.getTitle().contains("Old Entry") && root.getChildren().contains(editEntry)) {			// must be false because this means user is NOT ON the homepage (homepage should not be editable anyways)
						root.getChildren().remove(editEntry);		//remove editEntry since it is in search/recovery mode
						root.getChildren().remove(deleteEntry);		//remove deleteEntry as well
						
						//since we deleted editEntry, must reset the location of the other two buttons that are being displayed
						backToHome.layoutXProperty().bind(buttonBox.layoutXProperty().add(buttonBox.widthProperty().multiply(0.04593175853)));		
						backToHome.layoutYProperty().bind(buttonBox.layoutYProperty().add(buttonBox.heightProperty().multiply(0.0131302521)));
					}
					else if (stage.getTitle().contains("Recovery") && root.getChildren().contains(recover)) {			// must be false because this means user is NOT ON the homepage (homepage should not be editable anyways)
						root.getChildren().remove(recover);		//remove recover since it is in search/recovery mode
						
						//since we deleted editEntry, must reset the location of the other two buttons that are being displayed
						backToHome.layoutXProperty().bind(buttonBox.layoutXProperty().add(buttonBox.widthProperty().multiply(0.04593175853)));		
						backToHome.layoutYProperty().bind(buttonBox.layoutYProperty().add(buttonBox.heightProperty().multiply(0.0131302521)));
					}
				}
				else if (dropDownMenu.getValue() != null) {
					if (stage.getTitle().contains("Old Entry")) {			// must be false because this means user is NOT ON the homepage (homepage should not be editable anyways)
						if (!root.getChildren().contains(editEntry) || !root.getChildren().contains(deleteEntry)) {
							int i = root.getChildren().indexOf(backToHome);
							root.getChildren().add(i+1, editEntry);						//adding edit and delete buttons once an option is clicked
							root.getChildren().add(i+1, deleteEntry);
						}
						
						editEntry.layoutXProperty().bind(buttonBox.layoutXProperty().add(buttonBox.widthProperty().multiply(0.04593175853)));
						editEntry.layoutYProperty().bind(buttonBox.layoutYProperty().add(buttonBox.heightProperty().multiply(0.0131302521)));
						deleteEntry.layoutXProperty().bind(buttonBox.layoutXProperty().add(buttonBox.widthProperty().multiply(0.04593175853)));
						deleteEntry.layoutYProperty().bind(editEntry.layoutYProperty().add(editEntry.heightProperty()).add(buttonBox.widthProperty().multiply(0.04593175853)));
						backToHome.layoutXProperty().bind(buttonBox.layoutXProperty().add(buttonBox.widthProperty().multiply(0.04593175853)));
						backToHome.layoutYProperty().bind(deleteEntry.layoutYProperty().add(deleteEntry.heightProperty()).add(buttonBox.widthProperty().multiply(0.04593175853)));
					}
					else if (stage.getTitle().contains("Recovery")) {			// must be false because this means user is NOT ON the homepage (homepage should not be editable anyways)
						if (!root.getChildren().contains(recover)) {
							int i = root.getChildren().indexOf(backToHome);				//adding recover button once an option is clicked
							root.getChildren().add(i+1, recover);
						}
						
						recover.layoutXProperty().bind(buttonBox.layoutXProperty().add(buttonBox.widthProperty().multiply(0.04593175853)));
						recover.layoutYProperty().bind(buttonBox.layoutYProperty().add(buttonBox.heightProperty().multiply(0.0131302521)));
						backToHome.layoutXProperty().bind(buttonBox.layoutXProperty().add(buttonBox.widthProperty().multiply(0.04593175853)));
						backToHome.layoutYProperty().bind(recover.layoutYProperty().add(recover.heightProperty()).add(buttonBox.widthProperty().multiply(0.04593175853)));
					}
				}
			}
		});
		
		
		//---------------------------------------------------------------------------------------------------------------------------------------------
		//event after application exits
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
//				Platform.exit();		//closes the platforms that threads run on including the application's launch
//				System.exit(0);			//closes all the threads indefinitely 
										//there is already an auto-save system in place for NEW ENTRIES ONLY that aren't saved
										//there could also be an auto save by adding a listener but that would defeat the purpose of having a save button...
				if (stage.getTitle().contains("New Entry")) {
					autoSave();
				}
				clearFolder();
				clearFile(deletedFile);
			}
		});
		//---------------------------------------------------------------------------------------------------------------------------------------------

		//buttons
		createNewEntry.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				click();	//click sound/audio
				createNewEntry();
				
				//get a random unique number
				todayDate = setDateTime();
				fileTitle = todayDate;		//unique random num at 6 digits
				
				//creates a new txt file in specified folder location
				try {
					fileTitle = "src/fileDB/savedFiles/" + fileTitle + ".txt";
					entryPath = Paths.get(fileTitle);
					file = new File(fileTitle);
					Files.createFile(entryPath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				addLine(savedFile, fileTitle.substring(22,fileTitle.length()));	//add the entry within the saved folder
			}
		});
		searchOldEntry.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				click();	//click sound/audio
				searchOldEntry();
			}
		});
		recoverOldEntry.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				click();	//click sound/audio
				recoverOldEntry();
			}
		});
		recover.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				click();	//click sound/audio
				
				viewArea.setVisible(false);
				dropDownMenu.setVisible(false);
				
				String data = dropDownMenu.getValue().toString();												//for some reason fileTitle gets changed when calling substring below twice
				
				File file = new File("src/fileDB/deletedFiles/" + data);
				file.renameTo(new File("src/fileDB/savedFiles/" + data));			//move selected deleted file back into saved folder
			
				//move it between txt files as well
				addLine(savedFile, data);	//add the entry within the deleted folder
				deleteLine(deletedFile, data);	//delete the entry within saved folder
				
				setUpJW();  //return to homepage
			}
			
		});
		deleteEntry.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				click();	//click sound/audio
				
				deleteEntry();
			}
		});
		editEntry.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				click();	//click sound/audio
				area.setVisible(true);
				
				int i = root.getChildren().indexOf(editEntry);						//gets index of editEntry and replaces it with saveEntry in its position
				root.getChildren().add(i, saveEntry);
				root.getChildren().remove(editEntry);
				
				saveEntry.layoutXProperty().bind(buttonBox.layoutXProperty().add(buttonBox.widthProperty().multiply(0.04593175853)));			//sets up xy coordinate of saveEntry button
				saveEntry.layoutYProperty().bind(buttonBox.layoutYProperty().add(buttonBox.heightProperty().multiply(0.0131302521)));
				
				area.setEditable(true);												//makes the 3 fields (content, title, and date all able to be edited and worked on by user
				title.setEditable(true);
				date.setEditable(true);
				
				
				if (stage.getTitle().contains("Old Entry")) {
					
					viewArea.setVisible(false);
					dropDownMenu.setVisible(false);
					editEntry.setVisible(false);
					
					
					String data = fileTitle;		//for some reason fileTitle gets changed when calling substring below twice
					area.setText(viewArea.getText());													//transfer text into the big text area
					title.setText(data.substring(0,data.length()-24));						//set old date
					date.setText(data.substring(data.length()-24,data.length()-4));		//set old title
				}
			}
		});
		saveEntry.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				click();	//click sound/audio
				
				autoSave();
				
				setUpJW();		//exit back to homepage
			}
		});
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				click();	//click sound/audio
				
				cancelSave();
				
				setUpJW();							//return back to workspace home page
			}
		});
		backToHome.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				click();	//click sound/audio
				isFromHomePage = false;
				setUpJW();
			}
		});
		logOut.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				click();	//click sound/audio
				
				if (isFromHomePage == true) {
					RL.setUpRL("logouthome");		//plays background display from logging out of home page
				}
				else {
					RL.setUpRL("logout");			//plays background display from logging out of edit, search, etc. in workspace
					
					try {
						Files.delete(entryPath);			//delete the current entry since user logs out without saving
						entryPath = null;				//reset it back to null to prevent referencing a non-existent file
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		slider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				MediaView viewer = (MediaView) root.getChildren().get(1);
				MediaPlayer player = viewer.getMediaPlayer();
				player.setVolume((double) newValue/250);
			}
			
		});
		
		title.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				fileTitle = title.getText();		//record the updated title
				
				if (!fileTitle.isEmpty()) {
					if (stage.getTitle().contains("Old Entry") && !root.getChildren().contains(saveEntry)) {		//search for files with the specified text in saved folder
						if (dropDownMenu.getItems().size() != 0) {
							dropDownMenu.getItems().clear();				//clears dropDownMenu items of previous search; MUST be within if statements or editing title of any sort will cause big error at runtime
						}
						ArrayList<String> list = findFile(title.getText(), "saved");
						if (list.size()>0) {
							for (int i = 0; i < list.size(); i++) {
								dropDownMenu.getItems().add(list.get(i));		//add options to drop down
							}
						}
					}
					else if (stage.getTitle().contains("Recovery") && !root.getChildren().contains(recover)) {		//search for files with the specified text in deleted folder
						if (dropDownMenu.getItems().size() != 0) {
							dropDownMenu.getItems().clear();				//clears dropDownMenu items of previous search; MUST be within if statements or editing title of any sort will cause big error at runtime
						}
						ArrayList<String> list = findFile(title.getText(), "deleted");
						if (list.size()>0) {
							for (int i = 0; i < list.size(); i++) {
								dropDownMenu.getItems().add(list.get(i));		//add options to drop down
							}
						}
					}
				}
			}
		});
		
		date.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				fileDate = date.getText();		//record the updated date
			}
		});
	}
	
	/**
	 * Method initializeObjects initializes/creates all the referenced variables for the journal workspace.
	 */
	public void initializeObjects() {
		//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//set up display nodes
		//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//formula calculation: 
		//getWidth() / ((getWidth()-total spacing)/number of buttons) 					for buttons width
		//getWidth() / (total spacing/number of spaces)		
		
		//	1524
		//- 241.9047619047619
		//- 1219.2
		//= 62.8952381				//ratio of 0.04126984127% to root width
		// 10						//ratio of 0.00656167979% to root width
		
		buttonBox = rectangle(0,0,"rect1_1", 217.71428571428572,0,20.0,10.0,null,root.heightProperty().divide(3).multiply(2.55),null,null);
		buttonBoxBorder = rectangle(0,0,"rect3", 217.71428571428572,0,20.0,10.0,null,root.heightProperty().divide(3).multiply(2.55),null,null);
		buttonBox.layoutXProperty().bind(root.widthProperty().divide(30));
		buttonBox.layoutYProperty().bind(root.heightProperty().divide(8));
		buttonBoxBorder.layoutXProperty().bind(root.widthProperty().divide(30));
		buttonBoxBorder.layoutYProperty().bind(root.heightProperty().divide(8));
		
		//resizable width =
		//rootWidth() - (buttonBox width + buttonBox layoutX + 0.00656167979% (rootWidth()) + 0.04% (rootWidth())
		//root.widthProperty().subtract(buttonBox.widthProperty().add(buttonBox.layoutXProperty()).add(root.widthProperty().multiply(0.04783152106)))
		
		//resizable xcoord =
		//buttonBox width + buttonBox layoutX + 0.00656167979% (rootWidth())
		//buttonBox.widthProperty().add(buttonBox.layoutXProperty()).add(root.widthProperty().multiply(0.00656167979));
		
		contentBox = rectangle(0,0,"rect1_1", 0,0,20.0,10.0,root.widthProperty().subtract(buttonBox.widthProperty().add(buttonBox.layoutXProperty()).add(root.widthProperty().multiply(0.04783152106))),root.heightProperty().divide(3).multiply(2.55),null,null);
		contentBox.layoutXProperty().bind(buttonBox.widthProperty().add(buttonBox.layoutXProperty()).add(root.widthProperty().multiply(0.00656167979)));
		contentBox.layoutYProperty().bind(root.heightProperty().divide(8));
		
		titleBar = rectangle(0,0,"rect1_1", 0,0,20.0,10.0,root.widthProperty().divide(2.1),root.heightProperty().divide(18),null,null);
		titleBar.layoutXProperty().bind(root.widthProperty().divide(2.07));
		titleBar.layoutYProperty().bind(root.heightProperty().divide(15));
		
		area = textArea("", contentBox.getWidth(), contentBox.getHeight(), 0, 0, "textfield3");
		area.layoutXProperty().bind(contentBox.layoutXProperty());
		area.layoutYProperty().bind(contentBox.layoutYProperty());
		area.setStyle("-fx-font-size: " + root.getHeight()/37/1.618 + "pt;");
		area.setEditable(false);
		
		title = textField("",0, 0, 0, 0,"textfield3");
		title.layoutXProperty().bind(titleBar.layoutXProperty());
		title.layoutYProperty().bind(titleBar.layoutYProperty());
		title.setPrefSize(titleBar.getWidth()/4*3-(root.getWidth()*0.00656167979/2), titleBar.getHeight());
		title.setStyle("-fx-font-size: " + root.getHeight()/37/1.618 + "pt;");
		title.setEditable(false);
		
		date = textField("",0, 0, 0, 0,"textfield3");
		date.layoutXProperty().bind(titleBar.layoutXProperty().add(titleBar.widthProperty().multiply(0.625).add(root.getWidth()*0.00656167979/2)));
		date.layoutYProperty().bind(titleBar.layoutYProperty());
		date.setPrefSize(titleBar.getWidth()/4-(root.getWidth()*0.00656167979/2), titleBar.getHeight());
		date.setStyle("-fx-font-size: " + root.getHeight()/37/1.618 + "pt;");
		date.setEditable(false);
		
		displayUserDateTime = textField(todayDate,0, 0, 0, 0,"textfield3_1");
		displayUserDateTime.layoutXProperty().bind(root.widthProperty().divide(1.45));
		displayUserDateTime.layoutYProperty().bind(root.heightProperty().divide(100));
		displayUserDateTime.setPrefSize(root.getWidth()/5.5, root.getHeight()/20);
		displayUserDateTime.setEditable(false);
		displayUserDateTime.setAlignment(Pos.CENTER);
		displayUserDateTime.setStyle("-fx-font-size: " + root.getHeight()/37/1.618 + "pt;");
		displayLocalDateTime(displayUserDateTime, "both");
		
		//buttonBox width 			//buttonBox.getWidth()*0.90813648294						//buttonBox.widthProperty().multiply(0.90813648294);
		//button height 			//buttonBox.getHeight()/15									//buttonBox.heightProperty().divide(15);
		// 10						//ratio of 0.04593175853% to buttonBox width				//buttonBox.widthProperty().multiply(0.04593175853);
		// 10						//ratio of 0.0131302521% to buttonBox height				//buttonBox.heightProperty().multiply(0.0131302521);
		
		createNewEntry = button("+ New entry", 0, 0, "button4_3",197.71428571436573,50.77333333333333,0,0,null,null,null,null);
		createNewEntry.layoutXProperty().bind(buttonBox.layoutXProperty().add(buttonBox.widthProperty().multiply(0.04593175853)));
		createNewEntry.layoutYProperty().bind(buttonBox.layoutYProperty().add(buttonBox.heightProperty().multiply(0.0131302521)));
		
		searchOldEntry = button("Search old entry", 0, 0, "button4_3",197.71428571436573,50.77333333333333,0,0,null,null,null,null);
		searchOldEntry.layoutXProperty().bind(buttonBox.layoutXProperty().add(buttonBox.widthProperty().multiply(0.04593175853)));
		searchOldEntry.layoutYProperty().bind(createNewEntry.layoutYProperty().add(createNewEntry.heightProperty()).add(buttonBox.widthProperty().multiply(0.04593175853)));
		
		recoverOldEntry = button("Recover old entry", 0, 0, "button4_3",197.71428571436573,50.77333333333333,0,0,null,null,null,null);
		recoverOldEntry.layoutXProperty().bind(buttonBox.layoutXProperty().add(buttonBox.widthProperty().multiply(0.04593175853)));
		recoverOldEntry.layoutYProperty().bind(searchOldEntry.layoutYProperty().add(searchOldEntry.heightProperty()).add(buttonBox.widthProperty().multiply(0.04593175853)));
		
		logOut = button("Log out", 0, 0, "button4_3",197.71428571436573,50.77333333333333,0,0,null,null,null,null);
		logOut.layoutXProperty().bind(buttonBox.layoutXProperty().add(buttonBox.widthProperty().multiply(0.04593175853)));
		logOut.layoutYProperty().bind(recoverOldEntry.layoutYProperty().add(recoverOldEntry.heightProperty()).add(buttonBox.widthProperty().multiply(0.04593175853)));
		
		recover = button("Recover entry", 0, 0, "button4_3",197.71428571436573,50.77333333333333,0,0,null,null,null,null);
		recover.layoutXProperty().bind(buttonBox.layoutXProperty().add(buttonBox.widthProperty().multiply(0.04593175853)));
		recover.layoutYProperty().bind(logOut.layoutYProperty().add(logOut.heightProperty()).add(buttonBox.widthProperty().multiply(0.04593175853)));
		
		deleteEntry = button("Delete entry", 0, 0, "button4_3",197.71428571436573,50.77333333333333,0,0,null,null,null,null);
		deleteEntry.layoutXProperty().bind(buttonBox.layoutXProperty().add(buttonBox.widthProperty().multiply(0.04593175853)));
		deleteEntry.layoutYProperty().bind(recover.layoutYProperty().add(recover.heightProperty()).add(buttonBox.widthProperty().multiply(0.04593175853)));
		
		editEntry = button("Edit entry", 0, 0, "button4_3",197.71428571436573,50.77333333333333,0,0,null,null,null,null);
		editEntry.layoutXProperty().bind(buttonBox.layoutXProperty().add(buttonBox.widthProperty().multiply(0.04593175853)));
		editEntry.layoutYProperty().bind(deleteEntry.layoutYProperty().add(deleteEntry.heightProperty()).add(buttonBox.widthProperty().multiply(0.04593175853)));
		
		saveEntry = button("Save & exit", 0, 0, "button4_3",197.71428571436573,50.77333333333333,0,0,null,null,null,null);
		saveEntry.layoutXProperty().bind(buttonBox.layoutXProperty().add(buttonBox.widthProperty().multiply(0.04593175853)));
		saveEntry.layoutYProperty().bind(editEntry.layoutYProperty().add(editEntry.heightProperty()).add(buttonBox.widthProperty().multiply(0.04593175853)));
		
		cancel = button("Cancel", 0, 0, "button4_3",197.71428571436573,50.77333333333333,0,0,null,null,null,null);
		cancel.layoutXProperty().bind(buttonBox.layoutXProperty().add(buttonBox.widthProperty().multiply(0.04593175853)));
		cancel.layoutYProperty().bind(saveEntry.layoutYProperty().add(saveEntry.heightProperty()).add(buttonBox.widthProperty().multiply(0.04593175853)));
		
		backToHome = button("Back", 0, 0, "button4_3",197.71428571436573,50.77333333333333,0,0,null,null,null,null);
		backToHome.layoutXProperty().bind(buttonBox.layoutXProperty().add(buttonBox.widthProperty().multiply(0.04593175853)));
		backToHome.layoutYProperty().bind(cancel.layoutYProperty().add(cancel.heightProperty()).add(buttonBox.widthProperty().multiply(0.04593175853)));
		
		dropDownMenu = new ComboBox<String>();
		dropDownMenu.setId("dropdown2");
		dropDownMenu.layoutXProperty().bind(contentBox.layoutXProperty());
		dropDownMenu.layoutYProperty().bind(contentBox.layoutYProperty());
		dropDownMenu.getSelectionModel().select(0);
		dropDownMenu.setVisibleRowCount(18);
		
		viewArea = textArea("", contentBox.getHeight()/2, contentBox.getHeight(), 0,0, "textfield3");
		viewArea.layoutXProperty().bind(dropDownMenu.layoutXProperty().add(dropDownMenu.widthProperty()));
		viewArea.layoutYProperty().bind(contentBox.layoutYProperty());
		viewArea.setStyle("-fx-font-size: " + root.getHeight()/37/1.618 + "pt;");
		viewArea.setEditable(false);
		
		slider = new Slider();
		slider.setId("slider1");
		slider.layoutXProperty().bind(root.widthProperty().multiply(25).divide(1524));
		slider.layoutYProperty().bind(root.heightProperty().multiply(10).divide(895));
		
		
		if (isFromHomePage == true) {
			//fading in nodes
			initializeTransitions(11000);
		}
		else {
			//fading in nodes
			initializeTransitions(2000);
		}
	}

	/**
	 * Method initializeTransitions will set up the fade effect for specified nodes of the journal workspace.
	 * @param duration		time for fade to fill in
	 */
	public void initializeTransitions(double duration) {
		createTransition(Duration.millis(duration), contentBox,0.75);
		createTransition(Duration.millis(duration), buttonBox,0.75);
		createTransition(Duration.millis(duration), buttonBoxBorder,0.75);
		createTransition(Duration.millis(duration), titleBar,0.75);
		
		createTransition(Duration.millis(duration), area,1);
		createTransition(Duration.millis(duration), viewArea,1);
		createTransition(Duration.millis(duration), title,1);
		createTransition(Duration.millis(duration), date,1);
		createTransition(Duration.millis(duration), displayUserDateTime,1);
		
		createTransition(Duration.millis(duration), dropDownMenu,1);
		
		createTransition(Duration.millis(duration), slider,1);
		
		createTransition(Duration.millis(duration), createNewEntry,1);
		createTransition(Duration.millis(duration), searchOldEntry,1);
		createTransition(Duration.millis(duration), recoverOldEntry,1);
		createTransition(Duration.millis(duration), recover,1);
		createTransition(Duration.millis(duration), deleteEntry,1);
		createTransition(Duration.millis(duration), editEntry,1);
		createTransition(Duration.millis(duration), saveEntry,1);
		createTransition(Duration.millis(duration), cancel,1);
		createTransition(Duration.millis(duration), logOut,1);
		createTransition(Duration.millis(duration), backToHome,1);
	}
	
	/**
	 * Method setUpJW sets up the main workspace/home page after successfully signing in.
	 * JW = Journal Workspace (acronym)
	 */
	public void setUpJW() {
		stage.setTitle("Digi-Diary Workspace | Homepage");			//rename the title bar
		todayDate = setDateTime(); 		//gets current date of user editing text
		
		if (isFromHomePage == true) {
			resetPage("src/background display/WORKSPACE.mp4");
		}
		else if (isFromHomePage == false) {
			resetPage("src/background display/LEAVEEDIT.mp4");
			initializeObjects();
			bindObjectValues();
			setObjectListeners();
			isFromHomePage = true;
		}
		
		
		//add nodes
		add(buttonBox);
		add(buttonBoxBorder);
		add(contentBox);
		add(titleBar);
		add(displayUserDateTime);
		add(slider);
		add(area);
		add(title);
		add(date);		//make sure date is last object added before buttons so that the menu stays consistent when adding or deleting buttons
		
		add(createNewEntry);
		add(searchOldEntry);
		add(recoverOldEntry);
		add(logOut);
		
		
	}
	
	/**
	 * Method createNewEntry will load up the workspace into a new draft for user to be able to edit.
	 */
	public void createNewEntry() {
		stage.setTitle("Digi-Diary Workspace | New Entry");			//rename the title bar
		if (isFromHomePage == true) {
			resetPage("src/background display/EDIT.mp4");
			isFromHomePage = false;
		}
		else {
			resetPage("src/background display/SWITCHWORKPAGE.mp4");
		}
		
		
		//fade in nodes
		initializeTransitions(2000);
		
		
		add(buttonBox);
		add(buttonBoxBorder);
		add(contentBox);	
		add(titleBar);
		add(displayUserDateTime);
		add(area);
		add(title);
		add(date);	date.setText(todayDate);	//set the date when new entry
		
		add(editEntry);
		add(cancel);
		
		
		//setting the layout/order of the buttons menu
		editEntry.layoutXProperty().bind(buttonBox.layoutXProperty().add(buttonBox.widthProperty().multiply(0.04593175853)));
		editEntry.layoutYProperty().bind(buttonBox.layoutYProperty().add(buttonBox.heightProperty().multiply(0.0131302521)));
		
		cancel.layoutXProperty().bind(buttonBox.layoutXProperty().add(buttonBox.widthProperty().multiply(0.04593175853)));
		cancel.layoutYProperty().bind(editEntry.layoutYProperty().add(editEntry.heightProperty()).add(buttonBox.widthProperty().multiply(0.04593175853)));
	}
	
	/**
	 * Method searchOldEntry will make a list of all the past entries under user's search specification
	 * then sets the document into a viewing mode.
	 */
	public void searchOldEntry() {
		stage.setTitle("Digi-Diary Workspace | Old Entry");			//rename the title bar
		if (isFromHomePage == true) {
			resetPage("src/background display/EDIT.mp4");
			isFromHomePage = false;
		}
		else {
			resetPage("src/background display/SWITCHWORKPAGE.mp4");
		}
		title.setEditable(true);		//allows search feature for user
		
		//fade in nodes
		initializeTransitions(2000);
		
		
		add(buttonBox);
		add(buttonBoxBorder);
	    add(contentBox);
	    add(titleBar);
	    add(displayUserDateTime);
		add(area);
		add(viewArea);
		add(dropDownMenu);
		add(title);
		add(date);
		
		add(backToHome);
		
		
		//setting the layout/order of the buttons menu
		backToHome.layoutXProperty().bind(buttonBox.layoutXProperty().add(buttonBox.widthProperty().multiply(0.04593175853)));
		backToHome.layoutYProperty().bind(buttonBox.layoutYProperty().add(buttonBox.heightProperty().multiply(0.0131302521)));
	}
	
	/**
	 * Method recoverOldEntry will make a list of all the deleted entries under user's search specification.
	 * (this is LIMITED to whatever has been deleted by the user during the time the user launched, deleted, and closed the program
	 * once closed, deleted files become truly deleted forever)
	 */
	public void recoverOldEntry() {
		stage.setTitle("Digi-Diary Workspace | Recovery");			//rename the title bar
		if (isFromHomePage == true) {
			resetPage("src/background display/EDIT.mp4");
			isFromHomePage = false;
		}
		else {
			resetPage("src/background display/SWITCHWORKPAGE.mp4");
		}
		title.setEditable(true);		//allows search feature for user
		
		//fade in nodes
		initializeTransitions(2000);
		
		
		add(buttonBox);
		add(buttonBoxBorder);
	    add(contentBox);
	    add(titleBar);
	    add(displayUserDateTime);
		add(area);
		add(viewArea);
		add(dropDownMenu);
		add(title);
		add(date);
		
		add(backToHome);
		
		
		//setting the layout/order of the buttons menu
		backToHome.layoutXProperty().bind(buttonBox.layoutXProperty().add(buttonBox.widthProperty().multiply(0.04593175853)));
		backToHome.layoutYProperty().bind(buttonBox.layoutYProperty().add(buttonBox.heightProperty().multiply(0.0131302521)));
	}
	
	/**
	 * Method deleteEntry brings user to the confirmation page on confirm on deleting selected entry as well as give user the ability to cancel.
	 * (If user exits the application while haven't confirmed, this old entry will not deleted/it will still be saved)
	 */
	public void deleteEntry() {
		deleteEntry.setText("Confirm delete");
		deleteEntry.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				click();	//click sound/audio
				
				File file = new File(entryPath.toString());
				String data = entryPath.toString();
				data = data.substring(22,data.length());
				file.renameTo(new File("src/fileDB/deletedFiles/" + data));			//move to deleted files to allow for chance of recovery. All deleted entires will be permanantly deleted when user closes application.
				
				addLine(deletedFile, data);	//add the entry within the deleted folder
				deleteLine(savedFile, data);	//delete the entry within saved folder
				
				setUpJW();
			}
		});
		
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				click();	//click sound/audio
				
				try {
					FileWriter fw = new FileWriter(file);
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter pw = new PrintWriter(bw);
					
					clearFile(file);
					pw.println(area.getText());
					
					pw.close();
					bw.close();
					fw.close();
					
				}catch(IOException e) {e.printStackTrace();}
				
				setUpJW();
			}
		});
	}
	
	/**
	 * Method createTransition will make the specified node fade in during a select amount of time.
	 * @param duration		time for fade to fill in
	 * @param node			node to fade in
	 * @param opacity		opacity for fade to fill to
	 */
	public void createTransition(Duration duration, Node node, double opacity){
		FadeTransition fade = new FadeTransition(duration, node);
		fade.setFromValue(0.0);
		fade.setToValue(opacity);
		fade.play();
	}
	
	/**
	 * Method addLine adds the specified text within the specified file.
	 * @param file			the file to be modified
	 * @param text			the text to be deleted from specified file
	 */
	public void addLine(File file, String text) {
		try {									//re-add everything into the file except the excluded line of text
			FileWriter fw = new FileWriter(file, true);			//need to add statement true to append or else it will just overwrite entire file's content
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			
			pw.println(text);
			
			pw.close();
			bw.close();
			fw.close();
		}catch(IOException e) {e.printStackTrace();}
	}
	
	/**
	 * Method deleteLine deletes the specified text within the specified file.
	 * @param file			the file to be modified
	 * @param text			the text to be deleted from specified file
	 */
	public void deleteLine(File file, String text) {
		ArrayList<String> arr = new ArrayList<>();
		try {									//create an arraylist of the lines want added with the text being excluded
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String str = br.readLine();
			
			while (str != null) {
				if (!str.equals(text)) {		//skips adding the name that matches text parameter
					arr.add(str);
				}
				str = br.readLine();
			}
			
			br.close();
			fr.close();
		}catch(IOException e) {e.printStackTrace();}
		
		clearFile(file);		//erase everything in the file
		
		try {									//re-add everything into the file except the excluded line of text
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			
			for (int i = 0; i < arr.size(); i++) {
				pw.println(arr.get(i));
			}
			
			pw.close();
			bw.close();
			fw.close();
		}catch(IOException e) {e.printStackTrace();}
	}
	
	/**
	 * Method clearFolder is a helper method solely to erase all files in the deleted directory only.
	 * @param directory
	 */
	public void clearFolder() {
		ArrayList<String> arr = new ArrayList<>();
		try {									//create an arraylist of the lines want added with the text being excluded
			FileReader fr = new FileReader(deletedFile);
			BufferedReader br = new BufferedReader(fr);
			String str = br.readLine();
			
			while (str != null) {
				arr.add(str);
				str = br.readLine();
			}
			
			br.close();
			fr.close();
		}catch(IOException e) {e.printStackTrace();}
		
		for (int i = 0; i < arr.size(); i++) {
			File file = new File("src/fileDB/deletedFiles/" + arr.get(i));
			file.delete();
		}
	}
	
	/**
	 * Method autoSave will directly save the contents of the entry into a file txt and can be applied once user clicks on closing the application.
	 */
	public void autoSave() {
		String data = entryPath.toString();
		data = data.substring(22, data.length());		//just the txt file name
		
		File folderFile = new File("src/fileDB/savedFiles/" + data);		//if not auto save then delete default titleless draft
		folderFile.delete();
		deleteLine(savedFile, data);										//also delete it from savedFile txt
		
		
		if (date.getText().length() != 20 || !checkValidDate(date.getText())) {		//if user doesn't put in valid date just replace with current date of editing
			todayDate = setDateTime();
		}
		data = data.replace(data.substring(data.length()-24, data.length()-4), todayDate);	//set date/time
		if (data.length()-24 == 0) {															//add the new title
			data = title.getText() + data;
		}
		else {
			data = data.replace(data.substring(0, data.length()-24),title.getText());
		}
		
		file = new File("src/fileDB/savedFiles/" + data);					//create the new updated file with name/date
		addLine(savedFile, data);											//also add it to savedFile txt
		
		try {												//add content to file
			FileWriter fw = new FileWriter(file);			//file = "something.txt"
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			
			clearFile(file);
			pw.println(area.getText());
			
			pw.close();
			bw.close();
			fw.close();
			
		}catch(IOException e) {e.printStackTrace();}
	}
	
	/**
	 * Method setDateTime will get user's current date and time.
	 * @return str		the time + date in a string
	 */
	public String setDateTime() {
		String date = LocalDate.now().toString();
		String time = LocalTime.now().toString();
		time = "[" + time.substring(0,2) + "." + time.substring(3,5) + "." + time.substring(6,8) + "]";
		String str = time + date;
		return str;
	}
	
	/**
	 * Method checkValidDate checks with the date that the user input is valid or not.
	 * @param string	string to back checked
	 * @return tF		true = good to go, false = replace date with current date
	 */
	public boolean checkValidDate(String string) {
		boolean tF = true;
		String validChar = "[].-1234567890";
		for (int i = 0; i < string.length(); i++) {
			if (!validChar.contains(string.substring(i,i+1))){		//check if all characters are valid or not one character at a time
				tF = false;
				i = string.length();			//break loop if found false character
			}
		}
		if (tF == true) {			//check to make sure there are exactly 14 numbers
			validChar = string;
			validChar = validChar.replace("[","");
			validChar = validChar.replace("]","");
			validChar = validChar.replace("-","");
			validChar = validChar.replace(".","");
			if (validChar.length() != 14) {
				tF = false;
			}
		}
		return tF;
	}
	
	/**
	 * Method cancelSave will save the draft that user cancelled on in case they want to recover it during the session; otherwise, it will be autodeleted.
	 */
	public void cancelSave() {
		File file = new File(entryPath.toString());
		deleteLine(savedFile, entryPath.toString().substring(22, entryPath.toString().length()));	//delete the entry within saved folder
		
		if (date.getText().length() != 20 || !checkValidDate(date.getText())) {		//if current date by user is not 20 length or contains invalid characters then set to current date/time
			todayDate = setDateTime();
		}
		fileTitle = title.getText() + entryPath.toString().substring(22,entryPath.toString().length()-24) + todayDate + ".txt";
		try {												//add content to file
			FileWriter fw = new FileWriter(file);			//file = "something.txt"
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			
			clearFile(file);
			pw.println(area.getText());
			
			pw.close();
			bw.close();
			fw.close();
			
		}catch(IOException e) {e.printStackTrace();}
		
		file.renameTo(new File("src/fileDB/deletedFiles/"  + fileTitle));			//move to deleted files to allow for chance of recovery. All deleted entires will be permanantly deleted when user closes application.

		addLine(deletedFile, fileTitle);	//add the entry within the deleted folder
	}
}
