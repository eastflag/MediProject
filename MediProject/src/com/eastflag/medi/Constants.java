package com.lgcns.wd;

public final class Constants {
	
	public static final String HOST = "http://192.168.0.11:9510";
	
	//Auto Moving
	public static final String API_AUTO_READ = "/AUTOMOVING/READ";
	public static final String API_AUTO_WRITE = "/AUTOMOVING/WRITE";
	
	//환기제어
	public static final String API_VENTILATOR_READ = "/VENTILATOR/READ";
	public static final String API_VENTILATOR_WRITE = "/VENTILATOR/WRITE";
	public static final String API_VENTILATOR_READCO2 = "/VENTILATOR/READCO2";

	//Smart Glass
	public static final String API_SMARTGLASS_READ = "/SMARTGLASS/READ";
	public static final String API_SMARTGLASS_WRITEALL = "/SMARTGLASS/WRITEALL";
	public static final String API_SMARTGLASS_WRITE = "/SMARTGLASS/WRITE";
	
	//방범
	public static final String API_WATCHDOG_READ = "/WATCHDOG/READ";
	public static final String API_WATCHDOG_WRITE = "/WATCHDOG/WRITE";
	public static final String API_WATCHDOG_READDOOR = "/WATCHDOG/READDOOR";
}
