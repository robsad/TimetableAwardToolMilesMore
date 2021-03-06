package app.gateaway;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class FormChoosen {

	static final String ALL = "All";
	private int size;
	private List<String> choosenAirports = new ArrayList<>();
	private List<String> choosenCountries = new ArrayList<>();
	private List<Set<String>> zoneCalculation;
	private String startZone = ALL;
	private String endZone = ALL;
	private boolean zoneRule = true;
	
	public FormChoosen(int size) {
		this.size = size;
		for(int i=0 ; i < size; i++ ) {
			choosenAirports.add(ALL);
			choosenCountries.add(ALL);
		}
	}
	
	public int getSize() {
		return size;
	}

	public void setAirport(int i, String airportName) {
		choosenAirports.set(i, airportName);
	}
	
	public void setCountry(int i, String countryName) {
		choosenCountries.set(i, countryName);
	}
	
	public void setStartZone(String startZone) {
		this.startZone = startZone;
	}
	
	public void setEndZone(String endZone) {
		this.endZone = endZone;
	}
	
	public void setZoneRule(boolean zoneRule) {
		this.zoneRule = zoneRule;
	}
	
	public String getAirport(int i) {
		return choosenAirports.get(i);
	}
	
	public String getCountry(int i) {
		return choosenCountries.get(i);
	}
	
	public String getStartZone() {
		return startZone;
	}
	
	public String getEndZone() {
		return endZone;
	}
	
	public boolean isZoneRule() {
		return zoneRule;
	}
	
	public void setZoneCalculation(List<Set<String>> zoneCalculation) {
		this.zoneCalculation = zoneCalculation;
	}
	
	public Set<String> getZoneCalculation(int i) {
		return zoneCalculation.get(i);
	}
	
	public boolean isNothingChoosen() {
		boolean test = true;
		for(Set<String> zone : zoneCalculation) {
			if (!zone.contains("All")) test = false;
		}
		return test;
	}
	
	public String toString() {
		return "choosenAirports: " + choosenAirports.toString() +
				"choosenCountries" + choosenCountries.toString() +
				 " Start: " + startZone + " End: " + endZone;	 
	}
	
}
