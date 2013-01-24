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

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.easymock.EasyMock.*;
import org.junit.Test;

import com.sander.verhagen.domain.Chat;

/**
 * Test for {@link TrillianOutputHandler}.
 * 
 * @author Sander Verhagen
 */
public class TrillianOutputHandlerTest
{
    /**
     * Test for <code>TrillianOutputHandler.createValidFileName</code>.
     */
    @Test
    public void testGetValidFileName()
    {
        Chat chat = createMock(Chat.class);

        List<String> value = new ArrayList<String>();
        value.add("sander.verhagen");

        expect(chat.getPartners()).andReturn(value);
        expect(chat.getFinish()).andReturn(0L /* Wed Dec 31 1969 */);

        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM d yyyy");
        String date = formatter.format(new Date(0L));

        replay(chat);
        String result = TrillianOutputHandler.createValidFileName(chat);
        assertEquals("Group Conversation sander.verhagen; " + date, result);
        verify(chat);
    }
}
