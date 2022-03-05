package com.webcheckers.model;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.ui.BoardView;
import com.webcheckers.util.Message;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import com.webcheckers.ui.BoardView.*;

public class AIPlayer extends Player{

    /**
     * setter method for username
     *
     * @param name the username to be set
     */
    public AIPlayer(String name) {
        super("BOT");

    }
    public HashMap<Integer, Position> listOfAllPieces(CheckerGame game, Piece.Color c){
        HashMap<Integer, Position> whitePieces = new HashMap<Integer , Position>();
        int pieceNum = 0;
        for(int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++){
                Position position = new Position(row,Math.abs(col-7));
                Space space = game.getBoard().getBoard().getSpace(position);
                Piece piece = space.getPiece();
                if(piece == null){}
                else if (space.getPiece().getColor() == c ){
                    whitePieces.put(pieceNum,position);
                    pieceNum++;
                }
            }
        }
    return whitePieces;}

    /**
     * causes the AI player to make a move using makeAImove
     * @param game the current game the AI player is in
     * @param gameCenter the current gamecenter
     */
    public void MakeMove(CheckerGame game, GameCenter gameCenter) {
        HashMap<Integer, Position> whitePieces = listOfAllPieces(game, Piece.Color.WHITE);
        if (whitePieces.size() == 0) {
            gameCenter.updateWinner(true, game.getRedPlayer(), game.getWhitePlayer());

        } else {
                Move move = makeAIMove(whitePieces, game);
                if(move == null){
                    gameCenter.updateWinner(true, game.getRedPlayer(), game.getWhitePlayer());

                }
                else {
                    game.applyMove();

                    if(listOfAllPieces(game, Piece.Color.RED).size()==0) {
                        gameCenter.updateWinner(true, game.getWhitePlayer(), game.getRedPlayer());
                    }
                }
        }
    }
    public List<Move> GetKingSingleMoves(Position position,Board board,List<Move> moves){
        int index = 0;
        //checks to see if a king can make a jump
        //check each 4 directions for possible moves
        Position MoveNW = new Position(position.getRow()+1,Math.abs(position.getCell()+1));
        Position MoveNE= new Position(position.getRow()+1,Math.abs(position.getCell()+1));
        Position MoveSW= new Position(position.getRow()-1,Math.abs(position.getCell()+1));
        Position MoveSE= new Position(position.getRow()-1,Math.abs(position.getCell()-1));
        List<Boolean> directions = new LinkedList<>();
        directions.add(0,MoveNW.checkOutOfRange());
        directions.add(1, MoveNE.checkOutOfRange());
        directions.add(2,MoveSW.checkOutOfRange());
        directions.add(3, MoveSE.checkOutOfRange());
        if(!directions.get(0)){
            Piece p = board.getSpace(MoveNW).getPiece();
            if(p == null) {
                moves.add(index, new Move(position, MoveNW));
                index++;
            }
        }
         if(!directions.get(1)){
            Piece p = board.getSpace(MoveNE).getPiece();
            if(p == null) {
                moves.add(index, new Move(position, MoveNE));
                index++;
            }
        }
         if(!directions.get(2)){
            Piece p = board.getSpace(MoveSW).getPiece();
            if(p == null) {
                moves.add(index, new Move(position, MoveSW));
                index++;
            }
        }
         if(!directions.get(3)){
            Piece p = board.getSpace(MoveSE).getPiece();
            if(p == null) {
                moves.add(index, new Move(position, MoveSE));

            }
        }
    return moves;}

    /**
     * gets list of all possible moves the AI can make
     * @param position the position of the piece
     * @param board the current board
     * @param game the current game
     * @return list of all possible moves the AI can make
     */
    public List<Move> GetSingleMoves(Position position,Board board,CheckerGame game){
        Space space = board.getSpace(position);
        Piece piece = space.getPiece();
        Position Right = new Position(position.getRow()+1, position.getCell()-1);
        Position Left = new Position(position.getRow()+1, position.getCell()+1);
        int index = 0;
        List<Move> moves = new LinkedList<>();
        if(piece.getType().equals(Piece.Type.KING)) {
            return GetKingSingleMoves(position, board, moves);
        }
        else{
            if(board.checkSingle(Right)){
                Move m = new Move(position,Right);
                moves.add(index,m);
                index++;
                if(board.getSpace(Right).getPiece() == null){
                    Move l = new Move(position,Right);
                    moves.add(index,l);

                }
                return moves;
            }
            else if(board.checkSingle(Left) ){
                Move m = new Move(position,Left);
                moves.add(index,m);
                index++;
                if(board.getSpace(Left).getPiece() == null){
                    Move l = new Move(position,Left);
                    moves.add(index,l);
                }
                return moves;
            }
            else{
                return moves;
            }
        }
    }

    /**
     * gets list of current all possible moves an AI player can make with a king piece
     * @param position the position of the king piece
     * @param board the current board
     * @return list of all possible moves a king piece of an AI player can make
     */
    public List<Move> GetKingJumps(Position position,Board board) {
        List<Move> jumps = new LinkedList<>();
        Space space = board.getSpace(position);
        Piece piece = space.getPiece();
        int index = 0;
        Position endPos = position;
        HashMap<Position, Boolean> validMoves;
        while (board.checkKingJump(endPos, piece)) {
            //create a new move list with
            position = endPos;
            Position CapNE = new Position(position.getRow() + 2, Math.abs(position.getCell() - 2));
            Position OppNE = new Position(position.getRow() + 1, Math.abs(position.getCell() - 1));
            Position CapNW = new Position(position.getRow() + 2, Math.abs(position.getCell() + 2));
            Position OppNW = new Position(position.getRow() + 1, Math.abs(position.getCell() + 1));
            Position CapSE = new Position(position.getRow() - 2, Math.abs(position.getCell() - 2));
            Position OppSE = new Position(position.getRow() - 1, Math.abs(position.getCell() - 1));
            Position CapSW = new Position(position.getRow() - 2, Math.abs(position.getCell() + 2));
            Position OppSW = new Position(position.getRow() - 1, Math.abs(position.getCell() + 1));
            validMoves = new HashMap<>();
            validMoves.put(CapNE, board.checkPieceJump(OppNE, CapNE, piece));
            validMoves.put(CapNW, board.checkPieceJump(OppNW, CapNW, piece));
            validMoves.put(CapSE, board.checkPieceJump(OppSE, CapSE, piece));
            validMoves.put(CapSW, board.checkPieceJump(OppSW, CapSW, piece));
            if (validMoves.get(CapNE)) {
                Move m = new Move(position, CapNE);
                if (jumps.contains(new Move(m.getEnd(), m.getStart()))) {
                    continue;
                } else {
                    endPos = CapNE;
                    jumps.add(index, m);
                    board.updateBoard(m, Piece.Color.WHITE);
                }
            } else if (validMoves.get(CapNW)) {
                Move m = new Move(position, CapNW);
                if (jumps.contains(new Move(m.getEnd(), m.getStart()))) {
                    continue;
                } else {
                    endPos = CapNW;
                    jumps.add(index, m);
                    board.updateBoard(m, Piece.Color.WHITE);
                }
            } else if (validMoves.get(CapSE)) {
                Move m = new Move(position, CapSE);
                if (jumps.contains(new Move(m.getEnd(), m.getStart()))) {
                    continue;
                } else {
                    endPos = CapSE;
                    jumps.add(index, m);
                    board.updateBoard(m, Piece.Color.WHITE);
                }
            } else if (validMoves.get(CapSW)) {
                Move m = new Move(position, CapSW);
                if (jumps.contains(new Move(m.getEnd(), m.getStart()))) {
                    continue;
                } else {
                    endPos = CapSW;
                    jumps.add(index, m);
                    board.updateBoard(m, Piece.Color.WHITE);
                }
            }
            index++;
        }
    return jumps;}

    /**
     * gets list of all possible jump moves an AI can make
     * @param position the position of the piece
     * @param board the current board
     * @return list of all possible jump moves an AI can make
     */
    public List<Move> GetJumpMoves(Position position,Board board){//for the AI, if there is a multiple jump, the AI will always take it
        List<Move> jumps = new LinkedList<>();
        Space space = board.getSpace(position);
        Piece piece = space.getPiece();
        int index = 0;
        if(piece.getType().equals(Piece.Type.KING)){
            if(board.checkKingJump(position, piece)) {
                return GetKingJumps(position,board);
            }
        }
        else{
            Position endPos = position;
            while(board.isSingleJump(endPos, Piece.Color.WHITE)){
                position = endPos;
                Position RightOp = new Position(position.getRow()+1, position.getCell()-1);
                Position RightAfter = new Position(position.getRow()+2,position.getCell()-2 );
                Position LeftOp = new Position(position.getRow()+1, position.getCell()+1);
                Position LeftAfter = new Position(position.getRow()+2,position.getCell()+2 );
                HashMap<Position,Boolean> validMoves = new HashMap<>();
                validMoves.put(RightAfter, board.checkPieceJump(RightOp, RightAfter, piece));
                validMoves.put(LeftAfter,board.checkPieceJump(LeftOp, LeftAfter, piece));
                if(validMoves.get(RightAfter)){
                    Move m = new Move(position, RightAfter);
                    if (jumps.contains(new Move(m.getEnd(), m.getStart()))) {
                    } else {
                        endPos = RightAfter;
                        jumps.add(index, m);
                        index++;
                    }
                }
                else if(validMoves.get(LeftAfter)){
                    Move m = new Move(position, LeftAfter);
                    if (jumps.contains(new Move(m.getEnd(), m.getStart()))) {
                    } else {
                        endPos = LeftAfter;
                        jumps.add(index, m);
                        index++;
                    }
                }
            }

        }
        return SelectJumpMove(jumps);
    }
    public List<Move> SelectJumpMove(List<Move> jumps ){
        if(areDifferentMoves(jumps) == 1){//checks for a multi jump
            return jumps;
        }
        else if(areDifferentMoves(jumps)==2) {//there can be only one jump move, so select a random jump
            if (jumps.size() == 2) {

                Random rand = new Random();
                int jump = rand.nextInt(jumps.size());
                Move move = jumps.get(jump);
                jumps = new LinkedList<>();
                jumps.add(0, move);
            }
            else{//there are choices of a multi-jump, perform the first move, then randomly select the second
                Move move = jumps.get(0);
                Random rand = new Random();
                int jump = rand.nextInt(jumps.size()-1)+1;
                jumps.remove(jump);
            }
        }
    return jumps;}
    /**
     * eliminates repeated moves from list of jump moves
     * @param jumps the list of jump moves to be checked for repeated moves
     * @return true if moves are different, false otherwise
     */
    public int areDifferentMoves(List<Move> jumps){
        if(jumps.size()==1 || jumps.size() ==0){
            return 1;
        }
        Move initial = jumps.get(0);
        for(int i = 1; i<jumps.size(); i++){
            if(initial.getStart().equals(jumps.get(i).getStart())){
                return 2;
            }
            initial = jumps.get(i);
        }
    return 1;}

    /**
     * allows the AI to make a move
     * @param whitePieces hashmap of all white pieces since AI will always control white pieces
     * @param game the current game
     * @return the move to be made by the AI
     */
    public Move makeAIMove(HashMap<Integer, Position> whitePieces,CheckerGame game){
        List<Move> singleMove = new LinkedList<>();
        Board board = game.getFlippedBoard().getBoard();

        int singleIndex = 0;
        List<Move> jumpMoves = new LinkedList<>();
        for(int i = 0; i< whitePieces.size();i++) {
            Position position = whitePieces.get(i);
            List<Move> single = GetSingleMoves(position, board ,game);
            while (single.size() != 0) {            //adds all single moves to the list
                Move m = single.remove(0);
                singleMove.add(singleIndex, m);
                singleIndex++;
            }

            List<Move> jumps  = GetJumpMoves(position,board);
            if(jumps.size() > 1){//there was a multi jump, but also a single jump
                jumpMoves = new LinkedList<>(jumps);
            }
            else {
                jumpMoves.addAll(jumps);
            }
            if(jumpMoves.size()>1){//there is a multi-jump that should be taken
                for(Move m : jumpMoves) {
                    game.addMove(m);
                }
                return jumpMoves.get(0);
            }
        }
            if(jumpMoves.size()==0){//there are no possible jump moves, select a random move
                if(singleMove.size()==0){//there were no possible moves.
                  return null;
                }
                Random rand = new Random();
                int moveIndex = rand.nextInt(singleMove.size());
                Move m = singleMove.get(moveIndex);
                Position position = m.getStart();
                Piece piece = board.getSpace(position).getPiece();
                if(piece.getType().equals(Piece.Type.KING)) {
                    game.addMove(m);
                }
                else{
                    game.removeMove();
                    game.addMove(m);
                }
                return singleMove.get(moveIndex);
            }
            else{
                Random rand = new Random();
                int jumpIndex = rand.nextInt(jumpMoves.size());
                Move m = jumpMoves.get(jumpIndex);
                game.addMove(m);
                return jumpMoves.get(jumpIndex);
            }
        }



    }

