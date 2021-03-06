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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.example.manager.R;
import com.example.manager.databinding.FragmentModifyBinding;
import com.example.manager.model.Member;
import com.example.manager.ui.base.MainActivity;
import com.example.manager.ui.base.ViewModelFactory;
import com.example.manager.ui.home.HomeFragment;
import com.example.manager.ui.login.LoginFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyFragment extends Fragment {
    private static final int PERMISSION_REQUEST_CODE = 1000;

    private ImageView profileView;

    private FragmentModifyBinding binding;
    private ModifyViewModel modifyViewModel;

    private Member member;

    private Context context;

    public ModifyFragment(Member member) {
        super();
        this.member = member;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_modify, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = requireActivity().getApplicationContext();

        // Data Binding ??????
        modifyViewModel = new ViewModelProvider(this, new ViewModelFactory()).get(ModifyViewModel.class);
        modifyViewModel.initMemberData(member);
        binding.setVm(modifyViewModel);

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

        final Button modifyButton = view.findViewById(R.id.modify_btn);
        // ???????????? ?????? ?????? ????????? ??????
        modifyButton.setOnClickListener(v -> {
            if (!modifyViewModel.validateIdAndName()) {
                Toast.makeText(context, "???????????? ????????? ????????? ????????? ??? ????????????.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!modifyViewModel.validatePassword()) {
                Toast.makeText(context, "??????????????? ???????????? ????????????.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!modifyViewModel.validateModifyInfo()) {
                Toast.makeText(context, "??? ?????? ?????? ???????????????.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!modifyViewModel.validateProfile()) {
                Toast.makeText(context, "????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                return;
            }

            // ?????? ?????? ?????? ??????
            modifyViewModel.modify(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "?????? ?????? ?????? ??????! ?????? ????????? ????????????.", Toast.LENGTH_SHORT).show();
                        // ?????? ???????????? ????????????
                        ((MainActivity) requireActivity()).navigateTo(new LoginFragment(), false);
                    } else {
                        Toast.makeText(context, "?????? ?????? ?????? ??????", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context, "?????? ?????? ?????? ??????", Toast.LENGTH_SHORT).show();
                }
            });
        });

        final Button cancelButton = view.findViewById(R.id.modify_cancel_btn);
        // ?????? ?????? ?????? ????????? ??????
        cancelButton.setOnClickListener(v -> {
            // ?????? ???????????? ????????????
            ((MainActivity) requireActivity()).navigateTo(new HomeFragment(), false);
        });

        // ????????? ????????? ??????
        modifyViewModel.profileUrl.observe(getViewLifecycleOwner(), url -> {
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
                modifyViewModel.profileUrl.setValue(image.getPath());
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