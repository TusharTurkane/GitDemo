package Practice;

import io.restassured.path.json.JsonPath;


public class Resusablemethod {
	
public static JsonPath rawtoJson(String Response) {
		
		JsonPath js = new JsonPath(Response);
		
		return js;
		
	}

}
