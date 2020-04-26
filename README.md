# Streamui
## A POC of a web interface on flink streaming analysis, focused on Security Analytics

This is a POC built in java using flink to analyze log streams for patterns and alert.

The structure is that we have a set of configs in src\web\data\config.  These include for streams and filters, and eventually events and alerts.

The program is run in the FilterManager class for now, which looks at configured filters then runs a class manage the streaming analysis (flink), e.g. SearchWindowFilter.java which can run filters which search over time windows and create events and alerts. To run the analysis, run FilterManager.main, the output shows up in data/results.  Add a "-test" arg to run tests.

There is a web site at dash.html, which is mostly mocked up.  There is some live data in Filters.html, and Streams.html, which allow users to configure streams and filters in the config files mentioned above.  E.g. you can click on "New fitlers" or "New Streams" respectively and create new ones which will show in the UI.  Supporting more than one filter and more than one stream are WIP. ALso, still need to wire up the streams we create to the analysis.  Work In Progress! 

### Running and output
You will need a web server to serve the UI and to be able to post new fitlers and streams  I use a python web server for development and it is in the src\webserver folder.  Plans are to move the config and data files to a standard cloud storage like S3.  Lots changing here, so I won't include too many details.

NOTE: Need to run in IDE (VScode is what I use) as the build dependencies are a mess.  Plan is to use maven, but need to get dependencies working.

Check the repo issues to get a sense of what is working.  The active work 

### UI
Get a sense of the UI in src\web\dash.html and see what works.  Fitlers.html and Streams.html should load.

### References
There is also a POC based on a simple streaming example: https://github.com/joedatlive/flink-failed-password.  If you just want to see some flink filtering out security logs (without UI), this might be a good place to start.

