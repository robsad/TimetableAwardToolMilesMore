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
	private List<RouteLine> routeLines = new ArrayList<>();
	private IRulesModule rulesModule;
	private Airports airports;
	private final int size;

	public Container(int size, IRulesModule rulesModule) {
		this.size = size;
		this.rulesModule = rulesModule;
		this.airports = rulesModule.getAirports();
	}

	public FormPossibles calculateRoutes(FormChoosen formChoosen) {
		IZoneFilter zonefilter = rulesModule.getZoneFilterInstance();
		List<Set<String>> zoneCalculated = zonefilter
				.calculateZones(formChoosen);
		System.out.println("zoneCalculated:" + zoneCalculated);
		formChoosen.setZoneCalculation(zoneCalculated);
		setRouteLines(formChoosen);
		FormPossibles formPossibles = calculateRoutesIntersections(isNothingChoosen(zoneCalculated));
		int mileageNeeded = rulesModule.getMilesNeeded(
			zonefilter.getStartZone(), zonefilter.getEndZone());
		formPossibles.setMileageNeeded(mileageNeeded);
		return formPossibles;
	}

	private boolean isNothingChoosen(List<Set<String>> zoneCalculated) {
		boolean test = true; 
		for(Set<String> zone : zoneCalculated) {
			if (!zone.contains(ALL)) test = false;
		}
		return test;
	}
	
	private void setRouteLines(FormChoosen formChoosen) {
		routeLines.clear();
		for (int i = 0; i < size; i++) {
			routeLines.add(new RouteLine(size, i, formChoosen, rulesModule));
		}
	}

	private FormPossibles calculateRoutesIntersections(boolean nothingChoosen) {
		FormPossibles formPossibles = new FormPossibles(size);
		for (int i = 0; i < size; i++) {
			Set<String> intersection;
			if (nothingChoosen) {
				intersection = airports.getAllAirportNames();
			} else {
				intersection = intersection(i);
			}
			formPossibles.setAirports(i, intersection);
			Set<String> possibleCoutries = calculatePossibleCountry(intersection);
			System.out.println("Inter: Leg nr " + i + " " + intersection);
			formPossibles.setCountries(i, possibleCoutries);
			formPossibles.setZones(i, calculatePossibleZones(possibleCoutries));
		}
		return formPossibles;
	}

	private Set<String> calculatePossibleCountry(Set<String> possiblePorts) {
		Set<String> possibleCountries = new TreeSet<String>();
		for (String port : possiblePorts) {
				possibleCountries.add(airports.getAirportsCountryName(port));
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

	private Set<String> intersection(int i) {
		Set<String> intersection = null;
		for(int j=0 ; j < size; j++ ) {
			RouteLine routeLine = routeLines.get(j);
			if (routeLine.isRouteLineActive()) {
				Set<String> routeStop = routeLine.getRouteLineStop(i);
				if (intersection==null) {
					intersection=routeStop;
				} else {
					intersection.retainAll(routeStop);
				}
			}
		}
		System.out.println("INTERSECTION " + i + " result: " + intersection);
		System.out.println("");
		return intersection;
	}

}
