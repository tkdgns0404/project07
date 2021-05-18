package com.example.manager.ui.signup;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.manager.model.LoginInfo;
import com.example.manager.model.Member;
import com.example.manager.model.findMember;
import com.example.manager.repository.MemberRepository;

import java.io.File;

import retrofit2.Callback;

public class SignUpViewModel extends ViewModel {
    private final MemberRepository repository;
    public MutableLiveData<String> id;
    public MutableLiveData<String> password;
    public MutableLiveData<String> passwordConfirm;
    public MutableLiveData<String> name;
    public MutableLiveData<String> email;
    public MutableLiveData<String> phoneNumber;
    public MutableLiveData<String> profileUrl;

    public SignUpViewModel(MemberRepository repository) {
        this.repository = repository;
        id = new MutableLiveData<>();
        password = new MutableLiveData<>();
        passwordConfirm = new MutableLiveData<>();
        name = new MutableLiveData<>();
        email = new MutableLiveData<>();
        phoneNumber = new MutableLiveData<>();
        profileUrl = new MutableLiveData<>();
    }

    void signUp(Callback<Void> callback) {
        Member member = new Member(
                id.getValue(),
                password.getValue(),
                name.getValue(),
                email.getValue(),
                phoneNumber.getValue(),
                profileUrl.getValue()

        );

        File file = new File(profileUrl.getValue());
        repository.signUpMember(member, file, callback);

    }

    /**
     * 비어있는 정보나 잘못된 입력이 있는지 검사
     */
    boolean validateSignUpInfo() {
        return validateIdAndName()
                && validatePassword()
                && validateEmail()
                && phoneNumber.getValue() != null;
    }

    void findId(Callback<Member> callback) {
        repository.findMember(new findMember(id.getValue()), callback);
    }

    /**
     *  비밀번호 & 비밀번호 확인 검사
     */
    boolean validatePassword() {
        return password.getValue() != null
                && passwordConfirm.getValue() != null
                && password.getValue().equals(passwordConfirm.getValue());
    }

    /**
     * 아이디와 이름 유효성 검사
     */
    boolean validateIdAndName() {
        return id.getValue() != null
                && !id.getValue().contains(" ")
                && name.getValue() != null
                && !name.getValue().contains(" ");

    }

    boolean validateEmail() {
        if (email.getValue() != null) {
            return email.getValue().matches("[\\S]+@[\\S]+");
        }
        return false;
    }

    /**
     * 썸네일 등록 여부 검사
     */
    boolean validateProfile() {
        return profileUrl.getValue() != null;
    }
}