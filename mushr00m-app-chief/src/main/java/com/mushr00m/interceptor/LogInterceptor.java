package com.mushr00m.interceptor;

import com.mushr00m.model.SysUser;
import com.mushr00m.utils.BaseController;
import com.mushr00m.utils.JwtUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogInterceptor extends BaseController implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //moon-token-认证中心-接口【后期替换为mushr00m-token】
        String getToken = "http://120.55.53.172:7000/moon/SysUser/getToken";
        String loginAPI = "http://120.55.53.172:7000/login";

        /*查看子系统内的token*/
        //查看url后是否传token
        String urlToken = req.getParameter("token") == null ? "" : req.getParameter("token").trim();
        //查看游览器内部cookie是否存了token
        String broswerToken = super.getCookieVal(req,"token");
        //从url,cookie中选择一个作为token
        String Token = urlToken.equals("") ? broswerToken : urlToken ;


        /**对Token进行处理*/
        //当子系统这边没有token
        if(Token.equals("")){
            //先访问登录服务器，看看是否登录过,跳转至远端登陆服务器
            httpServletResponse.sendRedirect(getToken+"?callbackurl="+req.getRequestURL().toString());
            return false;
        }

        //当子系统内有token
        SysUser sysUser = JwtUtils.getObject(Token,SysUser.class);
        //子系统这边持有与远端登陆服务器相同的盐值加密工具进行解密，判断子系统持有的token是否正确
        if(sysUser == null){
            httpServletResponse.sendRedirect(loginAPI);
            return false;
        }

        super.setCookieVal(httpServletResponse,"token",Token);
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
