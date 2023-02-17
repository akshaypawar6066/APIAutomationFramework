package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Utils {
	public static RequestSpecification req;
	ResponseSpecification res;
    
	public RequestSpecification requestSpecification() throws IOException {
		if (req == null) {

			PrintStream stream = new PrintStream(new FileOutputStream("logging.txt"));
			req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
					.addQueryParam("key", "qaclick123").addFilter(RequestLoggingFilter.logRequestTo(stream))
					.addFilter(ResponseLoggingFilter.logResponseTo(stream)).setContentType(ContentType.JSON).build();
			return req;
		}
		return req;
	}

	public ResponseSpecification responseSpecification() {
		res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		return res;
	}

	public String getGlobalValue(String key) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				"C:\\Users\\aksha\\eclipse-workspace\\APIAutomation\\src\\test\\java\\resources\\global.properties");
		prop.load(fis);
		return prop.getProperty(key);

	}
	
	public String getJsonPath(Response response, String key)
	{
		String res = response.asString();
		JsonPath js = new JsonPath(res);
		return js.get(key).toString();
		
		
	}

}
