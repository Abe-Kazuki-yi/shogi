package jp.furykasukabe.shogi.controller;

import java.util.Arrays;
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
import jp.furykasukabe.shogi.bean.MoveRequest;
import jp.furykasukabe.shogi.bean.Square;
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
	public Board first(@PathVariable boolean isPlay, HttpSession session) {
		Board initialBoard = boardFactory.createInitialBoard(isPlay);
	    session.setAttribute("boardState", initialBoard);
	    //System.out.println("Session ID: " + session.getId());
	    return initialBoard;
	}
	
	@PostMapping("/next/{player}")
	public Board next(@RequestBody ShogiList shogiList, @PathVariable String player, HttpSession session) {
		Board currentBoard = (Board) session.getAttribute("boardState");
		//System.out.println("currentBoardは"+currentBoard+"です");
		//System.out.println("Session ID: " + session.getId());
	    Board nextBoard = boardFactory.createNextBoard(currentBoard, shogiList);
	    session.setAttribute("boardState", nextBoard);
	    return nextBoard;
	}
	
	@GetMapping("/select/{x}/{y}")
	public boolean hasMyPiece(@PathVariable int x, @PathVariable int y, HttpSession session) {
		Board currentBoard = (Board) session.getAttribute("boardState");
		return currentBoard.getMyFormation()[x][y] != null;
	}
	
	@PostMapping("/movable")
	public List<Square> moveable(@RequestBody Square square, HttpSession session){
		Board currentBoard = (Board) session.getAttribute("boardState");		
		return boardService.findMovableSquare(currentBoard, square);
	}
	
	@PostMapping("/move")
	public Board move(@RequestBody MoveRequest requestBody) {
	    System.out.println("from: " + Arrays.toString(requestBody.getFrom()));
	    System.out.println("to: " + Arrays.toString(requestBody.getTo()));
	    return null;
	}
}
