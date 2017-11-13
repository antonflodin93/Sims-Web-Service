package org.test.WS.model;

public class Floor {
    private int floorId;
    private String floorLevel;
    private String floorPlanFilePath;

    Floor() {
    	
    }
    
    

	public Floor(int floorId, String floorLevel, String floorPlanFilePath) {
		super();
		this.floorId = floorId;
		this.floorLevel = floorLevel;
		this.floorPlanFilePath = floorPlanFilePath;
	}
	
	



	public Floor(String floorLevel, String floorPlanFilePath) {
		super();
		this.floorLevel = floorLevel;
		this.floorPlanFilePath = floorPlanFilePath;
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
    
    
    
    
}
