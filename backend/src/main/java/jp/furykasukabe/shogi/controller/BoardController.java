package jp.furykasukabe.shogi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import jp.furykasukabe.shogi.bean.Board;
import jp.furykasukabe.shogi.dto.BoardDTO;
import jp.furykasukabe.shogi.dto.DropRequest;
import jp.furykasukabe.shogi.dto.MoveRequest;
import jp.furykasukabe.shogi.dto.MoveResponse;
import jp.furykasukabe.shogi.dto.Square;
import jp.furykasukabe.shogi.entity.ShogiList;
import jp.furykasukabe.shogi.factory.BoardFactory;
import jp.furykasukabe.shogi.service.BoardService;
import lombok.RequiredArgsConstructor;

@CrossOrigin
@RequestMapping("/board")
@RequiredArgsConstructor
@RestController
public class BoardController {
	private final BoardFactory boardFactory;
	private final BoardService boardService;

	@GetMapping("/initial/{isPlay}")
	public BoardDTO first(@PathVariable boolean isPlay, HttpSession session) {
	    Board initialBoard = boardFactory.createInitialBoard(isPlay);
	    session.setAttribute("boardState", initialBoard);
	    return new BoardDTO(initialBoard);
	}
	
	@PostMapping("/next/{player}")
	public BoardDTO next(@RequestBody ShogiList shogiList, @PathVariable String player, HttpSession session) {
		Board currentBoard = (Board) session.getAttribute("boardState");
		Board nextBoard = boardFactory.createNextBoard(currentBoard, shogiList);
		session.setAttribute("boardState", nextBoard);
		return new BoardDTO(nextBoard);
	}

	@GetMapping("/select/{x}/{y}")
	public boolean hasMyPiece(@PathVariable int x, @PathVariable int y, HttpSession session) {
		Board currentBoard = (Board) session.getAttribute("boardState");
		return currentBoard.getMyFormation()[x][y] != null;
	}

	@PostMapping("/movable")
	public List<Square> moveable(@RequestBody Square square, HttpSession session) {
		Board currentBoard = (Board) session.getAttribute("boardState");
		return boardService.findMovableSquare(currentBoard, square);
	}

	@PostMapping("/move")
	public MoveResponse move(@RequestBody MoveRequest requestBody, HttpSession session) {
		Board currentBoard = (Board) session.getAttribute("boardState");
		boolean promotable = boardService.isPromotable(currentBoard, requestBody);
		Board nextBoard = boardService.advanceOneStep(currentBoard, requestBody);
		session.setAttribute("boardState", nextBoard);
		return new MoveResponse(new BoardDTO(nextBoard), promotable);
	}

	@PostMapping("/promote")
	public BoardDTO promote(@RequestBody Square square, HttpSession session) {
		Board currentBoard = (Board) session.getAttribute("boardState");
		Board nextBoard = boardService.executePromote(currentBoard, square);
		session.setAttribute("boardState", nextBoard);
		return new BoardDTO(nextBoard);
	}

	@GetMapping("/dropable")
	public List<Square> dropable(HttpSession session) {
		Board currentBoard = (Board) session.getAttribute("boardState");
		return boardService.findDropableSquare(currentBoard);
	}

	@PostMapping("/drop")
	public BoardDTO drop(@RequestBody DropRequest dropRequest, HttpSession session) {
		Board currentBoard = (Board) session.getAttribute("boardState");
		Board nextBoard = boardService.dropPiece(currentBoard, dropRequest.getPiece(), dropRequest.getSquare());
		session.setAttribute("boardState", nextBoard);
		return new BoardDTO(nextBoard);
	}
}
