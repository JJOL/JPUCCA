package sim.app.jpuccagol;

import java.io.Serializable;

public class JPGoLCA implements JPUCCA.ExitCleaneable, Serializable{

	private int gridN;
	
	public JPGoLCA() {
		this(0);
	}
	
	public JPGoLCA(int n) {
		gridN = n;
		JPUCCA.getInstance().cleanWhenProgramExit(this);
	}
	
	public int getGridN() {
		return gridN;
	}
	
	public native boolean bind(int arr[], int n);
	public native boolean ready();
	public native boolean step();
	public native boolean read(int arr[], int n);
	public native boolean write(int arr[], int n);
	public native boolean done();
	
	public void cleanWhenExit() {
		done();
	}
	
}
