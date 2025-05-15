public class Game {

    private static final Direction[] WHITE_DIRECTIONS = {Direction.NW, Direction.NE};
    private static final Direction[] BLACK_DIRECTIONS = {Direction.SW, Direction.SE};

    private final Board board;
    private Player currentPlayer;
    private boolean hasWon;

    public Game(Board board) {
        this.board = board;
        this.currentPlayer = Player.WHITE;
        this.hasWon = false;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public boolean hasWon() {
        return this.hasWon;
    }

    private boolean isWhite(Player player){
        return player == Player.WHITE;
    }

    private boolean isBlack(Player player){
        return player == Player.BLACK;
    }

    public boolean isValidFrom(Position position) {
        return isValidFrom(position,this.currentPlayer);
    }

    private boolean isValidFrom(Position position, Player player){
        return isSameColor(position,player) && checkDirections(position,player);
    }

    private boolean isSameColor(Position position, Player player){
        //Checks if the piece's color is the same as the player
        return  isBlack(player) && this.board.isBlack(position) ||
                isWhite(player) && this.board.isWhite(position)
                ;
    }

    private boolean checkDirections(Position position, Player player){
        //Checks whether a move or a capture can be done from the position
        Direction[] directions = isWhite(player) ? WHITE_DIRECTIONS : BLACK_DIRECTIONS;
        for (Direction direction : directions) {
            Position normalMove = direction.apply(position);
            if (this.board.isEmpty(normalMove)) {
                return true;
            }
            Position captureMove = direction.apply(normalMove);
            if (isSameColor(normalMove, opponent()) && board.isEmpty(captureMove)) {
                return true;
            }
        }
        return false;
    }

    // Assumes validFrom is a valid starting position
    public boolean isValidTo(Position validFrom, Position to) {
        return  this.board.isEmpty(to)
                && correctDistance(validFrom,to)
                && correctCapture(validFrom,to)
                && goesForward(validFrom,to)
        ;
    }

    private boolean correctDistance(Position from, Position to){
        //Checks if the "to" position is at a move distance or at a capture distance
        return from.sameDiagonalAs(to) && Position.distance(from,to) <= 4;
    }

    private boolean correctCapture(Position from, Position to){
        //Checks if the move captures an opponent's piece
        Position middle = Position.middle(from,to);
        if (middle == null){
            return true; //if there isn't a middle position, we don't have to check
        }
        return isSameColor(middle,opponent()); //If there is, we check it's an opponent piece
    }

    private boolean goesForward(Position from, Position to){
        //Checks if the white piece moves up and the black moves down on the board.
        return  isWhite(currentPlayer) && from.getY() > to.getY()  ||
                isBlack(currentPlayer) && to.getY() > from.getY()
        ;
    }

    // Assumes both positions are valid
    public Move move(Position validFrom, Position validTo) {
        Move move = new Move(validFrom,Position.middle(validFrom,validTo),validTo);
        movePiece(move);
        checkHasWon(validTo.getY());
        if (!this.hasWon) {
            changePlayer();
        }
        return move;
    }

    private void movePiece(Move move){
        //Moves the piece; if it's a capture, removes the opponent's piece
        this.board.setEmpty(move.getFrom());
        if(isWhite(currentPlayer)){
            this.board.setWhite(move.getTo());
        }else {
            this.board.setBlack(move.getTo());
        }
        if (move.isCapture()){
            this.board.setEmpty(move.getMiddle());
        }
    }

    private void checkHasWon(int row){
        this.hasWon =  endRowReached(row) || noMovesLeft(opponent());
    }

    private boolean endRowReached(int row){
        //Checks if the top row or bottom row has been reached
        return row == 0 || row == board.getHeight() - 1;
    }

    private boolean noMovesLeft(Player player){
        //Checks if the player has any moves left
        for(int i = 0; i < board.getHeight(); i++){
            for (int j = 0; j < board.getWidth(); j++){
                Position pos = new Position(i,j);
                if(isValidFrom(pos,player)){
                    return false;
                }
            }
        }
        return true;
    }

    private Player opponent(){
        //Returns the opponent of the current player
        return isWhite(currentPlayer)? Player.BLACK : Player.WHITE;
    }

    private void changePlayer(){
        this.currentPlayer = opponent();
    }
    // Only for testing

    public void setPlayerForTest(Player player) {
        this.currentPlayer = player;
    }
}
