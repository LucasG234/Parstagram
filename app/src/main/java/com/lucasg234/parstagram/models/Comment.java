package com.lucasg234.parstagram.models;


import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;


@Parcel(analyze = {Comment.class})
@ParseClassName("Comment")
public class Comment extends ParseObject {
    public static final String KEY_TEXT = "text";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_PARENT_POST = "parentPost";
    public static final int QUERY_LIMIT = 20;


    // Empty constructor required to use Parceler
    public Comment() {
    }

    public String getText() {
        return getString(KEY_TEXT);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public Post getParentPost() {
        return (Post) getParseObject(KEY_PARENT_POST);
    }

    public void setText(String text) {
        put(KEY_TEXT, text);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public void setParentPost(Post parent) {
        put(KEY_PARENT_POST, parent);
    }
}
