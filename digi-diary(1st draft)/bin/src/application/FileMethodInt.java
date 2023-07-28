package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Interface solely used by JournalWorkspace to navigate issues regarding saved, deleted, recovering files.
 */
public interface FileMethodInt extends ProgramValueInt{
	/**
	 * Method displayLocalDateTime is used to either display a continiously updating date, time, or both on the GUI application.
	 * @param timeAndDate		textField that will list both time and/or date
	 * @param dateAndOrTime		"date" = just display date, "time" = just display time, "both" = display both
	 */
	public default void displayLocalDateTime(TextField timeAndDate, String dateAndOrTime) {
		boolean off = false;		//boolean variable to ensure thread keeps running indefinitely
		Thread thread = new Thread(() -> {
			SimpleDateFormat dateTime = null;
			
			if (dateAndOrTime.equals("date")) {							//if just want to display date
				dateTime = new SimpleDateFormat("yyyy/MM/dd");
			}
			else if (dateAndOrTime.equals("time")) {					//if just want to display time
				dateTime = new SimpleDateFormat("HH:mm:ss");
			}
			else if (dateAndOrTime.equals("both")) {					//if want to display both
				dateTime = new SimpleDateFormat("yyyy/MM/dd     	HH:mm:ss");
			}
			
			while (off == false) {		//the while loop that will keep updating the textfield; this method can be more specific in wanting label or textfield in its parameters but not for this proj
				try {
					Thread.sleep(1000);			//1000 milisecond convets to 1 second and will check/update after every second
					
				} catch(Exception e) {e.printStackTrace();}
				
				String currentDateTime = dateTime.format(new Date());		//get the current update date/time
				
				Platform.runLater(() -> {								//runs as a separate task after running main program
					timeAndDate.setText(currentDateTime);				//update the specified field
				});
			}
		});
		thread.setDaemon(true);		//this will make sure the thread closes when application closes by user
		thread.start();				//start the thread/run process
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
	
	/**
	 * Method getFileContent will get content from specified file in the form of an arraylist
	 * @param file				the file to retrieve content from
	 * @return content			the content of the file
	 */
	public default String getFileContent(File file) {
		String content = "";
		
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			String line = br.readLine();
			int tick = 0;
			while (line != null) {		// while line is not null = content remaining = read all till the end of file
				if (!line.isEmpty()) {		//empty line = indent twice
					if (tick == 0) {
						content = line;
						tick = 1;
					}
					else {
						content = content + "\n" + line;
					}
				}
				else {						//not empty line but new line = indent once
					content = content + "\n\n";
				}
				line = br.readLine();		//read next line
			}
			
			br.close();
			fr.close();
		} 
		catch (IOException e) {e.printStackTrace();}
		
		return content;
	}
	
	/**
	 * Method findFile will return a list of file entries who contain the specified line of text user wants to find.
	 * @param str		the string of text that user wants found
	 * @param mode		specifies if files are being searching from deleted folder or saved folder
	 * @return list		the list of entires that fit the search criteria
	 */
	public default ArrayList<String> findFile(String text, String mode) {
		ArrayList<String> list = new ArrayList<>();
		
		//get a list of ALL entries of specified folder
		if (mode.equals("deleted")) {		//recovery mode
			try {
				FileReader fr = new FileReader(deletedFile);
				BufferedReader br = new BufferedReader(fr);
				
				String line = br.readLine();
				while (line != null) {
					list.add("src/fileDB/deletedFiles/" + line);		//list of paths to all deleted files
					line = br.readLine();
				}
				
				br.close();
				fr.close();
			}
			catch(IOException e) {e.printStackTrace();}
		}
		else {		//search for old entry mode
			try {
				FileReader fr = new FileReader(savedFile);
				BufferedReader br = new BufferedReader(fr);
				
				String line = br.readLine();
				while (line != null) {
					list.add("src/fileDB/savedFiles/" + line);			//list of paths to all saved files
					line = br.readLine();
				}
				
				br.close();
				fr.close();
			}
			catch(IOException e) {e.printStackTrace();}
		}
		
		//run through each and every file in specified folder/list and delete those that don't contain user's desired text
		for (int i = 0; i < list.size(); i++) {
			String str = list.get(i);
			File file = new File(str);		//make a file with the path
			
			if (mode.equals("deleted")) {
				str = str.replace("src/fileDB/deletedFiles/", "");
			}
			else {
				str = str.replace("src/fileDB/savedFiles/", "");
			}
			list.remove(i);
			list.add(i, str);		//convert back to just the file name; this is the format that the dropBoxMenu will read in
			
			if (!file.toString().contains("null") && str.contains(text)) {
				str = str + getFileContent(file);		//get a string of the title + the file's content
			}
			else {				//remove entry from the list if it doesn't contain user's specified text
				list.remove(i);
				i--;
			}
		}
		
		return list;
	}
	
	public default void addFilesToMenu(ComboBox<String> box, ArrayList<String> list) {
		if (list.size()>0) {
			for (int i = 0; i < list.size(); i++) {
				String fileName = list.get(i);
				fileName = fileName.replace("[", "     [");					//add some space between title and date for user
				fileName = fileName.substring(0,fileName.length()-4);		//remove ".txt when displaying file
				box.getItems().add(fileName);		//add options to drop down
			}
		}
	}
	
	/**
	 * Method addLine adds the specified text within the specified file.
	 * @param file			the file to be modified
	 * @param text			the text to be deleted from specified file
	 */
	public default void addLine(File file, String text) {
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
	public default void deleteLine(File file, String text) {
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
	 * Method setDateTime will get user's current date and time.
	 * @return str		the time + date in a string
	 */
	public default String setDateTime() {
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
	public default boolean checkValidDate(String string) {
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
	 * Method clearFolder is a helper method solely to erase all files in the deleted directory only.
	 * @param directory
	 */
	public default void clearFolder() {
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
	public default void autoSave(Path entryPath, TextField title, String fileDate, File file, TextArea area) {
		String data = entryPath.toString();
		data = data.substring(22, data.length());		//just the txt file name
		
		File folderFile = new File("src/fileDB/savedFiles/" + data);		//if not auto save then delete default titleless draft
		folderFile.delete();
		deleteLine(savedFile, data);										//also delete it from savedFile txt
		
		
		data = data.replace(data.substring(data.length()-24, data.length()-4), fileDate);	//set date/time
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
	 * Method cancelSave will save the draft that user cancelled on in case they want to recover it during the session; otherwise, it will be autodeleted.
	 */
	public default void cancelSave(Path entryPath, String fileTitle, TextField title, String todayDate, TextArea area) {
		File file = new File(entryPath.toString());
		deleteLine(savedFile, entryPath.toString().substring(22, entryPath.toString().length()));	//delete the entry within saved folder
		
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
