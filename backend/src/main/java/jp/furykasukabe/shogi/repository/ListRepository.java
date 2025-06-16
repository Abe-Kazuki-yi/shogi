package jp.furykasukabe.shogi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.furykasukabe.shogi.entity.ShogiList;

public interface ListRepository extends JpaRepository<ShogiList, Integer> {

	List<ShogiList> findByTemplateId(int templateId);
}
