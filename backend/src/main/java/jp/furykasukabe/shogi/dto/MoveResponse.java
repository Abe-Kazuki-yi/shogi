package jp.furykasukabe.shogi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MoveResponse {
    private BoardDTO boardDTO;
    private boolean promotable;
}
