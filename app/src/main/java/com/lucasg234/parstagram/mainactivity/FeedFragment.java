package com.lucasg234.parstagram.mainactivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lucasg234.parstagram.R;
import com.lucasg234.parstagram.databinding.FragmentFeedBinding;
import com.lucasg234.parstagram.dialogs.CommentDialogFragment;
import com.lucasg234.parstagram.dialogs.PostDialogFragment;
import com.lucasg234.parstagram.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment contained within the MainActivity
 * Hosts a RecyclerView which displays all posts queried from Parse
 */
public class FeedFragment extends Fragment {

    private static final String TAG = "FeedFragment";
    // Used to determine what type of dialog fragment to create
    public static final int DIALOG_TYPE_POST = 82;
    public static final int DIALOG_TYPE_COMMENT = 83;

    protected FragmentFeedBinding mBinding;
    private List<Post> mPosts;
    protected FeedAdapter mAdapter;
    private EndlessRecyclerViewScrollListener mEndlessScrollListener;

    private ParseUser mFilterUser;

    public FeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of FeedFragment
     */
    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentFeedBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Listener to create Dialog Fragments
        FeedAdapter.PostClickListener postClickListener = new FeedAdapter.PostClickListener() {
            @Override
            public void onPostClicked(int position, int dialogType) {
                createDialogFragment(position, dialogType);
            }
        };

        mPosts = new ArrayList<>();
        mAdapter = new FeedAdapter(getContext(), mPosts, postClickListener);
        mBinding.feedRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.feedRecyclerView.setLayoutManager(layoutManager);

        // Listener for Swipe to Refresh
        mBinding.feedSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryPosts();
            }
        });
        // Listener for Endless Pagination
        mEndlessScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                queryAdditionalPosts();
            }
        };
        mBinding.feedRecyclerView.addOnScrollListener(mEndlessScrollListener);

        queryPosts();
    }

    // This method sets a specific user to display posts from
    public void setQueryFilterUser(ParseUser user) {
        this.mFilterUser = user;
    }

    /**
     * Resets the FeedAdapter to hold a new set of most recent posts
     */
    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.setLimit(Post.QUERY_LIMIT);
        query.include(Post.KEY_USER);
        if (mFilterUser != null) {
            query.whereEqualTo(Post.KEY_USER, mFilterUser);
        }
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error querying posts", e);
                    Toast.makeText(getContext(), getString(R.string.error_load), Toast.LENGTH_SHORT).show();
                    return;
                }
                // Clear all posts, then add the newly found set
                mAdapter.clear();
                mEndlessScrollListener.resetState();
                mAdapter.addAll(posts);
                // Signal refresh has stopped (no effect if not refreshing)
                mBinding.feedSwipeContainer.setRefreshing(false);
            }
        });
    }

    /**
     * Adds new posts to the end of the FeedAdapter
     */
    private void queryAdditionalPosts() {
        Log.i(TAG, "Loading more posts from endless scroll");
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.setLimit(Post.QUERY_LIMIT);
        query.include(Post.KEY_USER);
        query.whereLessThan(Post.KEY_CREATED_AT, mPosts.get(mPosts.size() - 1).getCreatedAt());
        if (mFilterUser != null) {
            query.whereEqualTo(Post.KEY_USER, mFilterUser);
        }
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error querying for additional posts", e);
                    Toast.makeText(getContext(), getString(R.string.error_load), Toast.LENGTH_SHORT).show();
                    return;
                }
                // Do not clear all posts, only add new posts to the end of the list
                mAdapter.addAll(posts);
            }
        });
    }

    /**
     * Creates a CommentDialogFragment or a PostDialogFragment
     */
    private void createDialogFragment(int position, int dialogFragmentType) {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        DialogFragment dialogFragment;
        switch (dialogFragmentType) {
            case DIALOG_TYPE_COMMENT:
                dialogFragment = CommentDialogFragment.newInstance(mPosts.get(position));
                break;
            case DIALOG_TYPE_POST:
                dialogFragment = PostDialogFragment.newInstance(mPosts.get(position));
                break;
            default:
                return;
        }
        dialogFragment.show(fragmentManager, "fragment_post_dialog");
    }
}