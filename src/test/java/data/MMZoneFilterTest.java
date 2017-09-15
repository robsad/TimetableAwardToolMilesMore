package data;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import app.data.entities.AirportsData;
import app.data.rulesModule.MMZoneFilter;
import app.gateaway.FormChoosen;

public class MMZoneFilterTest {

	private MMZoneFilter mmZoneFilter;
	private FormChoosen formChoosen;
	
	@Before
	public void beforeTests() {
		formChoosen = new FormChoosen(4);
		formChoosen.setAirport(0,"Poznan");
		formChoosen.setAirport(2,"Tokyo");
		mmZoneFilter = new MMZoneFilter();
	}
	
	@Test
	public void calculateZonesTest() {
		List<Set<String>> zoneCalculation = mmZoneFilter.calculateZones(formChoosen);
		
	}

}
