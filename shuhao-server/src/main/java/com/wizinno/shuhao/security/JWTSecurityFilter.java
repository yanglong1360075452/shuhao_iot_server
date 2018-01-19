package com.wizinno.shuhao.security;
import com.google.gson.Gson;
import com.wizinno.shuhao.app.utils.JwtUtil;
import com.wizinno.shuhao.app.utils.Path;
import com.wizinno.shuhao.data.ResponseVO;
import com.wizinno.shuhao.user.data.UserDto;
import com.wizinno.shuhao.exception.CustomExceptionCode;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class JWTSecurityFilter implements Filter {
    private static Logger log = LoggerFactory.getLogger(JWTSecurityFilter.class);
    private Gson gson = new Gson();
    @Autowired
    private JwtUtil jwtUtil;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        //获取请求地址加参数
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String scheme=httpRequest.getScheme();
        String servername=httpRequest.getServerName();
        String method = httpRequest.getMethod();
        String requestURI = httpRequest.getServletPath();
        String parameter=httpRequest.getQueryString();
        String allUrl = scheme+"://"+ servername+
                requestURI  +"?"+ parameter;
        //获取响应
        HttpServletResponse httpResponse=(HttpServletResponse)response;
        ResponseVO responseVO = null;
        UserDto user = null;
        if (requestURI.indexOf("/api/app/user/register") == -1 && requestURI.indexOf("/api/app/user/login") == -1
                &&requestURI.indexOf("/api/app/user/getAuthCode") == -1&&requestURI.indexOf("/api/app/user/resetPassword") == -1
                &&requestURI.indexOf("/api/app/user/getLatestAPP") == -1&&requestURI.indexOf("/api/app/user/saveAPP") == -1
                &&requestURI.indexOf("/api/manage/logs/getLogList") == -1) {
            String auth = httpRequest.getHeader("Authorization");
            if (auth == null) {
                responseVO = new ResponseVO(CustomExceptionCode.AuthFailed, "认证失败");
                String responseMessage="请求头信息为空";
                int statue=httpResponse.getStatus();
                Path.pathUrl(allUrl,requestURI,user,httpRequest,parameter,statue,responseMessage);
                response(response,responseVO);
            } else {
                try {
                    Claims claims = jwtUtil.parseJWT(auth);
                    if (claims != null) {
                        String subject = claims.getSubject();
                        Gson gson = new Gson();
                        user = gson.fromJson(subject, UserDto.class);
                        request.setAttribute("userId", user.getUserId());
                    }
                } catch (Exception e) {
                    responseVO = new ResponseVO(CustomExceptionCode.AuthFailed, "认证失败");
                    String responseMessage="请求头信息不正确";
                    int statue=httpResponse.getStatus();
                    Path.pathUrl(allUrl,requestURI,user,httpRequest,parameter,statue,responseMessage);
                    response(response,responseVO);
                }
                ResponseWrapper wrapper = new ResponseWrapper((HttpServletResponse) response);
                chain.doFilter(request, wrapper);
                byte[] result = wrapper.getResponseData();
                response.getOutputStream().write(result);
                System.out.println("测试~~~~");
                System.out.println(new String(result,"UTF-8"));
                String responseMessage=new String(result,"UTF-8");
                int statue=httpResponse.getStatus();
                Path.pathUrl(allUrl,requestURI,user,httpRequest,parameter,statue,responseMessage);
                return;
            }
        }else{
            ResponseWrapper wrapper = new ResponseWrapper((HttpServletResponse) response);
            chain.doFilter(request, wrapper);
            byte[] result = wrapper.getResponseData();
            response.getOutputStream().write(result);
            System.out.println("测试~~~~");
            System.out.println(new String(result,"UTF-8"));
            String responseMessage=new String(result,"UTF-8");
            int statue=httpResponse.getStatus();
            Path.pathUrl(allUrl,requestURI,user,httpRequest,parameter,statue,responseMessage);
            return;
        }

    }

    private void response(ServletResponse response, ResponseVO responseVO) {
        try {
            response.getOutputStream().write(gson.toJson(responseVO).toString().getBytes("UTF-8"));
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void destroy() {
    }
}
