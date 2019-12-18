package com.czk.shiro.realm;


import com.czk.dao.UserDao;
import com.czk.vo.User;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: Created by ChenZK
 * @Create: 2019/12/16 14:48
 */
public class CustomRealm extends AuthorizingRealm {

    @Resource
    private UserDao userDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName = (String) principalCollection.getPrimaryPrincipal();
        //获取角色权限
        Set<String> roles = getRolesByUserName(userName);
        Set<String> permissions = getPermissionsByUserName(userName);
        SimpleAuthorizationInfo simpleAuthenticationInfo = new SimpleAuthorizationInfo();
        simpleAuthenticationInfo.setStringPermissions(permissions);
        simpleAuthenticationInfo.setRoles(roles);

        return simpleAuthenticationInfo;
    }

    private Set<String> getPermissionsByUserName(String userName) {
        List<String> roles = userDao.queryRoleByUserName(userName);
        Set<String> sets = new HashSet<String>(roles);
        return sets;
    }

    private Set<String> getRolesByUserName(String userName) {
        List<String> roles = userDao.queryRoleByUserName(userName);
        Set<String> sets = new HashSet<String>(roles);
        return sets;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //从主体传过来的认证信息中，获取用户名
        String userName = (String) authenticationToken.getPrincipal();
        //通过用户名到数据库中获取凭证
        String password = getPasswordByUserName(userName);
        if (StringUtils.isBlank(password)){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo
                (userName, password, "customRealm");
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(userName));
        return authenticationInfo;
    }

    private String getPasswordByUserName(String userName) {
        User user = userDao.getUserByUserName(userName);
        if (StringUtils.isBlank(user.getPassword())) {
            return null;
        }
        return user.getPassword();
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123456", "test");
        System.out.println(md5Hash.toString());
    }
}
