package jp.furykasukabe.shogi.dto;

import jp.furykasukabe.shogi.bean.Board;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MoveResponse {
    private Board board;
    private boolean promotable;
}
