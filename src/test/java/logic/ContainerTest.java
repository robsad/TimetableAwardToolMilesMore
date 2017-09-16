package logic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import app.data.Airports;
import app.data.entities.AirportsData;
import app.data.entities.Connection;
import app.data.rulesModule.IRulesModule;
import app.data.rulesModule.RulesModuleFactory;
import app.gateaway.FormChoosen;
import app.gateaway.FormPossibles;
import app.logic.Container;
import app.logic.RouteLine;

public class ContainerTest {

	private IRulesModule milesMoreModule;
	
	@Before
	public void setUp() {
		List<AirportsData> airportsData = new ArrayList<>();
		airportsData.add(new AirportsData("POZ", "Poznan", "PL", 0, 0));
		airportsData.add(new AirportsData("WAW", "Warsaw", "PL", 0, 0));
		airportsData.add(new AirportsData("BKK", "Bangkok", "TH", 0, 0));
		airportsData.add(new AirportsData("MUC", "Munich", "DE", 0, 0));
		airportsData.add(new AirportsData("FRA", "Frankfurt", "DE", 0, 0));
		airportsData.add(new AirportsData("SIN", "Singapore", "SG", 0, 0));
		Map<String, List<Connection>> connectionsByOrigin = new HashMap<>();
		List<Connection> connectionsPOZ = new ArrayList<>();
		connectionsPOZ.add(new Connection("WAW",null,"LO"));
		connectionsPOZ.add(new Connection("FRA",null,"LH"));
		connectionsPOZ.add(new Connection("MUC",null,"LH"));
		connectionsByOrigin.put("POZ",connectionsPOZ);
		List<Connection> connectionsWAW = new ArrayList<>();
		connectionsWAW.add(new Connection("POZ",null,"LO"));
		connectionsWAW.add(new Connection("FRA",null,"LH"));
		connectionsWAW.add(new Connection("MUC",null,"LH"));
		connectionsByOrigin.put("WAW",connectionsWAW);
		List<Connection> connectionsFRA = new ArrayList<>();
		connectionsFRA.add(new Connection("POZ",null,"LH"));
		connectionsFRA.add(new Connection("WAW",null,"LH"));
		connectionsFRA.add(new Connection("MUC",null,"LH"));
		connectionsByOrigin.put("FRA",connectionsFRA);
		List<Connection> connectionsMUC = new ArrayList<>();
		connectionsMUC.add(new Connection("POZ",null,"LH"));
		connectionsMUC.add(new Connection("WAW",null,"LH"));
		connectionsMUC.add(new Connection("FRA",null,"LH"));
		connectionsMUC.add(new Connection("BKK",null,"LH"));
		connectionsByOrigin.put("MUC",connectionsMUC);
		List<Connection> connectionsBKK = new ArrayList<>();
		connectionsBKK.add(new Connection("FRA",null,"LH"));
		connectionsBKK.add(new Connection("MUC",null,"LH"));
		connectionsBKK.add(new Connection("SIN",null,"SQ"));
		connectionsByOrigin.put("BKK",connectionsBKK);
		List<Connection> connectionsSIN = new ArrayList<>();
		connectionsSIN.add(new Connection("BKK",null,"SQ"));
		connectionsByOrigin.put("SIN",connectionsSIN);
		Map<String, String> countryByCode = new HashMap<>();
		countryByCode.put("PL","Poland");
		countryByCode.put("TH","Thailand");
		countryByCode.put("DE","Germany");
		countryByCode.put("SG","Singapore");
		Airports airports = new Airports(airportsData,countryByCode);
		RulesModuleFactory rulesFactory = new RulesModuleFactory();
    	milesMoreModule = rulesFactory.getModule("MilesMore", airports, connectionsByOrigin);
	}
	
	@Test
	public void testCalculateRoutes1() {
		FormChoosen formChoosen = new FormChoosen(4);
		formChoosen.setCountry(0,"Singapore");
		formChoosen.setCountry(3,"Poland");
		formChoosen.setZoneRule(false);
		Container container = new Container(4,milesMoreModule);
		FormPossibles formPossibles = container.calculateRoutes(formChoosen);
		Set<String> result =  formPossibles.getAirports(2);
		Set<String> expected = new TreeSet<String>();
		expected.add("Frankfurt");
		expected.add("Munich");
		assertEquals(expected, result);
	}

	@Test
	public void testCalculateRoutes2() {
		Container container = new Container(3,milesMoreModule);
		FormChoosen formChoosen = container.getFormChoosen();
		formChoosen.setCountry(0,"Singapore");
		formChoosen.setCountry(2,"Germany");
		formChoosen.setZoneRule(false);
		FormPossibles formPossibles = container.calculateRoutes(formChoosen);
		Set<String> result =  formPossibles.getAirports(2);
		Set<String> expected = new TreeSet<String>();
		expected.add("Frankfurt");
		expected.add("Munich");
		assertEquals(expected, result);
	}
	
	@Test
	public void testCalculateRoutes3() {
		FormChoosen formChoosen = new FormChoosen(4);
		formChoosen.setCountry(0,"Singapore");
		formChoosen.setAirport(3,"Poznan");
		formChoosen.setZoneRule(false);
		Container container = new Container(4,milesMoreModule);
		FormPossibles formPossibles = container.calculateRoutes(formChoosen);
		Set<String> result =  formPossibles.getAirports(1);
		Set<String> expected = new TreeSet<String>();
		expected.add("Bangkok");
		assertEquals(expected, result);
	}
	
	@Test
	public void testCalculateRoutes4() {
		FormChoosen formChoosen = new FormChoosen(4);
		formChoosen.setCountry(0,"Singapore");
		formChoosen.setCountry(3,"Poland");
		formChoosen.setZoneRule(false);
		Container container = new Container(4,milesMoreModule);
		FormPossibles formPossibles = container.calculateRoutes(formChoosen);
		Set<String> result =  formPossibles.getCountries(2);
		Set<String> expected = new TreeSet<String>();
		expected.add("Germany");
		assertEquals(expected, result);
	}
}
