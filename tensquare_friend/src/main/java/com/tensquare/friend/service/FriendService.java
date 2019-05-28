package com.tensquare.friend.service;

import com.tensquare.friend.dao.NofriendDao;
import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NofriendDao nofriendDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public int addFrinend(String userid, String friendid){
        int count = friendDao.selectCount(userid, friendid);
        if(count > 0){
            return 0;
        }
        Friend friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);
        int i = friendDao.selectCount(friendid, userid);
        if(i > 0){
            friendDao.updateLike(userid, friendid, "1");
            friendDao.updateLike(friendid, userid, "1");
        }
        return 1;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addNofriend(String userid, String friendid) {
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        nofriendDao.save(noFriend);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteFriend(String userid, String friendid) {
        friendDao.deleteFriend(userid, friendid);
        friendDao.updateLike(friendid, userid, "0");
        addNofriend(userid, friendid);
    }
}
