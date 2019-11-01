package com.ezjobs.mystory.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ezjobs.mystory.entity.Search;
import com.ezjobs.mystory.repository.SearchRepository;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class SearchService {
	
	@Inject
	SearchRepository searchRepository;
	
	@Inject
	ObjectMapper mapper;
	
	List<Search> searchDummy=new ArrayList<>();
	int searchIdx=1;
	int tpTotal = 99;
	int tpCount = 0;

	public void searchAct(Model model, String act) {
		if(searchDummy.size()!=0) {
			searchDummy.clear();
			tpCount = 0;
		}
		for(int i=0;i<tpTotal;i++) {
			if(i<5) {
				Search search=new Search(i, "a","a","a","a","a","a","a", new Date(), i);
				if(search.getType().contains(act)||search.getDept().contains(act)|| search.getCompany().contains(act)|| search.getQuestion().contains(act) || search.getAnswer().contains(act) || search.getUserId().contains(act) || act.isEmpty()||act.trim()==""){
					// priority, date, group을 제외하고 모든 결과 검색
					searchDummy.add(search);
					tpCount++;
					}
			}
			else
			{
				Search search=new Search(i, "b","b","b","b","b","b","b", new Date(), i);
				if(search.getType().contains(act)||search.getDept().contains(act)|| search.getCompany().contains(act)|| search.getQuestion().contains(act) || search.getAnswer().contains(act) || search.getUserId().contains(act) || act.isEmpty()||act.trim()==""){
					searchDummy.add(search); tpCount++;
				}
			}
		}
		String npg = (String)model.getAttribute("npg");
		int pageNum=Integer.parseInt(npg);//값이없을경우 0
		PageRequest pr=PageRequest.of(pageNum, 3,Sort.by(Sort.Direction.DESC,"editDate"));
		Page<Search> searchs=searchRepository.findAll(pr);
		model.addAttribute("searchs",searchs);
		model.addAttribute("pageNavNumber",searchs.getNumber()/5);
		if(tpCount==0) {
			// 검색 결과가 없습니다
			model.addAttribute("total", tpCount);
			model.addAttribute("tps", act);
		}else {
			model.addAttribute("searchLists",searchDummy); // 이것과 searchList.jsp에 있는 데이터 목록과 맵핑됨
			model.addAttribute("total", tpCount);
			model.addAttribute("tps", act);
		}
	}	
		
	//이하는 페이징	
    private final static int pageCount = 5;
    private int blockStartNum = 0;
    private int blockLastNum = 0;
    private int lastPage = 0;

    public int getBlockStartNum() {
        return blockStartNum;
    }
    public void setBlockStartNum(int blockStartNum) {
        this.blockStartNum = blockStartNum;
    }
    public int getBlockLastNum() {
        return blockLastNum;
    }
    public void setBlockLastNum(int blockLastNum) {
        this.blockLastNum = blockLastNum;
    }
    public int getLastPage() {
        return lastPage;
    }
    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    // block을 생성
    // 현재 페이지가 속한 block의 시작 번호, 끝 번호를 계산
    public void makeBlock(Model model, int npg){
        
    	int blockNum = 0;
        blockNum = (int)Math.floor((npg-1)/ pageCount);
        blockStartNum = (pageCount * blockNum) + 1;
        blockLastNum = blockStartNum + (pageCount-1);
        model.addAttribute("startBlock", blockStartNum);
        model.addAttribute("lastBlock", blockLastNum);
        model.addAttribute("nowPage", npg);
    }

    // 총 페이지의 마지막 번호
    public void makeLastPage(Model model) {
        int total = tpCount;

        if( total % pageCount == 0 ) {
            lastPage = (int)Math.ceil(total/pageCount);
        }
        else {
            lastPage = (int)Math.ceil(total/pageCount) + 1;
        }
        model.addAttribute("lastPage", lastPage);
    }
}