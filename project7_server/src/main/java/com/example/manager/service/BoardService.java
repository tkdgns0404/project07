package com.example.manager.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.manager.common.BoardNotFoundException;
import com.example.manager.domain.Board;
import com.example.manager.repository.BoardRepository;

@Service
public class BoardService {
	@Autowired
    private BoardRepository boardRepository;

    /**
     * 모든 게시글 조회
     */
    public List<Board> findAllBoards() {
        return boardRepository.findAll();
    }

    /**
     * 게시글 작성
     */
    public void writeBoard(Board board) {
    	boardRepository.save(board);
    }
    	
    /**
     * 게시글 수정
     */
    public void modifyBoard(Board board) {
        // 게시글 조회(정보 불러오기)
        Board target = boardRepository.findByIdx(board.getIdx());
        if (target != null) {
            // DB 에서 회원 정보 수정
        	boardRepository.save(board);
        } else {
            throw new BoardNotFoundException("There is no Board by " + board.getIdx());
        }
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void deleteBoards(String targets) {
        String boardIdx = targets;
        Integer board_idx = Integer.parseInt(boardIdx);
                // DB 에서 게시글 삭제
        boardRepository.deleteByIdx(board_idx);
        }

    /**
     * 작성자로 게시글 검색
     */
    public List<Board> findBoardsByWriter(String writer) {
        return boardRepository.findByWriter(writer);
    }
    
    /*
     * 게시글 조회 (+조회수 증가) 
     */
    public Board findBoardByIdx(int idx) {
    	Board resultVO = boardRepository.findByIdx(idx);
		int cnt = resultVO.getCnt();
	      cnt+=1;
	      resultVO.setCnt(cnt);
	      boardRepository.save(resultVO);
		return resultVO;
    }


}
