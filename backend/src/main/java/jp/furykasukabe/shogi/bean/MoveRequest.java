package jp.furykasukabe.shogi.bean;

import lombok.Data;

@Data
public class MoveRequest {
	private int[] from;
	private int[] to;
}
