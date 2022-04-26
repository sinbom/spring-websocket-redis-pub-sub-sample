package com.example.websocket.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class MyHandShakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("이곳에서는 웹소켓 연결을 위한 HTTP 핸드쉐이크를 처리한다");
        System.out.println("하지만 Authorization Header값에 접근할 수 없다");
        System.out.println("웹소켓 CONNECT에 접근할 수 없다.");
        System.out.println("JWT토큰을 query string으로 전달해야하지만");
        System.out.println("url은 로그에 남기 때문에 웹소켓을 위한 짧은 유효기간의 토큰을 발급하는 과정이 필요");
        System.out.println("핸드 쉐이킹이 끝난후 웹소켓 인터셉터인 ChannelInterceptor에서는 Header값에 우회하여 접근할 수 있지만");
        System.out.println("핸드 쉐이킹 과정에서 인증이 처리되지 않은 경우 요청을 거절하는 것이 좋아보인다.");
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }

}
