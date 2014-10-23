package secondMavenTest.secondMavenTest;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.velocity.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args )
    {
    	
    	setPort(9595); 
    	
        get("/hello", (req, res) -> "Hello World");
        
        get("/hello/:name", (request, response) -> {
            return "Hello: " + request.params(":name"); 
        });
        
        get("/get", (request, response) -> {
        	return "this is the result of a get request";
        });
        
        post("/post", (request, response) -> {
        	String stringResult = request.body();
        	int result = Integer.parseInt(stringResult);
        	String resp;
        	if(result==11){
        		resp = "Das ist richtig!";
        	}
        	else{
        		resp = "Das ist leider Falsch.";
        	}
        	return resp;
        });
        
        get("/helloVelo", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("hello", "Velocity World");
            model.put("person", "Sven");

            return new ModelAndView(model, "testTemplate.wm");
        }, new VelocityTemplateEngine());
    }
}


