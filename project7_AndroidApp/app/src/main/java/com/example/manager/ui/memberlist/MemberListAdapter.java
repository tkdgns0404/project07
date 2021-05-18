package com.example.manager.ui.memberlist;


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
import com.example.manager.model.Member;

import java.util.ArrayList;
import java.util.List;

public class MemberListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER_POSITION = 0;

    private List<Member> memberList = new ArrayList<>();

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        if (viewType == R.layout.item_member_list_header) {
            return new HeaderViewHolder(binding.getRoot());
        } else {
            return new MemberViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof  MemberViewHolder) {
            ((MemberViewHolder) holder).bind(memberList.get(position - 1));
        }
    }

    @Override
    public int getItemCount() {
        return memberList.size() + 1;   // Header 가 있기 때문에 + 1
    }

    @Override
    public int getItemViewType(int position) {
        if (position == HEADER_POSITION) {
            return R.layout.item_member_list_header;
        }
        return R.layout.item_member_list;
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
                for (Member member : memberList) {
                    member.setChecked(!isChecked);
                }

                // 데이터가 변경 됐음을 알림
                notifyDataSetChanged();
            });
        }
    }

    private class MemberViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        private ImageView checkView;    // 체크 이미지
        private TextView numberView;    // 순번 텍스트
        private ImageView profileView;  // 프로필 이미지

        private Member member;

        private MemberViewHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            View itemView = binding.getRoot();
            checkView = itemView.findViewById(R.id.check_image_view);
            numberView = itemView.findViewById(R.id.number_text);
            profileView = itemView.findViewById(R.id.profile_view);

            // 체크 이미지 클릭 이벤트 처리
            checkView.setOnClickListener(v -> {
                if (member == null) {
                    return;
                }

                boolean isChecked = checkView.isSelected();
                checkView.setSelected(!isChecked);
                member.setChecked(!isChecked);

                // 데이터 변경 알림
                notifyItemChanged(getAdapterPosition());
            });
        }

        private void bind(Member member) {
            this.member = member;

            // 데이터 바인딩 연동
            binding.setVariable(BR.member, member);
            binding.executePendingBindings();

            checkView.setSelected(member.isChecked());
            numberView.setText(String.valueOf(getAdapterPosition()));

            // 프로필 이미지 설정
            Glide.with(profileView.getContext())
                    .load(member.getProfilePicUrl())
                    .apply(new RequestOptions().circleCrop())
                    .into(profileView);
        }
    }
}
