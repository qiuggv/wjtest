package com.myapp.wjtest.controller;

import com.myapp.wjtest.Errors;
import com.myapp.wjtest.entity.User;
import com.myapp.wjtest.service.IUserService;
import com.myapp.wjtest.common.Response;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

@Autowired
private IUserService userService;

    @ApiOperation("查询用户列表")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "String", name = "username", value = "用户名", required = true),
        @ApiImplicitParam(paramType = "query", dataType = "int", name = "pageNum", value = "页码"),
        @ApiImplicitParam(paramType = "query", dataType = "int", name = "pageSize", value = "每页记录数")
    })
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Response<PageInfo<User>> query(@RequestParam String username,
                                          @RequestParam(required = false, defaultValue = "1") int pageNum,
                                          @RequestParam(required = false, defaultValue = "10") int pageSize) {
        PageInfo<User> pageInfo = userService.query(username, pageNum, pageSize);
            return Response.success(pageInfo);
    }

    @ApiOperation("新增用户")
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public Response<Integer> save(@RequestBody User user) {
        int i = userService.save(user);
        return Response.success(i);
    }

    @ApiOperation("更新用户")
    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public Response<Integer> update(@RequestBody User user) {
        int i = userService.update(user);
        return Response.success(i);
    }

    @ApiOperation(value = "查询指定ID的用户", notes = "查询指定ID的用户，异常编码说明：[2404:用户不存在;2405:用户被禁用;2406:用户已过期]")
    @ApiImplicitParam(paramType = "path", dataType = "int", name = "id", value = "用户ID", required = true)
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public Response<User> selectById(@PathVariable Integer id) {
        User user = userService.selectById(id);
        if (user == null) {
        throw Errors.USER_NOT_FOUND.exception();
        }
        if (user.isExpired()) {
        throw Errors.USER_EXPIRED.exception();
        }
        if (!user.isEnabled()) {
        throw Errors.USER_DISABLED.exception();
        }
        return Response.success(user);
    }

    @ApiOperation("删除指定ID的用户")
    @ApiImplicitParam(paramType = "path", dataType = "int", name = "id", value = "用户ID", required = true)
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public Response<Integer> deleteById(@PathVariable Integer id) {
        int i = userService.deleteById(id);
        return Response.success(i);
    }

    @ApiOperation(value = "查询指定ID的用户", notes = "查询指定ID的用户]")
    @ApiImplicitParam(paramType = "path", dataType = "int", name = "id", value = "用户ID", required = true)
    @RequestMapping(value = "/simple_users/{id}", method = RequestMethod.GET)
        public User selectUserById(@PathVariable Integer id) {
        return userService.selectById(id);
    }
}
