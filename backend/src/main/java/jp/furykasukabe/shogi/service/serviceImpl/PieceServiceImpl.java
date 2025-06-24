package jp.furykasukabe.shogi.service.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import jp.furykasukabe.shogi.entity.PieceInfo;
import jp.furykasukabe.shogi.repository.PieceRepository;
import jp.furykasukabe.shogi.service.PieceService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PieceServiceImpl implements PieceService {

	private final PieceRepository boardRepository;
	
	@Override
	public List<PieceInfo> findAllPieces() {
		return boardRepository.findAll();
	}

}
