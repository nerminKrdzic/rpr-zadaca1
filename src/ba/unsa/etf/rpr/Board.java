package ba.unsa.etf.rpr;

import java.util.ArrayList;

public class Board {
    private ArrayList<ChessPiece> activeFigures = new ArrayList<>();
    public Board(){
        //dodavanje pijuna
        for(char i = 'A'; i <= 'H'; i++)
            activeFigures.add(new Pawn(Character.toString(i) + "2", ChessPiece.Color.WHITE));
        for(char i = 'A'; i <= 'H'; i++)
            activeFigures.add(new Pawn(Character.toString(i) + "7", ChessPiece.Color.BLACK));

        //dodvanje ostalih figura
        activeFigures.add(new King("E1", ChessPiece.Color.WHITE));
        activeFigures.add(new Queen("D1", ChessPiece.Color.WHITE));
        activeFigures.add(new Bishop("C1", ChessPiece.Color.WHITE));
        activeFigures.add(new Bishop("F1", ChessPiece.Color.WHITE));
        activeFigures.add(new Rook("A1", ChessPiece.Color.WHITE));
        activeFigures.add(new Rook("H1", ChessPiece.Color.WHITE));
        activeFigures.add(new Knight("B1", ChessPiece.Color.WHITE));
        activeFigures.add(new Knight("G1", ChessPiece.Color.WHITE));

        activeFigures.add(new King("E8", ChessPiece.Color.BLACK));
        activeFigures.add(new Queen("D8", ChessPiece.Color.BLACK));
        activeFigures.add(new Bishop("C8", ChessPiece.Color.BLACK));
        activeFigures.add(new Bishop("F8", ChessPiece.Color.BLACK));
        activeFigures.add(new Rook("A8", ChessPiece.Color.BLACK));
        activeFigures.add(new Rook("H8", ChessPiece.Color.BLACK));
        activeFigures.add(new Knight("B8", ChessPiece.Color.BLACK));
        activeFigures.add(new Knight("G8", ChessPiece.Color.BLACK));
    }

    public void move(Class type, ChessPiece.Color color, String position) throws IllegalChessMoveException{
        Integer index = -1;
        for(int i = 0; i < activeFigures.size(); i++){
            if(activeFigures.get(i).getClass() == type && activeFigures.get(i).getColor().equals(color)){
                try {
                    activeFigures.get(i).move(position);
                }catch (IllegalChessMoveException e){
                    if(i == activeFigures.size() - 1) throw new IllegalChessMoveException();
                    continue;
                }
                index = i;
                break;
            }
        }
        //kraljica, lovac, top i pijuni
        if(activeFigures.get(index) instanceof Rook) checkRookPath(activeFigures.get(index).getPosition(), position);
        if(activeFigures.get(index) instanceof Bishop) checkBishopPath(activeFigures.get(index).getPosition(), position);
        if(activeFigures.get(index) instanceof Pawn) checkPawnPath(activeFigures.get(index).getPosition(), position, color);
        if(activeFigures.get(index) instanceof Queen){
            if(activeFigures.get(index).getPosition().charAt(0) - position.charAt(0) == 0
                || activeFigures.get(index).getPosition().charAt(1) - position.charAt(1) == 0)
                checkRookPath(activeFigures.get(index).getPosition(), position);
            else checkBishopPath(activeFigures.get(index).getPosition(), position);
        }
        ChessPiece naOdredistu = checkDestination(index, position, color);
        if(activeFigures.get(index) instanceof Pawn && naOdredistu != null &&!naOdredistu.getColor().equals(activeFigures.get(index).getColor())){
            checkPawnDiagonal(activeFigures.get(index).getPosition(), position, color);
            return;
        }
        if(naOdredistu == null) return;
        activeFigures.remove(naOdredistu);
    }

    private ChessPiece checkDestination(Integer index, String position, ChessPiece.Color color) throws IllegalChessMoveException{
        for(int i = 0; i < activeFigures.size(); i++){
            //ako vec ima figura na odredistu
            if(i != index && activeFigures.get(i).getPosition().equals(position)) {
                // ako je iste boje ne valja
                if (activeFigures.get(i).getColor().equals(color))
                    throw new IllegalChessMoveException();
                    //ako je druge boje vrati tu figuru
                else return activeFigures.get(i);
            }
        }
        //ako nema nista na odreistu onda je OK
        return null;
    }
    private void checkRookPath(String oldPosition, String newPosition) throws IllegalChessMoveException {
        if(oldPosition.charAt(0) - newPosition.charAt(0) == 0){ //krecemo se po y osi
            char poc, kraj;
            if(oldPosition.charAt(1) > newPosition.charAt(1)){
                poc =  newPosition.charAt(1); poc++;
                kraj = oldPosition.charAt(1);
            }
            else {
                poc =  oldPosition.charAt(1); poc++;
                kraj = newPosition.charAt(1);
            }
            for(; poc < kraj; poc++)
                for(ChessPiece c : activeFigures)
                    if(c.getPosition().equals(oldPosition.substring(0,1) + Character.toString(poc)))
                        throw new IllegalChessMoveException();
        }{  // krecemo se po x osi
            char poc, kraj;
            if(oldPosition.charAt(0) > newPosition.charAt(0)){
                poc =  newPosition.charAt(0); poc++;
                kraj = oldPosition.charAt(0);
            }
            else {
                poc =  oldPosition.charAt(0); poc++;
                kraj = newPosition.charAt(0);
            }
            for(; poc < kraj; poc++)
                for(ChessPiece c : activeFigures)
                    if(c.getPosition().equals(Character.toString(poc) + oldPosition.substring(1,2)))
                        throw new IllegalChessMoveException();

        }
    }
    private void checkBishopPath(String oldPosition, String newPosition) throws IllegalChessMoveException {
        char poc1, poc2, kraj1, kraj2;

        if(oldPosition.charAt(0) - newPosition.charAt(0) > 0){ //  prema dole
            poc1 = newPosition.charAt(0);
            poc1++;
            kraj1 = oldPosition.charAt(0);
        }
        else{
            poc1 = oldPosition.charAt(0);  // prema gore
            poc1++;
            kraj1 = newPosition.charAt(0);
        }
        if(oldPosition.charAt(1) - newPosition.charAt(1) > 0){  // prema lijevo
            poc2 = newPosition.charAt(1);
            poc2++;
            kraj2 = oldPosition.charAt(1);
        }
        else{
            poc2 = oldPosition.charAt(1);  //  prema desno
            poc2++;
            kraj2 =  newPosition.charAt(1);
        }
        for(; poc1 < kraj1; poc1++){
            for(ChessPiece c : activeFigures){
                if(c.getPosition().equals(Character.toString(poc1) + Character.toString(poc2)))
                    throw new IllegalChessMoveException();
                poc2++;
            }
        }
    }
    private void checkPawnPath(String oldPosition, String newPosition, ChessPiece.Color color) throws IllegalChessMoveException {
        if(Math.abs(newPosition.charAt(1) - oldPosition.charAt(1)) == 2){
            char character;
            if(color == ChessPiece.Color.WHITE){
                character = oldPosition.charAt(1);
                character++;
            }
            else {
                character = newPosition.charAt(1);
                character++;
            }
            for(ChessPiece c : activeFigures)
                if(c.getPosition().equals(Character.toString(character) + oldPosition.substring(0,1)))
                    throw new IllegalChessMoveException();
        }
    }
    private void checkPawnDiagonal(String oldPosition, String newPosition, ChessPiece.Color color)throws IllegalChessMoveException{
        if(color.equals(ChessPiece.Color.WHITE) && !(Math.abs(oldPosition.charAt(0) - newPosition.charAt(0)) == 1
            && newPosition.charAt(1) - oldPosition.charAt(1) == 1)) throw new IllegalChessMoveException();
        else if(!(Math.abs(oldPosition.charAt(0) - newPosition.charAt(0)) == 1
                && oldPosition.charAt(1) - newPosition.charAt(1) == 1)) throw new IllegalChessMoveException();
    }
}
