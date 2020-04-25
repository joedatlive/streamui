# Streamui
## A POC of a web interface on flink streaming analysis, focused on Security Analytics

This is a POC built in java using flink to analyze log streams for patterns and alert.

The structure is that we have a set of configs in src\web\data\config.  These include for streams and filters, and eventually events and alerts.

The program is run in the FilterManager class for now, which looks at configured filters then runs a class manage the streaming analysis (flink), e.g. SearchWindowFilter.java which can run filters which search over time windows and create events and alerts. To run the analysis, run FilterManager.main, the output shows up in data/results.  Add a "-test" arg to run tests.

There is a web site at dash.html, which is mostly mocked up.  There is some live data in Filters.html, and Streams.html, which allow users to configure streams and filters in the config files mentioned above.  E.g. you can click on "New fitlers" and create a new one.  It only supports one filer and one stream for now (and the stream isn't the stream used in the analysis yet.  Work In Progress! 

### Running and output
You will need a web server to be able to post new fitlers and server the user web site.  I use a python web server for development and it is on the src\webserver folder.  Plans are to move the config and data files to a standard cloud storage like S3.  Lots changing here, so I won't include too many details.

Check the repo issues to get a sense of what is working.

### UI
Get a sense of the UI in src\web\dash.html and see what works.  Fitlers.html and Streams.html should load.

### References
There is also a POC based on a simple streaming example: https://github.com/joedatlive/flink-failed-password.  If you just want to see some flink filtering out security logs (without UI), this might be a good place to start.

