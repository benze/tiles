/*
 * $Id$
 *
 * Copyright 1999-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.tiles;

import java.util.Map;

/**
 * Interface for reading <code>{@link ComponentDefinition}</code> from a source.
 *
 * <p>This interface provides a standard way to read 
 * <code>{@link ComponentDefinition}</code> objects from a source.  Implementations
 * should define what the source is, whether it be a persistent store such as a
 * configuration file or database, or something like a web service.  The 
 * DefinitionsReader is responsible for reading from a single location.  It does
 * not perform any internationalization duties or inheritance of ComponentDefinitions.
 * It only reads from the source and returns a Map of objects read.</p>
 *
 * @version $Rev$ $Date$ 
 */
public interface DefinitionsReader {

    /**
     * Initializes the <code>DefinitionsReader</code> object.
     *
     * This method must be called before the {@link #read(java.lang.Object)} method is called.
     *
     * @param params A map of properties used to set up the reader.
     * @throws DefinitionsFactoryException if required properties are not
     *  passed in or the initialization fails.
     *
     */
    public void init(Map params) throws DefinitionsFactoryException;
    
    /**
     * Reads <code>{@link ComponentDefinition}</code> objects from a source.
     *
     * Implementations should publish what type of source object is expected.
     *
     * @param source The source from which definitions will be read.
     * @return a Map of <code>ComponentDefinition</code> objects read from
     *  the source.
     * @throws DefinitionsFactoryException if the source is invalid or
     *  an error occurs when reading definitions.
     */
    public Map read(Object source) throws DefinitionsFactoryException;
    
}
