package com.lyj.yygh.vod.controller;

import com.lyj.result.Result;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/admin/user/vod")
@CrossOrigin  //跨域
public class UserController {

    @PostMapping("login")
    public Result userlogin(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("token","admin-token");
        return Result.ok(map).code(20000);
    }

    @GetMapping("info")
    public Result userinfo(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("roles","admin");
        map.put("introduction","I am a super administrator");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name","Super Admin");

        return Result.ok(map).code(20000);
    }

}
