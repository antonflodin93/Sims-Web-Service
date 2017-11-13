package org.test.WS.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.test.WS.database.DBConnection;
import org.test.WS.model.Floor;

public class FloorService {
	DBConnection dBconnection;
	Connection connection;
	ResultSet resultSet;
	List<Floor> floors;

	// Returns all buildings
	public List<Floor> getFloorsInBuilding(int buildingId) throws ClassNotFoundException, SQLException {
		floors = new ArrayList<Floor>();
		connection = DBConnection.setDBConnection();
		String sql = "SELECT * from floors where floorBuildingId = ?";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setLong(1, buildingId);
		resultSet = pst.executeQuery();

		while (resultSet.next()) {
			Floor floor = new Floor(Integer.parseInt(resultSet.getString("floorId")), resultSet.getString("floorLevel"), resultSet.getString("floorPlanFilePath"));
			floors.add(floor);
		}
		pst.close();
		connection.close();

		return floors;
	}

}
