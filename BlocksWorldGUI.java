import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class BlocksWorldGUI {

    private final int gridLen = 80;
    
    private int boardCols, boardRows;
    private JLabel[][] grids;
    private JLabel prompter;
    private Polygon[] robotsPoly;
    private Polygon[] robotsCarryPoly;
    private String[] robotsNames;
    private Color[] robotsColor;
    JFrame frame;
    private final int[] emptyPolygon = {0,0,0};
    private final int[] emptyCarryPolygon = {0,0,0,0};

    public BlocksWorldGUI (int boardCols, int boardRows, int numOfRobots) {

        this.boardCols = boardCols;
        this.boardRows = boardRows;
        robotsNames = new String[numOfRobots];
        robotsPoly = new Polygon[numOfRobots];
        robotsCarryPoly = new Polygon[numOfRobots];
        robotsColor = new Color[numOfRobots];
        for (int i=0; i<numOfRobots; i++) {
            robotsNames[i] = new String("");
            robotsPoly[i] = new Polygon( emptyPolygon , emptyPolygon , 3 );
            robotsCarryPoly[i] = new Polygon( emptyCarryPolygon , emptyCarryPolygon , 4 );
            robotsColor[i] = Color.RED;
        }

        grids = new JLabel[boardRows][boardCols];
        for (int i=0; i<boardRows; i++)
            for (int j=0; j<boardCols; j++) {
                grids[i][j] = new JLabel("0",JLabel.CENTER);
                grids[i][j].setPreferredSize(new Dimension(gridLen, gridLen));
                grids[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
            }

        JLabel supplyPoint = new JLabel("(*)",JLabel.CENTER);
        supplyPoint.setPreferredSize(new Dimension(gridLen, gridLen));
        supplyPoint.setBorder(BorderFactory.createLineBorder(Color.red));

        prompter = new JLabel("General messages",JLabel.CENTER);

        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
        
        //Create and set up the window.
        frame = new JFrame("Termes Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //creating the actual board panel
        DrawTriangle gridsPanel = new DrawTriangle(new GridLayout(boardRows,boardCols), robotsPoly, robotsColor, robotsCarryPoly);
        for (int j=0; j<boardCols; j++)
            for (int i=0; i<boardRows; i++)
                gridsPanel.add(grids[i][j]);

        JPanel simulatorPanel = new JPanel(new BorderLayout());
        simulatorPanel.add(gridsPanel,BorderLayout.CENTER);
        simulatorPanel.add(supplyPoint,BorderLayout.EAST);
        simulatorPanel.add(prompter ,BorderLayout.SOUTH);
        
        //set window to feet componants
        frame.add(simulatorPanel);
        frame.pack();
        frame.setVisible(true);

    }

    public void updateDisplay (StateDesc state, AbstractRobot[] robots, String prompt){
        for (int i=0; i<boardRows; i++)
            for (int j=0; j<boardCols; j++)
                grids[i][j].setText(""+state.gridMap[i][j]);
        for (int i=0; i<state.robots.length; i++) {
            robotsNames[i] = robots[i].getPrompt();
            robotsPoly[i] = extractCoordinate(robots[i]);
            if (state.robots[i].carryBlock)
                robotsCarryPoly[i] = extractCarryCoordinate(robots[i]);
            else
                robotsCarryPoly[i] = new Polygon( emptyCarryPolygon , emptyCarryPolygon , 4 );
            robotsColor[i] = robots[i].color;
        }
        prompter.setText(prompt);
        frame.repaint();
    }

    private Polygon extractCoordinate (AbstractRobot robot) {   
        Polygon retVal = new Polygon();
        int diffLen = gridLen/4;
        if (robot.facing  == AbstractRobot.Orientation.NORTH) {
            retVal.addPoint ( robot.row * gridLen + diffLen   , robot.col * gridLen + gridLen );
            retVal.addPoint ( robot.row * gridLen + 3*diffLen , robot.col * gridLen + gridLen );
            retVal.addPoint ( robot.row * gridLen + 2*diffLen , robot.col * gridLen );
        }
        if (robot.facing  == AbstractRobot.Orientation.SOUTH) {
            retVal.addPoint ( robot.row * gridLen + diffLen   , robot.col * gridLen );
            retVal.addPoint ( robot.row * gridLen + 3*diffLen , robot.col * gridLen );
            retVal.addPoint ( robot.row * gridLen + 2*diffLen , robot.col * gridLen + gridLen );
        }
        if (robot.facing  == AbstractRobot.Orientation.EAST) {
            retVal.addPoint ( robot.row * gridLen           , robot.col * gridLen + diffLen );
            retVal.addPoint ( robot.row * gridLen           , robot.col * gridLen + 3*diffLen );
            retVal.addPoint ( robot.row * gridLen + gridLen , robot.col * gridLen + 2*diffLen );
        }
        if (robot.facing  == AbstractRobot.Orientation.WEST) {
            retVal.addPoint ( robot.row * gridLen + gridLen   , robot.col * gridLen + diffLen );
            retVal.addPoint ( robot.row * gridLen + gridLen   , robot.col * gridLen + 3*diffLen );
            retVal.addPoint ( robot.row * gridLen             , robot.col * gridLen + 2*diffLen );
        }
        return retVal;
    }

    private Polygon extractCarryCoordinate (AbstractRobot robot) {   
        Polygon retVal = new Polygon();
        int diffLen = gridLen/4;
        if (robot.facing  == AbstractRobot.Orientation.NORTH) {
            retVal.addPoint ( robot.row * gridLen + diffLen   , robot.col * gridLen + gridLen );
            retVal.addPoint ( robot.row * gridLen + 3*diffLen , robot.col * gridLen + gridLen );
            retVal.addPoint ( robot.row * gridLen + 3*diffLen , robot.col * gridLen + 3*diffLen);
            retVal.addPoint ( robot.row * gridLen + diffLen   , robot.col * gridLen + 3*diffLen);
        }
        if (robot.facing  == AbstractRobot.Orientation.SOUTH) {
            retVal.addPoint ( robot.row * gridLen + diffLen   , robot.col * gridLen );
            retVal.addPoint ( robot.row * gridLen + 3*diffLen , robot.col * gridLen );
            retVal.addPoint ( robot.row * gridLen + 3*diffLen , robot.col * gridLen + diffLen ); 
            retVal.addPoint ( robot.row * gridLen + diffLen   , robot.col * gridLen + diffLen );
        }
        if (robot.facing  == AbstractRobot.Orientation.EAST) {
            retVal.addPoint ( robot.row * gridLen           , robot.col * gridLen + diffLen );
            retVal.addPoint ( robot.row * gridLen           , robot.col * gridLen + 3*diffLen );
            retVal.addPoint ( robot.row * gridLen + diffLen , robot.col * gridLen + 3*diffLen ); 
            retVal.addPoint ( robot.row * gridLen + diffLen , robot.col * gridLen + diffLen );
        }
        if (robot.facing  == AbstractRobot.Orientation.WEST) {
            retVal.addPoint ( robot.row * gridLen + gridLen           , robot.col * gridLen + diffLen );
            retVal.addPoint ( robot.row * gridLen + gridLen          , robot.col * gridLen + 3*diffLen );
            retVal.addPoint ( robot.row * gridLen + 3*diffLen , robot.col * gridLen + 3*diffLen );   
            retVal.addPoint ( robot.row * gridLen + 3*diffLen , robot.col * gridLen + diffLen );
        }
        return retVal;
    }

}


// Inner class -- basically a panel that can draw triangle on itself
class DrawTriangle extends JPanel {
    private Polygon[] robotsPoly;  
    private Polygon[] robotsCarryPoly;
    private Color[] robotsColor;
    public DrawTriangle() {  
        super();
    }
    public DrawTriangle(LayoutManager layout, Polygon[] robotsPoly, Color[] robotsColor, Polygon[] robotsCarryPoly) {  
        super(layout);
        this.robotsPoly = robotsPoly; //points to the same place in purpose! do not change...
        this.robotsCarryPoly = robotsCarryPoly; //points to the same place in purpose! do not change...
        this.robotsColor = robotsColor;
    }
    public void paint(Graphics g) {  
        super.paint(g);
        for (int i=0; i<robotsPoly.length; i++) {
            g.setColor(new Color(robotsColor[i].getRed(),robotsColor[i].getGreen(),robotsColor[i].getBlue(),128));
            g.fillPolygon(robotsPoly[i]);
            g.setColor(Color.BLACK);
            g.fillPolygon(robotsCarryPoly[i]);
        }
        /*
        g.setColor(new Color(1,1,1));  
        //Set font that will use when draw String  
        g.setFont(new Font("Arial",Font.BOLD,14));  
        //Draw String in JPanel  
        g.drawString("(0,200)",10,200);  
        g.drawString("(150,0)",150,20);  
        g.drawString("(300,200)",290,200);
        */
    }   
}  
