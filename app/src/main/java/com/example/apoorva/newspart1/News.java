package com.example.apoorva.newspart1;

public class News {
    private String mWebUrl;
    private String mWebTitle;
    private String mAuthorName;
    private String mSectionName;
    private String mDateAndTime;

    public News (String webUrl,String webTitle, String authorName,String sectionName,String dateAndTime)
    {
        mWebUrl = webUrl;
        mWebTitle = webTitle;
        mAuthorName = authorName;
        mSectionName = sectionName;
        mDateAndTime = dateAndTime;
    }

    public String getWebUrl() { return mWebUrl; }
    public String getWebTitle() { return mWebTitle; }
    public String getAuthorName() { return mAuthorName; }
    public String getSectionName() { return mSectionName; }
    public String getDateAndTime() { return mDateAndTime; }

}

