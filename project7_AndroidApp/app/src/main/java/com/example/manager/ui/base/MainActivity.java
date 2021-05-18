package com.example.manager.ui.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import com.example.manager.R;
import com.example.manager.ui.login.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 스마트폰 화면이 돌아가면 화면이 초기화(로그인이 플리는 현상) 문제 해결
        if(savedInstanceState == null){
            navigateTo(new LoginFragment(), false);
        }

        /* 스마트폰 화면이 돌아가면 화면이 초기화(로그인이 플리는 현상) 문제 소스
        navigateTo(new LoginFragment(), false);

 */
    }

    /**
     * 프래그먼트 전환
     * @param fragment 전환할 프래그먼트
     */
    public void navigateTo(Fragment fragment, boolean addToBackStack) {
        String fragmentName = fragment.getClass().getName();

        FragmentManager manager = getSupportFragmentManager();
        // 히스토리에 남아있는 화면인지 체크하고, 있다면 해당 화면으로 돌아가기
        boolean fragmentPopped = manager.popBackStackImmediate(fragmentName, 0);

        if (!fragmentPopped) {
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment);

            // 히스토리를 남길 것인지 판단
            if (addToBackStack) {
                transaction.addToBackStack(null);
            }

            transaction.commit();
        }
    }
}