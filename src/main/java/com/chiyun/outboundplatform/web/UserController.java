package com.chiyun.outboundplatform.web;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.entity.UserEntity;
import com.chiyun.outboundplatform.repository.UserReposity;
import com.chiyun.outboundplatform.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
//import net.sf.json.JSONException;
//import net.sf.json.JSONObject;
//import org.apache.commons.lang.StringUtils;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "用户管理")
@RestController
@RequestMapping(value = "/user", method = {RequestMethod.POST, RequestMethod.GET})
public class UserController {

    private static final String APPID = "wxa52fa9f12b8746d8";
    private static final String SECRET = "b2ed2f764ac0467733840008eda01954";

    @Resource
    private UserReposity userReposity;

    @ApiOperation(value = "登录")
    @RequestMapping("/login")
    public ApiResult<Object> login(@RequestParam @ApiParam(value = "用户名")String yhm, @RequestParam @ApiParam(value = "密码")String mm, HttpSession session) throws Exception{
        if(StringUtil.isNull(yhm)||StringUtil.isNull(mm)){
            return ApiResult.FAILURE("用户名或密码不能为空");
        }
        mm = MD5Util.MD5(mm);
        UserEntity userEntity = userReposity.findByYhmAndMm(yhm, mm);
        if(userEntity == null){
            return ApiResult.FAILURE("用户名或密码为空");
        }
        session.setAttribute("yhm", userEntity.getYhm());
        session.setAttribute("id", userEntity.getId());
        session.setAttribute("szxzqdm", userEntity.getSzxzqdm());
        session.setAttribute("js", userEntity.getJs());
        return ApiResult.SUCCESS(userEntity);
    }

    @ApiOperation(value = "微信小程序登录")
    @RequestMapping("/weLogin")
    public ApiResult<Object> weLogin(@ApiParam(value = "授权码")String sqm,String encryptedData,String iv, String code, HttpServletResponse response){
        Map<String, Object> map = weChatLogin(code,encryptedData,iv);
        if(map.get("status").toString()=="0"){
            return ApiResult.FAILURE(map.get("msg").toString());
        }
        UserEntity userEntity = userReposity.findByOpenid(map.get("openid").toString());
        if(userEntity==null&&StringUtil.isNull(sqm)){
            return ApiResult.FAILURE("数据库中没有您的信息，请出示您的授权码");
        }else{
            //userEntity为空，sqm不为空
            if(!StringUtil.isNull(sqm)){
                UserEntity userEntity1 = userReposity.findBySqm(sqm);
                if(userEntity1==null){
                    //数据库找不到邀请码信息
                    return ApiResult.FAILURE("数据库没有该授权码");
                }else if(userEntity1.getOpenid()!=null){
                    //邀请码查询出来的数据有人绑定
                    return ApiResult.FAILURE("授权码已有人使用，请核实");
                }
                userEntity1.setOpenid(map.get("openid").toString());
               // userEntity1.setUnionid(map.get("unionid").toString());
                userEntity1.setSk(map.get("session_key").toString());
                userEntity1.setSkcjsj(new Date());
                userEntity1.setBdsj(new Date());
                UserEntity result = userReposity.save(userEntity1);
                if (result==null){
                    return ApiResult.FAILURE("数据添加失败");
                }
                return ApiResult.SUCCESS(result);

            }
            //userEntity不为空，数据库有openid的信息，不需要授权码，登录成功
            //保存本次session_key
            userEntity.setSk(map.get("session_key").toString());
            userEntity.setSkcjsj(new Date());
            UserEntity result = userReposity.save(userEntity);
            if (result==null){
                return ApiResult.FAILURE("数据添加失败");
            }
            return ApiResult.SUCCESS(result);
        }

    }

    @ApiOperation(value="添加用户")
    @RequestMapping("/add")
    public ApiResult<Object> add(HttpSession session,UserEntity userEntity) throws Exception {
        //判断是否登录

        //判断是否为管理员

        /* 添加用户 */
        UserEntity result=null;
        userEntity.setCjsj(new Date());
        userEntity.setZt(0);
        //判断添加的用户为什么网站用户还是微信小程序用户
        if("0".equals(userEntity.getLx())){
            //网站用户
            userEntity.setMm(MD5Util.MD5("666666"));
            result = userReposity.save(userEntity);
        }else if("1".equals(userEntity.getLx())){
            //微信小程序用户
            result = userReposity.save(userEntity);
            String sqm=CodeUtil.toSerialCode(result.getId());
            result.setSqm(sqm);
            result = userReposity.save(result);
        }else{
            return ApiResult.FAILURE("添加用户类型错误");
        }
        if(result==null){
            return ApiResult.FAILURE("添加失败");
        }
        return ApiResult.SUCCESS(result);
    }

    @ApiOperation(value="删除用户")
    @RequestMapping("/delete")
    public ApiResult<Object> delete(HttpSession session,int id) throws Exception {
        //判断是否登录

        //判断是否为管理员

        /* 删除用户 */
        UserEntity oldUserEntity = userReposity.findById(id);
        if (oldUserEntity == null) {
            return ApiResult.FAILURE("没有该用户的信息");
        }
        int result = userReposity.deleteById(id);
        if(result==0){
            return ApiResult.FAILURE("删除失败");
        }
        return ApiResult.SUCCESS("删除成功");
    }

    @ApiOperation(value="修改用户")
    @RequestMapping("/update")
    public ApiResult<Object> update(HttpSession session,UserEntity userEntity) throws Exception {
        //判断是否登录

        //判断是否为管理员

        /* 修改用户 */
        UserEntity oldUserEntity = userReposity.findById(userEntity.getId());
        if (oldUserEntity == null) {
            return ApiResult.FAILURE("没有该用户的信息");
        }
        userEntity.setCjsj(oldUserEntity.getCjsj());
        UserEntity result = userReposity.save(userEntity);
        if(result==null){
            return ApiResult.FAILURE("修改失败");
        }
        return ApiResult.SUCCESS(result);
    }

    @ApiOperation(value="查询所有用户")
    @RequestMapping("/findAll")
    public ApiResult<Object> findAll(@RequestParam int page, @RequestParam int size,HttpSession session){
        //判断是否登录

        Page<UserEntity> result;
        //判断用户权限
        String js = session.getAttribute("js").toString();
        if("1".equals(js)){
            result = userReposity.findAll(PageRequest.of(page - 1, size, Sort.by(new Sort.Order(Sort.Direction.DESC, "cjsj"))));
        }else if("2".equals(js)){
            int a[]={2,4};
            result = userReposity.findByJsAndSzxzqdm(a,session.getAttribute("szxzqdm").toString(),PageRequest.of(page - 1, size, Sort.by(new Sort.Order(Sort.Direction.DESC, "create_time"))));
        }else {
            return ApiResult.FAILURE("没有权限查看用户");
        }
        return ApiResult.SUCCESS(result);
    }

    public Map<String, Object> weChatLogin(String code, String encryptedData,String iv){
        Map<String, Object> map = new HashMap<String, Object>();
        String status = "1";
        String msg = "ok";
        String WX_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        try {
//            String code = request.getParameter("code");
            if(StringUtil.isNull(code)){
                status = "0";//失败状态
                msg = "code为空";
            }else {
                String requestUrl = WX_URL.replace("APPID", APPID).
                        replace("SECRET", SECRET).replace("JSCODE", code);
                //logger.info(requestUrl);
                // 发起GET请求获取凭证
                JSONObject jsonObject = HttpUtil.httpRequest(requestUrl, null);
                if (jsonObject != null) {
                    try {
                        map.put("openid", jsonObject.getString("openid"));
                        map.put("session_key", jsonObject.getString("session_key"));
                        Map<String, Object> map1 = decrypt(encryptedData,iv, jsonObject.getString("session_key"));
                        map.put("unionID",map1.get("unionId"));
                    } catch (JSONException e) {
                        // 获取token失败
                        status = "0";
                        msg = "code无效";
                    }
                }else {
                    status = "0";
                    msg = "code无效";
                }
            }
            map.put("status", status);
            map.put("msg", msg);
        } catch (Exception e) {
            // logger.error(e.getMessage(),e);
            //return map;
        }
        return map;
    }

    public Map<String, Object> decrypt(String encryptedData,String iv, String session_key){
//对encryptedData加密数据进行AES解密
        Map map = new HashMap();
        try {
            JSONObject result = AesUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            if (!result.isEmpty() && result.size() > 0) {
                map.put("status", 1);
                map.put("msg", "解密成功");
                Map userInfo = new HashMap();
//                System.out.print("result:"+result);
//                System.out.print("unionid:"+result.get("unionId"));
                userInfo.put("openId", result.get("openId"));
                userInfo.put("nickName", result.get("nickName"));
                userInfo.put("gender", result.get("gender"));
                userInfo.put("city", result.get("city"));
                userInfo.put("province", result.get("province"));
                userInfo.put("country", result.get("country"));
                userInfo.put("avatarUrl", result.get("avatarUrl"));
                //userInfo.put("unionId", result.get("unionId"));
                map.put("userInfo", userInfo);
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
