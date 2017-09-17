package app.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import app.data.Airports;
import app.data.entities.AirportsData;
import app.data.entities.Connection;
import app.data.rulesModule.IRulesModule;
import app.data.rulesModule.IZoneFilter;
import app.gateaway.FormChoosen;
import app.gateaway.FormPossibles;

public class Container {

	static final String ALL = "All";
	static final String ANY_PORT = "Any Airport";
	static final String ANY_COUNT = "Any Country";
	private List<RouteLine> routeLines = new ArrayList<>();
	private IRulesModule rulesModule;
	private Airports airports;
	private final int size;

	public Container(int size, IRulesModule rulesModule) {
		this.size = size;
		this.rulesModule = rulesModule;
		this.airports = rulesModule.getAirports();
	}

	public FormChoosen getFormChoosen() {
		return new FormChoosen(size);
	}

	public FormPossibles calculateRoutes(FormChoosen formChoosen) {
		IZoneFilter zonefilter = rulesModule.getZoneFilterInstance();
		List<Set<String>> zoneCalculated = zonefilter
				.calculateZones(formChoosen);
		//System.out.println("zoneCalculated:" + zoneCalculated);
		formChoosen.setZoneCalculation(zoneCalculated);
		setRouteLines(formChoosen);
		FormPossibles formPossibles = calculateRoutesIntersections(formChoosen);
		int mileageNeeded = rulesModule.getMilesNeeded(
				zonefilter.getStartZone(), zonefilter.getEndZone());
		formPossibles.setMileageNeeded(mileageNeeded);
		formPossibles.setZoneStart(zonefilter.getStartZone());
		formPossibles.setZoneEnd(zonefilter.getEndZone());
		formPossibles.setAirlines(getAirlines(formChoosen));
		return formPossibles;
	}

	private void setRouteLines(FormChoosen formChoosen) {
		routeLines.clear();
		for (int i = 0; i < size; i++) {
			routeLines.add(new RouteLine(size, i, formChoosen, rulesModule));
		}
	}

	private FormPossibles calculateRoutesIntersections(FormChoosen formChoosen) {
		FormPossibles formPossibles = new FormPossibles(size);
		for (int i = 0; i < size; i++) {
			Set<String> intersection;
			if (formChoosen.isNothingChoosen()) {
				intersection = new TreeSet<String>(airports.getAllAirportNames());
			} else {
				intersection = intersection(i,formChoosen.getAirport(i));
			}
			formPossibles.setAirports(i, intersection);
			Set<String> possibleCountries = calculatePossibleCountry(formChoosen.getAirport(i), intersection);
			formPossibles.setCountries(i, possibleCountries);
			formPossibles.setZones(i, calculatePossibleZones(possibleCountries));
		}
		return formPossibles;
	}

	private Set<String> calculatePossibleCountry(String choosenAirport, Set<String> possiblePorts) {
		Set<String> possibleCountries = new TreeSet<String>();
		if (!choosenAirport.equals(ALL)) {
			possibleCountries.add(airports.getAirportsCountryName(choosenAirport));
		} else {
			for (String port : possiblePorts) {
				possibleCountries.add(airports.getAirportsCountryName(port));
			}
		}
		return possibleCountries;
	}

	private Set<String> calculatePossibleZones(Set<String> possibleCoutries) {
		Set<String> possibleZones = new TreeSet<String>();
		for (String country : possibleCoutries) {
			possibleZones.add(rulesModule.getCountryNameZone(country));
		}
		return possibleZones;
	}

	private Set<String> intersection(int position,String choosenAirport) {
		Set<String> intersection = null;
		System.out.println("Position: "+position);
		for(int j=0 ; j < size; j++ ) {
			RouteLine routeLine = routeLines.get(j);
			if (routeLine.isRouteLineActive()) {
				Set<String> routeStop = routeLine.getRouteLineStop(position);
				System.out.println("Pobrany routeStop "+routeStop+"Pobrany routeLine "+routeLine);
				if ((position==j)&&(!choosenAirport.equals(ALL))) {
					System.out.println("choosenAirport " + choosenAirport + " routeStop przed: " + routeStop);
					routeStop = new TreeSet<String>(airports.getAllAirportNames());
					System.out.println(airports.getAllAirportNames());
					System.out.println("Route stop po zmianie " + j + " result: " + routeStop);
				}
				if (intersection==null) {
					intersection=routeStop;
				} else {
					intersection.retainAll(routeStop);
				}
				System.out.println("Route stop " + j + " result: " + routeStop);
			} else {
				System.out.println("routeLines " + j + " NOT active");
			}
		}
		System.out.println("INTERSECTION " + position + " result: " + intersection);
		System.out.println("");
		return intersection;
	}

	private List<String> getAirlines(FormChoosen formChoosen) {
		List<String> airlines = new ArrayList<String>();
		for (int i = 1; i < size; i++) {
			String choosenAirportLast = formChoosen.getAirport(i-1);
			String choosenAirportThis = formChoosen.getAirport(i);
			if ((!choosenAirportLast.equals(ALL))&&(!choosenAirportThis.equals(ALL))) {
				airlines.add("Flight " + i + " operated by " + rulesModule.getAirline(choosenAirportLast,choosenAirportThis));
			} else {
				airlines.add("Flight " + i );
			}
		}
		airlines.add("");
		return airlines;
	}

}
