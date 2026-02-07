package com.qhe.usercenter.interceptor;

import com.qhe.usercenter.Exception.BusinessException;
import com.qhe.usercenter.common.BusinessCode;
import com.qhe.usercenter.constant.UserConstants;
import com.qhe.usercenter.model.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局权限校验
 *
 * @author IamQhe
 */
@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
    /**
     * 管理员权限接口路径前缀
     */
    private static final String ADMIN_PATH_PRIFIX = "/admin";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        // 获取当前请求路径
        String requestUri = request.getRequestURI();
        log.info("拦截器生效，请求路径: " + requestUri);

        if (requestUri.contains(ADMIN_PATH_PRIFIX)) {
            log.info("访问管理员权限接口，进行权限校验...");
            Object attribute = request.getSession().getAttribute(UserConstants.USER_LOGIN_STATE);
            if (attribute == null) {
                throw new BusinessException(BusinessCode.BUSINESS_ERROR_NOT_LOGIN);
            }
            UserVO user = (UserVO) attribute;
            Integer userRole = user.getUserRole();
            if (UserConstants.USER_ROLE_ADMIN != userRole) {
                log.info("用户 {} 无管理员权限，访问被拒绝。", user.getUserAccount());
                throw new BusinessException(BusinessCode.BUSINESS_ERROR_NO_AUTH);
            }
        }
        return true;
    }

}
