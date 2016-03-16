
package com.raenarapps.simplecollage.pojo;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Item {

    @SerializedName("can_delete_comments")
    @Expose
    private Boolean canDeleteComments;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("images")
    @Expose
    private Images images;
    @SerializedName("can_view_comments")
    @Expose
    private Boolean canViewComments;
    @SerializedName("comments")
    @Expose
    private Comments comments;
    @SerializedName("alt_media_url")
    @Expose
    private Object altMediaUrl;
    @SerializedName("caption")
    @Expose
    private Caption caption;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("likes")
    @Expose
    private Likes likes;
    @SerializedName("created_time")
    @Expose
    private String createdTime;
    @SerializedName("user_has_liked")
    @Expose
    private Boolean userHasLiked;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("videos")
    @Expose
    private Videos videos;

    /**
     * 
     * @return
     *     The canDeleteComments
     */
    public Boolean getCanDeleteComments() {
        return canDeleteComments;
    }

    /**
     * 
     * @param canDeleteComments
     *     The can_delete_comments
     */
    public void setCanDeleteComments(Boolean canDeleteComments) {
        this.canDeleteComments = canDeleteComments;
    }

    /**
     * 
     * @return
     *     The code
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * @param code
     *     The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 
     * @return
     *     The location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * 
     * @param location
     *     The location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * 
     * @return
     *     The images
     */
    public Images getImages() {
        return images;
    }

    /**
     * 
     * @param images
     *     The images
     */
    public void setImages(Images images) {
        this.images = images;
    }

    /**
     * 
     * @return
     *     The canViewComments
     */
    public Boolean getCanViewComments() {
        return canViewComments;
    }

    /**
     * 
     * @param canViewComments
     *     The can_view_comments
     */
    public void setCanViewComments(Boolean canViewComments) {
        this.canViewComments = canViewComments;
    }

    /**
     * 
     * @return
     *     The comments
     */
    public Comments getComments() {
        return comments;
    }

    /**
     * 
     * @param comments
     *     The comments
     */
    public void setComments(Comments comments) {
        this.comments = comments;
    }

    /**
     * 
     * @return
     *     The altMediaUrl
     */
    public Object getAltMediaUrl() {
        return altMediaUrl;
    }

    /**
     * 
     * @param altMediaUrl
     *     The alt_media_url
     */
    public void setAltMediaUrl(Object altMediaUrl) {
        this.altMediaUrl = altMediaUrl;
    }

    /**
     * 
     * @return
     *     The caption
     */
    public Caption getCaption() {
        return caption;
    }

    /**
     * 
     * @param caption
     *     The caption
     */
    public void setCaption(Caption caption) {
        this.caption = caption;
    }

    /**
     * 
     * @return
     *     The link
     */
    public String getLink() {
        return link;
    }

    /**
     * 
     * @param link
     *     The link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * 
     * @return
     *     The likes
     */
    public Likes getLikes() {
        return likes;
    }

    /**
     * 
     * @param likes
     *     The likes
     */
    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    /**
     * 
     * @return
     *     The createdTime
     */
    public String getCreatedTime() {
        return createdTime;
    }

    /**
     * 
     * @param createdTime
     *     The created_time
     */
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 
     * @return
     *     The userHasLiked
     */
    public Boolean getUserHasLiked() {
        return userHasLiked;
    }

    /**
     * 
     * @param userHasLiked
     *     The user_has_liked
     */
    public void setUserHasLiked(Boolean userHasLiked) {
        this.userHasLiked = userHasLiked;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The user
     */
    public User getUser() {
        return user;
    }

    /**
     * 
     * @param user
     *     The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 
     * @return
     *     The videos
     */
    public Videos getVideos() {
        return videos;
    }

    /**
     * 
     * @param videos
     *     The videos
     */
    public void setVideos(Videos videos) {
        this.videos = videos;
    }

}
