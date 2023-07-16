package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public interface ProgramMethodInt extends ProgramValueInt{
	
	/**
	 * Method makes creating a rectangle object nice and clean (code wise)
	 * @param xCoord			x location on screen
	 * @param yCoord			y location on screen
	 * @param ID				any preset css customize format style want added
	 * @param width				width of rectangle
	 * @param height			height of rectangle
	 * @param arcWidth			the curve serverity of rectangle's corners T and B
	 * @param arcHeight			the curve severity of rectangle's corners L and R side
	 * @param x					width component that rectangle's width will be binded to
	 * @param y					height component that rectangle's height will be binded to
	 * @return					rectangle you customized
	 */
	public default Rectangle rectangle(double xCoord, double yCoord, String ID, double width, double height, double arcWidth, double arcHeight
			, ObservableValue<? extends Number> x, ObservableValue<? extends Number> y
			, ObservableValue<? extends Number> xTran, ObservableValue<? extends Number> yTran) {
		Rectangle rectangle = new Rectangle();
		rectangle.setLayoutX(xCoord);
		rectangle.setLayoutY(yCoord);
		rectangle.setId(ID);
		rectangle.setWidth(width);
		rectangle.setHeight(height);
		rectangle.setArcWidth(arcWidth);
		rectangle.setArcHeight(arcHeight);
		if (x!=null) rectangle.widthProperty().bind(x);
		if (y!=null) rectangle.heightProperty().bind(y);
		if (xTran!=null) rectangle.translateXProperty().bind(xTran);
		if (yTran!=null) rectangle.translateYProperty().bind(yTran);
		return rectangle;
	}

	
	/**
	 * Method resetPage will set up each page, remove past content on stage and display specified background (make code clean)
	 * @param name			name of display video
	 */
	public default void resetPage(String name) {
		root.getChildren().remove(2,root.getChildren().size());
		container.getChildren().remove(0, container.getChildren().size());
		if (!name.isEmpty()) {
			//setting background image
			if (!root.getChildren().isEmpty()) {
				root.getChildren().remove(0);
			}
			File file = new File(name);	//VIDEO MUST BE AT OR ABOVE stage scene size or it won't add for some reason
			Media media = new Media(file.toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(media);
			mediaPlayer.play();
			MediaView mediaView = new MediaView(mediaPlayer);
			mediaView.setOpacity(1);
			mediaView.setPreserveRatio(false);	//MUST SET FALSE or it gonna look dumb asf when user resizes it without bounded apsect ratio
			mediaView.fitWidthProperty().bind(root.widthProperty());
			mediaView.fitHeightProperty().bind(root.heightProperty());
			root.getChildren().add(0,mediaView);
		}
		setUpVBox(container, 25, Pos.CENTER, 0,0,0,0,root.widthProperty(),root.heightProperty());
	}
	

	/**
	 * Method makes creating a mediaplayer object nice and clean (code wise)
	 * @param file				audio file to be added
	 * @param volume			preset volume to be played at
	 * @return					the customized mediaplayer
	 */
 	public default MediaPlayer mediaPlayer(String file, Double volume) {
		MediaPlayer mp = new MediaPlayer(new Media(new File(file).toURI().toString()));
		mp.setVolume(volume);
		return mp;
	}
 	
 	/**
 	 * Method click() makes click sound for buttons
 	 */
 	public default void click() {
 		MediaPlayer click = mediaPlayer("src/music/click.mp3", 0.25);	//click sound/audio
		click.play();
 	}
	
	/**
	 * Method makes creating a textField object nice and clean (code wise)
	 * @param name				field name
	 * @param minWidth			minimum width
	 * @param minHeight			minimum height
	 * @param maxWidth			maximum width
	 * @param maxHeight			maximum height
	 * @param ID				any preset css customize format style want added
	 * @return					the customized textfield
	 */
	public default TextField textField(String name, double minWidth, double minHeight, double maxWidth, double maxHeight, String ID) {
		TextField textField = new TextField(name);
		if (minWidth != 0)
			textField.setMinWidth(minWidth);
		if (minHeight != 0)
			textField.setMinHeight(minHeight);	
		if (maxWidth != 0)
			textField.setMaxWidth(maxWidth);
		if (maxHeight != 0)
			textField.setMaxHeight(maxHeight);
		textField.setId(ID);
		return textField;
	}
	
	/**
	 * Method makes creating a textArea object nice and clean (code wise)
	 * @param name				field name
	 * @param minWidth			minimum width
	 * @param minHeight			minimum height
	 * @param maxWidth			maximum width
	 * @param maxHeight			maximum height
	 * @param ID				any preset css customize format style want added
	 * @return					the customized textArea
	 */
	public default TextArea textArea(String name, int minWidth, int minHeight, int maxWidth, int maxHeight, String ID) {
		TextArea textArea = new TextArea(name);
		if (minWidth != 0)
			textArea.setMinWidth(minWidth);
		if (minHeight != 0)
			textArea.setMinHeight(minHeight);	
		if (maxWidth != 0)
			textArea.setMaxWidth(maxWidth);
		if (maxHeight != 0)
			textArea.setMaxHeight(maxHeight);
		textArea.setId(ID);
		return textArea;
	}
	
	/**
	 * Method makes creating a passwordfield object nice and clean (code wise)
	 * @param minWidth			minimum width
	 * @param minHeight			minimum height
	 * @param maxWidth			maximum width
	 * @param maxHeight			maximum height
	 * @param ID				any preset css customize format style want added
	 * @return					the customized passwordfield
	 */
	public default PasswordField passwordField(String name,int minWidth, int minHeight, int maxWidth, int maxHeight, String ID) {
		PasswordField passwordField = new PasswordField();
		passwordField.setText(name);
		if (minWidth != 0)
			passwordField.setMinWidth(minWidth);
		if (minHeight != 0)
			passwordField.setMinHeight(minHeight);	
		if (maxWidth != 0)
			passwordField.setMaxWidth(maxWidth);
		if (maxHeight != 0)
			passwordField.setMaxHeight(maxHeight);
		passwordField.setId(ID);
		return passwordField;
	}
	
	/**
	 * Method makes creating a button object nice and clean (code wise)
	 * @param name				name of button
	 * @param xCoord			x location of button
	 * @param yCoord			y location of button
	 * @param ID				any preset css customize format style want added
	 * @param minWidth			minimum width a button can be
	 * @param minHeight			minimum height a button can be
	 * @param maxWidth			maximum width a button can be
	 * @param maxHeight			maximum height a button can be
	 * @param xScale			bind amount go resize button in x
	 * @param yScale			bind amount to resize button in y
	 * @param xTran				bind amount to be translated in x
	 * @param yTran				bind amount to be translated in y
	 * @return					the customized button
	 */
	public default Button button(String name, int xCoord, int yCoord, String ID
			, double minWidth, double minHeight, double maxWidth, double maxHeight 
			,ObservableValue<? extends Number> xScale, ObservableValue<? extends Number> yScale
			,ObservableValue<? extends Number> xTran, ObservableValue<? extends Number> yTran) {
		Button button = new Button(name);
		button.setWrapText(true);
		button.setLayoutX(xCoord);
		button.setLayoutY(yCoord);
		//setId() will link button to css file button style by name
		button.setId(ID);
		if (minWidth != 0) 
			button.setMinWidth(minWidth);
		if (minHeight != 0) 
			button.setMinHeight(minHeight);
		if (maxWidth != 0) 
			button.setMaxWidth(maxWidth);
		if (maxHeight != 0) 
			button.setMaxHeight(maxHeight);
		if (xScale != null) 
			button.scaleXProperty().bind(xScale);
		if (yScale != null)
			button.scaleYProperty().bind(yScale);
		if (xTran != null)
			button.translateXProperty().bind(xTran);
		if (yTran != null)
			button.translateYProperty().bind(yTran);
		return button;
	}
	
	/**
	 * Method makes creating a label object nice and clean (code wise)
	 * @param name				name of label
	 * @param xCoord			x location
	 * @param yCoord			y location
	 * @param ID				any preset css customize format style want added
	 * @param minWidth			minimum width
	 * @param minHeight			minimum height
	 * @param maxWidth			maximum width
	 * @param maxHeight			maximum height
	 * @param xScale			scale width value
	 * @param yScale			scale height value
	 * @param xTran				shift x by value
	 * @param yTran				shift y by value
	 * @return					the customized label
	 */
	public default Label label(String name, int xCoord, int yCoord, String ID, double minWidth, double minHeight, double maxWidth, double maxHeight 
			,ObservableValue<? extends Number> xScale, ObservableValue<? extends Number> yScale
			,ObservableValue<? extends Number> xTran, ObservableValue<? extends Number> yTran) {
		Label label = new Label(name);
		label.setWrapText(true);
		label.setLayoutX(xCoord);
		label.setLayoutY(yCoord);
		//setId() will link button to css file button style by name
		label.setId(ID);
		if (minWidth != 0) 
			label.setMinWidth(minWidth);
		if (minHeight != 0) 
			label.setMinHeight(minHeight);
		if (maxWidth != 0) 
			label.setMaxWidth(maxWidth);
		if (maxHeight != 0) 
			label.setMaxHeight(maxHeight);
		if (xScale != null) 
			label.scaleXProperty().bind(xScale);
		if (yScale != null)
			label.scaleYProperty().bind(yScale);
		if (xTran != null)
			label.translateXProperty().bind(xTran);
		if (yTran != null)
			label.translateYProperty().bind(yTran);
		return label;
	}
	
	//add to root unimplemented method
	public void add(Object obj);
	
	/**
	 * Method to add object to groups nice and clean (code wise)
	 * @param group				object getting addition
	 * @param obj				node to be added
	 */
	public default void add(Group group,Object obj) {
		group.getChildren().add((Node) obj);
	}
	
	/**
	 * Method to add object to panes nice and clean (code wise)
	 * @param group				object getting addition
	 * @param obj				node to be added
	 */
	public default void add(Pane pane,Object obj) {
		pane.getChildren().add((Node) obj);
	}
	
	/**
	 * Method to add object to borderpanes nice and clean (code wise)
	 * @param group				object getting addition
	 * @param obj				node to be added
	 */
	public default void add(BorderPane pane,Object obj) {
		pane.getChildren().add((Node) obj);
	}
	
	/**
	 * Method to add object to gridpanes nice and clean (code wise)
	 * @param group				object getting addition
	 * @param obj				node to be added
	 */
	public default void add(GridPane pane,Object obj) {
		pane.getChildren().add((Node) obj);
	}
	
	/**
	 * Method to add object to stackpanes nice and clean (code wise)
	 * @param group				object getting addition
	 * @param obj				node to be added
	 */
	public default void add(StackPane pane,Object obj) {
		pane.getChildren().add((Node) obj);
	}
	
	/**
	 * Method to add object to flowpanes nice and clean (code wise)
	 * @param group				object getting addition
	 * @param obj				node to be added
	 */
	public default void add(FlowPane pane,Object obj) {
		pane.getChildren().add((Node) obj);
	}
	
	/**
	 * Method to add object to tilepanes nice and clean (code wise)
	 * @param group				object getting addition
	 * @param obj				node to be added
	 */
	public default void add(TilePane pane,Object obj) {
		pane.getChildren().add((Node) obj);
	}
	
	/**
	 * Method to add object to anchorpanes nice and clean (code wise)
	 * @param group				object getting addition
	 * @param obj				node to be added
	 */
	public default void add(AnchorPane pane,Object obj) {
		pane.getChildren().add((Node) obj);
	}
	
	/**
	 * Method to add object to vboxes nice and clean (code wise)
	 * @param group				object getting addition
	 * @param obj				node to be added
	 */
	public default void add(VBox vbox,Object obj) {
		vbox.getChildren().add((Node) obj);
	}
	
	
	
	/**
	 * Method to create vBox object without size restrictions nice and clean (code wise)
	 * @param space				space between added objects
	 * @param position			position to be set in box
	 * @param x					bind amount to be resized in x
	 * @param y					bind amount to be resized in y
	 * @return					the customized vBox
	 */
	public default VBox vBox(int space, Pos position, ObservableValue<? extends Number> x, ObservableValue<? extends Number> y) {
		VBox VBox = new VBox(space);
		VBox.setAlignment(position);
		if (x != null) {
			VBox.prefWidthProperty().bind(x);
		}
		if (y != null) {
			VBox.prefHeightProperty().bind(y);
		}
		return VBox;
	}
	
	/**
	 * Method to create vBox object with minimum size restrictions nice and clean (code wise)
	 * @param space				space between added objects
	 * @param position			position to be set in box
	 * @param minWidth			the minimum width
	 * @param minHeight			the maximum height
	 * @param x					bind amount to be resized in x
	 * @param y					bind amount to be resized in y
	 * @return					the customized vBox
	 */
	public default VBox vBoxMin(int space, Pos position, double minWidth, double minHeight, ObservableValue<? extends Number> x, ObservableValue<? extends Number> y) {
		VBox VBox = new VBox(space);
		VBox.setAlignment(position);
		VBox.setMinSize(minWidth, minHeight);
		if (x != null) {
			VBox.prefWidthProperty().bind(x);
		}
		if (y != null) {
			VBox.prefHeightProperty().bind(y);
		}
		return VBox;
	}
	
	/**
	 * Method to create vBox object with maximum size restrictions nice and clean (code wise)
	 * @param space				space between added objects
	 * @param position			position to be set in box
	 * @param maxWidth			the maximum width
	 * @param maxHeight			the maximum height
	 * @param x					bind amount to be resized in x
	 * @param y					bind amount to be resized in y
	 * @return					the customized vBox
	 */
	public default VBox vBoxMax(int space, Pos position, double maxWidth, double maxHeight, ObservableValue<? extends Number> x, ObservableValue<? extends Number> y) {
		VBox VBox = new VBox(space);
		VBox.setAlignment(position);
		VBox.setMaxSize(maxWidth, maxHeight);
		if (x != null) {
			VBox.prefWidthProperty().bind(x);
		}
		if (y != null) {
			VBox.prefHeightProperty().bind(y);
		}
		return VBox;
	}
	
	/**
	 * Method to create vBox object with preferred size restrictions nice and clean (code wise)
	 * @param space				space between added objects
	 * @param position			position to be set in box
	 * @param prefWidth			the preferred width
	 * @param prefHeight		the preferred height
	 * @param x					bind amount to be resized in x
	 * @param y					bind amount to be resized in y
	 * @return					the customized vBox
	 */
	public default VBox vBoxPref(int space, Pos position, double prefWidth, double prefHeight, ObservableValue<? extends Number> x, ObservableValue<? extends Number> y) {
		VBox VBox = new VBox(space);
		VBox.setAlignment(position);
		VBox.setPrefSize(prefWidth, prefHeight);
		if (x != null) {
			VBox.prefWidthProperty().bind(x);
		}
		if (y != null) {
			VBox.prefHeightProperty().bind(y);
		}
		return VBox;
	}
	
	/**
	 * Method assigns values to set up VBox that is defined in interface
	 * @param vBox				the container to be edited
	 * @param space				space between added objects
	 * @param position			position to be set in box
	 * @param minWidth			the minimum width
	 * @param minHeight			the minimum height
	 * @param maxWidth			the maximum width
	 * @param maxHeight			the maximum height
	 * @param x					bind amount to be resized in x
	 * @param y					bind amount to be resized in y
	 */
	public default void setUpVBox(VBox vBox,int space, Pos position,double minWidth, double minHeight,double maxWidth, double maxHeight
			, ObservableValue<? extends Number> x, ObservableValue<? extends Number> y) {
		vBox.setSpacing(space);
		vBox.setAlignment(position);
		if (minWidth != 0) 
			vBox.setMaxWidth(minWidth);
		if (minHeight!= 0) 
			vBox.setMaxWidth(minHeight);
		if (maxWidth != 0) 
			vBox.setMaxWidth(maxWidth);
		if (maxHeight!= 0) 
			vBox.setMaxWidth(maxHeight);
		if (x != null) 
			vBox.prefWidthProperty().bind(x);
		if (y != null) 
			vBox.prefHeightProperty().bind(y);	
	}
	
	/**
	 * Method to create hBox object without size restrictions nice and clean (code wise)
	 * @param space				space between added objects
	 * @param position			position to be set in box
	 * @param x					bind amount to be resized in x
	 * @param y					bind amount to be resized in y
	 * @return					the customized vBox
	 */
	public default HBox hBox(int space, Pos position, ObservableValue<? extends Number> x, ObservableValue<? extends Number> y) {
		HBox HBox = new HBox(space);
		HBox.setAlignment(position);
		if (x != null) {
			HBox.prefWidthProperty().bind(x);
		}
		if (y != null) {
			HBox.prefHeightProperty().bind(y);
		}
		return HBox;
	}
	
	/**
	 * Method to create hBox object with minimum size restrictions nice and clean (code wise)
	 * @param space				space between added objects
	 * @param position			position to be set in box
	 * @param minWidth			the minimum width
	 * @param minHeight			the maximum height
	 * @param x					bind amount to be resized in x
	 * @param y					bind amount to be resized in y
	 * @return					the customized vBox
	 */
	public default HBox hBoxMin(int space, Pos position, double minWidth, double minHeight, ObservableValue<? extends Number> x, ObservableValue<? extends Number> y) {
		HBox HBox = new HBox(space);
		HBox.setAlignment(position);
		HBox.setMinSize(minWidth, minHeight);
		if (x != null) {
			HBox.prefWidthProperty().bind(x);
		}
		if (y != null) {
			HBox.prefHeightProperty().bind(y);
		}
		return HBox;
	}
	
	/**
	 * Method to create hBox object with maximum size restrictions nice and clean (code wise)
	 * @param space				space between added objects
	 * @param position			position to be set in box
	 * @param maxWidth			the maximum width
	 * @param maxHeight			the maximum height
	 * @param x					bind amount to be resized in x
	 * @param y					bind amount to be resized in y
	 * @return					the customized vBox
	 */
	public default HBox hBoxMax(int space, Pos position, double maxWidth, double maxHeight, ObservableValue<? extends Number> x, ObservableValue<? extends Number> y) {
		HBox HBox = new HBox(space);
		HBox.setAlignment(position);
		HBox.setMaxSize(maxWidth, maxHeight);
		if (x != null) {
			HBox.prefWidthProperty().bind(x);
		}
		if (y != null) {
			HBox.prefHeightProperty().bind(y);
		}
		return HBox;
	}
	
	/**
	 * Method to create hBox object with preferred size restrictions nice and clean (code wise)
	 * @param space				space between added objects
	 * @param position			position to be set in box
	 * @param prefWidth			the preferred width
	 * @param prefHeight		the preferred height
	 * @param x					bind amount to be resized in x
	 * @param y					bind amount to be resized in y
	 * @return					the customized vBox
	 */
	public default HBox hBoxPref(int space, Pos position, double prefWidth, double prefHeight, ObservableValue<? extends Number> x, ObservableValue<? extends Number> y) {
		HBox HBox = new HBox(space);
		HBox.setAlignment(position);
		HBox.setPrefSize(prefWidth, prefHeight);
		if (x != null) {
			HBox.prefWidthProperty().bind(x);
		}
		if (y != null) {
			HBox.prefHeightProperty().bind(y);
		}
		return HBox;
	}
	
	/**
	 * Method getFileContent will get content from specified file in the form of an arraylist
	 * @param file				the file to retrieve content from
	 */
	public default ArrayList<String> getFileContent(File file) {
		ArrayList<String> list = new ArrayList<>();
		
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			String line = br.readLine();
			String str = line;
			while (line != null) {		// while line is not null = content remaining = read all till the end of file
				if (line.isEmpty()) {	// if line is empty, add that string as an index and reset String str
					list.add(str);
					str = "";
				}
				else {
					str = str + " " + line;		//if not empty, keep adding to String str
				}
				
				line = br.readLine();	//reads the next line
				if (line == null) {		//**if next line is the end, add the finished string to the arraylist before exiting loop
					list.add(str);
				}
			}
			
			br.close();
			fr.close();
		} 
		catch (IOException e) {e.printStackTrace();}
		
		return list;
	}
	
	/**
	 * Method getFileContent will get content from specified file in the form of an arraylist
	 * @param file				the file to retrieve content from
	 * @param list				the list of content
	 */
	public default void saveContent(File file, ArrayList<String> list) {
		try{
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			
			clearFile(file);
			
			for (int i = 0; i < list.size(); i++) {
				pw.write(list.get(i));
				pw.write("");
			}
			
			pw.close();
			bw.close();
			fw.close();
		}
		catch (IOException e) {e.printStackTrace();}
	}
	
	/**
	 * Method clearFile will reset a specified file's content
	 * @param file				the file to clear
	 */
	public default void clearFile(File file)
	{ 
	    try{
	    FileWriter fw = new FileWriter(file, false);
	    PrintWriter pw = new PrintWriter(fw, false);
	    pw.flush();
	    pw.close();
	    fw.close();
	    }
	    catch(Exception exception){
	        System.out.println(exception);
	    }
	}
}

