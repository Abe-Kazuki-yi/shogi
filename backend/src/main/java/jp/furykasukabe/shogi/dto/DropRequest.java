package jp.furykasukabe.shogi.dto;

import jp.furykasukabe.shogi.bean.Piece;
import lombok.Data;

@Data
public class DropRequest {
	private Piece piece;
	private Square square;
}
