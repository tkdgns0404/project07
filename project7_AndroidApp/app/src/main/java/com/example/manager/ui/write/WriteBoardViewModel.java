package com.example.manager.ui.write;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.manager.model.Board;
import com.example.manager.model.LoginAccount;
import com.example.manager.repository.BoardRepository;

import retrofit2.Callback;

public class WriteBoardViewModel extends ViewModel {

    private final BoardRepository repository;
    public int idx;
    public MutableLiveData<String> title;
    public String writer;
    public MutableLiveData<String> content;
    public int cnt;

    public WriteBoardViewModel(BoardRepository repository) {
        this.repository = repository;
        title = new MutableLiveData<>();
        writer = LoginAccount.getInstance().getMember().getName();
        content = new MutableLiveData<>();
        cnt = 0;
    }

    void writeBoard(Callback<Void> callback) {
        Board board  = new Board(
                idx,
                title.getValue(),
                writer,
                content.getValue(),
                cnt
        );
        repository.writeBoard(board, callback);
    }


    boolean validateTitleAndContent() {
        return title.getValue() != null && content.getValue() != null;
    }
}