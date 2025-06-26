package jp.furykasukabe.shogi.dto;

import jp.furykasukabe.shogi.bean.Piece;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HandPieceDTO {
    private Piece piece;
    private int count;
}
