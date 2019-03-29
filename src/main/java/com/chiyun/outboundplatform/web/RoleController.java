package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.common.MustLogin;
import com.chiyun.outboundplatform.entity.RoleEntity;
import com.chiyun.outboundplatform.repository.RoleReposity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.util.List;

@Api(description = "角色管理")
@RestController
@RequestMapping(value = "/role", method = {RequestMethod.POST, RequestMethod.GET})
public class RoleController {

    @Resource
    private RoleReposity roleReposity;

    @MustLogin(rolerequired = {1, 2, 3})
    @ApiOperation(value = "添加")
    @RequestMapping("/add")
    public ApiResult<Object> add(RoleEntity roleEntity, HttpSession session) {
        String js = session.getAttribute("js").toString();
        if (js.equals("1")) {
             roleReposity.save(roleEntity);
            return ApiResult.SUCCESS("添加成功");
        } else {
            return ApiResult.FAILURE("没有权限添加用户");
        }
    }

    @ApiOperation(value = "删除")
    @RequestMapping("/delete")
    public ApiResult<Object> delete(int id, HttpSession session) {
        String js = session.getAttribute("js").toString();
        if (js.equals("1")) {
            int result = roleReposity.deleteById(id);
            if (result == 0) {
                return ApiResult.FAILURE("删除失败");
            } else {
                return ApiResult.SUCCESS("删除成功");
            }
        } else {
            return ApiResult.FAILURE("没有权限删除用户");
        }
    }

    @ApiOperation(value = "修改")
    @RequestMapping("/update")
    public ApiResult<Object> update(RoleEntity roleEntity, HttpSession session) {
        String js = session.getAttribute("js").toString();
        if (js.equals("1")) {
            roleReposity.save(roleEntity);
            return ApiResult.SUCCESS("修改成功");
        } else {
            return ApiResult.FAILURE("没有权限修改用户");
        }
    }

    @ApiOperation(value = "查询所有")
    @RequestMapping("/findAll")
    public ApiResult<Object> findAll(HttpSession session) {
        String js = session.getAttribute("js").toString();
        if (js.equals("1")) {
            List<RoleEntity> result = roleReposity.findAll();
            return ApiResult.SUCCESS(result);
        } else {
            return ApiResult.FAILURE("没有权限添加用户");
        }
    }

}
