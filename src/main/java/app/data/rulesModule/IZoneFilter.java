package app.data.rulesModule;

import java.util.List;
import java.util.Set;

import app.gateaway.FormChoosen;

public interface IZoneFilter {
	List<Set<String>> calculateZones(FormChoosen formChoosen);
	String getStartZone();
	String getEndZone();
}
