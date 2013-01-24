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

package com.sander.verhagen;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import org.easymock.EasyMock;
import org.junit.Test;

import com.sander.verhagen.DatabaseConnectionHelper;

/**
 * Tests for {@link DatabaseConnectionHelper}.
 * 
 * @author Sander Verhagen
 */
public class DatabaseConnectionHelperTest {

	/**
	 * Test for <code>DatabaseConnectionHelper.getApplicationDataFolder</code>
	 * for normal situation (in which case <code>%APPDATA%</code> is available).
	 * Rumor has it <code>%APPDATA%</code> is not available for &quot;Run
	 * as...&quot;, hence it's a red flag
	 */
	@Test
	public void testGetApplicationDataFolderNormal() {
		DatabaseConnectionHelper subject = new DatabaseConnectionHelper();
		String expected = System.getenv("APPDATA");
		File applicationData = subject.getApplicationDataFolder();
		assertEquals(expected, applicationData.toString());
	}

	/**
	 * Test for <code>DatabaseConnectionHelper.getSkypeFolder</code>. We have to
	 * fake where the application data are located since the PC that runs the
	 * test may not have Skype installed
	 */
	@Test
	public void testGetSkypeFolder() {
		final String applicationData = "src\\test\\resources\\myHome\\Application Data";
		DatabaseConnectionHelper subject = new DatabaseConnectionHelper() {
			File getApplicationDataFolder() {
				return new File(applicationData);
			}
		};

		String expected = applicationData + "\\Skype";
		File skypeFolder = subject.getSkypeFolder();
		assertEquals(expected, skypeFolder.toString());
	}

	/**
	 * Test for <code>DatabaseConnectionHelper.determineDatabaseUrl</code>.
	 */
	@Test
	public void testDetermineDatabaseUrl() {
		final String applicationData = "src\\test\\resources\\myHome\\Application Data";
		String fileName = applicationData + "\\Skype\\my.user\\main.db";
		DatabaseConnectionHelper subject = new DatabaseConnectionHelper() {
			File getApplicationDataFolder() {
				return new File(applicationData);
			}
		};

		File expectedFile = new File(fileName);
		String expectedUrl = "jdbc:sqlite:" + expectedFile.getAbsolutePath();
		String string = subject.determineDatabaseUrl();
		assertEquals(expectedUrl, string);
	}

	/**
	 * Test for <code>DatabaseConnectionHelper.determineDatabaseUrl</code> when
	 * no database exists.
	 */
	@Test
	public void testDetermineDatabaseUrlNoDatabase() {
		final String applicationData = "src\\test\\resources\\myHomeNoDatabase\\Application Data";
		DatabaseConnectionHelper subject = new DatabaseConnectionHelper() {
			File getApplicationDataFolder() {
				return new File(applicationData);
			}
		};

		try {
			subject.determineDatabaseUrl();
			fail("should not have found a database");
		} catch (RuntimeException exception) {
			String expected = "No database file found. Looked here: ";
			assertTrue(exception.getMessage().startsWith(expected));
		}
	}

	/**
	 * Test for <code>DatabaseConnectionHelper.determineDatabaseUrl</code> when
	 * multiple database exists.
	 */
	@Test
	public void testDetermineDatabaseUrlMultipleDatabases() {
		final String applicationData = "src\\test\\resources\\myHomeMultipleDatabases\\Application Data";
		DatabaseConnectionHelper subject = new DatabaseConnectionHelper() {
			File getApplicationDataFolder() {
				return new File(applicationData);
			}
		};

		try {
			subject.determineDatabaseUrl();
			fail("should not have found a single database");
		} catch (RuntimeException exception) {
			String expected = "Multiple database files found; don't know which one to choose";
			assertEquals(expected, exception.getMessage());
		}
	}

	/**
	 * Test for {@link DatabaseConnectionHelper#open()} and
	 * {@link DatabaseConnectionHelper#close()} .
	 * 
	 * @throws SQLException
	 *             if a database access error occurs
	 */
	@Test
	public void testConnection() throws SQLException {
		final String applicationData = "src\\test\\resources\\myHome\\Application Data";
		DatabaseConnectionHelper subject = new DatabaseConnectionHelper() {
			File getApplicationDataFolder() {
				return new File(applicationData);
			}
		};

		Connection connection = subject.open();
		assertFalse(connection.isClosed());
		subject.close();
		assertTrue(connection.isClosed());
	}

}
