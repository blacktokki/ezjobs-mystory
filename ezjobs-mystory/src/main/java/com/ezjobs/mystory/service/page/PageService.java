package com.ezjobs.mystory.service.page;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

public interface PageService<T extends Map<?,?>> {

	default Pageable getPageRequest(T map) {
		return null;
	};
	
	static void addPageAttributes(Map<?,?> map,Model model) {
		model.addAttribute("page", map.get("page"));
		model.addAttribute("size", map.get("size"));
		model.addAttribute("op", map.get("op"));
		model.addAttribute("keyword", map.get("keyword"));
	}
}
