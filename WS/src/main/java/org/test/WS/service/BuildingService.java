package org.test.WS.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.test.WS.database.DBConnection;
import org.test.WS.model.Building;

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

			while (resultSet.next()) {

				Building building = new Building(Integer.parseInt(resultSet.getString("buildingId")), resultSet.getString("buildingName"));
				buildings.add(building);
			}

			pst.close();
			connection.close();


		return buildings;
	}

}
