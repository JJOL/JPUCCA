/*
 * GeoGuiTestGUI
 *
 * $Id: GeoGuiTestGUI.java,v 1.1 2010-04-05 17:07:11 mcoletti Exp $
 * 
 */

package sim.app.geo.nearbyworld;

import com.vividsolutions.jts.io.ParseException;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import sim.display.Console;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.geo.GeomFieldPortrayal;
import sim.portrayal.simple.OvalPortrayal2D;


/** MASON GUI wrapper for GeoGuiTestGUI
 *
 * @author mcoletti
 */
public class GeoGuiTestGUI extends GUIState {

    private Display2D display;
    private JFrame displayFrame;

    private GeomFieldPortrayal worldFieldPortrayal = new GeomFieldPortrayal();
    private GeomFieldPortrayal agentFieldPortrayal = new GeomFieldPortrayal();

    
    public GeoGuiTestGUI(SimState state) { super(state); }

    public GeoGuiTestGUI() throws ParseException { super(new GeoGuiTest(System.currentTimeMillis())); }


    public void init(Controller controller)
    {
        super.init(controller);

        display = new Display2D(300, 300, this, 1);
        display.attach(worldFieldPortrayal, "World");
        display.attach(agentFieldPortrayal, "Agent");

        displayFrame = display.createFrame();
        controller.registerFrame(displayFrame);
        displayFrame.setVisible(true);
    }

    public void start()
    {
        super.start();
        setupPortrayals();
    }

    private void setupPortrayals()
    {
        GeoGuiTest world = (GeoGuiTest)state;

        worldFieldPortrayal.setField(world.world);

        agentFieldPortrayal.setField(world.agent);
        // We want a red dot for the agent.  We also need to specify the scale; if
        // we don't then the default agent dot will cover the entire area.
        //
        // TODO: would be nice if the scale could be autogenerated with sensible
        // default values instead of relying on trial and error.
        agentFieldPortrayal.setPortrayalForAll(new OvalPortrayal2D(Color.RED,0.01));

        display.reset();
        display.setBackdrop(Color.WHITE);
        display.repaint();
    }

	public static void main(String[] args)
    {
        GeoGuiTestGUI worldGUI = null;
        
        try
        {
            worldGUI = new GeoGuiTestGUI();
        }
        catch (ParseException ex)
        {
            Logger.getLogger(GeoGuiTestGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        Console console = new Console(worldGUI);
        console.setVisible(true);
    }
    
}