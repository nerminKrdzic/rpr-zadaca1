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
    private void checkRookPath(String oldPosition, String newPosition) throws IllegalChessMoveException{
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
                    if(c.getPosition().equals(oldPosition.substring(0,1) + Character.toString(poc))) throw new IllegalChessMoveException();
        }else{  // krecemo se po x osi
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
                    if(c.getPosition().equals(Character.toString(poc) + oldPosition.substring(1,2))) throw new IllegalChessMoveException();

        }
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
        

        ChessPiece naOdredistu = checkDestination(index, position, color);
        if(naOdredistu == null) return;
        activeFigures.remove(naOdredistu);
    }
}
