package com.tensquare.friend.controller;

import com.tensquare.tools.ResponseResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/friend")
public class FriendController {

    /**
     * 添加好友或者添加非好友
     */
    @RequestMapping(value = "/like/{friendid}/{type}", method = RequestMethod.PUT)
    public ResponseResult addFriend(@PathVariable String friendid, @PathVariable String type){
        return ResponseResult.SUCCESS();
        // TODO
    }

}
