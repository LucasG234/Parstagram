package com.lucasg234.parstagram.mainactivity;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lucasg234.parstagram.R;
import com.lucasg234.parstagram.databinding.ActivityMainBinding;
import com.lucasg234.parstagram.dialogs.SettingsDialogFragment;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        setSupportActionBar(mBinding.mainToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        // Set a listener for fragment selection
        mBinding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.menuCompose:
                        fragment = ComposeFragment.newInstance();
                        break;
                    case R.id.menuProfile:
                        fragment = ProfileFragment.newInstance();
                        break;
                    // Default to menuHome
                    default:
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menuSettings:
                openSettingsDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSettingsDialog() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        DialogFragment dialogFragment = SettingsDialogFragment.newInstance();
        dialogFragment.show(fragmentManager, "fragment_post_settings");
    }
}