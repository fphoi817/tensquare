package com.tensquare.friend.controller;

import com.tensquare.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NofriendDao extends JpaRepository<NoFriend, String> {
}
