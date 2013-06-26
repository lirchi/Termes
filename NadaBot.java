enum NadaActions { DO_NOTHING };

public class NadaBot extends AbstractRobot<NadaActions> {
    
    public NadaBot(int row, int col, String name, Orientation facing, java.awt.Color color) {
        super (row, col, name, facing, color, NadaActions.class);
    }
    
    public boolean makeMove(StateDesc state, Object action) {
        return true;
    }

    public AbstractRobot deepCopy() {
        // !!! note that deepCopy does NOT duplicate actions, planActions and planTimeSteps
        return new NadaBot(this.row, this.col, this.name, this.facing, this.color);
    }
    
}
