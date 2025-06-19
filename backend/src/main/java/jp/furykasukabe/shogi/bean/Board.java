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
	private Map<Piece, Integer> myHand;
	private Map<Piece, Integer> opponentHand;	
}
