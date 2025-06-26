package jp.furykasukabe.shogi.bean;

import java.io.Serializable;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Board implements Serializable{
	private Piece[][] myFormation;
	private Piece[][] opponentFormation;
	private Map<Piece, Integer> myHand;
	private Map<Piece, Integer> opponentHand;
	private boolean isPlay;
	
	public void addMyHand(Piece piece) {
	    myHand.compute(piece, (key, val) -> (val == null) ? 1 : val + 1);
	}
	
	public void addOpponentHand(Piece piece) {
	    myHand.compute(piece, (key, val) -> (val == null) ? 1 : val + 1);
	}
	
	public void removeMyHand(Piece piece) {
	    myHand.computeIfPresent(piece, (key, val) -> {
	        if (val <= 1) {
	            return null; // nullを返すとエントリが削除される
	        } else {
	            return val - 1;
	        }
	    });
	}

	public void removeOpponentHand(Piece piece) {
	    opponentHand.computeIfPresent(piece, (key, val) -> {
	        if (val <= 1) {
	            return null;
	        } else {
	            return val - 1;
	        }
	    });
	}
}
