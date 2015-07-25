#Export Skype

Little tool to export Skype chat history to another format. Implemented format is Trillian XML log format, but other formats may easily be supported by adding implementations of the `OutputHandler` interface.

You may use Maven (3.0.4 or higher) to build this project. See e.g. http://maven.apache.org/run-maven/index.html

Having installed and set up Maven you would do as follows:

	...\exportskype> mvn install

In case of `BUILD SUCCESS`, you should find an executable JAR file in target\exportskype-0.0.1-SNAPSHOT.jar that can be ran as follows:

	...\exportskype> java -jar target\exportskype-0.0.1-SNAPSHOT.jar

You can use Maven release to release the project. It will nicely label a version number and upload package archives to Google Code:

	...\exportskype> mvn release:prepare release:perform

For the latter to work, you need Google Code in your `settings.xml` of Maven:

	<server>
		<id>googlecode</id>
		<username>...</username>
		<password>...</password>
	</server>

*Obviously, Google Code is no more, and the above process would have to be fixed accordingly. That's just not yet the case.*
