package jp.furykasukabe.shogi.service;

import java.util.List;

import jp.furykasukabe.shogi.entity.Piece;

public interface PieceService {
	List<Piece> findAllPieces();
}
