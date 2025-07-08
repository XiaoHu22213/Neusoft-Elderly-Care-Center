package com.neuedu.core;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neuedu.util.JwtUtil;
import com.neuedu.vo.ResultVo;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

//@Component
//@Order(1)
//@ConfigurationProperties(prefix = "filter.white")
//@Setter
public class TokenFilter implements Filter {
    private List<String> urls;
    @Resource(name = "vredis")
    RedisTemplate<String, Object> redisTemplate;
    @Resource
    ObjectMapper objectMapper;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            String token = request.getHeader("token");
            String uri = request.getRequestURI();
            if("OPTIONS".equals(request.getMethod())) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            for (String url : urls) {
                if(pathMatcher.match(url, uri)){
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }
            try {
                long id = JwtUtil.decode(token);
                String key = "user::" + id + "::resource";
                if(!redisTemplate.hasKey(key)){
                    error(response, ResultVo.unlogin("登录超时"));
                }
                String resources = String.valueOf(redisTemplate.opsForValue().get(key));
                List<String> list = JSONObject.parseArray(resources, String.class);
                for(String url : list){
                    if(pathMatcher.match(url, uri)){
                        filterChain.doFilter(servletRequest, servletResponse);
                        return;
                    }
                }
                error(response, ResultVo.forbid());
            } catch (Exception ex) {
                ex.printStackTrace();
                error(response, ResultVo.unlogin(ex.getMessage()));
            }
        }
    }
    public void error(HttpServletResponse response, ResultVo resultVo) throws IOException {
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        response.setHeader("Access-Control-Allow-Origin","http://127.0.0.1");
        response.getWriter().write(mapper.writeValueAsString(resultVo));
    }
}
