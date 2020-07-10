package com.lucasg234.parstagram.dialogs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lucasg234.parstagram.R;
import com.lucasg234.parstagram.databinding.FragmentCommentDialogBinding;
import com.lucasg234.parstagram.models.Comment;
import com.lucasg234.parstagram.models.Post;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommentDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommentDialogFragment extends DialogFragment {

    private static final String TAG = "CommentDialogFragment";
    private static final String ARG_PARENT_POST = "parent_post_arg_1";

    private Post mParentPost;
    private FragmentCommentDialogBinding mBinding;

    public CommentDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CommentDialogFragment.
     */
    public static CommentDialogFragment newInstance(Post parent) {
        CommentDialogFragment fragment = new CommentDialogFragment();
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
        mBinding = FragmentCommentDialogBinding.bind(view);

        mBinding.commentSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Post new comment on click
                saveComment();
            }
        });
    }

    private void saveComment() {
        final Comment newComment = new Comment();
        newComment.setText(mBinding.commentSubmitText.getText().toString());
        newComment.setParentPost(mParentPost);
        newComment.setUser(ParseUser.getCurrentUser());
        newComment.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null) {
                    Log.e(TAG, "Error while saving comment", e);
                    Toast.makeText(getContext(),
                            getString(R.string.error_comment_save), Toast.LENGTH_SHORT).show();
                    return;
                }
                mParentPost.addComment(newComment);
                mParentPost.saveInBackground();
                dismiss();
            }
        });
    }
}