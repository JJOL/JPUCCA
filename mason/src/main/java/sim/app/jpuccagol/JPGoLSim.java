package sim.app.jpuccagol;

import bsh.Console;
import jogamp.graph.font.typecast.ot.table.GposTable;
import sim.app.students.LinealIntGrid2D;
import sim.display.SimpleController;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.field.grid.IntGrid2D;

public class JPGoLSim extends SimState implements Steppable {
	
	private static final String JPGoL_LIB_NAME = "sim_app_jpuccagol_JPGoLCA";
	private static final long serialVersionUID = 1L;
	
	final int gridN = 50;
	
	LinealIntGrid2D golSpaceGrid = new LinealIntGrid2D(gridN, gridN);
	JPGoLCA jpGolCA;
	
	private int[] gridBuffer;

	public JPGoLSim(long seed) {
		super(seed);
	}
	
	@Override
	public void start() {
		super.start();
		System.out.println("New Run!");
		
		JPUCCA.getInstance().load(JPGoL_LIB_NAME);
		jpGolCA = new JPGoLCA();
		jpGolCA.bind(golSpaceGrid.toArray(), gridN);
		
		golSpaceGrid.setTo(0);
		golSpaceGrid.set(5, 5, 1);
		golSpaceGrid.set(5, 6, 1);
		golSpaceGrid.set(5, 7, 1);
		golSpaceGrid.set(45, 15, 1);
		golSpaceGrid.set(45, 16, 1);
		golSpaceGrid.set(45, 17, 1);
		
		gridBuffer = new int[gridN*gridN];
		
		jpGolCA.write(golSpaceGrid.toArray(), gridN);
		
//		schedule.scheduleRepeating(new PlainGoLCA());
		schedule.scheduleRepeating(this);
	}

	@Override
	public void step(SimState state) {
		jpGolCA.step();
		jpGolCA.read(gridBuffer, gridN);
		golSpaceGrid.setTo(gridBuffer);
	}
	
	@Override
	public void awakeFromCheckpoint() {
		super.awakeFromCheckpoint();
		System.out.println("AQUI VOLVEMOS A SINCRONIZAR DATOS CON PUCCA!");
		jpGolCA.write(golSpaceGrid.toArray(), gridN);
	}
	
	@Override
	public void postCheckpoint() {
		super.postCheckpoint();
		System.out.println("AQUI VOLVEMOS A SINCRONIZAR DATOS CON PUCCA!");
		jpGolCA.write(golSpaceGrid.toArray(), gridN);
		jpGolCA.ready();
		jpGolCA.ready();
	}
	
	
	
	public static void main(String[] args) {
		System.out.println("GOT CALLED!");
		doLoop(JPGoLSim.class, args);
		System.exit(0);
	}

}
