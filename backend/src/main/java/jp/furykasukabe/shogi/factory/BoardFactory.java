package jp.furykasukabe.shogi.factory;

import jp.furykasukabe.shogi.bean.Board;
import jp.furykasukabe.shogi.entity.ShogiList;

public interface BoardFactory {
	Board createInitialBoard(boolean isPlay);
	
	Board createNextBoard(Board currentBoard, ShogiList shogiList);
}
