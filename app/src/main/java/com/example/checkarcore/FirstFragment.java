package com.example.checkarcore;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.checkarcore.databinding.FragmentFirstBinding;
import com.google.ar.core.ArCoreApk;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maybeEnableArButton();
            }

            public void maybeEnableArButton() {
                ArCoreApk.Availability availability = ArCoreApk.getInstance().checkAvailability(getActivity().getApplicationContext());
                if (availability.isTransient()) {
                    // Continue to query availability at 5Hz while compatibility is checked in the background.
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            maybeEnableArButton();
                        }
                    }, 200);
                }
                if (availability == ArCoreApk.Availability.SUPPORTED_APK_TOO_OLD) {
                    Toast myToast = Toast.makeText(getActivity(), "SUPPORTED_INSTALLED", Toast.LENGTH_SHORT);
                    myToast.show();
                } else if(availability == ArCoreApk.Availability.SUPPORTED_APK_TOO_OLD){ // The device is unsupported or unknown.
                    Toast myToast = Toast.makeText(getActivity(), "SUPPORTED_APK_TOO_OLD", Toast.LENGTH_SHORT);
                    myToast.show();
                } else if(availability == ArCoreApk.Availability.SUPPORTED_NOT_INSTALLED){ // The device is unsupported or unknown.
                    Toast myToast = Toast.makeText(getActivity(), "SUPPORTED_NOT_INSTALLED", Toast.LENGTH_SHORT);
                    myToast.show();
                }

                else {
                    Toast myToast = Toast.makeText(getActivity(), "error happend", Toast.LENGTH_SHORT);
                    myToast.show();
                }
            }
        }

        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}