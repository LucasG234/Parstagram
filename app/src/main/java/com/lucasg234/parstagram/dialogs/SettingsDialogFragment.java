package com.lucasg234.parstagram.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.lucasg234.parstagram.databinding.FragmentSettingsDialogBinding;
import com.lucasg234.parstagram.login.LoginActivity;
import com.parse.ParseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsDialogFragment extends DialogFragment {

    private static final String TAG = "SettingsDialogFragment";

    private FragmentSettingsDialogBinding mBinding;

    public SettingsDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SettingsDialogFragment.
     */
    public static SettingsDialogFragment newInstance() {
        return new SettingsDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentSettingsDialogBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ensure mBinding is referring to the correct view
        mBinding = FragmentSettingsDialogBinding.bind(view);

        mBinding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                Activity currentParent = getActivity();
                Intent loginIntent = new Intent(currentParent, LoginActivity.class);
                currentParent.startActivity(loginIntent);
                currentParent.finish();
            }
        });
    }
}