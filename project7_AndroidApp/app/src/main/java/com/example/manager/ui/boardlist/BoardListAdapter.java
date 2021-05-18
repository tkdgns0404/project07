package com.example.manager.ui.boardlist;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.manager.BR;
import com.example.manager.R;
import com.example.manager.model.Board;
import com.example.manager.model.Member;

import java.util.ArrayList;
import java.util.List;

public class BoardListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER_POSITION = 0;

    private List<Board> boardList = new ArrayList<>();

    public void setBoardList(List<Board> boardList) {
        this.boardList = boardList;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        if (viewType == R.layout.item_board_list_header) {
            return new HeaderViewHolder(binding.getRoot());
        } else {
            return new BoardViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof  BoardViewHolder) {
            ((BoardViewHolder) holder).bind(boardList.get(position - 1));
        }
    }

    @Override
    public int getItemCount() {
        return boardList.size() + 1;   // Header 가 있기 때문에 + 1
    }

    @Override
    public int getItemViewType(int position) {
        if (position == HEADER_POSITION) {
            return R.layout.item_board_list_header;
        }
        return R.layout.item_board_list;
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView checkView;

        private HeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            checkView = itemView.findViewById(R.id.check_image_view);
            // 체크 이미지 클릭 이벤트 처리
            checkView.setOnClickListener(v -> {
                boolean isChecked = checkView.isSelected();
                checkView.setSelected(!isChecked);

                // 모든 회원 리스트의 체크 온/오프 적용
                for (Board board : boardList) {
                    board.setChecked(!isChecked);
                }

                // 데이터가 변경 됐음을 알림
                notifyDataSetChanged();
            });
        }
    }

    private class BoardViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        private ImageView checkView;    // 체크 이미지

        private Board board;

        private BoardViewHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            View itemView = binding.getRoot();
            checkView = itemView.findViewById(R.id.check_image_view);

            // 체크 이미지 클릭 이벤트 처리
            checkView.setOnClickListener(v -> {
                if (board == null) {
                    return;
                }

                boolean isChecked = checkView.isSelected();
                checkView.setSelected(!isChecked);
                board.setChecked(!isChecked);

                // 데이터 변경 알림
                notifyItemChanged(getAdapterPosition());
            });
        }

        private void bind(Board board) {
            this.board = board;

            // 데이터 바인딩 연동
            binding.setVariable(BR.board, board);
            binding.executePendingBindings();

            checkView.setSelected(board.isChecked());

        }
    }
}
