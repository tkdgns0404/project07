package com.example.manager.ui.boardlist;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.manager.model.Board;
import com.example.manager.model.BoardAccount;
import com.example.manager.model.LoginAccount;
import com.example.manager.model.Member;
import com.example.manager.repository.BoardRepository;

import java.util.List;

import retrofit2.Callback;

public class ViewBoardViewModel extends ViewModel {
    private BoardRepository repository;
    public MutableLiveData<Integer> idx;
    public MutableLiveData<String> title;
    public MutableLiveData<String> writer;
    public MutableLiveData<String> content;
    public MutableLiveData<Integer> cnt;

    public ViewBoardViewModel(BoardRepository repository) {
        this.repository = repository;

        idx =  new MutableLiveData<>();
        title =  new MutableLiveData<>();
        writer =  new MutableLiveData<>();
        content =  new MutableLiveData<>();
        cnt =  new MutableLiveData<>();
    }


    /**
     * 선택된 게시글 조회
     */
    void getBoard(Callback<Board> callback) {
        Board board = new Board(
                idx.getValue(),
                title.getValue(),
                writer.getValue(),
                content.getValue(),
                cnt.getValue()
        );
        repository.getBoardByIdx(board.getIdx(), callback);
    }

    void initBoardData(Board board) {
        idx.setValue(board.getIdx());
        title.setValue(board.getTitle());
        writer.setValue(board.getWriter());
        content.setValue(board.getContent());
        cnt.setValue(board.getCnt());

    }

    /**
     * 현재 게시글 삭제 요청
     */

     void deleteBoard(Callback<Void> callback) {
            repository.deleteBoards(String.valueOf(BoardAccount.getInstance().getBoard().getIdx()), callback);
    }


}
