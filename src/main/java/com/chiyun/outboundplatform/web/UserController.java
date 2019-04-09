package com.chiyun.outboundplatform.web;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.chiyun.outboundplatform.common.*;
import com.chiyun.outboundplatform.entity.UserEntity;
import com.chiyun.outboundplatform.repository.UserReposity;
import com.chiyun.outboundplatform.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
//import net.sf.json.JSONException;
//import net.sf.json.JSONObject;
//import org.apache.commons.lang.StringUtils;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;


@Api(description = "用户管理")
@RestController
@RequestMapping(value = "/user", method = {RequestMethod.POST, RequestMethod.GET})
public class UserController {

    private static final String APPID = "wxa52fa9f12b8746d8";
    private static final String SECRET = "b2ed2f764ac0467733840008eda01954";
    @Resource
    private UserReposity userReposity;
    @Resource
    private LogController logController;

    @ApiOperation(value = "登录")
    @RequestMapping("/login")
    @ControllerLog(description = "后台管理系统登录")
    public ApiResult<Object> login(@RequestParam @ApiParam(value = "用户名") String yhm,
                                   @RequestParam @ApiParam(value = "密码") String mm,
                                   HttpServletRequest request) throws Exception {
        System.out.print("用戶名：" + yhm);
        if (StringUtil.isNull(yhm) || StringUtil.isNull(mm)) {
            return ApiResult.FAILURE("用户名或密码不能为空");
        }
        mm = MD5Util.MD5(mm);
        UserEntity userEntity = userReposity.findByYhm(yhm);
        if (userEntity == null) {
            return ApiResult.FAILURE("用户名不存在");
        }
        if (userEntity.getZt() == 1) {
            return ApiResult.FAILURE("该帐号已注销，请联系管理员");
        }
        //UserEntity userEntity = userReposity.findByYhmAndMm(yhm, mm);
        if (!mm.equals(userEntity.getMm())) {
            return ApiResult.FAILURE("密码错误");
        }
        HttpSession session = request.getSession();//创建session
        String sessionId = session.getId();//获取sessionid
        String userid = String.valueOf(userEntity.getId());//获取用户id
        //通过sessionid查看有没有其他登录的
        String key = SessionUtil.getMapKey(sessionId);
//        System.out.print("key:"+key);
//        System.out.print("userid:"+userid);
        if (key != null) {
            if (!key.equals(userid)) {
                SessionUtil.put(key, null);
                System.out.print("22222222:");
            }
        }
   /*     String sessionValue = SessionUtil.getMapValue(userid);
        if (sessionValue == null || !sessionValue.equals(sessionId)) {
            SessionUtil.put(userid, sessionId);
        } else if (sessionValue.equals(sessionId)) {
            //已登录
            return ApiResult.REPEATLOGIN();
        }*/
        SessionUtil.put(userid, sessionId);
        //给session存数据
        session.setAttribute("yhm", userEntity.getYhm());
        session.setAttribute("id", userEntity.getId());
        session.setAttribute("szxzqdm", userEntity.getSzxzqdm());
        session.setAttribute("js", userEntity.getJs());
        session.setAttribute("mz", userEntity.getMz());
        //记录日志
//        logController.add(String.valueOf(session.getAttribute("mz")), "网站登录");
        return ApiResult.SUCCESS(userEntity);
    }

    @ApiOperation(value = "微信小程序登录")
    @RequestMapping("/weLogin")
    @ControllerLog(description = "微信小程序登录")
    public ApiResult<Object> weLogin(@ApiParam(value = "授权码") String sqm, String encryptedData, String iv, String code, HttpServletRequest request) throws Exception {
        Map<String, Object> map = weChatLogin(code, encryptedData, iv);
        if (map.get("status").toString() == "0") {
            return ApiResult.FAILURE(map.get("msg").toString());
        }
        HttpSession session = request.getSession();//创建session
        String sessionId = session.getId();//获取sessionid
        UserEntity userEntity = userReposity.findByOpenid(map.get("openid").toString());
        Map<String, Object> result = new HashMap<>();
        if (userEntity == null) {
            //数据库中没有openid数据
            if (StringUtil.isNull(sqm)) {
                //授权码为空
                return ApiResult.FAILURE("数据库中没有您的信息，请出示您的授权码");
            } else {
                //授权码不为空
                UserEntity userEntity1 = userReposity.findBySqm(sqm);
                if (userEntity1 == null) {
                    //数据库找不到邀请码信息
                    return ApiResult.FAILURE("数据库没有该授权码");
                } else if (userEntity1.getOpenid() != null) {
                    //授权码查询出来的数据有人绑定
                    return ApiResult.FAILURE("授权码已有人使用，请核实");
                }
                if (userEntity1.getZt() == 1) {
                    return ApiResult.FAILURE("该帐号已注销，请联系管理员");
                }
                //使用授权码绑定帐号
                userEntity1.setOpenid(map.get("openid").toString());
                // userEntity1.setUnionid(map.get("unionid").toString());
                userEntity1.setSk(map.get("session_key").toString());
                userEntity1.setSkcjsj(new Date());
                userEntity1.setBdsj(new Date());
                UserEntity result1 = userReposity.save(userEntity1);
                if (result1 == null) {
                    return ApiResult.FAILURE("数据添加失败");
                }
                SessionUtil.put(String.valueOf(result1.getId()), sessionId);
                session.setAttribute("id", result1.getId());
                session.setAttribute("mz", result1.getMz());
                session.setAttribute("szxzqdm", result1.getSzxzqdm());
                result.put("userEntity", result1);
            }
        } else {
            if (userEntity.getZt() == 1) {
                return ApiResult.FAILURE("该帐号已注销，请联系管理员");
            }
            SessionUtil.put(String.valueOf(userEntity.getId()), sessionId);
            session.setAttribute("id", userEntity.getId());
            session.setAttribute("mz", userEntity.getMz());
            session.setAttribute("szxzqdm", userEntity.getSzxzqdm());
            result.put("userInfo", userEntity);
        }
        result.put("sessionId", sessionId);
        return ApiResult.SUCCESS(result);
    }


    @MustLogin(rolerequired = {1, 3})
    @ApiOperation(value = "添加用户")
    @RequestMapping("/add")
    @ControllerLog(description = "添加用户")
    public ApiResult<Object> add(UserEntity userEntity) throws Exception {
        //判断是否登录
        HttpSession session = SessionHelper.getSession();
        ApiResult<Object> isLogin = SessionUtil.isLogin(session);
        if (isLogin.getResCode() < 200) return isLogin;
        //判断是否为管理员
        String js = String.valueOf(session.getAttribute("js"));
        if (!"1".equals(js) && !"3".equals(js)) {
            return ApiResult.FAILURE("没有权限添加用户");
        }
        if (userEntity.getYhm() == null) {
            return ApiResult.FAILURE("用户名为空");
        }
        //判断用户名是否重复
        UserEntity oldUserEntity = userReposity.findByYhm(userEntity.getYhm());
        if (oldUserEntity != null) {
            return ApiResult.FAILURE("该用户名已存在！");
        }
        /* 添加用户 */
        userEntity.setCjsj(new Date());
        userEntity.setZt(0);
        userEntity.setMm(MD5Util.MD5("666666"));
        userEntity.setZje(new BigDecimal("0"));
        UserEntity userEntity1 = userReposity.save(userEntity);
        String sqm = CodeUtil.toSerialCode(userEntity1.getId());
        userEntity1.setSqm(sqm);
        UserEntity result = userReposity.save(userEntity1);
        if (result == null) {
            return ApiResult.FAILURE("授权码添加失败");
        }
        return ApiResult.SUCCESS(result);
    }

    @MustLogin(rolerequired = {1, 3})
    @ApiOperation(value = "删除用户")
    @RequestMapping("/delete")
    @ControllerLog(description = "删除用户")
    public ApiResult<Object> delete(int id) throws Exception {
        //判断是否登录
        HttpSession session = SessionHelper.getSession();
        ApiResult<Object> isLogin = SessionUtil.isLogin(session);
        if (isLogin.getResCode() < 200) return isLogin;
        /* 删除用户 */
        UserEntity oldUserEntity = userReposity.findById(id);
        if (oldUserEntity == null) {
            return ApiResult.FAILURE("没有该用户的信息");
        }
        int result = userReposity.deleteById(id);
        if (result == 0) {
            return ApiResult.FAILURE("删除失败");
        }
        return ApiResult.SUCCESS("删除成功");
    }

    @ApiOperation(value = "修改用户")
    @RequestMapping("/update")
    @ControllerLog(description = "修改用户")
    public ApiResult<Object> update(UserEntity userEntity) throws Exception {
        //判断是否登录
        HttpSession session = SessionHelper.getSession();
        ApiResult<Object> isLogin = SessionUtil.isLogin(session);
        if (isLogin.getResCode() < 200) return isLogin;
        //判断权限搜索用户再修改
        /* 修改用户 */
        UserEntity oldUserEntity = userReposity.findById(userEntity.getId());
        if (oldUserEntity == null) {
            return ApiResult.FAILURE("没有该用户的信息");
        }
        userEntity.setMm(oldUserEntity.getMm());
        userEntity.setCjsj(oldUserEntity.getCjsj());
        UserEntity result = userReposity.save(userEntity);
        if (result == null) {
            return ApiResult.FAILURE("修改失败");
        }
        return ApiResult.SUCCESS(result);
    }


    @ApiOperation(value = "修改密码")
    @RequestMapping("/changePassword")
    @ControllerLog(description = "修改密码")
    public ApiResult<Object> changePassword(int id, String mm) throws Exception {
        //判断是否登录
        HttpSession session = SessionHelper.getSession();
        ApiResult<Object> isLogin = SessionUtil.isLogin(session);
        if (isLogin.getResCode() < 200) return isLogin;
        //查询是否有该用户
        UserEntity oldUserEntity = userReposity.findById(id);
        if (oldUserEntity == null) {
            return ApiResult.FAILURE("没有该用户的信息");
        }
        oldUserEntity.setMm(MD5Util.MD5(mm));
        UserEntity result = userReposity.save(oldUserEntity);
        if (result == null) {
            return ApiResult.FAILURE("修改失败");
        }
        return ApiResult.SUCCESS(result);
    }


    @MustLogin(rolerequired = {1, 3})
    @ApiOperation(value = "查询所有用户")
    @RequestMapping("/findAll")
    public ApiResult<Object> findAll(@RequestParam(required = false) @ApiParam(value = "状态（0-启用用户，1-注销用户,不传显示全部）") Integer zt,
                                     @RequestParam int page, @RequestParam int pagesize) throws Exception {
        //判断是否登录
        HttpSession session = SessionHelper.getSession();
        ApiResult<Object> isLogin = SessionUtil.isLogin(session);
        if (isLogin.getResCode() < 200) return isLogin;
        //
        Pageable pageable = PageRequest.of(page - 1, pagesize, Sort.by(new Sort.Order(Sort.Direction.DESC, "cjsj")));
        Page<UserEntity> result;
        //判断用户权限
        String js = session.getAttribute("js").toString();
        List<Integer> ztList = new ArrayList<>();
        if (zt == null) {
            ztList.add(0);
            ztList.add(1);
        } else {
            ztList.add(zt);
        }
        if ("1".equals(js)) {
            result = userReposity.findByZtIn(ztList, pageable);
        } else if ("3".equals(js)) {
            //List<Integer> a=new ArrayList<>();
            int jsArray[] = {3, 4};
            result = userReposity.findByJsInAndZtInAndSzxzqdm(jsArray, ztList, session.getAttribute("szxzqdm").toString(), pageable);
        } else {
            return ApiResult.FAILURE("没有权限查看用户");
        }
        return ApiPageResult.SUCCESS(result.getContent(), page, pagesize, result.getTotalElements(), result.getTotalPages());
    }

    @ApiOperation(value = "通过id查询用户")
    @RequestMapping("/findById")
    public ApiResult<Object> findById(int id) throws Exception {
        //判断是否登录
        HttpSession session = SessionHelper.getSession();
        ApiResult<Object> isLogin = SessionUtil.isLogin(session);
        if (isLogin.getResCode() < 200) return isLogin;
        //通过id查询用户
        UserEntity oldUserEntity = userReposity.findById(id);
        if (oldUserEntity == null) {
            return ApiResult.FAILURE("没有该用户的信息");
        }
        return ApiResult.SUCCESS(oldUserEntity);
    }

    @MustLogin(rolerequired = {3})
    @ApiOperation(value = "区域主管通过所在行政区查询业务员等用户")
    @RequestMapping("/fingByAreacodeAndName")
    public ApiResult<Object> fingByAreacodeAndName(@RequestParam(required = false) @ApiParam(value = "用户真实名字") String mz) throws Exception {
        //判断是否登录
        HttpSession session = SessionHelper.getSession();
        ApiResult<Object> isLogin = SessionUtil.isLogin(session);
        if (isLogin.getResCode() < 200) return isLogin;
        /* 查询用户 */
       String areaCode = String.valueOf(session.getAttribute("szxzqdm"));
       String name = OtherUtils.nullReplace(mz);
        int jsArray[] = {3, 4};
        List<UserEntity> userEntityList = userReposity.findByJsInAndMzLikeAndSzxzqdm(jsArray, name, areaCode);
        return ApiResult.SUCCESS(userEntityList);
    }

    @ApiOperation(value = "退出登录")
    @RequestMapping("/outLogin")
    @ControllerLog(description = "退出登录")
    public ApiResult<Object> outLogin(int id) throws Exception {
        UserEntity oldUserEntity = userReposity.findById(id);
        if (oldUserEntity == null) {
            return ApiResult.FAILURE("没有该用户的信息");
        }
        //清掉session
        SessionUtil.put(String.valueOf(id), null);
        return ApiResult.SUCCESS();
    }

    @MustLogin(rolerequired = {1, 3})
    @ApiOperation(value = "注销帐号")
    @RequestMapping("/cancelAccount")
    @ControllerLog(description = "注销帐号")
    public ApiResult<Object> cancelAccount(int id,
                                           @RequestParam @ApiParam(value = "类型（0-启动用户、1-注销用户）", required = true) int zt) throws Exception {
        //判断是否登录
        HttpSession session = SessionHelper.getSession();
        ApiResult<Object> isLogin = SessionUtil.isLogin(session);
        if (isLogin.getResCode() < 200) return isLogin;
        UserEntity oldUserEntity = userReposity.findById(id);
        if (oldUserEntity == null) {
            return ApiResult.FAILURE("没有该用户的信息");
        }
        oldUserEntity.setZt(zt);
        //清掉session
        SessionUtil.put(String.valueOf(id), null);
        userReposity.save(oldUserEntity);
        return ApiResult.SUCCESS();
    }

    public Map<String, Object> weChatLogin(String code, String encryptedData, String iv) {
        Map<String, Object> map = new HashMap<String, Object>();
        String status = "1";
        String msg = "ok";
        String WX_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        try {
//            String code = request.getParameter("code");
            if (StringUtil.isNull(code)) {
                status = "0";//失败状态
                msg = "code为空";
            } else {
                String requestUrl = WX_URL.replace("APPID", APPID).
                        replace("SECRET", SECRET).replace("JSCODE", code);
                //logger.info(requestUrl);
                // 发起GET请求获取凭证
                JSONObject jsonObject = HttpUtil.httpRequest(requestUrl, null);
                if (jsonObject != null) {
                    try {
                        map.put("openid", jsonObject.getString("openid"));
                        map.put("session_key", jsonObject.getString("session_key"));
                        Map<String, Object> map1 = decrypt(encryptedData, iv, jsonObject.getString("session_key"));
                        map.put("unionID", map1.get("unionId"));
                    } catch (JSONException e) {
                        // 获取token失败
                        status = "0";
                        msg = "code无效";
                    }
                } else {
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

    public Map<String, Object> decrypt(String encryptedData, String iv, String session_key) {
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

    @ApiOperation(value = "test")
    @RequestMapping("/test")
    public String findAll(long id) {
        String result = CodeUtil.toSerialCode(id);
        return result;
    }



}
