package app.logic;

public interface IContainer {

	public void setAirport(int stopNr, String setAirportName);
	public void setCountry(int stopNr, String setCountryName);
	public void unset(int stopNr);
	public void setStartZone(String setStartZone);
	public void unsetStartZone();
	public void setEndZone(String setStartZone);
	public void unsetEndZone();
	
}
