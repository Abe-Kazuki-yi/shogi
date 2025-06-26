package jp.furykasukabe.shogi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.furykasukabe.shogi.entity.PieceInfo;

@Repository
public interface PieceRepository extends JpaRepository<PieceInfo, Integer> {
	PieceInfo findById(int id);
}
