package jp.furykasukabe.shogi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.furykasukabe.shogi.entity.Template;
import jp.furykasukabe.shogi.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

	private final TemplateRepository templateRepository;
	
	@GetMapping("/{id}/{playFirst}")
	List<Template> getFirst(@PathVariable int id, @PathVariable boolean playFirst){
		return templateRepository.findByPlayerIdAndPlayFirstAndAvailable(id, playFirst, true);
	}
	
}
