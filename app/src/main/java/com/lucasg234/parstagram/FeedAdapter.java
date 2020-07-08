package com.lucasg234.parstagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lucasg234.parstagram.databinding.ItemPostBinding;
import com.lucasg234.parstagram.models.Post;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder>{

    private Context mContext;
    private List<Post> mPosts;

    public FeedAdapter(Context context, List<Post> posts) {
        this.mContext = context;
        this.mPosts = posts;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new FeedViewHolder(ItemPostBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        Post currentPost = mPosts.get(position);
        holder.bind(currentPost);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    // Clean all elements of the RecyclerView
    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    // Add a list of Posts to the RecyclerView
    public void addAll(List<Post> list) {
        mPosts.addAll(list);
        notifyDataSetChanged();
    }

    class FeedViewHolder extends RecyclerView.ViewHolder {

        private ItemPostBinding mBinding;

        public FeedViewHolder(@NonNull @NotNull ItemPostBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public void bind(Post post) {
            mBinding.postDescription.setText(post.getDescription());
            mBinding.postUsername.setText(post.getUser().getUsername());
            if(post.getImage() != null) {
                Glide.with(mContext)
                        .load(post.getImage().getUrl())
                        .into(mBinding.postImage);
            }
        }
    }
}
