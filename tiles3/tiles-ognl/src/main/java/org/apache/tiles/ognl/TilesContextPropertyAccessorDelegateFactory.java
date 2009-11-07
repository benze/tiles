/*
 * $Id$
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.tiles.ognl;

import java.util.Map;

import ognl.PropertyAccessor;

import org.apache.tiles.request.ApplicationContext;
import org.apache.tiles.request.Request;
import org.apache.tiles.util.CombinedBeanInfo;

/**
 * Decides the appropriate {@link PropertyAccessor} for the given property name
 * and {@link Request}.
 *
 * @version $Rev$ $Date$
 * @since 2.2.0
 */
public class TilesContextPropertyAccessorDelegateFactory implements
        PropertyAccessorDelegateFactory<Request> {

    /**
     * The plain object property accessor, to be used directly for
     * {@link Request}.
     */
    private PropertyAccessor objectPropertyAccessor;

    /**
     * The application context property accessor.
     */
    private PropertyAccessor applicationContextPropertyAccessor;

    /**
     * The request scope property accessor.
     */
    private PropertyAccessor requestScopePropertyAccessor;

    /**
     * The session scope property accessor.
     */
    private PropertyAccessor sessionScopePropertyAccessor;

    /**
     * The application scope property accessor.
     */
    private PropertyAccessor applicationScopePropertyAccessor;

    /**
     * The bean info of {@link Request} and
     * {@link org.apache.tiles.request.ApplicationContext}.
     */
    private CombinedBeanInfo beanInfo;

    /**
     * Constructor.
     *
     * @param objectPropertyAccessor The plain object property accessor, to be
     * used directly for {@link Request}.
     * @param applicationContextPropertyAccessor The application context
     * property accessor.
     * @param requestScopePropertyAccessor The request scope property accessor.
     * @param sessionScopePropertyAccessor The session scope property accessor.
     * @param applicationScopePropertyAccessor The application scope property
     * accessor.
     * @since 2.2.0
     */
    public TilesContextPropertyAccessorDelegateFactory(
            PropertyAccessor objectPropertyAccessor,
            PropertyAccessor applicationContextPropertyAccessor,
            PropertyAccessor requestScopePropertyAccessor,
            PropertyAccessor sessionScopePropertyAccessor,
            PropertyAccessor applicationScopePropertyAccessor) {
        beanInfo = new CombinedBeanInfo(Request.class, ApplicationContext.class);
        this.objectPropertyAccessor = objectPropertyAccessor;
        this.applicationContextPropertyAccessor = applicationContextPropertyAccessor;
        this.requestScopePropertyAccessor = requestScopePropertyAccessor;
        this.sessionScopePropertyAccessor = sessionScopePropertyAccessor;
        this.applicationScopePropertyAccessor = applicationScopePropertyAccessor;
    }

    /** {@inheritDoc} */
    public PropertyAccessor getPropertyAccessor(String propertyName,
            Request request) {
        PropertyAccessor retValue;
        if (beanInfo.getMappedDescriptors(Request.class)
                .containsKey(propertyName)) {
            retValue = objectPropertyAccessor;
        } else if (beanInfo.getMappedDescriptors(ApplicationContext.class)
                .containsKey(propertyName)) {
            retValue = applicationContextPropertyAccessor;
        } else {
            Map<String, Object> scopeMap = request.getRequestScope();
            if (scopeMap.containsKey(propertyName)) {
                retValue = requestScopePropertyAccessor;
            } else {
                scopeMap = request.getSessionScope();
                if (scopeMap.containsKey(propertyName)) {
                    retValue = sessionScopePropertyAccessor;
                } else {
                    scopeMap = request.getApplicationContext()
                            .getApplicationScope();
                    if (scopeMap.containsKey(propertyName)) {
                        retValue = applicationScopePropertyAccessor;
                    } else {
                        retValue = requestScopePropertyAccessor;
                    }
                }
            }
        }
        return retValue;
    }
}
