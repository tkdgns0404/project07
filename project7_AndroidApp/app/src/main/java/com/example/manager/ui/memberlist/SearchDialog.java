package com.example.manager.ui.memberlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.manager.R;
import com.google.android.material.textfield.TextInputLayout;

public class SearchDialog extends DialogFragment {

    private final ResultListener resultListener;

    public SearchDialog(ResultListener resultListener) {
        super();
        this.resultListener = resultListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextInputLayout nameText = view.findViewById(R.id.search_name_field);
        Button searchButton = view.findViewById(R.id.search_name_button);
        searchButton.setOnClickListener(v -> {
            // 이름 입력란이 비어있을 경우에 대한 예외 처리
            if (nameText.getEditText() == null || nameText.getEditText().getText() == null) {
                Toast.makeText(requireActivity().getApplicationContext(), "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            // 조회할 이름을 콜백 함수로 전달
            resultListener.searchMembersByName(nameText.getEditText().getText().toString());

            // 다이얼로그 종료
            dismissAllowingStateLoss();
        });
    }

    @Override
    public void onResume() {
        // 다이얼로그 사이즈 조절 - 300x100
        try {
            int width = getResources().getDimensionPixelSize(R.dimen.dialog_width);
            int height = getResources().getDimensionPixelSize(R.dimen.dialog_height);
            getDialog().getWindow().setLayout(width, height);
        } catch (Exception ignore) {
            // do nothing
        }
        super.onResume();
    }

    /**
     * 검색 대상의 이름을 전달할 콜백 리스너
     */
    public interface ResultListener {
        void searchMembersByName(String name);
    }
}
