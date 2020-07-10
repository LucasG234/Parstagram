package com.lucasg234.parstagram.mainactivity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.parse.ParseUser;

// Class which acts similar to feed, but only shows current User's posts
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
