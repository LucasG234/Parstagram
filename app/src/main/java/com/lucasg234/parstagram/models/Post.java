package com.lucasg234.parstagram.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import org.parceler.Parcel;

/**
 * Parse model which stores User created posts
 * Keys specify all of the object's fields
 */
@Parcel(analyze = {Post.class})
@ParseClassName("Post")
public class Post extends ParseObject {
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_LIKED_BY = "likedBy";
    public static final String KEY_COMMENTS = "comments";
    public static final String KEY_OBJECT_ID = "objectId";
    public static final int QUERY_LIMIT = 20;

    // Empty constructor required to use Parceler
    public Post() {
    }

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }


    // Returns ParseQuery used to find likes associated with the post
    public ParseQuery<ParseUser> getLikeQuery() {
        ParseRelation<ParseUser> likedBy = getRelation(KEY_LIKED_BY);
        return likedBy.getQuery();
    }

    // Returns ParseQuery used to find comments associated with the post
    public ParseQuery<Comment> getCommentQuery() {
        ParseRelation<Comment> comments = getRelation(KEY_COMMENTS);
        return comments.getQuery();
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public void setImage(ParseFile image) {
        put(KEY_IMAGE, image);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public void addLike(ParseUser user) {
        ParseRelation<ParseUser> likedBy = getRelation(KEY_LIKED_BY);
        likedBy.add(user);
    }

    public void removeLike(ParseUser user) {
        ParseRelation<ParseUser> likedBy = getRelation(KEY_LIKED_BY);
        likedBy.remove(user);
    }

    public void addComment(Comment comment) {
        ParseRelation<Comment> comments = getRelation(KEY_COMMENTS);
        comments.add(comment);
    }

}
