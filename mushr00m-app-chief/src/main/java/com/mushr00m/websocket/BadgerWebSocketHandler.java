package com.mushr00m.websocket;

import com.alibaba.fastjson.JSON;
import com.mushr00m.model.BadgerMSG;
import com.mushr00m.model.SysUser;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BadgerWebSocketHandler extends TextWebSocketHandler{

    //创建集合收集参与某个竞拍室的所有用户
    public Map<String,WebSocketSession> tbRoomClients = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //先过拦截器，再过处理器，因此handler的ws-session中存放了之前存于interceptor map内的数据
        SysUser user = (SysUser) session.getAttributes().get("user");
        System.out.println("与用户"+user.getAccount()+"的连接已建立");
        tbRoomClients.put(session.getId(),session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //获取客户端发送过来的信息
        SysUser user = (SysUser) session.getAttributes().get("user");
        String content = message.getPayload();
        Double price = Double.parseDouble(content);

        BadgerMSG msg = new BadgerMSG();
        msg.setSender(user.getAccount());
        msg.setPrice(price);
        msg.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        TextMessage textMessage = new TextMessage(JSON.toJSONString(msg));
        tbRoomClients.entrySet().stream().forEach(o->{
            try {
                o.getValue().sendMessage(textMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //连接断开时关闭连接
        SysUser user = (SysUser) session.getAttributes().get("user");
        System.out.println("已断开与用户"+user.getAccount()+"的连接");
        tbRoomClients.remove(session.getId());
    }
}
