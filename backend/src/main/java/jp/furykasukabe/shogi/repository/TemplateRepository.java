package jp.furykasukabe.shogi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.furykasukabe.shogi.entity.Template;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Integer>{
	List<Template> findByPlayerIdAndPlayFirst(int playerId, boolean playFirst);
	
	List<Template> findByPlayerIdAndPlayFirstAndAvailable(int playerId, boolean playFirst, boolean available);
}
