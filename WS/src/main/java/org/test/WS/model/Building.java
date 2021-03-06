package org.test.WS.model;

import java.util.ArrayList;

public class Building {

    private int buildingId;
    private String buildingName;
    private ArrayList<Floor> floors;
    private int numOfEmployees;
    
    Building(){
    	
    }
    
    
    
    
	public Building(int buildingId, String buildingName, ArrayList<Floor> floors, int numOfEmployees) {
		super();
		this.buildingId = buildingId;
		this.buildingName = buildingName;
		this.floors = floors;
		this.numOfEmployees = numOfEmployees;
	}
	
	public Building(String buildingName, ArrayList<Floor> floors) {
		super();
		this.buildingName = buildingName;
		this.floors = floors;
	}




	public int getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}


	public ArrayList<Floor> getFloors() {
		return floors;
	}
	

	public void setFloors(ArrayList<Floor> floors) {
		this.floors = floors;
	}

	public int getNumOfEmployees() {
		return numOfEmployees;
	}




	public void setNumOfEmployees(int numOfEmployees) {
		this.numOfEmployees = numOfEmployees;
	}

	
	
    
	
}
