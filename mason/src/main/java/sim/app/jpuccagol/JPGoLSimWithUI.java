package sim.app.jpuccagol;

import java.awt.Color;
import java.lang.management.ManagementFactory;

import javax.swing.JFrame;

import sim.display.Console;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.grid.FastValueGridPortrayal2D;
import sim.util.gui.ColorMap;

public class JPGoLSimWithUI extends GUIState {

	public Display2D display;
	public JFrame displayFrame;
	FastValueGridPortrayal2D cellsPortrayal = new FastValueGridPortrayal2D();
	
	public static void main(String[] args) {
		JPGoLSimWithUI vid = new JPGoLSimWithUI();
		Console c = new Console(vid);
		c.setVisible(true);
	}
	
	public JPGoLSimWithUI(SimState state) {
		super(state);
	}
	
	public JPGoLSimWithUI() {
		super(new JPGoLSim(System.currentTimeMillis()));
	}
	
	public static String getName() {
		return "JPUCCA GoL Sim";
	}
	
	@Override
	public void start() {
		super.start();
		setupPortrayals();
	}
	
	public void setupPortrayals() {
		JPGoLSim jpGolSim = (JPGoLSim)state;
		cellsPortrayal.setField(jpGolSim.golSpaceGrid);
		
		cellsPortrayal.setMap(new ColorMap() {
			public boolean validLevel(double level) {
				return level > 0.5;
			}
			public int getRGB(double level) {
				if (level > 0.5)
					return 0xffffffff;
				else
					return 0;
			}
			public Color getColor(double level) {
				if (level > 0.5)
					return Color.white;
				else
					return Color.black;
			}
			public int getAlpha(double level) {
				return 1;
			}
			public double defaultValue() {
				return 0;
			}
		});
		
		display.reset();
		display.setBackdrop(Color.white);
		display.repaint();
	}
	
	@Override
	public void init(Controller c) {
		super.init(c);
		
		
		display = new Display2D(600, 600, this);
		display.setClipping(false);
		
		displayFrame = display.createFrame();
		displayFrame.setTitle(getName());
		c.registerFrame(displayFrame);
		displayFrame.setVisible(true);
		display.attach(cellsPortrayal, "Cells");
	}
	
	@Override
	public void quit() {
		super.quit();
		
		if (displayFrame != null) displayFrame.dispose();
		displayFrame = null;
		display = null;
	}
	
	

}
