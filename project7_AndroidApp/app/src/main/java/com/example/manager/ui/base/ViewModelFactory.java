package com.example.manager.ui.base;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.example.manager.network.BoardApi;
import com.example.manager.network.MemberApi;
import com.example.manager.repository.BoardRepository;
import com.example.manager.repository.MemberRepository;
import com.example.manager.ui.boardlist.BoardListViewModel;
import com.example.manager.ui.boardlist.ViewBoardViewModel;
import com.example.manager.ui.home.HomeViewModel;
import com.example.manager.ui.login.LoginViewModel;
import com.example.manager.ui.memberlist.MemberListViewModel;
import com.example.manager.ui.modify.ModifyBoardViewModel;
import com.example.manager.ui.modify.ModifyViewModel;
import com.example.manager.ui.signup.SignUpViewModel;
import com.example.manager.ui.write.WriteBoardViewModel;


public class ViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        MemberRepository memberRepository = new MemberRepository(MemberApi.getInstance().getMemberService());
        BoardRepository boardRepository = new BoardRepository(BoardApi.getInstance().getBoardService());
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(memberRepository);
        } else if (modelClass.isAssignableFrom(SignUpViewModel.class)) {
            return (T) new SignUpViewModel(memberRepository);
        } else if (modelClass.isAssignableFrom(ModifyViewModel.class)) {
            return (T) new ModifyViewModel(memberRepository);
        } else if (modelClass.isAssignableFrom(MemberListViewModel.class)) {
            return (T) new MemberListViewModel(memberRepository);
        } else if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(memberRepository);
        } else if (modelClass.isAssignableFrom(BoardListViewModel.class)) {
            return (T) new BoardListViewModel(boardRepository);
        } else if (modelClass.isAssignableFrom(ViewBoardViewModel.class)) {
            return (T) new ViewBoardViewModel(boardRepository);
        } else if (modelClass.isAssignableFrom(WriteBoardViewModel.class)) {
            return (T) new WriteBoardViewModel(boardRepository);
        } else if (modelClass.isAssignableFrom(ModifyBoardViewModel.class)) {
            return (T) new ModifyBoardViewModel(boardRepository);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}