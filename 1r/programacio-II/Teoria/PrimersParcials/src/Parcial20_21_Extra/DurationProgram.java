package Parcial20_21_Extra;
import acm.program.*;
import java.util.StringTokenizer;

public class DurationProgram extends  CommandLineProgram{

    private DurationAccumulator accumulator;

    public void run(){
        this.accumulator = new DurationAccumulator(5,3);
        String input = readLine("Enter your durations: ");
        storeDurations(input);
    }

    private void storeDurations(String input){
       StringTokenizer durations = new StringTokenizer(input);
       while(durations.hasMoreTokens()){
           String duration = durations.nextToken();
           StringTokenizer data = new StringTokenizer(duration,"#");
           while (data.hasMoreTokens()){
               int id = Integer.parseInt(data.nextToken());
               int hours = Integer.parseInt(data.nextToken());
               int minutes = Integer.parseInt(data.nextToken());
               int seconds = Integer.parseInt(data.nextToken());
               Duration d = new Duration(hours,minutes,seconds);
               this.accumulator.storeDuration(id,d);
           }
       }
       println("The minimum duration corresponds with id number" + this.accumulator.getMinimum());
    }
}
