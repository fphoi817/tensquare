package com.tensquare.friend.controller;

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
        }else {
            friendService.addNofriend(claims.getId(), friendid);
        }
        return ResponseResult.SUCCESS();
    }
}
