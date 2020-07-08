package com.lucasg234.parstagram.fragments;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lucasg234.parstagram.R;
import com.lucasg234.parstagram.databinding.FragmentPostDialogBinding;
import com.lucasg234.parstagram.models.Post;

import org.parceler.Parcels;

import java.util.Date;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostDialogFragment extends DialogFragment {

    private static String KEY_POST_ARG = "post_arg_1";

    private Post mPost;
    private FragmentPostDialogBinding mBinding;

    public PostDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PostDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        //mBinding = FragmentPostDialogBinding.bind(view);

        mBinding.detailsDescription.setText(mPost.getDescription());
        mBinding.detailsUsername.setText(mPost.getUser().getUsername());

        // Use relative time for post
        Date absoluteCreatedAt = mPost.getCreatedAt();
        String relativeCreatedAt = String.valueOf(DateUtils.getRelativeTimeSpanString(absoluteCreatedAt.getTime(),
                System.currentTimeMillis(), 0L, DateUtils.FORMAT_ABBREV_TIME));

        mBinding.detailsCreatedAt.setText(relativeCreatedAt);
        if(mPost.getImage() != null) {
            Glide.with(getContext())
                    .load(mPost.getImage().getUrl())
                    .override(100, 100)
                    .into(mBinding.detailsImage);
        }
    }
}