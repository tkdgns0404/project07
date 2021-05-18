package com.example.manager.ui.modify;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.manager.R;
import com.example.manager.databinding.FragmentModifyBinding;
import com.example.manager.databinding.FragmentModifyboardBinding;
import com.example.manager.model.Board;
import com.example.manager.ui.base.MainActivity;
import com.example.manager.ui.base.ViewModelFactory;
import com.example.manager.ui.boardlist.BoardListFragment;
import com.example.manager.ui.boardlist.ViewBoardFragment;
import com.example.manager.ui.login.LoginFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyBoardFragment extends Fragment {

    private FragmentModifyboardBinding binding;
    private ModifyBoardViewModel modifyBoardViewModel;

    private Board board;

    private Context context;

    public ModifyBoardFragment(Board board) {
        super();
        this.board = board;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_modifyboard, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = requireActivity().getApplicationContext();

        // Data Binding 연결
        modifyBoardViewModel = new ViewModelProvider(this, new ViewModelFactory()).get(ModifyBoardViewModel.class);
        modifyBoardViewModel.initBoardData(board);
        binding.setVm(modifyBoardViewModel);


        final Button modifyButton = view.findViewById(R.id.modifyBoard_btn);
        // 수정하기 버튼 클릭 이벤트 처리
        modifyButton.setOnClickListener(v -> {

            if (!modifyBoardViewModel.validateModifyInfo()) {
                Toast.makeText(context, "제목 또는 내용을 입력하세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            // 회원 정보 수정 요청
            modifyBoardViewModel.modify(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "게시글 수정 성공", Toast.LENGTH_SHORT).show();
                        // 이전 화면으로 돌아가기
                        ((MainActivity) requireActivity()).navigateTo(new BoardListFragment(), false);
                    } else {
                        Toast.makeText(context, "게시글 수정 실패", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context, "게시글 수정 실패", Toast.LENGTH_SHORT).show();
                }
            });
        });

        final Button cancelButton = view.findViewById(R.id.modifyBoard_cancel_btn);
        // 취소 버튼 클릭 이벤트 처리
        cancelButton.setOnClickListener(v -> {
            // 이전 화면으로 돌아가기
            requireActivity().onBackPressed();
        });

    }

}