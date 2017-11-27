package org.test.WS.model;

import java.util.ArrayList;

public class Floor {
    private int floorId;
    private String floorLevel;
    private String floorPlanFilePath;
    private int floorBuildingId;
    private ArrayList<FactoryObject> objects;
    private int floorAreaRows, floorAreaColumns;

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

	
	




	public Floor(int floorId, String floorLevel, String floorPlanFilePath, int floorBuildingId,
			ArrayList<FactoryObject> objects, int floorAreaRows, int floorAreaColumns) {
		super();
		this.floorId = floorId;
		this.floorLevel = floorLevel;
		this.floorPlanFilePath = floorPlanFilePath;
		this.floorBuildingId = floorBuildingId;
		this.objects = objects;
		this.floorAreaRows = floorAreaRows;
		this.floorAreaColumns = floorAreaColumns;
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




	public int getFloorAreaRows() {
		return floorAreaRows;
	}




	public void setFloorAreaRows(int floorAreaRows) {
		this.floorAreaRows = floorAreaRows;
	}




	public int getFloorAreaColumns() {
		return floorAreaColumns;
	}




	public void setFloorAreaColumns(int floorAreaColumns) {
		this.floorAreaColumns = floorAreaColumns;
	}
	
    
}
