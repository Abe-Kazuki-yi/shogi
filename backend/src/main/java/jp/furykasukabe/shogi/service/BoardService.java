package jp.furykasukabe.shogi.service;

import java.util.List;

import jp.furykasukabe.shogi.bean.Board;
import jp.furykasukabe.shogi.bean.Square;
import jp.furykasukabe.shogi.dto.MoveRequest;

public interface BoardService {
	List<Square> findMovableSquare(Board board, Square square);
	
	Board advanceOneStep(Board board, MoveRequest moveRequest);
	
	boolean isPromotable(Board board, MoveRequest moveRequest);
}