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
 * Interface for helpers that are to format the body of the message before being written to a
 * Trillian log file.
 * 
 * @author Sander Verhagen
 * 
 */
public interface BodyFormatter
{
    /**
     * Format the given body.
     * 
     * @param originalBody
     *        body that needs to be formatted
     * @return formatted body
     */
    String format(String originalBody);
}
