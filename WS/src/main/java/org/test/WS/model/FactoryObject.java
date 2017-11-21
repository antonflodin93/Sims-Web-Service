package org.test.WS.model;

public class FactoryObject {

	private int objectId;
	private String objectName;
	private int objectFloorId;
	private int areaXStart, areaXEnd, areaYStart, areaYEnd;
	
	public FactoryObject() {
		
	}
	
	public FactoryObject(int objectId, String objectName, int objectFloorId, int xStart, int xEnd, int yStart,
			int yEnd) {
		super();
		this.objectId = objectId;
		this.objectName = objectName;
		this.objectFloorId = objectFloorId;
		this.areaXStart = xStart;
		this.areaXEnd = xEnd;
		this.areaYStart = yStart;
		this.areaYEnd = yEnd;
	}

	public int getObjectId() {
		return objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public int getObjectFloorId() {
		return objectFloorId;
	}

	public void setObjectFloorId(int objectFloorId) {
		this.objectFloorId = objectFloorId;
	}

	public int getXstart() {
		return areaXStart;
	}

	public void setXstart(int xstart) {
		this.areaXStart = xstart;
	}

	public int getXend() {
		return areaXEnd;
	}

	public void setXend(int xend) {
		this.areaXEnd = xend;
	}

	public int getYstart() {
		return areaYStart;
	}

	public void setYstart(int ystart) {
		this.areaYStart = ystart;
	}

	public int getYend() {
		return areaYEnd;
	}

	public void setYend(int yend) {
		this.areaYEnd = yend;
	}
	
	
	
	
}
