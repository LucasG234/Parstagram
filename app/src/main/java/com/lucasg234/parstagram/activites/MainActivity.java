package com.lucasg234.parstagram.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lucasg234.parstagram.R;
import com.lucasg234.parstagram.databinding.ActivityMainBinding;
import com.lucasg234.parstagram.fragments.ComposeFragment;
import com.lucasg234.parstagram.fragments.FeedFragment;
import com.lucasg234.parstagram.fragments.ProfileFragment;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        final FragmentManager fragmentManager = getSupportFragmentManager();

        // Set a listener for fragment selection
        mBinding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch(item.getItemId()) {
                    case R.id.menuCompose:
                        fragment = ComposeFragment.newInstance();
                        break;
                    case R.id.menuProfile:
                        //TODO: change
                        fragment = ProfileFragment.newInstance();
                        break;
                    // Default to menuHome
                    default:
                        //TODO: change
                        fragment = FeedFragment.newInstance();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.frameContainer, fragment).commit();
                return true;
            }
        });

        // Set a default fragment
        mBinding.bottomNavigationView.setSelectedItemId(R.id.menuFeed);
    }

    // Static method stored here for use through Activity
    public static String dateToRelative(Date date) {
        return String.valueOf(DateUtils.getRelativeTimeSpanString(date.getTime(),
                System.currentTimeMillis(), 0L, DateUtils.FORMAT_ABBREV_TIME));
    }
}