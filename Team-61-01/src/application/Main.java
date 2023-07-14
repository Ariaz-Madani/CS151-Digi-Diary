package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.Cursor;
import javafx.scene.Group;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Label lbl = new Label("Welcome to Digi-Diary!");
			lbl.setFont(new Font("Lucida Sans Unicdoe", 40));
			
			Button loginbtn = new Button("Login");
			Button ChangePass = new Button("Change Password");
			Button ForgotPass = new Button("Forgot Password");
			
			HBox buttons = new HBox();
			buttons.setSpacing(10);
			
			 
			buttons.getChildren().add(loginbtn);
			VBox login = new VBox();
			TextField username = new TextField();
			TextField pass = new TextField();
			login.getChildren().add(username);
			login.getChildren().add(pass);
			
			buttons.getChildren().add(ChangePass);
			buttons.getChildren().add(ForgotPass);
			

			VBox root = new VBox();
			root.setSpacing(20);
			root.getChildren().add(lbl);
			root.getChildren().add(buttons);
			
			loginbtn.setOnAction(new EventHandler<ActionEvent> () {
			
			@Override
			public void handle(ActionEvent event) {
				
			
				
			}
			
			});
			
			
			ChangePass.setOnAction(new EventHandler<ActionEvent> () {
				
				@Override
				public void handle(ActionEvent event) {
					
				//	lbl.se 
				}
				
				});
			
			
			ForgotPass.setOnAction(new EventHandler<ActionEvent> () {
				
				@Override
				public void handle(ActionEvent event) {
					
				//	lbl.se 
				}
				
				});

			
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
