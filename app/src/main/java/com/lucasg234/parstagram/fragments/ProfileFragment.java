package com.lucasg234.parstagram.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lucasg234.parstagram.databinding.FragmentFeedBinding;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

// Class which acts similar to feed, but only shows current User's posts
// and provides access to the settings Fragment
public class ProfileFragment extends FeedFragment {

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setQueryFilterUser(ParseUser.getCurrentUser());
        super.onViewCreated(view, savedInstanceState);
    }
}
