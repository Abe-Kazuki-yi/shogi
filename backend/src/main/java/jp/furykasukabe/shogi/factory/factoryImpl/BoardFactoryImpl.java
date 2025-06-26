package jp.furykasukabe.shogi.factory.factoryImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import jp.furykasukabe.shogi.bean.Board;
import jp.furykasukabe.shogi.bean.Piece;
import jp.furykasukabe.shogi.entity.PieceInfo;
import jp.furykasukabe.shogi.entity.ShogiList;
import jp.furykasukabe.shogi.factory.BoardFactory;
import jp.furykasukabe.shogi.service.PieceService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardFactoryImpl implements BoardFactory {

	private final PieceService pieceService;

	@Override
	public Board createInitialBoard(boolean isPlay) {
		List<PieceInfo> pieces = pieceService.findAllPieces();
		Piece[][] myFormation = new Piece[10][10];
		Piece[][] opponentFormation = new Piece[10][10];
		Map<Piece, Integer> myHand = new HashMap<>();           // ← OK（変更可能）
		Map<Piece, Integer> opponentHand = new HashMap<>();

		for (PieceInfo piece : pieces) {
		    switch (piece.getId()) {
		        case 1:
		            myFormation[5][9] = new Piece(piece);
		            opponentFormation[5][1] = new Piece(piece);
		            break;
		        case 2:
		            myFormation[4][9] = new Piece(piece);
		            myFormation[6][9] = new Piece(piece);
		            opponentFormation[4][1] = new Piece(piece);
		            opponentFormation[6][1] = new Piece(piece);
		            break;
		        case 3:
		            myFormation[3][9] = new Piece(piece);
		            myFormation[7][9] = new Piece(piece);
		            opponentFormation[3][1] = new Piece(piece);
		            opponentFormation[7][1] = new Piece(piece);
		            break;
		        case 4:
		            myFormation[2][9] = new Piece(piece);
		            myFormation[8][9] = new Piece(piece);
		            opponentFormation[2][1] = new Piece(piece);
		            opponentFormation[8][1] = new Piece(piece);
		            break;
		        case 5:
		            myFormation[1][9] = new Piece(piece);
		            myFormation[9][9] = new Piece(piece);
		            opponentFormation[1][1] = new Piece(piece);
		            opponentFormation[9][1] = new Piece(piece);
		            break;
		        case 6:
		            myFormation[2][8] = new Piece(piece);
		            opponentFormation[8][2] = new Piece(piece);
		            break;
		        case 7:
		            myFormation[8][8] = new Piece(piece);
		            opponentFormation[2][2] = new Piece(piece);
		            break;
		        case 8:
		            for (int i = 1; i < 10; i++) {
		                myFormation[i][7] = new Piece(piece);
		                opponentFormation[i][3] = new Piece(piece);
		            }
		            break;
		    }
		}

		return new Board(myFormation, opponentFormation, myHand, opponentHand, isPlay);
	}

	@Override
	public Board createNextBoard(Board currentBoard, ShogiList shogiList) {

		Board nextBoard = null;
		try {
			if (currentBoard.getMyFormation()[shogiList.getBeforeX()][shogiList.getBeforeY()] == null) throw new RuntimeException("元の盤面に駒がありません");
			if (currentBoard.getMyFormation()[shogiList.getTargetX()][shogiList.getTargetY()] != null) throw new RuntimeException("移動先に自分の駒があります");

			Piece[][] nextMyFormation = currentBoard.getMyFormation();
			nextMyFormation[shogiList.getTargetX()][shogiList.getTargetY()] = nextMyFormation[shogiList.getBeforeX()][shogiList.getBeforeY()];
			nextMyFormation[shogiList.getBeforeX()][shogiList.getBeforeY()] = null;
			currentBoard.setMyFormation(nextMyFormation);
			
			if(currentBoard.getOpponentFormation()[shogiList.getTargetX()][shogiList.getTargetY()] != null) {
				Piece[][] nextOpponentFormation = currentBoard.getOpponentFormation();
				Piece piece = currentBoard.getOpponentFormation()[shogiList.getTargetX()][shogiList.getTargetY()];
				
				nextOpponentFormation[shogiList.getTargetX()][shogiList.getTargetY()] = null;
				currentBoard.setOpponentFormation(nextOpponentFormation);
				
				Map<Piece, Integer> currentMyHand = currentBoard.getMyHand();
				currentMyHand.merge(piece, 1, Integer::sum);
				currentBoard.setMyHand(currentMyHand);
			}
			nextBoard = currentBoard;
					
		} catch (RuntimeException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return nextBoard;
	}

}
