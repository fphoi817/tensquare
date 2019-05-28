package com.tensquare.friend.controller;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.service.FriendService;
import com.tensquare.tools.ResponseResult;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserClient userClient;

    /**
     * 添加好友或者添加非好友
     */
    @RequestMapping(value = "/like/{friendid}/{type}", method = RequestMethod.PUT)
    public ResponseResult addFriend(@PathVariable String friendid, @PathVariable String type){
        Claims claims = (Claims) request.getAttribute("normal_claims");
        if(claims == null){
            return ResponseResult.UNAUTHORIZED(request.getRequestURI());
        }
        if(type.equals("1")){
            int i = friendService.addFrinend(claims.getId(), friendid);
            if(i == 0){
                return ResponseResult.FAILED("已经添加了好友");
            }
            userClient.incFollowcount(claims.getId(), 1);
            userClient.incFanscount(friendid, 1);
        }else {
            friendService.addNofriend(claims.getId(), friendid);
        }

        return ResponseResult.SUCCESS();
    }

    /**
     * 删除好友
     */
    @RequestMapping(value = "/{friendid}", method = RequestMethod.DELETE)
    public ResponseResult remove(@PathVariable String friendid){
        Claims claims = (Claims) request.getAttribute("normal_claims");
        if(claims == null){
            return ResponseResult.UNAUTHORIZED(request.getRequestURI());
        }
        friendService.deleteFriend(claims.getId(), friendid);
        userClient.incFollowcount(claims.getId(), -1);
        userClient.incFanscount(friendid, -1);
        return ResponseResult.SUCCESS();
    }


}
