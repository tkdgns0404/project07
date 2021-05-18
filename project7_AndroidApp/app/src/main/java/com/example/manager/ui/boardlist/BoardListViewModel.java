package com.example.manager.ui.boardlist;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.manager.model.Board;
import com.example.manager.repository.BoardRepository;

import java.util.List;

import retrofit2.Callback;

public class BoardListViewModel extends ViewModel {
    private BoardRepository repository;

    private MutableLiveData<List<Board>> boardList;

    public BoardListViewModel(BoardRepository repository) {
        this.repository = repository;

        boardList = new MutableLiveData<>();
    }

    public MutableLiveData<List<Board>> getBoardList() {
        return boardList;
    }

    /**
     * 모든 게시글 조회
     */
    void getAllBoards(Callback<List<Board>> callback) {
        repository.getAllBoards(callback);
    }

//    /**
//     * 체크된 게시글 모두 삭제 요청
//     */
//    void deleteBoards(Callback<Void> callback) {
//        List<Board> boards = boardList.getValue();
//        if (boards != null) {
//            StringBuilder targets = new StringBuilder();
//            for (Board board : boards) {
//                if (board.isChecked()) {
//                    targets.append(board.getIdx());
//                    targets.append(",");
//                }
//            }
//            targets.deleteCharAt(targets.length() - 1);
//
//            repository.deleteBoards(targets.toString(), callback);
//        }
//    }

//     void deleteBoard(Callback<Void> callback) {
//
//            repository.deleteBoards(targets.toString(), callback);
//
//    }

    /**
     * 작성자로 게시글 검색
     */
    void searchBoardsByWriter(String writer, Callback<List<Board>> callback) {
        repository.getBoardsByWriter(writer, callback);
    }

    /**
     * 체크된 회원 중 가장 상위의 회원을 반환
     */
    Board getSelectedBoard() {
        if (boardList.getValue() == null) {
            return null;
        }

        for (Board board : boardList.getValue()) {
            if (board.isChecked()) {
                return board;
            }
        }

        return null;
    }
}
