package Examen_20_21.ex1;

import acm.program.CommandLineProgram;
import java.io.*;
import java.util.StringTokenizer;

public class Updater extends CommandLineProgram {
    private static final String RAIN = "rain.csv";
    private static final String STATS = "stats.dat";
    private Stats stats;
    private BufferedReader rain;

    public void run() {
        try {
            openFiles();
            updateStats();
            closeFiles();
        } catch (IOException ex) {
            println("Houston, Houston, we have a problem");
        }
    }
    private void openFiles() throws IOException {
        stats = new Stats(STATS);
        rain = new BufferedReader(new FileReader(RAIN));
    }
    private void closeFiles() throws IOException {
        stats.close(); rain.close();
    }
    private void updateStats() throws IOException {
        String operation = rain.readLine();
        while(operation != null){
            updateRecord(operation);
            operation = rain.readLine();
        }
    }

    private void updateRecord (String operation) throws  IOException{
        StringTokenizer st = new StringTokenizer(",");
        while(st.hasMoreTokens()){
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int hours = Integer.parseInt(st.nextToken());
            int minutes = Integer.parseInt(st.nextToken());
            double liters = Double.parseDouble(st.nextToken());
            if(x >= 0 && x < Record.GRID_SIZE &&
                y >= 0 && y < Record.GRID_SIZE &&
                !(hours == 0 && minutes == 0) &&
                hours >= 0 && minutes >= 0 &&
                liters > 0
            ){
                update(x,y,hours,minutes,liters);
            }
        }
    }

    private void update(int x, int y, int hours, int minutes, double liters) throws IOException{
        Record record = stats.read(x,y);
        Duration duration = new Duration(hours,minutes);
        record.addRainEvent(duration,liters);
        stats.write(record);
    }
}
