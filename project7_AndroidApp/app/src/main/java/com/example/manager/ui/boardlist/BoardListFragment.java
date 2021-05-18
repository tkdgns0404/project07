package com.example.manager.ui.boardlist;

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
import com.example.manager.databinding.FragmentBoardListBinding;
import com.example.manager.model.Board;
import com.example.manager.ui.base.MainActivity;
import com.example.manager.ui.base.ViewModelFactory;
import com.example.manager.ui.home.HomeFragment;
import com.example.manager.ui.write.WriteBoardFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardListFragment extends Fragment implements SearchDialog.ResultListener {

    private FragmentBoardListBinding binding;
    private BoardListViewModel boardListViewModel;

    private BoardListAdapter boardListAdapter;

    private Button viewButton;
    private Button searchButton;
    private Button writeButton;
    private Button listButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_board_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boardListViewModel = new ViewModelProvider(this, new ViewModelFactory()).get(BoardListViewModel.class);

        viewButton = view.findViewById(R.id.view_btn);
        searchButton = view.findViewById(R.id.search_btn);
        writeButton = view.findViewById(R.id.write_btn);
        listButton = view.findViewById(R.id.list_btn);

        boardListAdapter = new BoardListAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.board_list_recycler_view);
        recyclerView.setAdapter(boardListAdapter);

        // 전체 회원 조회
        updateBoardList();

        handleViewEvent();
        handleSearchEvent();
        handleWriteEvent();
        handleListEvent();
    }

    /**
     * 전체 회원 조회
     */
    private void updateBoardList() {
        boardListViewModel.getAllBoards(new Callback<List<Board>>() {
            @Override
            public void onResponse(Call<List<Board>> call, Response<List<Board>> response) {
                if (response.isSuccessful()) {
                    // 회원 조회 성공
                    boardListViewModel.getBoardList().setValue(response.body());
                    // 조회한 회원 정보를 어댑터에 세팅
                    boardListAdapter.setBoardList(boardListViewModel.getBoardList().getValue());
                } else if (requireActivity() != null) {
                    Toast.makeText(requireActivity().getApplicationContext(), "게시글 조회 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Board>> call, Throwable t) {
                if (requireActivity() != null) {
                    Toast.makeText(requireActivity().getApplicationContext(), "게시글 조회 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 게시글 보기 버튼 클릭 이벤트 처리
     */
    private void handleViewEvent() {
        viewButton.setOnClickListener(v -> {
            Board selectedBoard = boardListViewModel.getSelectedBoard();
            if (selectedBoard != null) {
                ((MainActivity) requireActivity()).navigateTo(new ViewBoardFragment(selectedBoard), true);
            }
        });
    }


    /**
     * 회원 정보 검색 버튼 클릭 이벤트 처리
     */
    private void handleSearchEvent() {
        searchButton.setOnClickListener(v -> {
            SearchDialog searchDialog = new SearchDialog(this);
            searchDialog.show(requireActivity().getSupportFragmentManager(), null);
        });
    }

    private void handleListEvent() {
        listButton.setOnClickListener(v -> {
            // 이전 화면으로 돌아가기
            ((MainActivity) requireActivity()).navigateTo(new HomeFragment(), false);
        });
    }

    /**
     * 회원 정보 등록 버튼 클릭 이벤트 처리
     */
    private void handleWriteEvent() {
        writeButton.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).navigateTo(new WriteBoardFragment(), true);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (boardListViewModel != null) {
            updateBoardList();
        }
    }

    @Override
    public void searchBoardsByWriter(String writer) {
        boardListViewModel.searchBoardsByWriter(writer, new Callback<List<Board>>() {
            @Override
            public void onResponse(Call<List<Board>> call, Response<List<Board>> response) {
                if (response.isSuccessful()) {
                    // 회원 조회 성공
                    boardListViewModel.getBoardList().setValue(response.body());
                    // 조회한 회원 정보를 어댑터에 세팅
                    boardListAdapter.setBoardList(boardListViewModel.getBoardList().getValue());
                } else if (requireActivity() != null) {
                    Toast.makeText(requireActivity().getApplicationContext(), "게시글 검색 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Board>> call, Throwable t) {
                if (requireActivity() != null) {
                    Toast.makeText(requireActivity().getApplicationContext(), "게시글 검색 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}