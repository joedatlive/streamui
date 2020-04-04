package failedpwd;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.*;

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

    static void jsonread() {

		Gson gson = new Gson();

		try {

			BufferedReader br = new BufferedReader(
				new FileReader("C:\\Users\\joed\\Documents\\Source\\secdur\\streamui\\src\\web\\filters.json"));

			//convert the json string back to object
			// We need to create classes which map to the json structure. e.g. a filter class.
			//String[] stringArray = gson.fromJson(br, String[].class);

			System.out.println(stringArray);

		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
