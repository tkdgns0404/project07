package com.example.manager.ui.memberlist;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.manager.model.Member;
import com.example.manager.repository.MemberRepository;

import java.util.List;

import retrofit2.Callback;

public class MemberListViewModel extends ViewModel {
    private MemberRepository repository;

    private MutableLiveData<List<Member>> memberList;

    public MemberListViewModel(MemberRepository repository) {
        this.repository = repository;

        memberList = new MutableLiveData<>();
    }

    public MutableLiveData<List<Member>> getMemberList() {
        return memberList;
    }

    /**
     * 모든 회원 조회
     */
    void getAllMembers(Callback<List<Member>> callback) {
        repository.getAllMembers(callback);
    }

    /**
     * 체크된 회원 모두 삭제 요청
     */
    void deleteMembers(Callback<Void> callback) {
        List<Member> members = memberList.getValue();
        if (members != null) {
            StringBuilder targets = new StringBuilder();
            for (Member member : members) {
                if (member.isChecked()) {
                    targets.append(member.getId());
                    targets.append(",");
                }
            }
            targets.deleteCharAt(targets.length() - 1);

            repository.deleteMembers(targets.toString(), callback);
        }
    }

    /**
     * 이름으로 회원 검색
     */
    void searchMembersByName(String name, Callback<List<Member>> callback) {
        repository.getMembersByName(name, callback);
    }

    /**
     * 체크된 회원 중 가장 상위의 회원을 반환
     */
    Member getSelectedMember() {
        if (memberList.getValue() == null) {
            return null;
        }

        for (Member member : memberList.getValue()) {
            if (member.isChecked()) {
                return member;
            }
        }

        return null;
    }
}
