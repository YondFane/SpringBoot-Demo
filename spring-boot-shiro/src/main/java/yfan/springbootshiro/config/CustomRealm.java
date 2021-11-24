package yfan.springbootshiro.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


/**
 * 自定义
 *
 * @Author YFAN
 * @Date 2021/11/22
 */
@Slf4j
public class CustomRealm extends AuthorizingRealm {

    /**
     * USERNAME_PASSWORD
     * 模拟用户密码
     */
    private static HashMap<String, String> USERNAME_PASSWORD = new HashMap<>();

    static {
        USERNAME_PASSWORD.put("JACK", "JACK123456");
        USERNAME_PASSWORD.put("TOM", "TOM_!@#");
        USERNAME_PASSWORD.put("MEIKO", "MEIKOABC");
    }

    /**
     * 权限认证
     * 配合权限注解使用
     *
     * @return org.apache.shiro.authz.AuthorizationInfo
     * @Author YFAN
     * @Date 2021/11/22
     * @params [principals]
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> stringSet = new HashSet<>();
        stringSet.add("user:info");
        info.setStringPermissions(stringSet);
        return info;
    }

    /**
     * 权限认证
     *
     * @return org.apache.shiro.authc.AuthenticationInfo
     * @Author YFAN
     * @Date 2021/11/22
     * @params [authenticationToken]
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("-------身份认证方法--------");
        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());
        // 判断用户是否存在
        if (!USERNAME_PASSWORD.containsKey(username)) {
            throw new UnknownAccountException("未知用户");
        }
        // 根据用户名从数据库获取密码
        String correctPassword = USERNAME_PASSWORD.get(username);
        log.info("{}的正确密码：{}", username, correctPassword);
        if (username == null) {
            throw new AccountException("用户名不正确");
        } else if (!password.equals(correctPassword)) {
            throw new IncorrectCredentialsException("密码不正确");
        }
        return new SimpleAuthenticationInfo(username, correctPassword, getName());
    }
}
