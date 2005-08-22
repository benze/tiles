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

package org.apache.tiles.mock;

import org.apache.tiles.DefinitionsReader;

/**
 * Mock Defintions Reader implementation.  Stubs out all functionality.
 *
 * @version $Rev$ $Date$ 
 */
public class MockDefinitionsReader implements DefinitionsReader {
    
    /**
     * Hokey way to verify that this was created.
     */
    private static int instanceCount = 0;
    
    /**
     * Hokey way to verify that this class was created.
     */
    public static int getInstanceCount() {
        return instanceCount;
    }
    
    /** Creates a new instance of MockDefinitionsReader */
    public MockDefinitionsReader() {
        instanceCount++;
    }

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
    public java.util.Map read(Object source) throws org.apache.tiles.DefinitionsFactoryException {
        return null;
    }

    /**
     * Initializes the <code>DefinitionsReader</code> object.
     * 
     * This method must be called before the {@link read} method is called.
     * 
     * @param params A map of properties used to set up the reader.
     * @throws DefinitionsFactoryException if required properties are not
     *  passed in or the initialization fails.
     */
    public void init(java.util.Map params) throws org.apache.tiles.DefinitionsFactoryException {
    }
    
}
