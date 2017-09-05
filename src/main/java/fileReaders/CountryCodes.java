package fileReaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class CountryCodes {

	private static Map<String, String> countryByCodeMap;
	private static Map<String, String> codeByCountryMap;

	public CountryCodes(File file) throws IOException {
		String path = file.getCanonicalPath();
		String csvLine;
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			while ((csvLine = br.readLine()) != null) {
                String[] dataArray = csvLine.split(";");
                countryByCodeMap.put(dataArray[0],dataArray[1]);
                codeByCountryMap.put(dataArray[1],dataArray[0]);
                //if (!countryList.contains(city.getCountry())) countryList.add(city.getCountry());
                //cityList.add(city.getCity());
			} 
		} catch (IOException ex) { throw new RuntimeException("Error in reading CSV file: "+ex); }
      }
	
	public static Map<String, String> getCountryByCodeMap() {
		return countryByCodeMap;
	}
	
	public static Map<String, String> getCodeByCountryMap() {
		return codeByCountryMap;
	}
	
	}


