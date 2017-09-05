package app.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import app.AirportsData;
import app.Connection;
import app.data.Airports;
import app.data.rulesModule.IRulesModule;
import app.data.rulesModule.IZoneFilter;
import app.gateaway.FormChoosen;
import app.gateaway.FormPossibles;

public class Container  {
	
	private List<RouteLine> routeLines = new ArrayList<>();
	private IRulesModule rulesModule;
	private Airports airports;
	private final int size;
	
	public Container(int size, IRulesModule rulesModule){
		this.size = size;
		this.rulesModule = rulesModule;
		this.airports = rulesModule.getAirports();	
	}
	
	public FormPossibles setRouteLines(FormChoosen formChoosen) {
		routeLines.clear();
		IZoneFilter zonefilter = rulesModule.getZoneFilterInstance();
		List<Set<String>> zoneCalculation = zonefilter.calculateZones(formChoosen);
		formChoosen.setZoneCalculation(zoneCalculation);
		System.out.println(zoneCalculation);
		for(int i=0 ; i < size; i++ ) {
			routeLines.add(new RouteLine(size,i,formChoosen,rulesModule));
		}
		System.out.println("RouteLines created");
		return recalculate();
	}
	
	private FormPossibles recalculate() {
		FormPossibles formPossibles = new FormPossibles(size);
		for(int i=0 ; i < size; i++ ) {
			formPossibles.setAirports(i, intersection(i));
			System.out.println("nr: "+i+" inter: "+formPossibles.getAirports(i));
			formPossibles.setCountries(i, calculatePossibleCountry(i, intersection(i)));
		}
		return formPossibles;
	}
	
	private Set<String> calculatePossibleCountry(int i, Set<String> possiblePorts) {
	Set<String> possibleCountries = new TreeSet<String>();
	for(String port : possiblePorts) {
		if (port.equals("All")) {
			possibleCountries.add("All");
		} else {
		possibleCountries.add(airports.getAirportsCountryName(port));
		}
	}
	if (possibleCountries.isEmpty()) {
		possibleCountries.add("All");
	}
	return possibleCountries;
	}
	
	private Set<String> intersection(int i) {
		Set<String> intersection = new TreeSet<>();
		for(int j=0 ; j < size; j++ ) {
			RouteLine routeLine = routeLines.get(j);
			Set<String> routeStop = routeLine.getRouteLineStop(i);
			//System.out.println("nr: "+i+" route(j): "+j+" routestop: "+routeStop);   //komentarz
			if (!routeStop.isEmpty()) {
				if (intersection.isEmpty()) {
					intersection=routeStop; 
				} else {
					intersection.retainAll(routeStop);
				}		
			}	
		}
		if (intersection.isEmpty()) {
			intersection.add("All");
		}
		return intersection;
	}
	
}
