package jp.furykasukabe.shogi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.furykasukabe.shogi.entity.ShogiList;
import jp.furykasukabe.shogi.repository.ListRepository;
import lombok.RequiredArgsConstructor;

@CrossOrigin
@RequestMapping("/list")
@RequiredArgsConstructor
@RestController
public class ListController {

	private final ListRepository listRepository;
	
	@GetMapping("/{templateId}")
	public List<ShogiList> getListsByTemplateId(@PathVariable int templateId) {
	    return listRepository.findByTemplateId(templateId);
	}
	
	@PostMapping("/{num}")
	public List<ShogiList> getListsByTemplateIdAndNum(@RequestBody int[] templateIds, @PathVariable int num){
		List<ShogiList> shogiList = new ArrayList<>();
		
		for(int id: templateIds) {
			if(listRepository.findByTemplateIdAndNum(id, num)!=null)
			shogiList.addAll(listRepository.findByTemplateIdAndNum(id, num));
			}
		return shogiList;
	}
	
}
