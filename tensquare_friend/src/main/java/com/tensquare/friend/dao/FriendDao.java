package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend, String> {

    @Query("SELECT count(f) FROM Friend f WHERE f.userid = ?1 AND f.friendid = ?2")
    int selectCount(String userid, String friendid);

    @Modifying
    @Query("UPdate Friend f SET f.islike = ?3 WHERE f.userid = ?1 AND f.friendid = ?2")
    void updateLike(String userid, String friendid, String islike);

}
