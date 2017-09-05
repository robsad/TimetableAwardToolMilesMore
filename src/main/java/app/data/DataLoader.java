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

import app.AirportsData;
import app.Connection;

public class DataLoader {

	public List<AirportsData> getAirports() {
		List<AirportsData> airportsData = (List<AirportsData>)loadFromFile("src/resources/starAllianceAirports.cnt");
		return airportsData;
	}
		
	public Map<String, List<Connection>> getConnections() {
		Map<String, List<Connection>> connectionsByOrigin = (Map<String, List<Connection>>)loadFromFile("src/resources/starAllianceConnections.cnt");
		return connectionsByOrigin;
	}
	
	public Map<String, String> getCountryCodes() {
		Map<String, String> countryByCode = loadCountriesFromFile("src/resources/countries.csv");
		return countryByCode;
	}
	
	private Object loadFromFile(String filename) {
		File fileConnections = new File(filename);
		Object result = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(
					new BufferedInputStream(
							new FileInputStream(fileConnections)));
			try {
				result =  ois.readObject();
			} catch (ClassNotFoundException i) {
				System.out.println(i);
			}
			ois.close();
		} catch (IOException i) {
			System.out.println(i);
		}
		return result;
	}
	
	private Map<String, String> loadCountriesFromFile(String filename) {
		Map<String, String> countryByCode = new HashMap<>();
		File file = new File(filename);
		String csvLine;
		try (BufferedReader br = new BufferedReader(new FileReader(file.getCanonicalPath()))) {
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
