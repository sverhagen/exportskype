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

import java.util.regex.Pattern;

/**
 * Helper to escape the body of the message to remove XML tags that Skype uses
 * to show emoticons ( <code>ss</code>) and quotes (<code>quote</code>,
 * <code>legacycode</code>).
 * 
 * @author Sander Verhagen
 */
public class TagBodyFormatter implements BodyFormatter {
	class TagReplacement {
		private Pattern pattern;

		private String replacement;

		public TagReplacement(String find, String replacement) {
			this(find, replacement, 0);
		}

		public TagReplacement(String find, String replacement, int flags) {
			this.pattern = Pattern.compile(find, flags);
			this.replacement = replacement;
		}

		public String replace(String original) {
			return pattern.matcher(original).replaceAll(replacement);
		}
	}

	private TagReplacement smiley = new TagReplacement(
			"<ss type=\".*?\">(.*?)</ss>", "$1");

	private TagReplacement legacyQuote = new TagReplacement(
			"<legacyquote>(.*?)</legacyquote>", "$1", Pattern.DOTALL);

	private TagReplacement quoteOpen = new TagReplacement("<quote .*?>", "");

	private TagReplacement quoteClose = new TagReplacement("</quote>", "\r\n");

	private TagReplacement[] replacements = { smiley, legacyQuote, quoteOpen,
			quoteClose };

	public String format(String originalBody) {
		String body = new String(originalBody);
		for (TagReplacement replacement : replacements) {
			body = replacement.replace(body);
		}
		return body;
	}

}
