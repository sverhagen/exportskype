/**
 * Copyright 2012 Sander Verhagen
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.sander.verhagen.trillian;


/**
 * Helper to delegate to all {@link BodyFormatter}s.
 * 
 * @author Sander Verhagen
 */
final public class BodyFormatterHelper
{
    private static BodyFormatter escapeBodyFormatter = new EscapeBodyFormatter();

    private static BodyFormatter tagBodyFormatter = new TagBodyFormatter();

    /**
     * Hidden constructor.
     */
    private BodyFormatterHelper()
    {
        ;
    }

    /**
     * Format the given body by delegating to all {@link BodyFormatter}s.
     * 
     * @param originalBody
     *        body that needs to be formatted
     * @return formatted body
     */
    static String format(String originalBody)
    {
        return escapeBodyFormatter.format(tagBodyFormatter.format(originalBody));
    }
}
