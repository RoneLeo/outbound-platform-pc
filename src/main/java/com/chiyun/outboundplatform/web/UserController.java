package com.chiyun.outboundplatform.web;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.chiyun.outboundplatform.common.ApiPageResult;
import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.common.MustLogin;
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Api(description = "用户管理")
@RestController
@RequestMapping(value = "/user", method = {RequestMethod.POST, RequestMethod.GET})
public class UserController {

    private static final String APPID = "wxa52fa9f12b8746d8";
    private static final String SECRET = "b2ed2f764ac0467733840008eda01954";
    private static final Map<String, String> sessionList = new HashMap<String, String>();
    @Resource
    private UserReposity userReposity;

    @ApiOperation(value = "登录")
    @RequestMapping("/login")
    public ApiResult<Object> login(@RequestParam @ApiParam(value = "用户名") String yhm,
                                   @RequestParam @ApiParam(value = "密码") String mm,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.print("用戶名：" + yhm);
        if (StringUtil.isNull(yhm) || StringUtil.isNull(mm)) {
            return ApiResult.FAILURE("用户名或密码不能为空");
        }
        mm = MD5Util.MD5(mm);
        UserEntity userEntity = userReposity.findByYhm(yhm);
        if (userEntity == null) {
            return ApiResult.FAILURE("用户名不存在");
        }
        //UserEntity userEntity = userReposity.findByYhmAndMm(yhm, mm);
        if (!mm.equals(userEntity.getMm())) {
            return ApiResult.FAILURE("密码错误");
        }
        HttpSession session = request.getSession();//创建session
        String sessionId = session.getId();//获取sessionid
        String userid = String.valueOf(userEntity.getId());//获取用户id
        //通过sessionid查看有没有其他登录的
        String key = getMapKey(sessionList, sessionId);
//        System.out.print("key:"+key);
//        System.out.print("userid:"+userid);
        if (key != null) {
            if (!key.equals(userid)) {
                sessionList.put(key, null);
                System.out.print("22222222:");
            }
        }
        String sessionValue = getMapValue(sessionList, userid);
        if (sessionValue == null || sessionValue != sessionId) {
            sessionList.put(userid, sessionId);
        } else if (sessionValue == sessionId) {
            //已登录
            return ApiResult.FAILURE("重复登录");
        }
        session.setAttribute("yhm", userEntity.getYhm());
        session.setAttribute("id", userEntity.getId());
        session.setAttribute("szxzqdm", userEntity.getSzxzqdm());
        session.setAttribute("js", userEntity.getJs());
        return ApiResult.SUCCESS(userEntity);
    }

    @ApiOperation(value = "微信小程序登录")
    @RequestMapping("/weLogin")
    public ApiResult<Object> weLogin(@ApiParam(value = "授权码") String sqm, String encryptedData, String iv, String code, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = weChatLogin(code, encryptedData, iv);
        if (map.get("status").toString() == "0") {
            return ApiResult.FAILURE(map.get("msg").toString());
        }
        HttpSession session=request.getSession();//创建session
        String sessionId = session.getId();//获取sessionid
        UserEntity userEntity = userReposity.findByOpenid(map.get("openid").toString());
        if (userEntity == null && StringUtil.isNull(sqm)) {
            return ApiResult.FAILURE("数据库中没有您的信息，请出示您的授权码");
        } else {
            //userEntity为空，sqm不为空
            if (!StringUtil.isNull(sqm)) {
            if(userEntity==null&&!StringUtil.isNull(sqm)){
                UserEntity userEntity1 = userReposity.findBySqm(sqm);
                if (userEntity1 == null) {
                    //数据库找不到邀请码信息
                    return ApiResult.FAILURE("数据库没有该授权码");
                } else if (userEntity1.getOpenid() != null) {
                    //邀请码查询出来的数据有人绑定
                }else if(userEntity1.getOpenid()!=null){
                    //授权码查询出来的数据有人绑定
                    return ApiResult.FAILURE("授权码已有人使用，请核实");
                }
                //使用授权码绑定帐号
                userEntity1.setOpenid(map.get("openid").toString());
               // userEntity1.setUnionid(map.get("unionid").toString());
                userEntity1.setSk(map.get("session_key").toString());
                userEntity1.setSkcjsj(new Date());
                userEntity1.setBdsj(new Date());
                UserEntity result = userReposity.save(userEntity1);
                if (result == null) {
                    return ApiResult.FAILURE("数据添加失败");
                }
                return ApiResult.SUCCESS(result);
            } else if(userEntity!=null){
                //通过openid在数据库中查询出了数据，登录成功
                String userid = String.valueOf(userEntity.getId());//获取用户id
                String sessionValue = getMapValue(sessionList, userid);
                if(sessionValue==null||sessionValue!=sessionId){
                    sessionList.put(userid,sessionId);
                }else if(sessionValue==sessionId){
                    //已登录
                    return ApiResult.FAILURE("重复登录");
                }
                session.setAttribute("yhm", userEntity.getYhm());
                session.setAttribute("id", userEntity.getId());
                session.setAttribute("szxzqdm", userEntity.getSzxzqdm());
                session.setAttribute("js", userEntity.getJs());
                    return ApiResult.SUCCESS(userEntity);
                }
            }
            //userEntity不为空，数据库有openid的信息，不需要授权码，登录成功
            //保存本次session_key
            userEntity.setSk(map.get("session_key").toString());
            userEntity.setSkcjsj(new Date());
            UserEntity result = userReposity.save(userEntity);
            if (result == null) {
                return ApiResult.FAILURE("数据添加失败");
            }
            return ApiResult.SUCCESS(result);
        }

    }
    @MustLogin(rolerequired = {1, 3})
    @ApiOperation(value = "添加用户")
    @RequestMapping("/add")
    public ApiResult<Object> add(HttpServletRequest request, UserEntity userEntity) throws Exception {
        //判断是否登录
        HttpSession session=request.getSession();
        int isLogin=isLogin(session);
        if(isLogin==0){
            return ApiResult.UNKNOWN();
        }else if(isLogin==2){
            return ApiResult.SEIZE();
        }else if(isLogin == 3){
            return ApiResult.FAILURE("其他情况");
        }
        //判断是否为管理员
        String js = String.valueOf(session.getAttribute("js"));
        if(!"1".equals(js)&&!"3".equals(js)){
            return ApiResult.FAILURE("没有权限添加用户");
        }
        //判断用户名是否重复
        UserEntity oldUserEntity = userReposity.findByYhm(userEntity.getYhm());
        if (oldUserEntity != null) {
            return ApiResult.FAILURE("该用户名已存在！");
        }
        /* 添加用户 */
        UserEntity result=null;
        userEntity.setCjsj(new Date());
        userEntity.setZt(0);
        //判断添加的用户为什么网站用户还是微信小程序用户
        if(0==userEntity.getLx()){
            //网站用户
            userEntity.setMm(MD5Util.MD5("666666"));
            result = userReposity.save(userEntity);
        }else if(1==userEntity.getLx()){
            //微信小程序用户,
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

    @MustLogin(rolerequired = {1, 3})
    @ApiOperation(value = "删除用户")
    @RequestMapping("/delete")
    public ApiResult<Object> delete(HttpServletRequest request,int id) throws Exception {
        //判断是否登录
        HttpSession session=request.getSession();
        int isLogin=isLogin(session);
        if(isLogin==0){
            return ApiResult.UNKNOWN();
        }else if(isLogin==2){
            return ApiResult.SEIZE();
        }else if(isLogin == 3){
            return ApiResult.FAILURE("其他情况");
        }
        //判断是否为管理员
        String js = String.valueOf(session.getAttribute("js"));
        if(!"1".equals(js)&&!"3".equals(js)){
            return ApiResult.FAILURE("没有权限删除用户");
        }
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

    @MustLogin(rolerequired = {1, 3})
    @ApiOperation(value="修改用户")
    @RequestMapping("/update")
    public ApiResult<Object> update(HttpServletRequest request,UserEntity userEntity) throws Exception {
        //判断是否登录
        HttpSession session=request.getSession();
        int isLogin=isLogin(session);
        if(isLogin==0){
            return ApiResult.UNKNOWN();
        }else if(isLogin==2){
            return ApiResult.SEIZE();
        }else if(isLogin == 3){
            return ApiResult.FAILURE("其他情况");
        }
        //判断权限搜索用户再修改
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


    @ApiOperation(value="修改密码")
    @RequestMapping("/changePassword")
    public ApiResult<Object> changePassword(HttpServletRequest request,int id, String mm) throws Exception {
        //判断是否登录
        HttpSession session=request.getSession();
        int isLogin=isLogin(session);
        if(isLogin==0){
            return ApiResult.UNKNOWN();
        }else if(isLogin==2){
            return ApiResult.SEIZE();
        }else if(isLogin == 3){
            return ApiResult.FAILURE("其他情况");
        }
        //查询是否有该用户
        UserEntity oldUserEntity = userReposity.findById(id);
        if (oldUserEntity == null) {
            return ApiResult.FAILURE("没有该用户的信息");
        }
        oldUserEntity.setMm(MD5Util.MD5(mm));
        UserEntity result = userReposity.save(oldUserEntity);
        if(result==null){
            return ApiResult.FAILURE("修改失败");
        }
        return ApiResult.SUCCESS(result);
    }


    @MustLogin(rolerequired = {1, 3})
    @ApiOperation(value="查询所有用户")
    @RequestMapping("/findAll")
    public ApiResult<Object> findAll(@RequestParam(required = true) @ApiParam(value = "类型（0-系统用户、1-小程序用户）") int lx,
                                     @RequestParam(required = false)@ApiParam(value = "状态（0-启用用户，1-注销用户,不传显示全部）") Integer zt,
                                     @RequestParam int page, @RequestParam int pagesize, HttpServletRequest request){
        //判断是否登录
        HttpSession session=request.getSession();
        int isLogin=isLogin(session);
        if(isLogin==0){
            return ApiResult.UNKNOWN();
        }else if(isLogin==2){
            return ApiResult.SEIZE();
        }else if(isLogin == 3){
            return ApiResult.FAILURE("其他情况");
        }
//        System.out.print("userid:"+request.getHeader("userid"));
//        System.out.print("sessionUserid:"+session.getAttribute("id"));
//        String userid = request.getHeader("userid");
//        String sessionUserid = String.valueOf(session.getAttribute("id"));
//        if(!userid.equals(sessionUserid)){
//            return ApiResult.FAILURE("已登录其他帐号！退出该帐号");
//        }
        Pageable pageable = PageRequest.of(page-1,pagesize,Sort.by(new Sort.Order(Sort.Direction.DESC, "cjsj")));
        Page<UserEntity> result;
        //判断用户权限
        String js = session.getAttribute("js").toString();
        List<Integer> ztList= new ArrayList<>();
        if(zt==null){
            ztList.add(0);
            ztList.add(1);
        }else{
            ztList.add(zt);
        }
        if("1".equals(js)){
            result = userReposity.findByZtInAndLx(ztList, lx, pageable);
        }else if("2".equals(js)){
            //List<Integer> a=new ArrayList<>();
            int jsArray[] = {2,4};
            result = userReposity.findByJsInAndZtInAndLxAndSzxzqdm(jsArray, ztList, lx, session.getAttribute("szxzqdm").toString(),pageable);
        }else {
            return ApiResult.FAILURE("没有权限查看用户");
        }
        return ApiPageResult.SUCCESS(result.getContent(), page, pagesize, result.getTotalElements(), result.getTotalPages());
    }

    @ApiOperation(value = "退出登录")
    @RequestMapping("/outLogin")
    public ApiResult<Object> outLogin(HttpServletRequest request,int id) throws Exception {
        //判断是否登录
        HttpSession session=request.getSession();
        int isLogin=isLogin(session);
        if(isLogin==0){
            return ApiResult.UNKNOWN();
        }else if(isLogin==2){
            return ApiResult.SEIZE();
        }else if(isLogin == 3){
            return ApiResult.FAILURE("其他情况");
        }
        UserEntity oldUserEntity = userReposity.findById(id);
        if (oldUserEntity == null) {
            return ApiResult.FAILURE("没有该用户的信息");
        }
        //清掉session
        sessionList.put(String.valueOf(id),null);
        return ApiResult.SUCCESS();
    }

    @MustLogin(rolerequired = {1, 3})
    @ApiOperation(value = "注销帐号")
    @RequestMapping("/cancelAccount")
    public ApiResult<Object> cancelAccount(HttpServletRequest request,int id) throws Exception {
        //判断是否登录
        HttpSession session=request.getSession();
        int isLogin=isLogin(session);
        if(isLogin==0){
            return ApiResult.UNKNOWN();
        }else if(isLogin==2){
            return ApiResult.SEIZE();
        }else if(isLogin == 3){
            return ApiResult.FAILURE("其他情况");
        }
        UserEntity oldUserEntity = userReposity.findById(id);
        if (oldUserEntity == null) {
            return ApiResult.FAILURE("没有该用户的信息");
        }
        oldUserEntity.setZt(1);
        //清掉session
        sessionList.put(String.valueOf(id),null);
        userReposity.save(oldUserEntity);
        return ApiResult.SUCCESS();
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

    public int isLogin(HttpSession session){
        String sessionId = session.getId();//获取sessionid
        String id= String.valueOf(session.getAttribute("id"));//用户id，map的键值
        String sessionValue = getMapValue(sessionList,id);
        if(sessionValue==null){
            return 0;//未登录
        }else if(sessionValue!=sessionId){
            return 2;//该帐号在其他地方登录
        }else if(sessionValue==sessionId){
            //已登录
            return 1;//已登录
        }
        return 3;//其他情况
    }
    public String getMapValue(Map<String, String> sessionList,String key){
        String result=null;
        Iterator<Map.Entry<String, String>> iterator = sessionList.entrySet().iterator();
        Map.Entry<String, String> entry;
        while(iterator.hasNext()){
            entry = iterator.next();
            if(key.equals(entry.getKey())){
                result = entry.getValue();
            }
        }
        System.out.print("result_value:"+result);
        return result;
    }
    public String getMapKey(Map<String, String> sessionList,String value){
        String result=null;
        Iterator<Map.Entry<String, String>> iterator = sessionList.entrySet().iterator();
        Map.Entry<String, String> entry;
        while(iterator.hasNext()){
            entry = iterator.next();
            if(value.equals(entry.getValue())){
                result = entry.getKey();
            }
        }
        System.out.print("result_key:"+result);
        return result;
    }
}
