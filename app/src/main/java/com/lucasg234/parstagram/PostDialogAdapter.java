package com.lucasg234.parstagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lucasg234.parstagram.activites.MainActivity;
import com.lucasg234.parstagram.databinding.ItemCommentBinding;
import com.lucasg234.parstagram.models.Comment;

import java.util.Date;
import java.util.List;

public class PostDialogAdapter extends RecyclerView.Adapter<PostDialogAdapter.PostDialogViewHolder> {

    private static final String TAG = "PostDialogAdapter";

    private Context mContext;
    private List<Comment> mComments;

    public PostDialogAdapter(Context context, List<Comment> comments) {
        this.mContext = context;
        this.mComments = comments;
    }

    @NonNull
    @Override
    public PostDialogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new PostDialogViewHolder(ItemCommentBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostDialogViewHolder holder, int position) {
        holder.bind(mComments.get(position));
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    class PostDialogViewHolder extends RecyclerView.ViewHolder {

        private ItemCommentBinding mBinding;

        public PostDialogViewHolder(@NonNull ItemCommentBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public void bind(Comment comment) {
            mBinding.commentText.setText(comment.getText());
            mBinding.commentUsername.setText(comment.getUser().getUsername());
            // Use relative time for post created at description
            Date createdAt = comment.getCreatedAt();
            mBinding.commentCreatedAt.setText(MainActivity.dateToRelative(createdAt));
        }
    }
}
