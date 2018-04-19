package uploadTable;

import java.util.LinkedList;

public class ListOfTables {
	
	private LinkedList<Table> listOfTables = new LinkedList<Table>();
	
	public void setListOfTables(LinkedList<Table> listOfTables){
		this.listOfTables = listOfTables;
	}
	
	public LinkedList<Table> getListOfTables(){
		return this.listOfTables;
	}

}
