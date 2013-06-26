import java.lang.reflect.Field;
import java.util.List;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;

public class RandomTermesBotEngine implements Runnable {
    
    StateDesc state;
    TermesBot myRobot;

    RandomTermesBotEngine (StateDesc state, TermesBot myRobot) {
        this.state = state;
        this.myRobot = myRobot;
    }

    public void run() {
        Random random = new Random();
        for (int i=0; i<5; i++) {
            TermesActions randAction = TermesActions.values()[random.nextInt(TermesActions.values().length)];
            myRobot.addActionToPlan( randAction , state.allocateTimeStep() );
        }
        
    }
}

        // How to: Enum A = (myRobot.actions).getEnumConstants();
        /* Maybe switch to Reflections? Polymorphism?
        Field[] flds = myRobot.actions.getDeclaredFields();
        List<Field> cst = new ArrayList<Field>();  // enum constants
        for (Field f : flds)
            if (f.isEnumConstant()) {// should be all fields!
                //                ((TermesBot)myRobot).doesItWork (f);
                //                       (myRobot.actions).cast(f);

                cst.add(f);
                System.out.println(f.getGenericType());
                print (cst, myRobot.actions);
            }
        */

    /* SOME REFLECTIONS METHODS...
    private static void print(List<Field> lst, Class<?> c) {
        for (Field f : lst) {
            System.out.println(c.cast(f));
        }
    }

    protected static <E extends Enum<E>> void enumValues(Class<E> enumData) {
        for (Enum<E> enumVal: enumData.getEnumConstants()) {  
            System.out.println(enumVal.toString());
        }  
    }
    */
