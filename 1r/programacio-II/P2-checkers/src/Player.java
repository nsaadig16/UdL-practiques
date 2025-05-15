public class Player {

    public static final Player WHITE = new Player("WHITE");
    public static final Player BLACK = new Player("BLACK");

    private final String name;

    private Player(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}