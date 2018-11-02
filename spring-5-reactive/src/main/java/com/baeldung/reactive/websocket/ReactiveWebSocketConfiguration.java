package com.baeldung.reactive.websocket;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.resource.PathResourceResolver;
import org.springframework.web.reactive.resource.ResourceWebHandler;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

@Configuration
public class ReactiveWebSocketConfiguration {

    @Autowired
    @Qualifier("ReactiveWebSocketHandler")
    private WebSocketHandler webSocketHandler;

    @Bean
    public HandlerMapping fileHandlerMapping() {
        ResourceWebHandler filesHandler = new ResourceWebHandler();
        filesHandler.setLocations(Arrays.asList(new ClassPathResource("files/")));
        filesHandler.setResourceResolvers(Arrays.asList(new PathResourceResolver()));
        try { filesHandler.afterPropertiesSet(); } catch(Exception ex) { ex.printStackTrace(); }
        ResourceWebHandler staticHandler = new ResourceWebHandler();
        staticHandler.setLocations(Arrays.asList(new ClassPathResource("static/")));
        staticHandler.setResourceResolvers(Arrays.asList(new PathResourceResolver()));
        try { staticHandler.afterPropertiesSet(); } catch(Exception ex) { ex.printStackTrace(); }

        Map<String, ResourceWebHandler> resMap = new HashMap<>();
        //resMap.put("/webjars/**", resourceHandler);
        resMap.put("/files/**", filesHandler);
        resMap.put("/static/**", staticHandler);

        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
        handlerMapping.setOrder(-1);
        handlerMapping.setUrlMap(resMap);

        return handlerMapping;
    }

    @Bean
    public HandlerMapping webSocketHandlerMapping() {
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/event-emitter", webSocketHandler);

        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
        handlerMapping.setOrder(1);
        handlerMapping.setUrlMap(map);
        return handlerMapping;
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}