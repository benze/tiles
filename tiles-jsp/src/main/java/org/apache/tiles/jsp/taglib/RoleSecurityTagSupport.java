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
package org.apache.tiles.jsp.taglib;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tiles.TilesException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

/**
 * Base tag for the tiles tags which provides standard support for security.
 * 
 * @since Tiles 2.0
 * @version $Rev$ $Date$
 */
public abstract class RoleSecurityTagSupport extends BodyTagSupport {
    
    /**
     * The log instance for this tag.
     */
    private static final Log LOG = LogFactory.getLog(RoleSecurityTagSupport.class);

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int doEndTag() throws JspException {
        try {
            if (isAccessAllowed()) {
                execute();
            }
        } catch (TilesException e) {
            String message = "Error executing tag: " + e.getMessage();
            LOG.error(message, e);
            throw new JspException(message, e);
        } catch (IOException io) {
            String message = "IO Error executing tag: " + io.getMessage();
            LOG.error(message, io);
            throw new JspException(message, io);
        }
        
        return EVAL_PAGE;
    }



    public void release() {
        super.release();
        this.role = null;
    }

    protected abstract void execute() throws TilesException, JspException, IOException;

    protected boolean isAccessAllowed() {
        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        return (role == null || req.isUserInRole(role));
    }
}