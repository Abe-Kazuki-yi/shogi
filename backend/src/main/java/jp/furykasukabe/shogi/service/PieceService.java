package jp.furykasukabe.shogi.service;

import java.util.List;

import jp.furykasukabe.shogi.entity.PieceInfo;

public interface PieceService {
	List<PieceInfo> findAllPieces();
}
