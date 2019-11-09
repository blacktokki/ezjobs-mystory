package com.ezjobs.mystory.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ezjobs.mystory.entity.Resume;
import com.ezjobs.mystory.repository.ResumeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SearchService {

	@Inject
	ResumeRepository resumeRepository;

	@Inject
	ObjectMapper mapper;

	public void searchAct(Model model) {

		String userSearchWord = (String) model.getAttribute("userSearchWord");
		String nowPage = (String) model.getAttribute("nowPage");
		int searchWay = (int) model.getAttribute("searchWay");
		int numberOfSeeResume = (int) model.getAttribute("numberOfSeeResume");
		if(numberOfSeeResume<=0) {
			numberOfSeeResume = 3;
		}
		
		int pageNum = Integer.parseInt(nowPage) - 1;// 값이없을경우 0
		PageRequest pr = PageRequest.of(pageNum, numberOfSeeResume, Sort.by(Sort.Direction.DESC, "editDate"));
		
		if(searchWay==2) {
			
			if(userSearchWord.isEmpty() || userSearchWord.trim()=="") {
				userSearchWord = "";
				Page<Resume> Searchs = resumeRepository.findAll(pr);
				model.addAttribute("searchs", Searchs);
				model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
				model.addAttribute("recordCount", Searchs.getTotalElements());
			}else{
				Page<Resume> Searchs = resumeRepository.findByQuestionContaining(pr, userSearchWord);
				model.addAttribute("searchs", Searchs);
				model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
				model.addAttribute("recordCount", Searchs.getTotalElements());
			}
			if(pageNum<=4) {
				model.addAttribute("ifPageZeroThenPlusOne", 1);
			}else {
				model.addAttribute("ifPageZeroThenPlusOne", 0);
			}			
		}else if(searchWay==3) {
			
			if(userSearchWord.isEmpty() || userSearchWord.trim()=="") {
				userSearchWord = "";
				Page<Resume> Searchs = resumeRepository.findAll(pr);
				model.addAttribute("searchs", Searchs);
				model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
				model.addAttribute("recordCount", Searchs.getTotalElements());
			}else{
				Page<Resume> Searchs = resumeRepository.findByAnswerContaining(pr, userSearchWord);
				model.addAttribute("searchs", Searchs);
				model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
				model.addAttribute("recordCount", Searchs.getTotalElements());
			}
			if(pageNum<=4) {
				model.addAttribute("ifPageZeroThenPlusOne", 1);
			}else {
				model.addAttribute("ifPageZeroThenPlusOne", 0);
			}
		}else { // 만일, 제 4의 값을 지니고 있더라도 전체로 검색하니 생각해둘것
			if(userSearchWord.isEmpty() || userSearchWord.trim()=="") {
				userSearchWord = "";
				Page<Resume> Searchs = resumeRepository.findAll(pr);
				model.addAttribute("searchs", Searchs);
				model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
				model.addAttribute("recordCount", Searchs.getTotalElements());
			}else{
				Page<Resume> Searchs = resumeRepository.findByTypeContainingOrDeptContainingOrCompanyContainingOrQuestionContainingOrAnswerContainingOrUserIdContaining(pr, userSearchWord, userSearchWord, userSearchWord, userSearchWord, userSearchWord, userSearchWord);
				model.addAttribute("searchs", Searchs);
				model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
				model.addAttribute("recordCount", Searchs.getTotalElements());
			}
			if(pageNum<=4) {
				model.addAttribute("ifPageZeroThenPlusOne", 1);
			}else {
				model.addAttribute("ifPageZeroThenPlusOne", 0);
			}
		}
		model.addAttribute("searchWay", searchWay);
	}
}