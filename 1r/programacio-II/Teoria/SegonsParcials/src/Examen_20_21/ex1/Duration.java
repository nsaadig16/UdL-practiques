package Examen_20_21.ex1;

public class Duration {
    public static final int SIZE = 4 + 4;

    private final int hours; // 0 <= hours

    private final int minutes; // 0 <= minutes <= 59

    public Duration(int hours, int minutes) {
        // Precondición: 0 <= hours && 0 <= minutes
        this.hours =  hours + minutes / 60;
        this.minutes = minutes % 60;
    }

    public static Duration add(Duration d1, Duration d2) {
        int hours = d1.getHours() + d2.getHours();
        int minutes = d1.getMinutes() + d2.getMinutes();
        return new Duration(hours,minutes);
    }

    public void packDuration(byte[] record, int offset) {
        // Empaqueta la información de una Duration en los bytes del
        // registro record a partir de la posición offset (ocupando
        // SIZE bytes). Como en PackUtils, suponemos que en el array
        // existe suficiente espacio para empaquetar. TODO
    }

    public static Duration unpackDuration(byte[] record, int offset) {
        // Desempaqueta la información de una Duration en los bytes
        // del registro record a partir de la posición offset y que
        // ocupan SIZE bytes y los devuelve en forma de una instancia
        // de Duration. Como en PackUtils, suponemos que en el array
        // existe suficiente espacio para desempaquetar.
        //TODO
        return null;
    }

    public String toString() {
        // Devuelve un String con los datos de la duration
        return String.format("%dh:%dm", this.hours, this.minutes);
    }

    public int getHours (){
        return this.hours;
    }

    public int getMinutes(){
        return this.minutes;
    }

}
