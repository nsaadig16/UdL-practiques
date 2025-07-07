package Parcial20_21_Extra;

public class DurationAccumulator {

    private Duration[] durations;
    private int numDurations;
    private final int marginSize;

    public DurationAccumulator (int initialSize, int marginSize){
        this.durations = new Duration[initialSize];
        this.numDurations = 0;
        this.marginSize = marginSize;
    }

    public boolean storeDuration (int id, Duration duration){
        if( id > 0 && duration != null){
            if(this.durations[id] == null){
                store(id,duration);
            }else{
                this.durations[id].add(duration);
                numDurations += 1;
            }
            return true;
        }
        return false;
    }

    private void store (int id, Duration duration){
        if(this.numDurations == this.durations.length){
            widenArray();
        }
        this.durations[id] = duration;
    }

    private void widenArray(){
        Duration[] widened = new Duration[this.numDurations + this.marginSize];
        for (int i = 0; i < numDurations; i++){
            widened[i] = this.durations[i];
        }
        this.durations = widened;
    }

    public Duration durationAt(int id){
        return this.durations[id];
    }

    public int getMinimum(){
        if (this.numDurations > 0) {
            int min = 0;
            for (int i = 1; i < numDurations; i++) {
                min = checkMinimum(min,i);
            }
            return min;
        }
        return -1;
    }

    private int checkMinimum (int id1, int id2){
        Duration d1 = durationAt(id1);
        Duration d2 = durationAt(id2);
        if(d1.getSeconds() < d2.getSeconds()){
            return id1;
        }
        return id2;
    }
}
