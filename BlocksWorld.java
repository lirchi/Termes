import java.io.*;
import java.util.*;
import java.awt.Color;

public class BlocksWorld {
    
    static byte[][] initial_state;
    static byte[][] goal_state;
    static StateDesc state;
    static AbstractRobot[] robots;
    static BlocksWorldGUI gui;

    final static int NUMOFAGENTS = 2;
    final static boolean GUI_ON = true;
    final static boolean VERBOSE_ON = true;

    public static void main (String[] args) throws Exception {
        // read initial and goal states
        try {
            initial_state = readMap(args[0]);
            goal_state = readMap(args[1]);
        } catch (Exception e) {
            System.out.println ("Usage: args[0]: init ; args[1]: goal");
            e.printStackTrace();
            System.exit(0);
        }

        // create robots
        robots = new AbstractRobot[NUMOFAGENTS];
        robots[0] = new TermesBot(0,0,"R1",AbstractRobot.Orientation.EAST, Color.BLUE );
        robots[1] = new TermesBot(1,1,"R2",AbstractRobot.Orientation.WEST, Color.RED );

        // create initial state description
        state = new StateDesc (initial_state, robots);

        // create AI Engines (as Threads), start each, and join each
        Thread[] engines = new Thread[NUMOFAGENTS];
        engines[0] = new Thread(new RandomTermesBotEngine(state, (TermesBot)robots[1]));
        engines[1] = new Thread(new BuildFixedRampTermes(state, (TermesBot)robots[0]));
        for (int i=0; i<NUMOFAGENTS; i++)
            engines[i].start();
        for (int i=0; i<NUMOFAGENTS; i++)
            engines[i].join();

        // the following is extremely inefficient. Solely for GUI purposes (AFTER we have all plans)
        if (VERBOSE_ON)
            for (int i=0; i<NUMOFAGENTS; i++) {
                System.out.println (robots[i].planActions);        
                System.out.println (robots[i].planTimeSteps);
            }
                Thread.sleep(3000);
        if (GUI_ON)
            gui = new BlocksWorldGUI(initial_state.length, initial_state[0].length, robots.length);

        int[] indices = new int[NUMOFAGENTS];
        for (int currTimeStep=1; currTimeStep<=state.timeStep; currTimeStep++) {
            String prompt = "Timestep:" + currTimeStep + "\n";
            for (int currRobot=0; currRobot<NUMOFAGENTS; currRobot++) {
                if (robots[currRobot].planTimeSteps.contains(currTimeStep)) {
                    robots[currRobot].makeMove (state, robots[currRobot].planActions.elementAt(indices[currRobot]));
                    prompt = prompt + "   Robot:" + robots[currRobot].name + " action:" + robots[currRobot].planActions.elementAt(indices[currRobot]) + " action#:" + (indices[currRobot]+1) + "\n";
                    indices[currRobot]++;
                }
            }
            if (VERBOSE_ON)
                System.out.println (prompt);
            if (GUI_ON) {
                gui.updateDisplay (state, robots, prompt);
                Thread.sleep(1000);
            }
        }
        
        if ( Arrays.deepEquals (state.gridMap, goal_state) == true )
            System.out.print("Plan succeeded to achieve the goal!");
        else
            System.out.println("Plan failed to achieve the goal!");
        System.exit(1);
    }

    public static byte[][] readMap (String fname) {
        MapFileReader mapFile = new MapFileReader(fname);
        return mapFile.getMap();
    }
}

