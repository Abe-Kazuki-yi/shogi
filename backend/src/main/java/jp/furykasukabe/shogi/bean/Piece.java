package jp.furykasukabe.shogi.bean;

import jp.furykasukabe.shogi.entity.PieceInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Piece {
	private int id;
	private String name;
	private String promotedName;
	private boolean isPromoted;
	private String displayName;

	public void setPromoted(boolean promoted) {
		this.isPromoted = promoted;
		this.displayName = promoted ? promotedName : name;
	}
	
	public Piece(PieceInfo pieceInfo, boolean isPromoted) {
		this.id = pieceInfo.getId();
		this.name = pieceInfo.getName();
		this.promotedName = pieceInfo.getPromotedName();
		this.isPromoted = isPromoted;
		this.displayName = isPromoted ? this.promotedName : this.name;
	}
	
	public Piece(PieceInfo pieceInfo) {
		this(pieceInfo, false);
	}
}
