import java.lang.reflect.Field;
import java.util.List;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;

public class BuildFixedRampTermes implements Runnable {
    
    StateDesc state;
    TermesBot myRobot;

    BuildFixedRampTermes (StateDesc state, TermesBot myRobot) {
        this.state = state;
        this.myRobot = myRobot;
    }

    public void run() {
        myRobot.addActionToPlan( TermesActions.GO_FORWARD , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.GO_FORWARD , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.PICK_UP , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.TURN_RIGHT , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.GO_FORWARD , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.PUT_DOWN , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.TURN_LEFT , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.PICK_UP , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.TURN_RIGHT , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.GO_FORWARD , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.TURN_RIGHT , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.GO_FORWARD , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.GO_FORWARD , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.TURN_RIGHT , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.TURN_RIGHT , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.PUT_DOWN , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.GO_FORWARD , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.GO_FORWARD , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.PICK_UP , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.TURN_RIGHT , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.TURN_RIGHT , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.GO_FORWARD , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.TURN_RIGHT , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.TURN_RIGHT , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.PUT_DOWN , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.GO_FORWARD , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.GO_FORWARD , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.TURN_LEFT , state.allocateTimeStep() );
        myRobot.addActionToPlan( TermesActions.GO_FORWARD , state.allocateTimeStep() );        
    }
}
