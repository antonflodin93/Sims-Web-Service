package org.test.WS.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.test.WS.database.DBConnection;
import org.test.WS.model.Floor;
import org.test.WS.model.FactoryObject;

public class FloorService {
	DBConnection dBconnection;
	Connection connection;
	ResultSet resultSet;
	List<Floor> floors;


	public List<Floor> getAllFloors() throws ClassNotFoundException, SQLException {
		floors = new ArrayList<Floor>();
		connection = DBConnection.setDBConnection();
		String sql = "SELECT * from floors";
		PreparedStatement pst = connection.prepareStatement(sql);
		resultSet = pst.executeQuery();
		
		// For each row, get the corresponding objects
		while (resultSet.next()) {
			
			// Get the id of the floor
			int floorId = Integer.parseInt(resultSet.getString("floorId"));
			
			ArrayList<FactoryObject> objects = new ArrayList<FactoryObject>();
			Connection innerConnection = DBConnection.setDBConnection();
			String sqlObjects = "SELECT * from factoryobjects where objectFloorId = ?";
			PreparedStatement pstObjects = connection.prepareStatement(sqlObjects);
			pstObjects.setLong(1, floorId);
			ResultSet resultSetObjects = pstObjects.executeQuery();
			
			// Create floor objects
			while (resultSetObjects.next()) {
				
				FactoryObject object = new FactoryObject(Integer.parseInt(resultSetObjects.getString("objectId")), resultSetObjects.getString("objectName"), Integer.parseInt(resultSetObjects.getString("objectFloorId")),
						Integer.parseInt(resultSetObjects.getString("areaXStart")), Integer.parseInt(resultSetObjects.getString("areaXEnd")), Integer.parseInt(resultSetObjects.getString("areaYStart")), Integer.parseInt(resultSetObjects.getString("areaYEnd")));
				objects.add(object);
			}
											
		
			Floor floor = new Floor(Integer.parseInt(resultSet.getString("floorId")), 
						resultSet.getString("floorLevel"), resultSet.getString("floorPlanFilePath"), Integer.parseInt(resultSet.getString("floorBuildingId")), objects);
			floors.add(floor);
			
			pstObjects.close();
			innerConnection.close();
		
		}

		pst.close();
		connection.close();


	return floors;
	}
}
