// a full state description is a representation of the blocks and robots
public class StateDesc {

    /* mapLock will be used to synchronize different threads(robots)
       changes to the map (robots' actions) */
    public static Object mapLock = new Object();
    public static int timeStep = 0;

    public byte[][] gridMap;
    public AbstractRobot[] robots;
    
    public StateDesc ( byte[][] gridMap , AbstractRobot[] robots ) {
        this.gridMap = gridMap;
        this.robots = robots;
    }

    // copy c'tor
    public StateDesc ( StateDesc other ) {
        this.gridMap = copyGridMap(other.gridMap);
        this.robots = copyRobots(other.robots); // check out notes for AbstractRobot copy c'tor
    }

    public static synchronized int allocateTimeStep(){
        timeStep++;
        return timeStep;
    }

    public static int getTimeStep(){
        return timeStep;
    }

    private byte[][] copyGridMap (byte[][] map) {
        byte[][] mapCopy = new byte[map.length][map[0].length];
        for (int i=0; i<map.length; i++)
            System.arraycopy (map[i], 0, mapCopy[i], 0, map[i].length);
        return mapCopy;
    }

    private AbstractRobot[] copyRobots (AbstractRobot[] robots) {
        AbstractRobot[] retVal = new AbstractRobot[robots.length];
        for (int i=0; i<robots.length; i++)
            retVal[i] = (AbstractRobot) robots[i].deepCopy();
        return retVal;
    }

}
