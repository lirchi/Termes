import java.io.*;
import java.util.*;

public class MapFileReader {

    String[] currLine;
    CSVReader csvr;
    FileReader fr;
    byte[][] map;
	
    public MapFileReader (String filename) {
        try {
            // Creates a FileReader stream (The CVSReader wrap it with BufferedReader).
            fr = new FileReader(filename);
            csvr = new CSVReader(fr, ',');
            // read row,col and creates the right sized map
            currLine = csvr.readNext();
            int rows = Integer.parseInt(currLine[0]);
            int cols = Integer.parseInt(currLine[1]);
            map = new byte[rows][cols];
            // read "terrian"
            currLine = csvr.readNext();
            for (int i=0; i<rows; i++) {
                for (int j=0; j<cols; j++)
                    map[i][j] = Byte.parseByte(currLine[j]);
                currLine = csvr.readNext();
            }
            csvr.close();
            fr.close();
        } catch (FileNotFoundException e) {
            System.out.println ("Could not find " + filename);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println ("Bad Map File Format - " + filename);
            e.printStackTrace();
        }
    }

    public byte[][] getMap(){
        return map;
    }

}
