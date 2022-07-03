package restautotest;
import org.json.JSONObject;


public class IndexCount extends APINeedTesting {
	public String creRequest(String... request) {
		JSONObject req = new JSONObject();
		req.put("index", request[0]);
		req.put("count", request[1]);
		return req.toString();
	}
}
