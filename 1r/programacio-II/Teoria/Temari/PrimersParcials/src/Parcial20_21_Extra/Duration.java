package Parcial20_21_Extra;

public class Duration {

    private int hours;
    private int minutes;
    private int seconds;

    public Duration(int hours, int minutes, int seconds){
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        correctFormat();
    }

    public int getHours(){
        return this.hours;
    }

    public int getMinutes(){
        return this.minutes;
    }

    public int getSeconds(){
        return this.seconds;
    }
    public int inSeconds(){
        return this.hours * 3600 + this.minutes * 60 + this.seconds;
    }

    public void add(Duration other){
        if (other != null){
            this.hours += other.hours;
            this.minutes += other.minutes;
            this.seconds += other.seconds;
            correctFormat();
        }
    }

    private void correctFormat(){
        while(this.minutes > 60 || this.seconds > 60){
            this.minutes += this.seconds / 60;
            this.seconds = this.seconds % 60;
            this.hours += this.minutes / 60;
            this.minutes = this.seconds % 60;
        }
    }

    public static Duration add(Duration d1, Duration d2){
        if(d1 != null && d2 != null){
            Duration result = new Duration(0,0,0);
            result.add(d1);
            result.add(d2);
            return result;
        }
        return null;
    }
}
