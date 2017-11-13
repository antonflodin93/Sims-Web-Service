package org.test.WS.model;

import java.util.ArrayList;

public class Building {

    private int buildingId;
    private String buildingName;
    
    Building(){
    	
    }
    
    
    
    
	public Building(int buildingId, String buildingName) {
		super();
		this.buildingId = buildingId;
		this.buildingName = buildingName;
	}
	
	public Building(String buildingName) {
		super();
		this.buildingName = buildingName;
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

    
	
}
