# Streamui
## A work-in-process project with a web interface on flink streaming analysis, focused on Security Analytics
The project consists of streams, filters, and alerts.  Streams are data sources which can be files (e.g. json or syslog) or fed from stream processing systems like Apache Kafka, Amazon Kinesis, or Azure Event hub.  Filters use Apache flink for real-time streaming analysis.  Our intial scenario is filtering a stream for a search string and resulting "event" in a time window.  For example filter a syslog authentication stream for "Failed password" sting and look for n hits in s seconds.

We have three main applicatino modules: Streaming analysis using flink in java (/src/main/java/streams), a web interfacce with json files for config and results (/src/web), and Python web server for development (src/webserver).

## Streaming analysis utilize flink streaming analysis methods.
The program is run in the FilterManager class for now, which looks at configured filters then calls the proper classto run the streaming analysis (in flink), e.g. SearchWindowFilter.java which can run filters which search over time windows and create events and alerts. To run the analysis, run FilterManager.main, the output shows up in data/results.  Add a "-test" arg to run tests.  More info on how to run us below.

## The web interface
dash.html - a mocked dashboard
![dash.html](https://github.com/joedatlive/streamui/blob/master/src/screenshots/dash.jpg)
streams.html - view and configure streams
![streams.html](https://github.com/joedatlive/streamui/blob/master/src/screenshots/streams.jpg)
filters.html - view and configre filters
![filters.html](https://github.com/joedatlive/streamui/blob/master/src/screenshots/filters.jpg)
alerts.html - tbd 

Not everything is wired up, and definetly a work-in-progress.  Check the issues list (milestone = Now or Next) to get a sense of the state of things.

### Running and output
You will need a web server to serve the UI and to be able to post new filters and streams  I use a python web server for development and it is in the src\webserver folder.  Plans are to move the config and data files to a standard cloud storage like S3.  Lots changing here, so I won't include too many details.

You can build the FilterManager.java (with main) jar with maven: in the root project (aka repo aka workspace) directory (i.e. streamui) run "mvn clean install --also-make"
then from same directory run "target\streamui-0.1.jar". This will launch FilterManager.main

Check the repo issues to get a sense of what is working. The issues in milestone "now" are the active ones with the latest status.

### Flink logging
Turned off by default - turn on by editing the log4j.properties file (/src/main/resources/log4j.properties).

### References
There is also a POC based on a simple streaming example: https://github.com/joedatlive/flink-failed-password.  If you just want to see some flink filtering out security logs (without UI), this might be a good place to start. Run it in an IDE.

