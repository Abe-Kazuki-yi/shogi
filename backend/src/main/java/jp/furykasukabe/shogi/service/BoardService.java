package jp.furykasukabe.shogi.service;

import java.util.List;

import jp.furykasukabe.shogi.bean.Board;
import jp.furykasukabe.shogi.bean.Piece;
import jp.furykasukabe.shogi.dto.MoveRequest;
import jp.furykasukabe.shogi.dto.Square;

public interface BoardService {
	List<Square> findMovableSquare(Board board, Square square);
	
	Board advanceOneStep(Board board, MoveRequest moveRequest);
	
	boolean isPromotable(Board board, MoveRequest moveRequest);
	
	Board executePromote(Board board, Square square);
	
	List<Square> findDropableSquare(Board board);
	
	Board dropPiece(Board board, Piece piece, Square square);
}