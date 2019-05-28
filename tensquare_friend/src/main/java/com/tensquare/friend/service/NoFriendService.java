package com.tensquare.friend.service;

import com.tensquare.friend.controller.NofriendDao;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoFriendService {

    @Autowired
    private NofriendDao nofriendDao;


    @Transactional(propagation = Propagation.REQUIRED)
    public void addNofriend(String userid, String friendid) {
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        nofriendDao.save(noFriend);
    }
}
