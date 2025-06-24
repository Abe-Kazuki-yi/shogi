package jp.furykasukabe.shogi.service;

import java.util.List;

import jp.furykasukabe.shogi.bean.Board;
import jp.furykasukabe.shogi.bean.MoveRequest;
import jp.furykasukabe.shogi.bean.Square;

public interface BoardService {
	List<Square> findMovableSquare(Board board, Square square);
	
	Board advanceOneStep(Board board, MoveRequest moveRequest, boolean isPromoted);
	
}