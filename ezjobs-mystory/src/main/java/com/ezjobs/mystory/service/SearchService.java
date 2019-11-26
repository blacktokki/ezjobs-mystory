package com.ezjobs.mystory.service;

import java.util.Arrays;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

	public void searchPauseByTags(Model model) { // 사용자의 태그 갯수를 3개 이하로 제한
		model.addAttribute("tagsError", 1);
		String searchTags = (String) model.getAttribute("searchTags");
		String[] searchTagArray = searchTags.split(",");
		model.addAttribute("searchTagArray0", searchTagArray[0]);
		model.addAttribute("searchTagArray1", searchTagArray[1]);
		model.addAttribute("searchTagArray2", searchTagArray[2]);
	}

	public void searchAct(Model model) {

		model.addAttribute("tagsError", 0);
		String searchTags = (String) model.getAttribute("searchTags"); // 유저가 선택한 태그
		String[] searchTagArray;
		if (searchTags.isEmpty()) {
			searchTagArray = new String[0];
		} else {
			searchTagArray = searchTags.split(",");
		}

		String userSearchWord = (String) model.getAttribute("userSearchWord"); // 유저의 검색어
		String nowPage = (String) model.getAttribute("nowPage"); // 유저의 현제 페이지
		int searchWay = (int) model.getAttribute("searchWay"); // 유저의 검색조건(AND, OR)
		int numberOfSeeSentence = (int) model.getAttribute("numberOfSeeSentence"); // 한 페이지 당 보이는 문장 카드 수
		if (numberOfSeeSentence <= 0) {
			numberOfSeeSentence = 3;
		}

		int pageNum = Integer.parseInt(nowPage) - 1;// 값이없을경우 0
		PageRequest pr = PageRequest.of(pageNum, numberOfSeeSentence, Sort.by(Sort.Direction.DESC, "id"));

		if (searchWay == 1) {
			if (userSearchWord.isEmpty() || userSearchWord.trim() == "") { // 태그만으로 검색
				userSearchWord = "";
				System.out.println("태그 길이 : " + searchTagArray.length);

				if (searchTagArray.length == 0) {
					Page<Sentence> Searchs = sentenceRepository.findAll(pr);
					System.out.println(Searchs);
					model.addAttribute("searchs", Searchs);
					model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
					model.addAttribute("recordCount", Searchs.getTotalElements());

				} else if (searchTagArray.length == 1) {
					Page<Sentence> Searchs = sentenceRepository.findByTagsContains(searchTagArray[0], pr);
					System.out.println(Searchs);
					model.addAttribute("searchs", Searchs);
					model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
					model.addAttribute("recordCount", Searchs.getTotalElements());

				} else if (searchTagArray.length == 2) {
					Page<Sentence> Searchs = sentenceRepository.findByTagsContainsOrTagsContains(searchTagArray[0],
							searchTagArray[1], pr);
					System.out.println(Searchs);
					model.addAttribute("searchs", Searchs);
					model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
					model.addAttribute("recordCount", Searchs.getTotalElements());

				} else {
					Page<Sentence> Searchs = sentenceRepository.findByTagsContainsOrTagsContainsOrTagsContains(
							searchTagArray[0], searchTagArray[1], searchTagArray[2], pr);
					System.out.println(Searchs);
					model.addAttribute("searchs", Searchs);
					model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
					model.addAttribute("recordCount", Searchs.getTotalElements());

				}
			} else {
				if (searchTagArray.length == 0) {
					Page<Sentence> Searchs = sentenceRepository.findByTextContains(userSearchWord, pr);
					model.addAttribute("searchs", Searchs);
					model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
					model.addAttribute("recordCount", Searchs.getTotalElements());

				} else if (searchTagArray.length == 1) {
					Page<Sentence> Searchs = sentenceRepository.findByTextContainsAndTagsContains(userSearchWord,
							searchTagArray[0], pr);
					model.addAttribute("searchs", Searchs);
					model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
					model.addAttribute("recordCount", Searchs.getTotalElements());

				} else if (searchTagArray.length == 2) {
					Page<Sentence> Searchs = sentenceRepository.findByTextContainsAndTagsContainsOrTagsContains(
							userSearchWord, searchTagArray[0], searchTagArray[1], pr);
					model.addAttribute("searchs", Searchs);
					model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
					model.addAttribute("recordCount", Searchs.getTotalElements());

				} else {
					Page<Sentence> Searchs = sentenceRepository
							.findByTextContainsAndTagsContainsOrTagsContainsOrTagsContains(userSearchWord,
									searchTagArray[0], searchTagArray[1], searchTagArray[2], pr);
					model.addAttribute("searchs", Searchs);
					model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
					model.addAttribute("recordCount", Searchs.getTotalElements());

				}
			}
			if (pageNum <= 4) {
				model.addAttribute("ifPageZeroThenPlusOne", 1);

			} else {
				model.addAttribute("ifPageZeroThenPlusOne", 0);

			}
		} else { // 만일, 제 4의 값을 지니고 있더라도 전체로 검색하니 생각해둘것
			if (userSearchWord.isEmpty() || userSearchWord.trim() == "") {
				userSearchWord = "";

				if (searchTagArray.length == 0) { // 태그 검색어 둘다 X
					Page<Sentence> Searchs = sentenceRepository.findAll(pr);
					model.addAttribute("searchs", Searchs);
					model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
					model.addAttribute("recordCount", Searchs.getTotalElements());
				} else if (searchTagArray.length == 1) { // 태그만 1개
					Page<Sentence> Searchs = sentenceRepository.findByTagsContains(searchTagArray[0], pr);
					model.addAttribute("searchs", Searchs);
					model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
					model.addAttribute("recordCount", Searchs.getTotalElements());

				} else if (searchTagArray.length == 2) { // 태그만 2개
					Page<Sentence> Searchs = sentenceRepository.findByTagsContainsAndTagsContains(searchTagArray[0],
							searchTagArray[1], pr);
					model.addAttribute("searchs", Searchs);
					model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
					model.addAttribute("recordCount", Searchs.getTotalElements());

				} else { // 태그만 3개
					Page<Sentence> Searchs = sentenceRepository.findByTagsContainsAndTagsContainsAndTagsContains(
							searchTagArray[0], searchTagArray[1], searchTagArray[2], pr);
					model.addAttribute("searchs", Searchs);
					model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
					model.addAttribute("recordCount", Searchs.getTotalElements());

				}

			} else {
				if (searchTagArray.length == 0) {
					Page<Sentence> Searchs = sentenceRepository.findByTextContains(userSearchWord, pr);
					model.addAttribute("searchs", Searchs);
					model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
					model.addAttribute("recordCount", Searchs.getTotalElements());
				} else if (searchTagArray.length == 1) {
					Page<Sentence> Searchs = sentenceRepository.findByTextContainsAndTagsContains(userSearchWord,
							searchTagArray[0], pr);
					model.addAttribute("searchs", Searchs);
					model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
					model.addAttribute("recordCount", Searchs.getTotalElements());

				} else if (searchTagArray.length == 2) {
					Page<Sentence> Searchs = sentenceRepository.findByTextContainsAndTagsContainsAndTagsContains(
							userSearchWord, searchTagArray[0], searchTagArray[1], pr);
					model.addAttribute("searchs", Searchs);
					model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
					model.addAttribute("recordCount", Searchs.getTotalElements());

				} else {
					Page<Sentence> Searchs = sentenceRepository.findByTextContainsAndTagsContainsAndTagsContainsAndTagsContains(
							userSearchWord, searchTagArray[0], searchTagArray[1], searchTagArray[2], pr);
					model.addAttribute("searchs", Searchs);
					model.addAttribute("pageNavNumber", Searchs.getNumber() / 5);
					model.addAttribute("recordCount", Searchs.getTotalElements());

				}
			}
			if (pageNum <= 4) {
				model.addAttribute("ifPageZeroThenPlusOne", 1);

			} else {
				model.addAttribute("ifPageZeroThenPlusOne", 0);

			}
		}
		model.addAttribute("searchWay", searchWay);
		model.addAttribute("userSearchWord", userSearchWord);
	}

	public void searchWordCloud(Model model) {
			
	}
}