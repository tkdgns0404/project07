package com.example.manager.ui.modify;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.manager.model.Board;
import com.example.manager.model.LoginAccount;
import com.example.manager.model.Member;
import com.example.manager.repository.BoardRepository;
import com.example.manager.repository.MemberRepository;

import java.io.File;

import retrofit2.Callback;

public class ModifyBoardViewModel extends ViewModel {
    private final BoardRepository repository;
    public MutableLiveData<Integer> idx;
    public MutableLiveData<String> title;
    public MutableLiveData<String> writer;
    public MutableLiveData<String> content;
    public MutableLiveData<Integer> cnt;

    public ModifyBoardViewModel(BoardRepository repository) {
        this.repository = repository;

        idx = new MutableLiveData<>();
        title = new MutableLiveData<>();
        writer = new MutableLiveData<>();
        content = new MutableLiveData<>();
        cnt = new MutableLiveData<>();
    }

    /**
     * 수정할 멤버 데이터로 초기화
     */
    void initBoardData(Board board) {
        idx.setValue(board.getIdx());
        title.setValue(board.getTitle());
        writer.setValue(board.getWriter());
        content.setValue(board.getContent());
        cnt.setValue(board.getCnt());
    }

    /**
     * 회원 정보 수정 요청
     */
    void modify(Callback<Void> callback) {
        Board board = new Board(
                idx.getValue(),
                title.getValue(),
                writer.getValue(),
                content.getValue(),
                cnt.getValue()
                );
        repository.modifyBoard(board, callback);
    }

    /**
     * 비어있는 정보나 잘못된 입력이 있는지 검사
     */
    boolean validateModifyInfo() {
        return validateTitleAndContent();
    }

    /**
     * 아이디와 이름 유효성 검사
     */
    boolean validateTitleAndContent() {
        return title.getValue() != null
                && content.getValue() != null;
    }

}