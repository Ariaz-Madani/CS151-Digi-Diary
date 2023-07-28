package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * Class JournalWorkspace contains methods that will be called to edit the GUI with features regarding the journal workspace.
 */
public class JournalWorkspace implements ProgramMethodInt, FileMethodInt{
	
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
	
	//date picker
	Rectangle pickDateTime;
	Rectangle pickDateTimeBorder;
	DatePicker datePicker;
	ComboBox<String> hours;
	ComboBox<String> minutes;
	ComboBox<String> seconds;
	
	//data holders
	Path entryPath;		//key ingredient to navigating changing files (path will always generate the updated title, date, content, and location.
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
		hours.setPrefSize(pickDateTime.getWidth()*(0.2732808399), titleBar.getHeight());
		hours.setStyle("-fx-font-size: " + root.getHeight()/37/1.618 + "pt;");
		minutes.setPrefSize(pickDateTime.getWidth()*(0.2732808399), titleBar.getHeight());
		minutes.setStyle("-fx-font-size: " + root.getHeight()/37/1.618 + "pt;");
		seconds.setPrefSize(pickDateTime.getWidth()*(0.2732808399), titleBar.getHeight());
		seconds.setStyle("-fx-font-size: " + root.getHeight()/37/1.618 + "pt;");
		datePicker.setPrefSize(pickDateTime.getWidth()*(0.9249343832), pickDateTime.getHeight()*(0.6360454945)/6);
		datePicker.setStyle("-fx-font-size: " + root.getHeight()/53/1.618 + "pt;");
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
		stage.fullScreenProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				bindObjectValues();
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
					if (date.getId().equals("textfield3_2")) {
						toggleDateTimePicker(true);
					}
					else {
						toggleDateTimePicker(false);
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
					if (date.getId().equals("textfield3_2")) {
						toggleDateTimePicker(true);
					}
					else {
						toggleDateTimePicker(false);
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
					fileTitle = fileTitle.substring(0,fileTitle.length()-25) + fileTitle.substring(fileTitle.length()-20,fileTitle.length());
					entryPath = Paths.get("src/fileDB/savedFiles/" + fileTitle + ".txt");	//gets the path to that file
					file = new File(entryPath.toString());							//make a new file object that links to the content of selected file path
					fileContent = getFileContent(file);								//get the file content
					
					viewArea.setText(fileContent);					//display the file's content
				}
				else if (stage.getTitle().contains("Recovery") && dropDownMenu.getValue()!=null) {		//if user is using drop down menu on recovery page
					fileTitle = dropDownMenu.getValue();							//gets the selected file name
					fileTitle = fileTitle.substring(0,fileTitle.length()-25) + fileTitle.substring(fileTitle.length()-20,fileTitle.length());
					entryPath = Paths.get("src/fileDB/deletedFiles/" + fileTitle + ".txt");	//gets the path to that file
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
				//there is already an auto-save system in place for NEW ENTRIES ONLY that aren't saved
				//there could also be an auto save for editing old files by adding a listener but that would defeat the purpose of having a save button...
				if (stage.getTitle().contains("New Entry")) {
					autoSave(entryPath, title, fileDate, file, area);
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
				data = data.substring(0,data.length()-25) + data.substring(data.length()-20,data.length()) + ".txt";
				
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
				
				
				if (stage.getTitle().contains("Old Entry")) {
					
					viewArea.setVisible(false);
					dropDownMenu.setVisible(false);
					editEntry.setVisible(false);
					
					
					String data = fileTitle;		//for some reason fileTitle gets changed when calling substring below twice
					area.setText(viewArea.getText());													//transfer text into the big text area
					title.setText(data.substring(0,data.length()-20));						//set old title
					date.setText(data.substring(data.length()-20,data.length()));		//set old date
				}
			}
		});
		saveEntry.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				click();	//click sound/audio
				
				autoSave(entryPath, title, fileDate, file, area);
				
				setUpJW();		//exit back to homepage
			}
		});
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				click();	//click sound/audio
				
				cancelSave(entryPath, fileTitle, title, todayDate, area);
				
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
						addFilesToMenu(dropDownMenu, list);
					}
					else if (stage.getTitle().contains("Recovery") && !root.getChildren().contains(recover)) {		//search for files with the specified text in deleted folder
						if (dropDownMenu.getItems().size() != 0) {
							dropDownMenu.getItems().clear();				//clears dropDownMenu items of previous search; MUST be within if statements or editing title of any sort will cause big error at runtime
						}
						ArrayList<String> list = findFile(title.getText(), "deleted");
						addFilesToMenu(dropDownMenu, list);
					}
				}
				else if (fileTitle.isEmpty()) {
					if (stage.getTitle().contains("Old Entry") && !root.getChildren().contains(saveEntry)) {		//search for files with the specified text in saved folder
						if (dropDownMenu.getItems().size() != 0) {
							dropDownMenu.getItems().clear();				//clears dropDownMenu items of previous search; MUST be within if statements or editing title of any sort will cause big error at runtime
						}
						ArrayList<String> list = findFile(".", "saved");
						addFilesToMenu(dropDownMenu, list);
					}
					else if (stage.getTitle().contains("Recovery") && !root.getChildren().contains(recover)) {		//search for files with the specified text in deleted folder
						if (dropDownMenu.getItems().size() != 0) {
							dropDownMenu.getItems().clear();				//clears dropDownMenu items of previous search; MUST be within if statements or editing title of any sort will cause big error at runtime
						}
						ArrayList<String> list = findFile(".", "deleted");
						addFilesToMenu(dropDownMenu, list);
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
		date.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (!root.getChildren().contains(pickDateTime) && !stage.getTitle().contains("Homepage") && !date.getText().isEmpty()) {
					toggleDateTimePicker(true);
				}
				else if (root.getChildren().contains(pickDateTime)) {
					toggleDateTimePicker(false);
				}
			}
		});
		hours.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String str = date.getText().substring(0,1) + newValue + date.getText().substring(3,date.getText().length());
				date.setText(str);
			}
		});
		minutes.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String str = date.getText().substring(0,4) + newValue + date.getText().substring(6,date.getText().length());
				date.setText(str);
			}
		});
		seconds.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String str = date.getText().substring(0,7) + newValue + date.getText().substring(9,date.getText().length());
				date.setText(str);
			}
		});
		hours.showingProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue)
					datePicker.setVisible(false);
				else
					datePicker.setVisible(true);
			}
		});
		minutes.showingProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue)
					datePicker.setVisible(false);
				else
					datePicker.setVisible(true);
			}
		});
		seconds.showingProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue)
					datePicker.setVisible(false);
				else
					datePicker.setVisible(true);
			}
		});
		
		datePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
			String str = date.getText();
			String newDate = datePicker.getValue().toString();		//this already comes in the format wanted yyyy-MM-dd
			str = str.substring(0,str.length()-10) + newDate;
			date.setText(str);
        });
	}
	
	/**
	 * Method initializeObjects initializes/creates all the referenced variables for the journal workspace.
	 */
	public void initializeObjects() {
		//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//set up display nodes
		//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		buttonBox = rectangle(0,0,"rect1_1", 217.71428571428572,0,20.0,10.0,null,root.heightProperty().divide(3).multiply(2.55),null,null);
		buttonBoxBorder = rectangle(0,0,"rect3", 217.71428571428572,0,20.0,10.0,null,root.heightProperty().divide(3).multiply(2.55),null,null);
		buttonBox.layoutXProperty().bind(root.widthProperty().divide(30));
		buttonBox.layoutYProperty().bind(root.heightProperty().divide(8));
		buttonBoxBorder.layoutXProperty().bind(root.widthProperty().divide(30));
		buttonBoxBorder.layoutYProperty().bind(root.heightProperty().divide(8));
		
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
		date.setEditable(false);			//will ALWAYS be false but user will pick time from a pop up
		
		displayUserDateTime = textField(todayDate,0, 0, 0, 0,"textfield3_1");
		displayUserDateTime.layoutXProperty().bind(root.widthProperty().divide(1.45));
		displayUserDateTime.layoutYProperty().bind(root.heightProperty().divide(100));
		displayUserDateTime.setPrefSize(root.getWidth()/5.5, root.getHeight()/20);
		displayUserDateTime.setEditable(false);
		displayUserDateTime.setAlignment(Pos.CENTER);
		displayUserDateTime.setStyle("-fx-font-size: " + root.getHeight()/37/1.618 + "pt;");
		displayLocalDateTime(displayUserDateTime, "both");
		
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
		//-----------------------------------------------------------------------------------------------------------------------------------------------------
		
		//-----------------------------------------------------------------------------------------------------------------------------------------------------
		//date-time picker
		//-----------------------------------------------------------------------------------------------------------------------------------------------------
		//266.4335664 - 60 margin /3 = 68.8111888 per field
		//ratio 0.2582677165 per field;
		//ratio 0.05629921261 per gap;
		//266.4335664 / 5 and /5*3 = height  =  53.28671328 && 159.8601398
		//pickDateTime.widthProperty().divide(5)  &&  pickDateTime.widthProperty().divide(5).multiply(3)
		//10 / 217.7142857 = 0.04593175853
		//69.23809523 / 217.7142857 = 0.3180227471
		//138.4761905 / 217.7142857 = 0.6360454945
		//59.23809523 / 217.7142857 = 0.2720909886
		pickDateTime = rectangle(0,0,"rect1_11", 217.71428571428572,0,20.0,10.0,root.widthProperty().divide(5.72),root.heightProperty().divide(2),null,null);
		pickDateTimeBorder = rectangle(0,0,"rect3_1", 217.71428571428572,0,20.0,10.0,root.widthProperty().divide(5.72),root.heightProperty().divide(2),null,null);
		pickDateTime.layoutXProperty().bind(titleBar.layoutXProperty().add(titleBar.widthProperty().multiply(0.625).add(root.getWidth()*0.00656167979/2)));
		pickDateTime.layoutYProperty().bind(root.heightProperty().divide(8));
		pickDateTimeBorder.layoutXProperty().bind(titleBar.layoutXProperty().add(titleBar.widthProperty().multiply(0.625).add(root.getWidth()*0.00656167979/2)));
		pickDateTimeBorder.layoutYProperty().bind(root.heightProperty().divide(8));
		
		hours = new ComboBox<>();
		hours.setId("dropdown2");
		addMenu(hours, 23,5);
		hours.layoutXProperty().bind(pickDateTime.layoutXProperty().add(pickDateTime.widthProperty().multiply(0.04503937008)));
		hours.layoutYProperty().bind(pickDateTime.layoutYProperty().add(pickDateTime.heightProperty().multiply(0.04593175853)));
		hours.setPrefSize(pickDateTime.getWidth()*(0.2732808399), pickDateTime.getHeight()*(0.2720909886));
		hours.setStyle("-fx-font-size: " + root.getHeight()/37/1.618 + "pt;");
		
		minutes = new ComboBox<>();
		minutes.setId("dropdown2");
		addMenu(minutes, 59,5);
		minutes.layoutXProperty().bind(pickDateTime.widthProperty().multiply(0.04503937008).add(hours.layoutXProperty()).add(hours.widthProperty()));
		minutes.layoutYProperty().bind(pickDateTime.layoutYProperty().add(pickDateTime.heightProperty().multiply(0.04593175853)));
		minutes.setPrefSize(pickDateTime.getWidth()*(0.2732808399), pickDateTime.getHeight()*(0.2720909886));
		minutes.setStyle("-fx-font-size: " + root.getHeight()/37/1.618 + "pt;");
		
		seconds = new ComboBox<>();
		seconds.setId("dropdown2");
		addMenu(seconds, 59,5);
		seconds.layoutXProperty().bind(pickDateTime.widthProperty().multiply(0.04503937008).add(minutes.layoutXProperty()).add(minutes.widthProperty()));
		seconds.layoutYProperty().bind(pickDateTime.layoutYProperty().add(pickDateTime.heightProperty().multiply(0.04593175853)));
		seconds.setPrefSize(pickDateTime.getWidth()*(0.2732808399), pickDateTime.getHeight()*(0.2720909886));
		seconds.setStyle("-fx-font-size: " + root.getHeight()/37/1.618 + "pt;");
		
		datePicker = new DatePicker();
		datePicker.layoutXProperty().bind(pickDateTime.layoutXProperty().add(pickDateTime.widthProperty().multiply(0.04593175853)));
		datePicker.layoutYProperty().bind(hours.layoutYProperty().add(hours.heightProperty()).add(pickDateTime.heightProperty().multiply(0.04593175853)));
		datePicker.setPrefSize(pickDateTime.getWidth()*(0.9249343832), pickDateTime.getHeight()*(0.6360454945)/6);
		datePicker.setEditable(false);		//user cannot change the textfield date only select from calendar to prevent any false dates chosen
		datePicker.setId("datepicker1");
		//-----------------------------------------------------------------------------------------------------------------------------------------------------
		//set transitions
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
		
		add(saveEntry);
		add(cancel);
		
		
		//set areas editable
		area.setEditable(true);												//makes the 3 fields (content, title, and date all able to be edited and worked on by user
		title.setEditable(true);
		
		//setting the layout/order of the buttons menu
		saveEntry.layoutXProperty().bind(buttonBox.layoutXProperty().add(buttonBox.widthProperty().multiply(0.04593175853)));
		saveEntry.layoutYProperty().bind(buttonBox.layoutYProperty().add(buttonBox.heightProperty().multiply(0.0131302521)));
		
		cancel.layoutXProperty().bind(buttonBox.layoutXProperty().add(buttonBox.widthProperty().multiply(0.04593175853)));
		cancel.layoutYProperty().bind(saveEntry.layoutYProperty().add(saveEntry.heightProperty()).add(buttonBox.widthProperty().multiply(0.04593175853)));
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
		
		//set up dropDownMenu
		ArrayList<String> list = findFile(".", "saved");
		addFilesToMenu(dropDownMenu, list);
		
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
		
		//set up dropDownMenu
		ArrayList<String> list = findFile(".", "deleted");
		addFilesToMenu(dropDownMenu, list);
		
		
		
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
	 * Method toggleDateTimePicker will display nodes for user to enter in date and time manually.
	 * @param wantToDisplay	t = show picker; f = don't show picker
	 */
	public void toggleDateTimePicker(boolean wantToDisplay) {
		if (wantToDisplay == true) {
			
			Label semi = label(":",0,0,"label2",0,0,550,100,null,null,null,null);
			semi.styleProperty().bind(hours.styleProperty());
			semi.layoutXProperty().bind(hours.layoutXProperty().add(hours.widthProperty().multiply(1.04)));
			semi.layoutYProperty().bind(hours.layoutYProperty().add(hours.heightProperty().divide(4)));
			
			Label semi2 = label(":",0,0,"label2",0,0,550,100,null,null,null,null);
			semi2.styleProperty().bind(hours.styleProperty());
			semi2.layoutXProperty().bind(minutes.layoutXProperty().add(minutes.widthProperty().multiply(1.04)));
			semi2.layoutYProperty().bind(hours.layoutYProperty().add(hours.heightProperty().divide(4)));
			
			createTransition(Duration.millis(1000), pickDateTime,1);
			createTransition(Duration.millis(1000), pickDateTimeBorder,1);
			createTransition(Duration.millis(1000), hours,1);
			createTransition(Duration.millis(1000), minutes,1);
			createTransition(Duration.millis(1000), seconds,1);
			createTransition(Duration.millis(1000), datePicker,1);
			createTransition(Duration.millis(1000), semi,1);
			createTransition(Duration.millis(1000), semi2,1);
			
			add(pickDateTime);
			add(pickDateTimeBorder);
			add(hours);
			add(minutes);
			add(seconds);
			add(datePicker);
			add(semi);
			add(semi2);
			
			date.setId("textfield3_2");
		}
		else if (wantToDisplay == false) {
			
			root.getChildren().remove(pickDateTime);
			root.getChildren().remove(pickDateTimeBorder);
			root.getChildren().remove(hours);
			root.getChildren().remove(minutes);
			root.getChildren().remove(seconds);
			root.getChildren().remove(datePicker);
			root.getChildren().remove(root.getChildren().size()-1);
			root.getChildren().remove(root.getChildren().size()-1);
			
			date.setId("textfield3");
		}
	}
	
}
