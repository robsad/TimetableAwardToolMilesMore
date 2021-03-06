package data;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import app.data.Airports;
import app.data.entities.AirportsData;

public class AirportsTest {

	private Airports airports;
	
	@Before
	public void AirportsTestBefore(){
	List<AirportsData> airportsData = new ArrayList<>();
	airportsData.add(new AirportsData("POZ", "Poznan", "PL", 0, 0));
	airportsData.add(new AirportsData("WAW", "Warsaw", "PL", 0, 0));
	airportsData.add(new AirportsData("BKK", "Bangkok", "PL", 0, 0));
	airportsData.add(new AirportsData("MUC", "Munich", "PL", 0, 0));
	airportsData.add(new AirportsData("FRA", "Frankfurt", "PL", 0, 0));
	Map<String, String> countryByCode = new HashMap<>();
	countryByCode.put("PL","Poland");
	countryByCode.put("TH","Thailand");
	countryByCode.put("DE","Germany");
	countryByCode.put("SG","Singapore");
	airports = new Airports(airportsData, countryByCode);
	}
	
	@Test
	public void getAirportNamesTest() {
		Set<String> result = airports.getAllAirportNames();
		Set<String> expected = new TreeSet<String>(Arrays.asList("Bangkok", "Frankfurt", "Munich", "Poznan", "Warsaw"));
		assertEquals(expected, result);
	}
	
	@Test
	public void getAirportCodeByNameTest() {
		String result = airports.getAirportCodeByName("Poznan");
		assertEquals("POZ", result);
	}
	
	@Test
	public void getAirportNameByCodeTest() {
		String result = airports.getAirportNameByCode("POZ");
		assertEquals("Poznan", result);
	}
}
