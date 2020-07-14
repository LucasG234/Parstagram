package com.lucasg234.parstagram.dialogs;

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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.lucasg234.parstagram.R;
import com.lucasg234.parstagram.databinding.FragmentPostDialogBinding;
import com.lucasg234.parstagram.mainactivity.MainActivity;
import com.lucasg234.parstagram.models.Comment;
import com.lucasg234.parstagram.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostDialogFragment extends DialogFragment {

    private static String KEY_POST_ARG = "post_arg_1";
    private static final String TAG = "PostDialogFragment";

    private Post mPost;
    private FragmentPostDialogBinding mBinding;
    private List<Comment> mComments;
    private PostDialogAdapter mAdapter;

    public PostDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PostDialogFragment.
     */
    public static PostDialogFragment newInstance(Post post) {
        PostDialogFragment fragment = new PostDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_POST_ARG, Parcels.wrap(post));
        fragment.setArguments(args);


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPost = Parcels.unwrap(getArguments().getParcelable(KEY_POST_ARG));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        // Workaround for https://issuetracker.google.com/issues/37042151
//        LayoutInflater parentInflater = getActivity().getLayoutInflater();
        mBinding = FragmentPostDialogBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ensure mBinding is referring to the correct view
        mBinding = FragmentPostDialogBinding.bind(view);

        mBinding.detailsDescription.setText(mPost.getDescription());
        mBinding.detailsUsername.setText(mPost.getUser().getUsername());

        mComments = new ArrayList<>();
        mAdapter = new PostDialogAdapter(getContext(), mComments);
        mBinding.detailsRecyclerView.setAdapter(mAdapter);
        mBinding.detailsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Use relative time for post created at description
        Date createdAt = mPost.getCreatedAt();
        mBinding.detailsCreatedAt.setText(MainActivity.dateToRelative(createdAt));

        if (mPost.getImage() != null) {
            Glide.with(getContext())
                    .load(mPost.getImage().getUrl())
                    .placeholder(R.drawable.placeholder_image)
                    .into(mBinding.detailsImage);
        } else {
            Glide.with(getContext())
                    .load(R.drawable.placeholder_image)
                    .into(mBinding.detailsImage);
        }

        queryComments();
    }

    private void queryComments() {
        ParseQuery<Comment> query = mPost.getCommentQuery();
        query.addDescendingOrder(Comment.KEY_CREATED_AT);
        query.setLimit(Comment.QUERY_LIMIT);
        query.include(Comment.KEY_USER);
        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> comments, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error querying comments", e);
                    Toast.makeText(getContext(), getString(R.string.error_load), Toast.LENGTH_SHORT).show();
                    return;
                }
                // Clear all posts, then add the newly found set
                mComments.addAll(comments);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}