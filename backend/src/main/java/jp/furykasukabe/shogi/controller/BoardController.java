package jp.furykasukabe.shogi.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.furykasukabe.shogi.bean.Board;
import jp.furykasukabe.shogi.factory.BoardFactory;
import lombok.RequiredArgsConstructor;

@CrossOrigin
@RequestMapping("/board")
@RequiredArgsConstructor
@RestController
public class BoardController {
	private final BoardFactory boardFactory;
	
	@GetMapping("/first")
	public Board test() {
		return  boardFactory.createInitinalBoard();
	}
}
