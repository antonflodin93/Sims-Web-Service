package org.test.WS.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.test.WS.database.DBConnection;
import org.test.WS.model.Building;
import org.test.WS.model.Floor;

public class BuildingService {
	DBConnection dBconnection;
	Connection connection;
	ResultSet resultSet;
	List<Building> buildings;

	// Returns all buildings
	public List<Building> getAllBuildings() throws ClassNotFoundException, SQLException {
		
			buildings = new ArrayList<Building>();
			connection = DBConnection.setDBConnection();
			String sql = "SELECT * from buildings";
			PreparedStatement pst = connection.prepareStatement(sql);
			resultSet = pst.executeQuery();
			
			// For each row, get the corresponding floors
			while (resultSet.next()) {
				// Get the id of the building
				int buildingId = Integer.parseInt(resultSet.getString("buildingId"));
				
				ArrayList<Floor> floors = new ArrayList<Floor>();
				Connection innerConnection = DBConnection.setDBConnection();
				String sqlFloors = "SELECT * from floors where floorBuildingId = ?";
				PreparedStatement pstFloors = connection.prepareStatement(sqlFloors);
				pstFloors.setLong(1, buildingId);
				ResultSet resultSetFloors = pstFloors.executeQuery();
				
				// Create floor objects
				while (resultSetFloors.next()) {
					Floor floor = new Floor(Integer.parseInt(resultSetFloors.getString("floorId")), resultSetFloors.getString("floorLevel"), resultSetFloors.getString("floorPlanFilePath"));
					floors.add(floor);
				}
				pstFloors.close();
				innerConnection.close();
				
				// Create building object with the floors
				Building building = new Building(Integer.parseInt(resultSet.getString("buildingId")), resultSet.getString("buildingName"), floors);
				buildings.add(building);
			}

			pst.close();
			connection.close();


		return buildings;
	}

}
