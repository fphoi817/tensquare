package com.tensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("tensquare-user")
public interface UserClient {

    @PutMapping("/user/incfans/{userid}/{x}")
    void incFanscount(@PathVariable("userid") String userid, @PathVariable("x") int x);

    @PutMapping("/user/incfollow/{userid}/{x}")
    void incFollowcount(@PathVariable("userid") String userid, @PathVariable("x") int x);
}
