package ba.unsa.etf.rpr;

public abstract class ChessPiece {
    public static Color color = null;
    protected String position = null;
    //methods realized in child classes
    public String getPosition(){ return position; }
    public Color getColor(){ return color; }
    public abstract void move(String position) throws IllegalArgumentException, IllegalChessMoveException;
    public ChessPiece(String position, Color color){
        if(!CheckPositionFormat.checkPositionFormat(position)) throw new IllegalArgumentException();
        this.position = position;
        this.color = color;
    }
}
