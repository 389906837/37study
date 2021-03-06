/*
 * Copyright (C) 2016 37cang All rights reserved
 * Author: zhouhong
 * Date: 2016年3月2日
 * Description:SsoSubjectFactory.java 
 */
package com.cloud.cang.security.core;

import com.cloud.cang.security.vo.SSOToken;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;


/**
 * @author zhouhong
 * @version 1.0
 */
public class SsoSubjectFactory extends DefaultWebSubjectFactory {

	@Override
    public Subject createSubject(SubjectContext context) {

        //the authenticated flag is only set by the SecurityManager after a successful authentication attempt.
        boolean authenticated = context.isAuthenticated();

        //although the SecurityManager 'sees' the submission as a successful authentication, in reality, the
        //login might have been just a CAS rememberMe login.  If so, set the authenticated flag appropriately:
        if (authenticated) {

            AuthenticationToken token = context.getAuthenticationToken();

            if (token != null && token instanceof SSOToken) {
                SSOToken casToken = (SSOToken) token;
                // set the authenticated flag of the context to true only if the CAS subject is not in a remember me mode
                if (casToken.isRememberMe()) {
                    context.setAuthenticated(false);
                }
            }
        }

        return super.createSubject(context);
    }
}
