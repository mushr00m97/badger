package com.mushr00m.websocket;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.mushr00m.model.SysUser;
import com.mushr00m.utils.BaseController;
import com.mushr00m.utils.JwtUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class BadgerHandShakeInterceptor extends BaseController implements HandshakeInterceptor {


    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
                                   WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {

        //握手阶段注入token内获取的用户信息
        ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) serverHttpRequest;
        HttpServletRequest req = servletServerHttpRequest.getServletRequest();
        String token = super.getCookieVal(req,"token");
        SysUser sysUser = JwtUtils.getObject(token,SysUser.class);
        map.put("user",sysUser);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        System.out.println("握手建立");
    }
}
