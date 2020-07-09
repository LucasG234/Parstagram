package com.lucasg234.parstagram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lucasg234.parstagram.R;
import com.lucasg234.parstagram.databinding.FragmentCommentDialogBinding;
import com.lucasg234.parstagram.models.Post;

import org.parceler.Parcels;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommentFragmentDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommentFragmentDialog extends DialogFragment {

    private static final String ARG_PARENT_POST = "parent_post_arg_1";

    private Post mParentPost;
    private FragmentCommentDialogBinding mBinding;

    public CommentFragmentDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CommentFragmentDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static CommentFragmentDialog newInstance(Post parent) {
        CommentFragmentDialog fragment = new CommentFragmentDialog();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARENT_POST, Parcels.wrap(parent));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParentPost = Parcels.unwrap(getArguments().getParcelable(ARG_PARENT_POST));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        // Workaround for https://issuetracker.google.com/issues/37042151
//        LayoutInflater parentInflater = getActivity().getLayoutInflater();
        mBinding = FragmentCommentDialogBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ensure mBinding is referring to the correct view
        //mBinding = FragmentPostDialogBinding.bind(view);
    }
}