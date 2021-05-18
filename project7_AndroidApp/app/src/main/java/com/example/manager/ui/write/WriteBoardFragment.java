package com.example.manager.ui.write;

import android.Manifest;
import android.content.Context;
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
import com.example.manager.databinding.FragmentHomeBinding;
import com.example.manager.databinding.FragmentWriteboardBinding;
import com.example.manager.model.LoginAccount;
import com.example.manager.ui.base.MainActivity;
import com.example.manager.ui.base.ViewModelFactory;
import com.example.manager.ui.boardlist.BoardListFragment;
import com.example.manager.ui.home.HomeViewModel;
import com.example.manager.ui.login.LoginFragment;
import com.example.manager.ui.modify.ModifyFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteBoardFragment extends Fragment  {

    private FragmentWriteboardBinding binding;
    private WriteBoardViewModel writeBoardViewModel;

    private Context context;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_writeboard, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.context = requireContext().getApplicationContext();

        writeBoardViewModel = new ViewModelProvider(this, new ViewModelFactory()).get(WriteBoardViewModel.class);
        binding.setVm(writeBoardViewModel);

        final Button boardList_btn = view.findViewById(R.id.writeBoard_cancel_btn);
        boardList_btn.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).navigateTo(new BoardListFragment(), false);
        });


        final Button write_btn = view.findViewById(R.id.writeBoard_btn);
        write_btn.setOnClickListener(v -> {
            if (!writeBoardViewModel.validateTitleAndContent()) {
                Toast.makeText(context, "제목 또는 내용을 입력하세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            // 게시글 작성 요청
            writeBoardViewModel.writeBoard(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "게시글 등록 성공", Toast.LENGTH_SHORT).show();
                        // 이전 화면으로 돌아가기
                        ((MainActivity) requireActivity()).navigateTo(new BoardListFragment(), false);
                    } else {
                        Toast.makeText(context, "게시글 등록 실패", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context, "게시글 등록 실패", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}