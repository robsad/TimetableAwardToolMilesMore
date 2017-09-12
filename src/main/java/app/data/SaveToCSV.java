package app.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import app.data.entities.AirportsData;
import app.data.entities.Connection;

public class SaveToCSV {

	private final char SEPARATOR = ';';
	List<AirportsData> airportsData;
	Map<String, List<Connection>> connectionsByOrigin;
	
	public SaveToCSV (List<AirportsData> airportsData, Map<String, List<Connection>> connectionsByOrigin){
		this.airportsData = airportsData;
		this.connectionsByOrigin = connectionsByOrigin;
	}
	
	public void toFile() {
		airportsDataToFile("star_alliance_airports.csv");
		connectionsToFile("star_alliance_connections.csv");
	}
	
	private void airportsDataToFile(String filename) {
		try {
			PrintWriter pw = new PrintWriter(new File(filename));
		for(AirportsData airport : airportsData) {
			String line = airportsDataToLine(airport);
	        pw.write(line);
		}
    pw.close();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	
	private void connectionsToFile(String filename) {
		try {
			PrintWriter pw = new PrintWriter(new File(filename));
		for (Map.Entry<String, List<Connection>> cursor : connectionsByOrigin.entrySet()) {
			for (Connection connection : cursor.getValue()) {
				String line = connectionsByOriginToLine(cursor.getKey(),connection);
		        pw.write(line);
			}
		}
        pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String airportsDataToLine(AirportsData airport) {
		
		String lat = String.valueOf(airport.getLat());
		String lon = String.valueOf(airport.getLon());
		return airport.getCityCode() + SEPARATOR 
				+ airport.getCityName() + SEPARATOR
				+ airport.getCountryCode() + SEPARATOR
				+ lat + SEPARATOR
				+ lon + '\n';
	}
	
	private String connectionsByOriginToLine(String origin, Connection connection) {
		return 	origin + SEPARATOR
				+ connection.getDestination() + SEPARATOR
				+ connection.getAirlinecode() + SEPARATOR
				+ connection.getStops() + '\n';
	}
	
}
