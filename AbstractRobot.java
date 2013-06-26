import java.awt.Color;
import java.util.Vector;
import java.util.EnumSet;
import java.util.Set;

abstract class AbstractRobot<E extends Enum<E>> {

    enum Orientation { NORTH, EAST, SOUTH, WEST }; // do NOT change the order of this enum
    // robot's location, orientation and name
    int row, col;
    Orientation facing;
    String name;
    String prompt; // prompt used to print messages by him to GUI
    java.awt.Color color;
    boolean carryBlock;
    Class<E> actions;
    Vector<E> planActions;
    Vector<Integer> planTimeSteps;

    AbstractRobot (int row, int col, String name, Orientation facing, java.awt.Color color, Class<E> actionsEnum) {
        this.row = row;
        this.col = col;
        this.name = new String(name);
        this.facing = facing;
        setPrompt(name);
        this.color = color;
        this.carryBlock = false;
        this.actions = actionsEnum;
        this.planActions = new Vector<E>(100);
        this.planTimeSteps = new Vector<Integer>(100);
    }

    public void enumerateVals(){
        //                for (E d : EnumSet.allOf(this.actions)) {
        //                    System.out.println(d);
        //                }
        for (E d : actions.getEnumConstants()) {
            System.out.println(d);
        }
        //        Set<E> actionsEnum = EnumSet.allOf(actions);
        //        for(E direction : actionsEnum) {
        //            // do stuff
        //        }
    }

    public static <E extends Enum<?>> void iterateOverEnumsByInstance(E e) {
        iterateOverEnumsByClass(e.getClass());
    }
    
    public static <E extends Enum<?>> void iterateOverEnumsByClass(Class<E> c) {
        for (E o: c.getEnumConstants()) {
            System.out.println(o + " " + o.ordinal());
        }
    }

    // commit to move (also for GUI)
    public abstract boolean makeMove (StateDesc state, Object action); //change Object to E?

    // used a copy c'tor for AbstractRobot
    public abstract AbstractRobot deepCopy();

    public void addActionToPlan (E action, int timeStep) {
        planActions.add(action);
        planTimeSteps.add(timeStep);
    }

    public void setPrompt (String s) {
        prompt = name + " " + s;
    }

    public String getPrompt(){
        return new String(prompt);
    }

}
