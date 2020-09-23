package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor    // 모든 파라미터를 이용하는 생성자를 만듦
public class BoardController {
	
	private BoardService service;
	
	@GetMapping("/list")
	public void list(Model model) {   //model 객체로 게시물의 목록 전달
		
		log.info("list");
		
		model.addAttribute("list",service.getList()); 
	}
	
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
									//등록 작업 후 다시 목록 화면으로 이동하기 위해. 새롭게 등록된 게시물의 번호를 전달하기 위해
		log.info("register: "+board);
		
		service.register(board);
		
		rttr.addFlashAttribute("result", board.getBno());
		
		return "redirect:/board/list";
		
	}
	
	@GetMapping("/get")
	public void get(@RequestParam("bno")Long bno, Model model) {  //화면 쪽으로 해당 번호의 게시물 전달
		
		log.info("/get");
		model.addAttribute("board",service.get(bno));
	}
	
	@PostMapping("/modify")
	public String modify(BoardVO board, RedirectAttributes rttr) {
		log.info("modify: "+board);
		
		if(service.modify(board)) {      //성공한 경우에만 redirectAttrivutes에 추가
			rttr.addFlashAttribute("result","success");
		}
		
		return "redirect:/board/list";
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno")Long bno, RedirectAttributes rttr) {
		log.info("remove..."+bno);
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result","success");
		}
		return "redirect:/board/list";
	}
	
	
	
	
}










