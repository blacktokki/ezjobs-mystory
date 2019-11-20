package com.ezjobs.mystory.service;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ezjobs.mystory.entity.Sentence;
import com.ezjobs.mystory.repository.SentenceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SearchService {

	@Inject
	SentenceRepository sentenceRepository;

	@Inject
	ObjectMapper mapper;

	public void searchAct(Model model) {

		String userSearchWord = (String) model.getAttribute("userSearchWord");
		String nowPage = (String) model.getAttribute("nowPage");
		int searchWay = (int) model.getAttribute("searchWay");
		int numberOfSeeSentence = (int) model.getAttribute("numberOfSeeSentence");
		if(numberOfSeeSentence<=0) {
			numberOfSeeSentence = 3;
		}
		
		int pageNum = Integer.parseInt(nowPage) - 1;// 값이없을경우 0
		PageRequest pr = PageRequest.of(pageNum, numberOfSeeSentence, Sort.by(Sort.Direction.DESC, "id"));
	
		
		if(searchWay==2) {
			
			if(userSearchWord.isEmpty() || userSearchWord.trim()=="") {
				userSearchWord = "";
				Page<Sentence> Searchs = sentenceRepository.findAll(pr);
				model.addAttribute("searchs", Searchs);
				model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
				model.addAttribute("recordCount", Searchs.getTotalElements());
			}else{
				/*
				 * Page<Sentence> Searchs = sentenceRepository.findByQuestionContaining(pr,
				 * userSearchWord); model.addAttribute("searchs", Searchs);
				 * model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
				 * model.addAttribute("recordCount", Searchs.getTotalElements());
				 */
			}
			if(pageNum<=4) {
				model.addAttribute("ifPageZeroThenPlusOne", 1);
			}else {
				model.addAttribute("ifPageZeroThenPlusOne", 0);
			}			
		}else if(searchWay==3) {
			
			if(userSearchWord.isEmpty() || userSearchWord.trim()=="") {
				userSearchWord = "";
				Page<Sentence> Searchs = sentenceRepository.findAll(pr);
				model.addAttribute("searchs", Searchs);
				model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
				model.addAttribute("recordCount", Searchs.getTotalElements());
			}else{
				/*
				 * Page<Sentence> Searchs = sentenceRepository.findByAnswerContaining(pr,
				 * userSearchWord); model.addAttribute("searchs", Searchs);
				 * model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
				 * model.addAttribute("recordCount", Searchs.getTotalElements());
				 */
			}
			if(pageNum<=4) {
				model.addAttribute("ifPageZeroThenPlusOne", 1);
			}else {
				model.addAttribute("ifPageZeroThenPlusOne", 0);
			}
		}else { // 만일, 제 4의 값을 지니고 있더라도 전체로 검색하니 생각해둘것
			if(userSearchWord.isEmpty() || userSearchWord.trim()=="") {
				userSearchWord = "";
				Page<Sentence> Searchs = sentenceRepository.findAll(pr);
				model.addAttribute("searchs", Searchs);
				model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
				model.addAttribute("recordCount", Searchs.getTotalElements());
			}else{
				/*
				 * Page<Sentence> Searchs = sentenceRepository.
				 * findByTypeContainingOrDeptContainingOrCompanyContainingOrQuestionContainingOrAnswerContainingOrUserIdContaining
				 * (pr, userSearchWord, userSearchWord, userSearchWord, userSearchWord,
				 * userSearchWord, userSearchWord); model.addAttribute("searchs", Searchs);
				 * model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
				 * model.addAttribute("recordCount", Searchs.getTotalElements());
				 */
			}
			if(pageNum<=4) {
				model.addAttribute("ifPageZeroThenPlusOne", 1);
			}else {
				model.addAttribute("ifPageZeroThenPlusOne", 0);
			}
		}
		model.addAttribute("searchWay", searchWay);
	}
	
	public void searchWordCloud(Model model) {
		
	}
}