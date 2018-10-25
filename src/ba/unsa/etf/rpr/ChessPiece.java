package ba.unsa.etf.rpr;

public abstract class ChessPiece {
    protected Color color = null;
    protected String position = null;
    //methods realized in child classes
    public String getPosition(){ return position; }
    public Color getColor(){ return color; }
    public abstract void move(String position) throws IllegalArgumentException, IllegalChessMoveException;
    public ChessPiece(String position, Color color) throws IllegalArgumentException{
        if(!CheckPositionFormat.checkPositionFormat(position)) throw new IllegalArgumentException();
        this.position = position.substring(0,1).toUpperCase() + position.substring(1,2);
        this.color = color;
    }
    public static enum Color {
        BLACK, WHITE
    }
}
