package ba.unsa.etf.rpr;

public class King extends ChessPiece {
    String position = null;

    public King(String position, Color color) throws IllegalArgumentException{
        super(position, color);
    }

    @Override
    public void move(String position) throws IllegalArgumentException, IllegalChessMoveException {
        if(!CheckPositionFormat.checkPositionFormat(position)) throw new IllegalArgumentException("");
        String oldPosition = getPosition();
        position = position.substring(0,1).toUpperCase() + position.substring(1,2);
        if(Math.abs((int)(oldPosition.charAt(0) - position.charAt(0))) > 1 || Math.abs((int)(oldPosition.charAt(1) - position.charAt(1))) > 1) throw new IllegalChessMoveException();
    }
}
