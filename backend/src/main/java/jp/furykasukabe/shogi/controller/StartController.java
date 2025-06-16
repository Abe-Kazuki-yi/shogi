package jp.furykasukabe.shogi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.furykasukabe.shogi.entity.Template;
import jp.furykasukabe.shogi.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/start")
@CrossOrigin
@RequiredArgsConstructor
public class StartController {

	private final TemplateRepository templateRepository;
	
	@GetMapping("/{playerId}/{playFirst}")
	public List<Template> getTemplate(@PathVariable int playerId, @PathVariable boolean playFirst) {
		return templateRepository.findByPlayerIdAndPlayFirst(playerId, playFirst);
	}
}
