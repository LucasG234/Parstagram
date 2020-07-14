package com.lucasg234.parstagram.mainactivity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.parse.ParseUser;

/**
 * Fragment which is held within MainActivity
 * Identical function to FeedFragment but only displays posts created by the current user
 */
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
