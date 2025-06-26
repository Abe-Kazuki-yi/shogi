package jp.furykasukabe.shogi.dto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jp.furykasukabe.shogi.bean.Board;
import jp.furykasukabe.shogi.bean.Piece;
import lombok.Data;

@Data
public class BoardDTO {
	private Piece[][] myFormation;
    private Piece[][] opponentFormation;
    private List<HandPieceDTO> myHand;
    private List<HandPieceDTO> opponentHand;
    private boolean isPlay;

    public BoardDTO(Board board) {
        this.myFormation = board.getMyFormation();
        this.opponentFormation = board.getOpponentFormation();
        this.myHand = convertMap(board.getMyHand());
        this.opponentHand = convertMap(board.getOpponentHand());
        this.isPlay = board.isPlay();
    }

    private List<HandPieceDTO> convertMap(Map<Piece, Integer> handMap) {
        return handMap.entrySet().stream()
                .map(e -> new HandPieceDTO(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }
}
