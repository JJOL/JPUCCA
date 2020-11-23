package sim.app.jpuccagol;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JPUCCA {

	private static JPUCCA instance = null;
	
	private Map<String, Boolean> loadedLibMap = new HashMap<String, Boolean>();
	private List<ExitCleaneable> exitCleanables = new ArrayList<ExitCleaneable>();
	
	private JPUCCA() {
		setupSafetyMemoryManagement();
	}
	
	public static synchronized JPUCCA getInstance() {
		
		if (instance == null) {
			instance = new JPUCCA();
		}
		
		return instance;
	}
	
	public void load(String libName) {
		if (!loadedLibMap.containsKey(libName)) {
			System.loadLibrary(libName);
			loadedLibMap.put(libName, true);
		}	
	}
	
	public void cleanWhenProgramExit(ExitCleaneable cleanable) {
		exitCleanables.add(cleanable);
	}
	
	public static interface ExitCleaneable {
		void cleanWhenExit();
	}
	
	private void setupSafetyMemoryManagement() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				for (ExitCleaneable cleanable : exitCleanables)
					cleanable.cleanWhenExit();
			}
		}));
	}
}
