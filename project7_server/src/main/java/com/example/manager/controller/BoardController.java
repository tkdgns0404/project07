package com.example.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.manager.domain.Board;
import com.example.manager.service.BoardService;


@RestController
@RequestMapping("/board")
public class BoardController {
	@Autowired
    private BoardService boardService;

    /**
     * 전체 게시글 조회
     */
    @GetMapping("")
    public List<Board> getBoards() {
        return boardService.findAllBoards();
    }
    
    /**
     * 게시글 작성
     */
    @PutMapping(value = "/write")
    public void writeBoard(@RequestBody Board board){
    	boardService.writeBoard(board);
    }
    
    /**
     * 게시글 수정
     */
    @PutMapping("/modify")
    public void modifyBoard(@RequestBody Board board) {
    	boardService.modifyBoard(board);
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("")
    public void deleteBoard(@RequestParam("targets") String targets) {
    	boardService.deleteBoards(targets);
    }

    /**
     * 작성자로 게시글 조회
     */
    @GetMapping("/search/{writer}")
    public List<Board> findBoardsByWriter(@PathVariable("writer") String writer) {
        return boardService.findBoardsByWriter(writer);
    }
    
    @GetMapping("/{idx}")
    public Board findBoardByIdx(@PathVariable("idx") int idx) {
        return boardService.findBoardByIdx(idx);
    }
    
}
