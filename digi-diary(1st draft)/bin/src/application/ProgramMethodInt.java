package application;

import java.io.File;

import javafx.animation.FadeTransition;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Interface holding methods for creating/displaying GUI objects/nodes.
 */
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
	 * Method createTransition will make the specified node fade in during a select amount of time.
	 * @param duration		time for fade to fill in
	 * @param node			node to fade in
	 * @param opacity		opacity for fade to fill to
	 */
	public default void createTransition(Duration duration, Node node, double opacity){
		FadeTransition fade = new FadeTransition(duration, node);
		fade.setFromValue(0.0);
		fade.setToValue(opacity);
		fade.play();
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
	public default TextArea textArea(String name, double minWidth, double minHeight, double maxWidth, double maxHeight, String ID) {
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
	 * Method addMenu will preset numbers in specified combobox while also limiting how many menu items are displayed.
	 * @param box			the combobox to add numbers to
	 * @param numbers		the numbers to be added from 0
	 * @param visibleTotal	the total number of visible menu items displayed at a time
	 */
	public default void addMenu(ComboBox<String> box, int numbers, int visibleTotal) {
		for (int i = numbers; i > -1; i--) {
			if (i < 10) {
				box.getItems().add("0" + String.valueOf(i));
			}
			else {
				box.getItems().add(String.valueOf(i));
			}
		}
		box.setVisibleRowCount(visibleTotal);
	}
}

