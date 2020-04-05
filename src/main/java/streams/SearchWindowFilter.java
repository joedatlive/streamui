package streams;

import java.util.List;
import java.util.Map;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple6;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.types.StringValue;
import org.apache.flink.util.Collector;



public class SearchWindowFilter {

	public static void ingest(Filter filter, String[] logArray) throws Exception {
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		
		//Read stream
		DataStream<String> streamIn = env.fromElements(logArray);
		env.setParallelism(filter.parallelism);

		//Filter out only the elements with the pattern and create a new stream  with only Failed Password logs/events
		String pattern = filter.search;
		DataStream<String> filteredStream = Utils.pfilter(streamIn, pattern);
		
		//Apply alert logic and create a stream
		
		//First we put "fields" from our filtered log lines into Tuple objects (and an integer for counting).  And then create a stream of these Tuples
		DataStream<Tuple6<Integer, StringValue, StringValue, StringValue, StringValue, StringValue>> logStream = filteredStream.flatMap(new tuplefy());
		
		
		//Ok, let's look into alerting using the Flink Pattern class
		
		//First create a Pattern...
		
		Pattern<Tuple6<Integer, StringValue, StringValue, StringValue, StringValue, StringValue>, ?> alertPattern = Pattern
				.<Tuple6<Integer, StringValue, StringValue, StringValue, StringValue, StringValue>>begin("ALERT: " + filter.title)
				.times(4)
				.within(Time.minutes(1));
		
		//Then create a stream based on the Pattern
		 PatternStream<Tuple6<Integer, StringValue, StringValue, StringValue, StringValue, StringValue>> alertPatternStream = CEP.pattern(
		    		logStream.keyBy(2),
		    		alertPattern);	
		 
		 //Then create an alert stream based on the events matching the pattern stream
		 @SuppressWarnings("serial")
		 DataStream<String> alerts = alertPatternStream.select(
				 new PatternSelectFunction<Tuple6<Integer, StringValue, StringValue, StringValue, StringValue, StringValue>, String>() {
					 //@Override
					 public String select(Map<String, List<Tuple6<Integer, StringValue, StringValue, StringValue, StringValue, StringValue>>> alertPattern) throws Exception {
						
						 String alert = alertPattern.toString();
						 
						 
						 return alert;
						 //we are just passing a String of the log events.
					 }
				 });
		
		 //For Security events, we want to to the same as above for Alerts, just with different thresholds.  The "event" term here is a "security event", not necessarily events in the streaming context.
		 //Create a pattern with the desired message and thresholds
		 Pattern<Tuple6<Integer, StringValue, StringValue, StringValue, StringValue, StringValue>, ?> eventPattern = Pattern
					.<Tuple6<Integer, StringValue, StringValue, StringValue, StringValue, StringValue>>begin("EVENT - 3 failed passwords in 5 minute")
					.times(3)
					.within(Time.minutes(5));
			
			//Then create a stream based on the Pattern
			 PatternStream<Tuple6<Integer, StringValue, StringValue, StringValue, StringValue, StringValue>> pwdEventPatternStream = CEP.pattern(
			    		logStream.keyBy(2),
			    		eventPattern);	
			 
			 //Then create an Event message stream based on the events matching the pattern stream
			 @SuppressWarnings("serial")
			 DataStream<String> events = pwdEventPatternStream.select(
					 new PatternSelectFunction<Tuple6<Integer, StringValue, StringValue, StringValue, StringValue, StringValue>, String>() {
						 //@Override
						 public String select(Map<String, List<Tuple6<Integer, StringValue, StringValue, StringValue, StringValue, StringValue>>> eventPattern) throws Exception {
							
							 String event = eventPattern.toString();
							 
							 
							 return event;
							 //we are just passing a String of the log events.
						 }
					 });
		
		//Print output
		alerts.printToErr();
		events.print();
		
		//Write to file.  Note this will overwrite into parallel files (per the env.setParallelism() setting) for each thread processing the streams (8 on my local dev box).
		alerts.writeAsText(filter.alertSink, FileSystem.WriteMode.OVERWRITE);
		events.writeAsText(filter.eventSink, FileSystem.WriteMode.OVERWRITE);


		env.execute();
	}

		
		//Here we turn field(s) into tuple(s)
	@SuppressWarnings("serial")
	private static class tuplefy implements FlatMapFunction<String, Tuple6<Integer, StringValue, StringValue, StringValue, StringValue, StringValue>> {
			//create fields we can re-use
			StringValue timeStampValue = new StringValue();
			StringValue hostNameValue = new StringValue();
			StringValue processValue = new StringValue();
			StringValue accountNameValue = new StringValue();
			StringValue sourceNameValue = new StringValue();
			
			Tuple6<Integer, StringValue, StringValue, StringValue, StringValue, StringValue> result = new Tuple6<>(1, timeStampValue, hostNameValue, processValue, accountNameValue, sourceNameValue);
			
			public void flatMap(String s, Collector<Tuple6<Integer, StringValue, StringValue, StringValue, StringValue, StringValue>> collector) throws Exception {
				//Every event contains values separated by spaces
				String[] split = s.split(" ");
				
				String timeStamp = split[0] + " " + split[1] + " "  + split[2];
				String hostName = split[3];
				String process = split[4];
				String accountName = split[8];
				String sourceName = split[10];
				
				timeStampValue.setValue(timeStamp);
				hostNameValue.setValue(hostName);
				processValue.setValue(process);
				accountNameValue.setValue(accountName);
				sourceNameValue.setValue(sourceName);
				
				collector.collect(result);
				
			}
		}
	
}
