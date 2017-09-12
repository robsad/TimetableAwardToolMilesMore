package app.data;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import app.data.entities.AirportsData;
import app.data.entities.Connection;

public class DataLoader {

	public List<AirportsData> getAirports() {
		List<AirportsData> airportsData = (List<AirportsData>) loadAirportsFromFile("src/resources/star_alliance_airports.csv");
		return airportsData;
	}

	public Map<String, List<Connection>> getConnections() {
		Map<String, List<Connection>> connectionsByOrigin = (Map<String, List<Connection>>) loadConnectionsFromFile("src/resources/star_alliance_connections.csv");
		return connectionsByOrigin;
	}

	public Map<String, String> getCountryCodes() {
		Map<String, String> countryByCode = loadCountriesFromFile("src/resources/countries.csv");
		return countryByCode;
	}

	private List<AirportsData> loadAirportsFromFile(String filename) {
		File file = new File(filename);
		List<AirportsData> airportsData = new ArrayList<>();
		String csvLine;
		try (BufferedReader br = new BufferedReader(new FileReader(
				file.getCanonicalPath()))) {
			while ((csvLine = br.readLine()) != null) {
				String[] dataArray = csvLine.split(";");
				airportsData.add(new AirportsData(dataArray[0], dataArray[1],
						dataArray[2], Double.parseDouble(dataArray[3]), Double
								.parseDouble(dataArray[4])));
			}
		} catch (IOException ex) {
			throw new RuntimeException("Error in reading CSV file: " + ex);
		}
		return airportsData;
	}

	private Map<String, List<Connection>> loadConnectionsFromFile(
			String filename) {
		File file = new File(filename);
		Map<String, List<Connection>> connectionsByOrigin = new HashMap<>();
		String csvLine;
		try (BufferedReader br = new BufferedReader(new FileReader(
				file.getCanonicalPath()))) {
			while ((csvLine = br.readLine()) != null) {
				String[] dataArray = csvLine.split(";");
				String key = dataArray[0];
				List<Connection> connections = connectionsByOrigin.get(key);
				if (connections != null) {
					connections.add(new Connection(dataArray[1], dataArray[3],
							dataArray[2]));
					connectionsByOrigin.put(key, connections);
				} else {
					List<Connection> newConnections = new ArrayList<Connection>();
					newConnections.add(new Connection(dataArray[1],
							dataArray[3], dataArray[2]));
					connectionsByOrigin.put(key, newConnections);
				}
			}
		} catch (IOException ex) {
			throw new RuntimeException("Error in reading CSV file: " + ex);
		}
		return connectionsByOrigin;
	}

	private Map<String, String> loadCountriesFromFile(String filename) {
		Map<String, String> countryByCode = new HashMap<>();
		File file = new File(filename);
		String csvLine;
		try (BufferedReader br = new BufferedReader(new FileReader(
				file.getCanonicalPath()))) {
			while ((csvLine = br.readLine()) != null) {
				String[] dataArray = csvLine.split(";");
				countryByCode.put(dataArray[0], dataArray[1]);
			}
		} catch (IOException ex) {
			throw new RuntimeException("Error in reading CSV file: " + ex);
		}
		return countryByCode;
	}

}
