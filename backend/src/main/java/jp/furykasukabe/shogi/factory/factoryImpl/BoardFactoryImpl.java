package jp.furykasukabe.shogi.factory.factoryImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import jp.furykasukabe.shogi.bean.Board;
import jp.furykasukabe.shogi.entity.Piece;
import jp.furykasukabe.shogi.factory.BoardFactory;
import jp.furykasukabe.shogi.service.PieceService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardFactoryImpl implements BoardFactory {

	private final PieceService pieceService;
	
	@Override
	public Board createInitinalBoard() {
		List<Piece> pieces = pieceService.findAllPieces();
		Piece[][] myFormation = new Piece[10][10];
		Piece[][] opponentFormation = new Piece[10][10];
		Map<Piece, Integer> myHand = Map.of();         // 空の手駒
		Map<Piece, Integer> opponentHand = Map.of();   // 空の手駒

		for(Piece piece: pieces) {
			switch(piece.getId()){
			case 1:
				myFormation[5][9] = piece;
				opponentFormation[5][1] = piece;
				break;
			case 2:
				myFormation[4][9] = piece;
				myFormation[6][9] = piece;
				opponentFormation[4][1] = piece;
				opponentFormation[6][1] = piece;
				break;
			case 3:
				myFormation[3][9] = piece;
				myFormation[7][9] = piece;
				opponentFormation[3][1] = piece;
				opponentFormation[7][1] = piece;
				break;	
			case 4:
				myFormation[2][9] = piece;
				myFormation[8][9] = piece;
				opponentFormation[2][1] = piece;
				opponentFormation[8][1] = piece;
				break;
			case 5:
				myFormation[1][9] = piece;
				myFormation[9][9] = piece;
				opponentFormation[1][1] = piece;
				opponentFormation[9][1] = piece;
				break;
			case 6:
				myFormation[2][8] = piece;
				opponentFormation[8][2] = piece;
				break;
			case 7:
				myFormation[8][8] = piece;
				opponentFormation[2][2] = piece;
				break;
			case 8:
				for(int i = 1; i < 10; i++) {
					myFormation[i][7] = piece;
					opponentFormation[i][3] = piece;
				}
			}
		}
		return new Board(myFormation, opponentFormation, myHand, opponentHand);
	}

}
