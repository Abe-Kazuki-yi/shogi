package jp.furykasukabe.shogi.dto;

import lombok.Data;

@Data
public class MoveRequest {
	private int[] from;
	private int[] to;
}
