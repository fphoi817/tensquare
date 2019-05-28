package com.tensquare.user.dao;

import com.tensquare.user.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaRepository<User, String> {

    User findByMobile(String mobile);

    @Modifying
    @Query("UPDATE User u SET u.fanscount = u.fanscount + ?2 WHERE u.id = ?1")
    void incFanscount(String userid, int x);

    @Modifying
    @Query("UPDATE User u SET u.followcount = u.followcount + ?2 WHERE u.id = ?1")
    void incFollowcount(String userid, int x);
}
