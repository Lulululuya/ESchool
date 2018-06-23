package org.jit.sose.eschool.service;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.jit.sose.eschool.config.ServerConfiguration;
import org.springframework.http.converter.StringHttpMessageConverter;


/**
 * Author: chenmin
 * Date: 2018/6/23
 * GITHUB: https://github.com/JKchenmin/
 * Description: 处理用户登录注册等一系列的网络请求的接口
 */
@Rest(rootUrl = ServerConfiguration.BASEURL, converters = {StringHttpMessageConverter.class })
public interface UserService {

    //登录的网络请求
    @Post(ServerConfiguration.LOGIN_DO + "?username={username}&pwd={pwd}")
    @Accept(MediaType.APPLICATION_JSON)
    String login(String username,String pwd);


    //注册的网络请求
    @Post(ServerConfiguration.REGISTER_DO + "?username={username}&pwd={pwd}")
    @Accept(MediaType.APPLICATION_JSON)
    String register(String username,String pwd);
}
