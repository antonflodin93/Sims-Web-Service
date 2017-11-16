package org.test.WS.model;

public class FactoryObject {

	private int objectId;
	private String objectName;
	private int objectFloorId;
	
	public FactoryObject() {
		
	}
	
	public FactoryObject(int objectId, String objectName, int objectFloorId) {
		super();
		this.objectId = objectId;
		this.objectName = objectName;
		this.objectFloorId = objectFloorId;
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
	
	
	
}
