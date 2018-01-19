package com.wizinno.shuhao.user.domain.model;

import java.io.Serializable;

public class UserFriends implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_friends.user_id
     *
     * @mbggenerated Tue Sep 26 13:42:19 CST 2017
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_friends.friend_id
     *
     * @mbggenerated Tue Sep 26 13:42:19 CST 2017
     */
    private Long friendId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user_friends
     *
     * @mbggenerated Tue Sep 26 13:42:19 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_friends.user_id
     *
     * @return the value of user_friends.user_id
     *
     * @mbggenerated Tue Sep 26 13:42:19 CST 2017
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_friends.user_id
     *
     * @param userId the value for user_friends.user_id
     *
     * @mbggenerated Tue Sep 26 13:42:19 CST 2017
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_friends.friend_id
     *
     * @return the value of user_friends.friend_id
     *
     * @mbggenerated Tue Sep 26 13:42:19 CST 2017
     */
    public Long getFriendId() {
        return friendId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_friends.friend_id
     *
     * @param friendId the value for user_friends.friend_id
     *
     * @mbggenerated Tue Sep 26 13:42:19 CST 2017
     */
    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }
}