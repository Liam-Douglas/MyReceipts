package com.bignerdranch.android.MyReceipts;

import java.util.Date;
import java.util.UUID;

public class Receipt {

    private UUID mId;
    private String mTitle;
    private String mShopname;
    private String mComment;
    private Date mDate;
    private boolean mSolved;

    public Receipt() {
        this(UUID.randomUUID());
    }

    public Receipt(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getShopname() {
        return mShopname;
    }

    public void setShopname(String shopname) {
        mShopname = shopname;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpb";
    }

}
