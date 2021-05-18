package com.example.manager.ui.modify;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.manager.model.LoginAccount;
import com.example.manager.model.Member;
import com.example.manager.repository.MemberRepository;

import java.io.File;
import java.util.Date;

import retrofit2.Callback;

public class ModifyViewModel extends ViewModel {
    private final MemberRepository repository;
    public MutableLiveData<String> id;
    public MutableLiveData<String> password;
    public MutableLiveData<String> passwordConfirm;
    public MutableLiveData<String> name;
    public MutableLiveData<String> email;
    public MutableLiveData<String> phoneNumber;
    public MutableLiveData<String> profileUrl;
    private String initProfileUrl;

    public ModifyViewModel(MemberRepository repository) {
        this.repository = repository;

        id = new MutableLiveData<>();
        password = new MutableLiveData<>();
        passwordConfirm = new MutableLiveData<>();
        name = new MutableLiveData<>();
        email = new MutableLiveData<>();
        phoneNumber = new MutableLiveData<>();
        profileUrl = new MutableLiveData<>();
    }

    /**
     * 수정할 멤버 데이터로 초기화
     */
    void initMemberData(Member member) {
        id.setValue(LoginAccount.getInstance().getMember().getId());
        name.setValue(member.getName());
        email.setValue(member.getEmail());
        phoneNumber.setValue(member.getPhoneNumber());
        profileUrl.setValue(member.getProfilePicUrl());

        initProfileUrl = member.getProfilePicUrl();
    }

    /**
     * 회원 정보 수정 요청
     */
    void modify(Callback<Void> callback) {
        Member member = new Member(
                id.getValue(),
                password.getValue(),
                name.getValue(),
                email.getValue(),
                phoneNumber.getValue(),
                profileUrl.getValue()
                );

        if (member.getProfilePicUrl().equals(initProfileUrl)) {
            repository.modifyMember(member, callback);
        } else {
            File file = new File(profileUrl.getValue());
            repository.modifyMember(member, file, callback);
        }
    }

    /**
     * 비어있는 정보나 잘못된 입력이 있는지 검사
     */
    boolean validateModifyInfo() {
        return validateIdAndName()
                && validatePassword()
                && validateEmail()
                && phoneNumber.getValue() != null;
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