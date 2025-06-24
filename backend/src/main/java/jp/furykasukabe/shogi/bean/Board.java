package jp.furykasukabe.shogi.bean;

import java.io.Serializable;
import java.util.Map;

import jp.furykasukabe.shogi.entity.Piece;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Board implements Serializable{
	private Piece[][] myFormation;
	private Piece[][] opponentFormation;
	private Map<String, Integer> myHand;
	private Map<String, Integer> opponentHand;
	private boolean isPlay;
	
	public void addMyHand(String pieceName) {
	    myHand.compute(pieceName, (key, val) -> (val == null) ? 1 : val + 1);
	}
	
	public void addOpponentHand(String pieceName) {
	    myHand.compute(pieceName, (key, val) -> (val == null) ? 1 : val + 1);
	}
}
