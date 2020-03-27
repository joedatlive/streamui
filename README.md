# flink-failed-password
## A POC using flink to analyze logs for volume of failed passwords in a time window.

This is a POC built in java using flink to analyze log streams for patterns and alert.

The core streaming analysis info is in main.java, including sample log data, the pattern to match, and new streams of Alerts and Info messages.

### Running and output
The program outputs to terminal and file.  
You can run from the Test.java file and you will see a bunch of flink status messages.  About 2/3rds down you shoud see ALERTS and EVENTS.

Sample Alert: {ALERT- 4 failed passwords in 1 minute=[(1,Jan 13 20:22:30,host1,sshd[21487]:,root,192.168.20.185), (1,Jan 13 20:22:28,host1,sshd[21487]:,root,192.168.20.185), (1,Jan 13 20:23:00,host1,sshd[21487]:,root,192.168.20.185), (1,Jan 13 20:22:32,host1,sshd[21487]:,root,192.168.20.185)]}

The test file writes to "C:\\tmp\\flink\\test\\results.txt"

### Setting up flink with Eclipse
https://ci.apache.org/projects/flink/flink-docs-stable/dev/projectsetup/java_api_quickstart.html

Enter at command line
 $ mvn archetype:generate                               \
      -DarchetypeGroupId=org.apache.flink              \
      -DarchetypeArtifactId=flink-quickstart-java      \
      -DarchetypeVersion=1.7.1
	  
When prompted enter:

groupId: com.yourdomain.streaming
artifactId: flink-xxxxx
version: 1
package: com.yourdomain.streaming.xxxxx

Import package as an existing maven package by entering this:
mvn eclipse:eclipse

When this is done, build package to get all the dependencies downloaded.

go to root directory of package (usually one deeper from where you created the package.
Enter:
mvn clean package

You should now see flink dependencies in the pom.xml file

To use CEP you need to add the dependecies manually.

<dependency>
  <groupId>org.apache.flink</groupId>
  <artifactId>flink-cep_2.11</artifactId>
  <version>1.7.1</version>
</dependency>

And add the CEP jar file to the build path (I did this by accepting a reco in Eclipse...)

### Other notes
* log4j and gson libraries are included for future (likely) use.
* I created this in a Eclipse or IDEA last year, and have ported it to VScode with no problems.
