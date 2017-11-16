package org.test.WS.model;

import java.util.ArrayList;

public class Floor {
    private int floorId;
    private String floorLevel;
    private String floorPlanFilePath;
    private int floorBuildingId;
    private ArrayList<FactoryObject> objects;

    Floor() {
    	
    }
    
    

	
	public Floor(int floorId, String floorLevel, String floorPlanFilePath, int floorBuildingId) {
		this.floorId = floorId;
		this.floorLevel = floorLevel;
		this.floorPlanFilePath = floorPlanFilePath;
		this.floorBuildingId = floorBuildingId;
	}
	
	
	public Floor(int floorId, String floorLevel, String floorPlanFilePath, int floorBuildingId,  ArrayList<FactoryObject> objects) {
		this.floorId = floorId;
		this.floorLevel = floorLevel;
		this.floorPlanFilePath = floorPlanFilePath;
		this.floorBuildingId = floorBuildingId;
		this.objects = objects;
	}





	public int getFloorId() {
		return floorId;
	}

	public void setFloorId(int floorId) {
		this.floorId = floorId;
	}

	public String getFloorLevel() {
		return floorLevel;
	}

	public void setFloorLevel(String floorLevel) {
		this.floorLevel = floorLevel;
	}

	public String getFloorPlanFilePath() {
		return floorPlanFilePath;
	}

	public void setFloorPlanFilePath(String floorPlanFilePath) {
		this.floorPlanFilePath = floorPlanFilePath;
	}



	public int getFloorBuildingId() {
		return floorBuildingId;
	}



	public void setFloorBuildingId(int floorBuildingId) {
		this.floorBuildingId = floorBuildingId;
	}




	public ArrayList<FactoryObject> getObjects() {
		return objects;
	}




	public void setObjects(ArrayList<FactoryObject> objects) {
		this.objects = objects;
	}
	
	



    
    
    
    
}
