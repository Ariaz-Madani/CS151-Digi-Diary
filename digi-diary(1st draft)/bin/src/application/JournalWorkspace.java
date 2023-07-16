package application;

import javafx.scene.Node;

public class JournalWorkspace implements ProgramMethodInt{
	@Override
	public void add(Object obj) {
		root.getChildren().add((Node) obj);
	}
	
	/**
	 * Method setUpJW sets up the main workspace after successfully signing in.
	 * JW = Journal Workspace (acronym)
	 */
	public void setUpJW() {
		
	}
}
