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
 *
 */

package org.apache.tiles.context;

import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.context.TilesRequestContext;

import java.util.Map;

/**
 * Default implementation for TilesContextFactory, that creates a chain of
 * sub-factories, trying each one until it returns a not-null value.
 * 
 * @version $Rev$ $Date$
 */
public class ChainedTilesContextFactory implements TilesContextFactory {

    public static final String FACTORY_CLASS_NAMES = "org.apache.tiles.context.ChainTilesContextFactory.FACTORY_CLASS_NAMES";

    public static final String[] DEFAULT_FACTORY_CLASS_NAMES = {
            "org.apache.tiles.context.servlet.ServletTilesContextFactory",
            "org.apache.tiles.context.portlet.PortletTilesContextFactory",
            "org.apache.tiles.context.jsp.JspTilesContextFactory" };

    private TilesContextFactory[] factories;

    public void init(Map configParameters) {
        String[] classNames = null;
        String classNamesString = (String) configParameters
                .get(FACTORY_CLASS_NAMES);
        if (classNamesString != null) {
            classNames = classNamesString.split("\\s*,\\s*");
        }
        if (classNames == null || classNames.length <= 0) {
            classNames = DEFAULT_FACTORY_CLASS_NAMES;
        }

        factories = new TilesContextFactory[classNames.length];
        for (int i = 0; i < classNames.length; i++) {
            try {
                Class clazz = Class.forName(classNames[i]);
                if (TilesContextFactory.class.isAssignableFrom(clazz)) {
                    factories[i] = (TilesContextFactory) clazz.newInstance();
                } else {
                    throw new IllegalArgumentException("The class "
                            + classNames[i]
                            + " does not implement TilesContextFactory");
                }
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException(
                        "Cannot find TilesContextFactory class "
                                + classNames[i], e);
            } catch (InstantiationException e) {
                throw new IllegalArgumentException(
                        "Cannot instantiate TilesFactoryClass " + classNames[i],
                        e);
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(
                        "Cannot access TilesFactoryClass " + classNames[i]
                                + " default constructor", e);
            }
        }
    }

    /**
     * Creates a TilesApplicationContext from the given context.
     */
    public TilesApplicationContext createApplicationContext(Object context) {
        TilesApplicationContext retValue = null;

        for (int i = 0; i < factories.length && retValue == null; i++) {
            retValue = factories[i].createApplicationContext(context);
        }

        if (retValue == null) {
            throw new IllegalArgumentException(
                    "Cannot find a factory to create the application context");
        }

        return retValue;
    }

    public TilesRequestContext createRequestContext(
            TilesApplicationContext context, Object... requestItems) {
        TilesRequestContext retValue = null;

        for (int i = 0; i < factories.length && retValue == null; i++) {
            retValue = factories[i].createRequestContext(context, requestItems);
        }

        if (retValue == null) {
            throw new IllegalArgumentException(
                    "Cannot find a factory to create the request context");
        }

        return retValue;
    }
}
