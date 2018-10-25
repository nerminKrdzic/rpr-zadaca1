package ba.unsa.etf.rpr;

public abstract class ChessPiece {
    public static Color color = null;
    //methods realized in child classes
    public abstract String getPosition();
    public abstract Color getColor();
    public abstract void move(String position) throws IllegalArgumentException, IllegalChessMoveException;
}
