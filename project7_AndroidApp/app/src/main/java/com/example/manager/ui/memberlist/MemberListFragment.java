package com.example.manager.ui.memberlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.manager.R;
import com.example.manager.databinding.FragmentMemberListBinding;
import com.example.manager.model.Member;
import com.example.manager.ui.base.MainActivity;
import com.example.manager.ui.base.ViewModelFactory;
import com.example.manager.ui.modify.ModifyFragment;
import com.example.manager.ui.signup.SignUpFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberListFragment extends Fragment implements SearchDialog.ResultListener {

    private FragmentMemberListBinding binding;
    private MemberListViewModel memberListViewModel;

    private MemberListAdapter memberListAdapter;

    private Button modifyButton;
    private Button deleteButton;
    private Button searchButton;
    private Button signUpButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        memberListViewModel = new ViewModelProvider(this, new ViewModelFactory()).get(MemberListViewModel.class);

        modifyButton = view.findViewById(R.id.modify_btn);
        deleteButton = view.findViewById(R.id.delete_btn);
        searchButton = view.findViewById(R.id.search_btn);
        signUpButton = view.findViewById(R.id.sign_up_btn);

        memberListAdapter = new MemberListAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.member_list_recycler_view);
        recyclerView.setAdapter(memberListAdapter);

        // 전체 회원 조회
        updateMemberList();

        handleModifyEvent();
        handleDeleteEvent();
        handleSearchEvent();
        handleSignUpEvent();
    }

    /**
     * 전체 회원 조회
     */
    private void updateMemberList() {
        memberListViewModel.getAllMembers(new Callback<List<Member>>() {
            @Override
            public void onResponse(Call<List<Member>> call, Response<List<Member>> response) {
                if (response.isSuccessful()) {
                    // 회원 조회 성공
                    memberListViewModel.getMemberList().setValue(response.body());
                    // 조회한 회원 정보를 어댑터에 세팅
                    memberListAdapter.setMemberList(memberListViewModel.getMemberList().getValue());
                } else if (requireActivity() != null) {
                    Toast.makeText(requireActivity().getApplicationContext(), "회원 조회 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Member>> call, Throwable t) {
                if (requireActivity() != null) {
                    Toast.makeText(requireActivity().getApplicationContext(), "회원 조회 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 회원 정보 수정 버튼 클릭 이벤트 처리
     */
    private void handleModifyEvent() {
        modifyButton.setOnClickListener(v -> {
            Member selectedMember = memberListViewModel.getSelectedMember();
            if (selectedMember != null) {
                ((MainActivity) requireActivity()).navigateTo(new ModifyFragment(selectedMember), true);
            }
        });
    }

    /**
     * 회원 정보 삭제 버튼 클릭 이벤트 처리
     */
    private void handleDeleteEvent() {
        deleteButton.setOnClickListener(v -> {
            memberListViewModel.deleteMembers(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (requireActivity() != null) {
                        if (response.isSuccessful()) {
                            Toast.makeText(requireActivity().getApplicationContext(), "회원 삭제 성공", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireActivity().getApplicationContext(), "회원 삭제 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                    updateMemberList();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    if (requireActivity() != null) {
                        Toast.makeText(requireActivity().getApplicationContext(), "회원 삭제 실패", Toast.LENGTH_SHORT).show();
                    }
                    updateMemberList();
                }
            });
        });
    }

    /**
     * 회원 정보 조회 버튼 클릭 이벤트 처리
     */
    private void handleSearchEvent() {
        searchButton.setOnClickListener(v -> {
            SearchDialog searchDialog = new SearchDialog(this);
            searchDialog.show(requireActivity().getSupportFragmentManager(), null);
        });
    }

    /**
     * 회원 정보 등록 버튼 클릭 이벤트 처리
     */
    private void handleSignUpEvent() {
        signUpButton.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).navigateTo(new SignUpFragment(), true);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (memberListViewModel != null) {
            updateMemberList();
        }
    }

    @Override
    public void searchMembersByName(String name) {
        memberListViewModel.searchMembersByName(name, new Callback<List<Member>>() {
            @Override
            public void onResponse(Call<List<Member>> call, Response<List<Member>> response) {
                if (response.isSuccessful()) {
                    // 회원 조회 성공
                    memberListViewModel.getMemberList().setValue(response.body());
                    // 조회한 회원 정보를 어댑터에 세팅
                    memberListAdapter.setMemberList(memberListViewModel.getMemberList().getValue());
                } else if (requireActivity() != null) {
                    Toast.makeText(requireActivity().getApplicationContext(), "회원 조회 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Member>> call, Throwable t) {
                if (requireActivity() != null) {
                    Toast.makeText(requireActivity().getApplicationContext(), "회원 조회 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}