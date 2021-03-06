package com.example.manager.ui.signup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.example.manager.R;
import com.example.manager.databinding.FragmentSignUpBinding;
import com.example.manager.model.LoginAccount;
import com.example.manager.model.Member;
import com.example.manager.ui.base.MainActivity;
import com.example.manager.ui.base.ViewModelFactory;
import com.example.manager.ui.login.LoginFragment;
import com.example.manager.ui.memberlist.MemberListFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {
    private static final int PERMISSION_REQUEST_CODE = 1000;

    private ImageView profileView;

    private FragmentSignUpBinding binding;
    private SignUpViewModel signUpViewModel;

    private Context context;

    private boolean isDuplicateCheck;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.context = requireContext().getApplicationContext();

        // Data Binding ??????
        signUpViewModel = new ViewModelProvider(this, new ViewModelFactory()).get(SignUpViewModel.class);
        binding.setVm(signUpViewModel);

        profileView = view.findViewById(R.id.profile_view);
        final ImageView addProfileView = view.findViewById(R.id.add_profile_view);
        // ????????? ?????? ?????? ????????? ??????
        addProfileView.setOnClickListener(v -> {
            checkAndRequestPermission();

            // ???????????? ????????? ????????????
            ImagePicker.create(this)
                    .returnMode(ReturnMode.GALLERY_ONLY)
                    .includeVideo(false)
                    .includeAnimation(false)
                    .single()
                    .limit(1)
                    .imageDirectory("Camera")
                    .start();
        });
        final  Button idCheckButton = view.findViewById(R.id.idCheck_btn);

        idCheckButton.setOnClickListener(v -> {
            signUpViewModel.findId(new Callback<Member>() {
                @Override
                public void onResponse(Call<Member> call, Response<Member> response) {
                    isDuplicateCheck = true;
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "?????? ???????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                        ((MainActivity) requireActivity()).navigateTo(new SignUpFragment(),false);
                    } else
                        Toast.makeText(context, "??????????????? ??????????????????.", Toast.LENGTH_SHORT).show();

                }
                @Override
                public void onFailure(Call<Member> call, Throwable t) {
                if (requireActivity() != null) {
                    Toast.makeText(context, "????????? ?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                }
            }
                });
                    });

        final Button signUpButton = view.findViewById(R.id.sign_up_btn);
        // ???????????? ?????? ?????? ????????? ??????
        signUpButton.setOnClickListener(v -> {
            if (!signUpViewModel.validateIdAndName()) {
                Toast.makeText(context, "???????????? ????????? ????????? ????????? ??? ????????????.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!signUpViewModel.validatePassword()) {
                Toast.makeText(context, "??????????????? ???????????? ????????????.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!signUpViewModel.validateEmail()) {
                Toast.makeText(context, "????????? ????????? ???????????? ????????????.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!signUpViewModel.validateSignUpInfo()) {
                Toast.makeText(context, "??? ?????? ?????? ???????????????.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!signUpViewModel.validateProfile()) {
                Toast.makeText(context, "????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isDuplicateCheck) {
                Toast.makeText(context, "?????? ????????? ????????????.", Toast.LENGTH_SHORT).show();
                return;
            }



            // ?????? ?????? ??????
            signUpViewModel.signUp(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "?????? ?????? ??????", Toast.LENGTH_SHORT).show();
                        // ?????? ???????????? ????????????
                        ((MainActivity) requireActivity()).navigateTo(new LoginFragment(),false);
                    } else {
                        Toast.makeText(context, "?????? ?????? ??????", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context, "?????? ?????? ??????", Toast.LENGTH_SHORT).show();
                }
            });
        });

        final Button cancelButton = view.findViewById(R.id.sign_up_cancel_btn);
        // ?????? ?????? ?????? ????????? ??????
        cancelButton.setOnClickListener(v -> {
            // ?????? ???????????? ????????????
            ((MainActivity) requireActivity()).navigateTo(new LoginFragment(),false);
        });

        // ????????? ????????? ??????
        signUpViewModel.profileUrl.observe(getViewLifecycleOwner(), url -> {
            Glide.with(requireContext())
                    .load(url)
                    .apply(new RequestOptions().circleCrop())
                    .into(profileView);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            Image image = ImagePicker.getFirstImageOrNull(data);
            if (image != null) {
                signUpViewModel.profileUrl.setValue(image.getPath());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * ????????? ?????? ?????? ?????? ??? ??????
     */
    private void checkAndRequestPermission() {
        if (requireActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                    PERMISSION_REQUEST_CODE
            );
        }
    }
}