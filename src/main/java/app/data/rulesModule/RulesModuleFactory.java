package app.data.rulesModule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import app.data.Airports;
import app.data.entities.AirportsData;
import app.data.entities.Connection;

public class RulesModuleFactory {

	private List<String> zoneNameList = new ArrayList<>();
	private Map<String, List<String>> countriesByZone = new HashMap<>();
	private Map<String, List<Integer>> milesTable = new HashMap<>();
	
	public IRulesModule getModule(String moduleName, Airports airports, Map<String, List<Connection>> connectionsByOrigin) {
		initModule();
		//if (moduleName.equals("MilesMore") {}
		IRulesModule rulesModule = new RulesMilesMore(connectionsByOrigin, airports, zoneNameList, countriesByZone, milesTable);
		return rulesModule;
	}
	
	public void initModule(){
			loadZonesFromFile();
			loadTableFromFile();
		}
	
	
	public void loadZonesFromFile() {
		File file = new File("src/resources/zones.csv");
		String csvLine;
		try (BufferedReader br = new BufferedReader(new FileReader(file.getCanonicalPath()))) {
			while ((csvLine = br.readLine()) != null) {
				String[] dataArray = csvLine.split(";");
				List<String> countryCodes = Arrays.asList(Arrays.copyOfRange(dataArray, 1, dataArray.length));
				countriesByZone.put(dataArray[0], countryCodes);
				zoneNameList.add(dataArray[0]);
			}
		} catch (IOException ex) {
			throw new RuntimeException("Error in reading CSV file: " + ex);
		}
	}
	
	public void loadTableFromFile() {
		File file = new File("src/resources/table.csv");
		String csvLine;
		try (BufferedReader br = new BufferedReader(new FileReader(file.getCanonicalPath()))) {
			while ((csvLine = br.readLine()) != null) {
				String[] dataArray = csvLine.split(";");
				List<String> milesNeededListString = Arrays.asList(Arrays.copyOfRange(dataArray, 1, dataArray.length));
				List<Integer> milesNeededList = new LinkedList<>();
				for(String value : milesNeededListString) milesNeededList.add(Integer.valueOf(value));
				milesTable.put(dataArray[0], milesNeededList);
			}
		} catch (IOException ex) {
			throw new RuntimeException("Error in reading CSV file: " + ex);
		}
	}



	
}


