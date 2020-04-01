package failedpwd;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStream;

@SuppressWarnings("serial")
public class Utils {
	
	//Here we can filter a stream of events to only have events on a pattern we supply
		static DataStream<String> pfilter (DataStream<String> stream, String pattern) {
			DataStream<String> streamOut = stream.filter(new FilterFunction<String>() {
				@Override
				public boolean filter(String value) throws Exception {
					return value.contains(pattern);
				}
			});
			return streamOut;
		}

}
