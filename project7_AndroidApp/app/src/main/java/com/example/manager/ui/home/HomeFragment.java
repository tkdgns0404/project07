package com.example.manager.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.manager.R;
import com.example.manager.databinding.FragmentHomeBinding;
import com.example.manager.databinding.FragmentLoginBinding;
import com.example.manager.databinding.FragmentMemberListBinding;
import com.example.manager.model.LoginAccount;
import com.example.manager.model.Member;
import com.example.manager.repository.MemberRepository;
import com.example.manager.ui.base.MainActivity;
import com.example.manager.ui.base.ViewModelFactory;
import com.example.manager.ui.boardlist.BoardListFragment;
import com.example.manager.ui.login.LoginFragment;
import com.example.manager.ui.memberlist.MemberListFragment;
import com.example.manager.ui.modify.ModifyFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment  {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeViewModel = new ViewModelProvider(this, new ViewModelFactory()).get(HomeViewModel.class);
        binding.setVm(homeViewModel);

        final Button boardList_btn = view.findViewById(R.id.boardList_btn);
        boardList_btn.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).navigateTo(new BoardListFragment(),false);
        });

        final Button log_out_btn = view.findViewById(R.id.log_out_btn);
        log_out_btn.setOnClickListener(v -> {
            LoginAccount.getInstance().setMember(null);
            Toast.makeText(requireActivity().getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            ((MainActivity) requireActivity()).navigateTo(new LoginFragment(),false);
        });

        final Button myInfo_btn = view.findViewById(R.id.myInfo_btn);
        myInfo_btn.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).navigateTo(new ModifyFragment(LoginAccount.getInstance().getMember()),false);
        });

        final Button deleteAccount_btn = view.findViewById(R.id.deleteAccount_btn);
        deleteAccount_btn.setOnClickListener(v -> {
            homeViewModel.deleteMember(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (requireActivity() != null) {
                        if (response.isSuccessful()) {
                            Toast.makeText(requireActivity().getApplicationContext(), "회원 삭제 성공", Toast.LENGTH_SHORT).show();
                            ((MainActivity) requireActivity()).navigateTo(new LoginFragment(),false);
                        } else {
                            Toast.makeText(requireActivity().getApplicationContext(), "회원 삭제 실패", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    if (requireActivity() != null) {
                        Toast.makeText(requireActivity().getApplicationContext(), "회원 삭제 실패", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
            });

        });

    }

}