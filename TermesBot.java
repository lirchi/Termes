import java.lang.Math;

enum TermesActions { 
    TURN_RIGHT , TURN_LEFT , GO_FORWARD , PICK_UP , PUT_DOWN;
    /*    public TermesActions getNext() {
        return values()[(ordinal()+1) % values().length];
    }
    public TermesActions getLast() {
        return values()[(this.ordinal() + values().length - 1) % values().length];
        }*/
};

public class TermesBot extends AbstractRobot<TermesActions> {
    
    public TermesBot(int row, int col, String name, Orientation facing, java.awt.Color color) {
        super (row, col, name, facing, color, TermesActions.class);
    }

    public AbstractRobot deepCopy() {
        // !!! note that deepCopy does NOT duplicate actions, planActions and planTimeSteps
        return new TermesBot(row, col, name, facing, color);
    }
    
    public boolean makeMove(StateDesc state, Object action) {

        switch ((TermesActions) action) {
        case TURN_RIGHT:
            if (facing == Orientation.NORTH) {
                facing = Orientation.EAST;
                return true;
            }
            if (facing == Orientation.EAST) {
                facing = Orientation.SOUTH;
                return true;
            }
            if (facing == Orientation.SOUTH) {
                facing = Orientation.WEST;
                return true;
            }
            if (facing == Orientation.WEST) {
                facing = Orientation.NORTH;
                return true;
            }
            return false;
            //            break; // END OF TURN_RIGHT
        case TURN_LEFT:
            if (facing == Orientation.NORTH) {
                facing = Orientation.WEST;
                return true;
            }
            if (facing == Orientation.EAST) {
                facing = Orientation.NORTH;
                return true;
            }
            if (facing == Orientation.SOUTH) {
                facing = Orientation.EAST;
                return true;
            }
            if (facing ==Orientation.WEST) {
                facing = Orientation.SOUTH;
                return true;
            }
            return false;
            //            break; // END OF TURN_LEFT
        case GO_FORWARD:
            if (facing == Orientation.WEST && row>0)
                if (Math.abs(state.gridMap[row][col] - state.gridMap[row-1][col]) <= 1)
                    if (!robotAt(row-1,col,state)) {
                        row--;
                        return true;
                    }
            if (facing == Orientation.SOUTH && col<state.gridMap[0].length-1)
                if (Math.abs(state.gridMap[row][col] - state.gridMap[row][col+1]) <= 1)
                    if (!robotAt(row,col+1,state)) {
                        col++;
                        return true;
                    }
            if (facing == Orientation.EAST && row<state.gridMap.length-1)
                if (Math.abs(state.gridMap[row][col] - state.gridMap[row+1][col]) <= 1)
                    if (!robotAt(row+1,col,state)) {
                        row++;
                        return true;
                    }
            if (facing == Orientation.NORTH && col>0)
                if (Math.abs(state.gridMap[row][col] - state.gridMap[row][col-1]) <= 1)
                    if (!robotAt(row,col-1,state)) {
                        col--;
                        return true;
                    }
            return false;
            //            break; // END OF GO_FORWARD
        case PICK_UP:
            // SUPPLY POINT
            if (facing == Orientation.EAST && row == state.gridMap[0].length-1) {
                carryBlock = true;
                return true;
            }    
            // ALL THE REST
            if (!carryBlock && facing == Orientation.WEST && row>0)
                if (state.gridMap[row][col] + 1 == state.gridMap[row-1][col])
                    if (!robotAt(row-1,col,state)) {
                        carryBlock = true;
                        synchronized(state.mapLock) {
                            state.gridMap[row-1][col]--; //Synchronize!
                        }
                        return true;
                    }
            if (!carryBlock && facing == Orientation.SOUTH && col<state.gridMap[0].length-1)
                if (state.gridMap[row][col] + 1 == state.gridMap[row][col+1])
                    if (!robotAt(row,col+1,state)) {
                        carryBlock = true;
                        synchronized(state.mapLock) {
                            state.gridMap[row][col+1]--; //Synchronize!
                        }
                        return true;
                    }
            if (!carryBlock && facing == Orientation.EAST && row<state.gridMap.length-1)
                if (state.gridMap[row][col] + 1 == state.gridMap[row+1][col])
                    if (!robotAt(row+1,col,state)) {
                        carryBlock = true;
                        synchronized(state.mapLock) {
                            state.gridMap[row+1][col]--; //Synchronize!
                        }
                        return true;
                    }
            if (!carryBlock && facing == Orientation.NORTH && col>0)
                if (state.gridMap[row][col] + 1 == state.gridMap[row][col-1])
                    if (!robotAt(row,col-1,state)) {
                        carryBlock = true;
                        synchronized(state.mapLock) {
                            state.gridMap[row][col-1]--; //Synchronize!
                        }
                        return true;
                    }
            return false;
            //            break; // END OF PICK_UP
        case PUT_DOWN:
            // SUPPLY POINT
            if (facing == Orientation.EAST && row == state.gridMap[0].length-1) {
                carryBlock = false;
                return true;
            }    
            // ALL THE REST
            if (carryBlock && facing == Orientation.WEST && row>0)
                if (state.gridMap[row][col] == state.gridMap[row-1][col])
                    if (!robotAt(row-1,col,state)) {
                        carryBlock = false;
                        synchronized(state.mapLock) {
                            state.gridMap[row-1][col]++; //Synchronize!
                        }
                        return true;
                    }
            if (carryBlock && facing == Orientation.SOUTH && col<state.gridMap[0].length-1)
                if (state.gridMap[row][col] == state.gridMap[row][col+1])
                    if (!robotAt(row,col+1,state)) {
                        carryBlock = false;
                        synchronized(state.mapLock) {
                            state.gridMap[row][col+1]++; //Synchronize!
                        }
                        return true;
                    }
            if (carryBlock && facing == Orientation.EAST && row<state.gridMap.length-1)
                if (state.gridMap[row][col] == state.gridMap[row+1][col])
                    if (!robotAt(row+1,col,state)) {
                        carryBlock = false;
                        synchronized(state.mapLock) {
                            state.gridMap[row+1][col]++; //Synchronize!
                        }
                        return true;
                    }
            if (carryBlock && facing == Orientation.NORTH && col>0)
                if (state.gridMap[row][col] == state.gridMap[row][col-1])
                    if (!robotAt(row,col-1,state)) {
                        carryBlock = false;
                        synchronized(state.mapLock) {
                            state.gridMap[row][col-1]++; //Synchronize!
                        }
                        return true;
                    }
            return false;
            //            break; // END OF PUT_DOWN
        default: return false; // break;
        }
    }

    private boolean robotAt (int row, int col, StateDesc state) {
        for (AbstractRobot r : state.robots)
            if (r.row == row && r.col == col)
                return true;
        return false;
    }
    
}
