package com.lucasg234.parstagram;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lucasg234.parstagram.databinding.ItemPostBinding;
import com.lucasg234.parstagram.fragments.FeedFragment;
import com.lucasg234.parstagram.models.Post;
import com.parse.CountCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private static final String TAG = "FeedAdapter";

    private Context mContext;
    private List<Post> mPosts;
    private PostClickListener mPostClickListener;

    public interface PostClickListener {
        void onPostClicked(int position, int dialogType);
    }


    public FeedAdapter(Context context, List<Post> posts, PostClickListener postClickListener) {
        this.mContext = context;
        this.mPosts = posts;
        this.mPostClickListener = postClickListener;
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
        private boolean mLikedByCurrentUser;

        public FeedViewHolder(@NonNull @NotNull ItemPostBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
            mLikedByCurrentUser = false;
        }

        public void bind(final Post post) {
            mBinding.postDescription.setText(post.getDescription());
            mBinding.postUsername.setText(post.getUser().getUsername());

            // Use relative time for post created at description
            Date absoluteCreatedAt = post.getCreatedAt();
            String relativeCreatedAt = String.valueOf(DateUtils.getRelativeTimeSpanString(absoluteCreatedAt.getTime(),
                    System.currentTimeMillis(), 0L, DateUtils.FORMAT_ABBREV_TIME));
            mBinding.postCreatedAt.setText(relativeCreatedAt);

            // Set image on the post
            if(post.getImage() != null) {
                Glide.with(mContext)
                        .load(post.getImage().getUrl())
                        .into(mBinding.postImage);

                mBinding.postImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPostClickListener.onPostClicked(getAdapterPosition(), FeedFragment.DIALOG_TYPE_POST);
                    }
                });
            }

            configureInteractionButtons(post);
        }

        private void configureInteractionButtons(final Post post) {
            // Set listener on the comment button
            mBinding.postCommentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPostClickListener.onPostClicked(getAdapterPosition(), FeedFragment.DIALOG_TYPE_COMMENT);
                }
            });

            // Determine if the post is already liked before setting initial image on like button
            post.isLikedBy(ParseUser.getCurrentUser(), new CountCallback() {
                @Override
                public void done(int count, ParseException e) {
                    if(e != null) {
                        Log.e(TAG, "Failed to load like count", e);
                        return;
                    }
                    mLikedByCurrentUser = count > 0;
                    if(mLikedByCurrentUser) {
                        // If already liked, change image
                        Glide.with(mContext)
                                .load(R.drawable.ufi_heart_active)
                                .into(mBinding.postLikeButton);
                    }
                }
            });
            // Set listener on like button immediately
            mBinding.postLikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mLikedByCurrentUser) {
                        post.removeLike(ParseUser.getCurrentUser());
                        // Change image to unliked version
                        Glide.with(mContext)
                                .load(R.drawable.ufi_heart)
                                .into(mBinding.postLikeButton);
                        Log.i(TAG, "unliked image");
                    } else {
                        post.addLike(ParseUser.getCurrentUser());
                        // Change image to liked version
                        Glide.with(mContext)
                                .load(R.drawable.ufi_heart_active)
                                .into(mBinding.postLikeButton);
                        Log.i(TAG, "liked image");
                    }
                    post.saveInBackground();
                    mLikedByCurrentUser = !mLikedByCurrentUser;
                }
            });
        }
    }
}
