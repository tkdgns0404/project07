package com.example.manager.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.manager.model.LoginAccount;
import com.example.manager.model.Member;
import com.example.manager.repository.MemberRepository;

import java.util.List;

import retrofit2.Callback;

public class HomeViewModel extends ViewModel {

    private final MemberRepository repository;

    public HomeViewModel(MemberRepository repository) {
        this.repository = repository;
    }

    void deleteMember(Callback<Void> callback) {
        repository.deleteMembers(LoginAccount.getInstance().getMember().getId(), callback);
    }
}