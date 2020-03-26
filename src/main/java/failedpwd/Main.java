package failedpwd;

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
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.types.StringValue;
import org.apache.flink.util.Collector;



public class Main {

	public static void main(String[] args) throws Exception {
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		
		//Read stream
		
		
		
		DataStream<String> streamf = env.fromElements(
				
				"Jan 13 20:22:28 host1 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2",
				"Jan 13 20:22:29 host1 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2",
				"Jan 13 20:22:30 host1 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2",
				"Jan 13 20:22:32 host1 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2",
				"Jan 13 20:22:32 host2 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2",
				"Jan 13 20:23:00 host1 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2",
				"Jan 13 20:23:10 host2 sshd[21487]: Failed password for root from 192.168.20.185 port 1045 ssh2", 
				"Jan 17 22:43:54 ip-172-0.0.0 sshd[2632]: pam_unix(sshd:session): session opened for user ec2-user by (uid=0)");
			
		
		//Filter out only the elements with the pattern and create a new stream  with only Failed Password logs/events
		String pattern = "Failed password";
		DataStream<String> failedPwd = Utils.pfilter(streamf, pattern);
		
		//Apply alert logic and create a stream
		
		//First we put "fields" from our log lines into Tuple objects (and an integer for counting).  And then create a stream of these Tuples
		DataStream<Tuple6<Integer, StringValue, StringValue, StringValue, StringValue, StringValue>> eventStream = failedPwd.flatMap(new tuplefy());
		
		
		//Ok, let's look into alerting using the Flink Pattern class
		
		//First create a Pattern...
		
		Pattern<Tuple6<Integer, StringValue, StringValue, StringValue, StringValue, StringValue>, ?> pwdAlertPattern = Pattern
				.<Tuple6<Integer, StringValue, StringValue, StringValue, StringValue, StringValue>>begin("ALERT- 4 failed passwords in 1 minute")
				.times(4)
				.within(Time.minutes(1));
		
		//Then create a stream based on the Pattern
		 PatternStream<Tuple6<Integer, StringValue, StringValue, StringValue, StringValue, StringValue>> pwdAlertPatternStream = CEP.pattern(
		    		eventStream.keyBy(2),
		    		pwdAlertPattern);	
		 
		 //Then create an alert stream based on the events matching the pattern stream
	 
		 DataStream<String> pwdAlerts = pwdAlertPatternStream.select(
				 new PatternSelectFunction<Tuple6<Integer, StringValue, StringValue, StringValue, StringValue, StringValue>, String>() {
					 //@Override
					 public String select(Map<String, List<Tuple6<Integer, StringValue, StringValue, StringValue, StringValue, StringValue>>> alertPattern) throws Exception {
						
						 String alert = alertPattern.toString();
						 
						 
						 return alert;
						 //we are just passing a String of the log events.
					 }
				 });
		
		 
		 //Create a pattern with the desired message and thresholds
		 Pattern<Tuple6<Integer, StringValue, StringValue, StringValue, StringValue, StringValue>, ?> pwdInfoPattern = Pattern
					.<Tuple6<Integer, StringValue, StringValue, StringValue, StringValue, StringValue>>begin("INFO- 3 failed passwords in 5 minute")
					.times(3)
					.within(Time.minutes(5));
			
			//Then create a stream based on the Pattern
			 PatternStream<Tuple6<Integer, StringValue, StringValue, StringValue, StringValue, StringValue>> pwdInfoPatternStream = CEP.pattern(
			    		eventStream.keyBy(2),
			    		pwdInfoPattern);	
			 
			 //Then create an Info message stream based on the events matching the pattern stream
		 
			 DataStream<String> pwdInfos = pwdInfoPatternStream.select(
					 new PatternSelectFunction<Tuple6<Integer, StringValue, StringValue, StringValue, StringValue, StringValue>, String>() {
						 //@Override
						 public String select(Map<String, List<Tuple6<Integer, StringValue, StringValue, StringValue, StringValue, StringValue>>> alertPattern) throws Exception {
							
							 String alert = alertPattern.toString();
							 
							 
							 return alert;
							 //we are just passing a String of the log events.
						 }
					 });
		 
		 
		//Print output
		pwdAlerts.printToErr();
		pwdInfos.print();
		
		env.execute();

	}

		
		//Here we turn field(s) into tuple(s)
	
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
