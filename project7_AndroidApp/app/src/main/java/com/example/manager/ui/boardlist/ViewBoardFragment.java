package com.example.manager.ui.boardlist;

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
import androidx.recyclerview.widget.RecyclerView;

import com.example.manager.R;
import com.example.manager.databinding.FragmentBoardViewBinding;
import com.example.manager.model.Board;
import com.example.manager.model.BoardAccount;
import com.example.manager.model.LoginAccount;
import com.example.manager.ui.base.MainActivity;
import com.example.manager.ui.base.ViewModelFactory;
import com.example.manager.ui.modify.ModifyBoardFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewBoardFragment extends Fragment {

    private FragmentBoardViewBinding binding;
    private ViewBoardViewModel viewBoardViewModel;

    private Board board;
    Button deleteButton;
    Button modifyButton;
    Button listButton;

    public ViewBoardFragment(Board board) {
        super();
        this.board = board;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_board_view, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewBoardViewModel = new ViewModelProvider(this, new ViewModelFactory()).get(ViewBoardViewModel.class);
        viewBoardViewModel.initBoardData(board);
        binding.setBoard(board);

        deleteButton = view.findViewById(R.id.delete_btn);
        modifyButton = view.findViewById(R.id.modify_btn);
        listButton = view.findViewById(R.id.list_btn);

        // ????????? ??????
        updateBoard();

        handleDeleteEvent();
        handleModifyEvent();
        handleListEvent();
    }

    private void updateBoard() {
        viewBoardViewModel.getBoard(new Callback<Board>() {
            @Override
            public void onResponse(Call<Board> call, Response<Board> response) {
                if (response.isSuccessful()) {
                    // ????????? ?????? ??????
                    Board board = response.body();
                    BoardAccount.getInstance().setBoard(board);

                } else if (requireActivity() != null) {
                    Toast.makeText(requireActivity().getApplicationContext(), "????????? ?????? ??????", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Board> call, Throwable t) {
                if (requireActivity() != null) {
                    Toast.makeText(requireActivity().getApplicationContext(), "????????? ?????? ??????", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void handleModifyEvent() {
        // ????????? ?????? ??????
        modifyButton.setOnClickListener(v -> {
            if(BoardAccount.getInstance().getBoard().getWriter().equals(LoginAccount.getInstance().getMember().getName())) {
                ((MainActivity) requireActivity()).navigateTo(new ModifyBoardFragment(BoardAccount.getInstance().getBoard()), true);
            } else {
                Toast.makeText(requireActivity().getApplicationContext(), "????????? ????????????.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void handleListEvent() {
        listButton.setOnClickListener(v -> {
            // ???????????? ????????????
            ((MainActivity) requireActivity()).navigateTo(new BoardListFragment(), false);
        });
    }


    private void handleDeleteEvent() {
        deleteButton.setOnClickListener(v -> {
            if(BoardAccount.getInstance().getBoard().getWriter().equals(LoginAccount.getInstance().getMember().getName())) {
        viewBoardViewModel.deleteBoard(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        // ????????? ?????? ??????
                        Toast.makeText(requireActivity().getApplicationContext(), "????????? ?????? ??????", Toast.LENGTH_SHORT).show();
                        ((MainActivity) requireActivity()).navigateTo(new BoardListFragment(), false);
                    } else if (requireActivity() != null) {
                        Toast.makeText(requireActivity().getApplicationContext(), "????????? ?????? ??????", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    if (requireActivity() != null) {
                        Toast.makeText(requireActivity().getApplicationContext(), "????????? ?????? ??????. ????????? ?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            } else {
                Toast.makeText(requireActivity().getApplicationContext(), "????????? ????????????.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}