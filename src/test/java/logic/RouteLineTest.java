package logic;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import app.AirportsData;
import app.Connection;
import app.data.Airports;
import app.logic.RouteLine;
import junit.framework.TestCase;

public class RouteLineTest extends TestCase {
	
	private Map<String, List<Connection>> connectionsByOrigin = new HashMap<>();
	private List<AirportsData> airportsData = new ArrayList<>();
	private Airports airports;
	private Set<String> airportNamesBKK = new TreeSet<String>(Arrays.asList("Bangkok"));
	private Set<String> airportNamesPL = new TreeSet<String>(Arrays.asList("Poznan","Warsaw"));
	private Set<String> airportNamesSIN = new TreeSet<String>(Arrays.asList("Singapore"));
	private RouteLine routeLine;
	
	@Before
	public void setUp() {
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
		airportsData.add(new AirportsData("POZ", "Poznan", "PL", 0, 0));
		airportsData.add(new AirportsData("WAW", "Warsaw", "PL", 0, 0));
		airportsData.add(new AirportsData("BKK", "Bangkok", "PL", 0, 0));
		airportsData.add(new AirportsData("MUC", "Munich", "PL", 0, 0));
		airportsData.add(new AirportsData("FRA", "Frankfurt", "PL", 0, 0));
		airportsData.add(new AirportsData("SIN", "Singapore", "PL", 0, 0));
		this.airports = new Airports(airportsData,connectionsByOrigin);
	}

	
	@Test
	public void testCalculateNeighbors() {	
		this.routeLine = new RouteLine(4,0,airports,connectionsByOrigin);
		Set<String> result = routeLine.calculateNeighbors(airportNamesBKK);
		Set<String> expected = new TreeSet<String>();
		expected.add("Frankfurt");
		expected.add("Munich");
		expected.add("Singapore");
		assertEquals(expected, result);
	}
	
	@Test
	public void testCalculateNeighbors2() {	
		this.routeLine = new RouteLine(4,0,airports,connectionsByOrigin);
		Set<String> result = routeLine.calculateNeighbors(airportNamesPL);
		Set<String> expected = new TreeSet<String>();
		expected.add("Frankfurt");
		expected.add("Munich");
		expected.add("Poznan");
		expected.add("Warsaw");
		assertEquals(expected, result);
	}
	
	@Test
	public void testRecalculate() {
		this.routeLine = new RouteLine(4,0,airports,connectionsByOrigin);
		routeLine.recalculate(airportNamesPL);
		Set<String> result = routeLine.getRouteLineStop(1);
		Set<String> expected = new TreeSet<String>();
		expected.add("Frankfurt");
		expected.add("Munich");
		expected.add("Poznan");
		expected.add("Warsaw");
		assertEquals(expected, result);
	}
	
	@Test
	public void testRecalculate1() {
		this.routeLine = new RouteLine(4,1,airports,connectionsByOrigin);
		routeLine.recalculate(airportNamesSIN);
		Set<String> result = routeLine.getRouteLineStop(0);
		Set<String> expected = new TreeSet<String>();
		expected.add("Bangkok");
		assertEquals(expected, result);
	}
	
	@Test
	public void testRecalculate2() {
		this.routeLine = new RouteLine(3,2,airports,connectionsByOrigin);
		routeLine.recalculate(airportNamesSIN);
		Set<String> result = routeLine.getRouteLineStop(2);
		Set<String> expected = new TreeSet<String>();
		expected.add("Bangkok");
		assertEquals(expected, result);
	}
}
