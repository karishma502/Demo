package BasicPack;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;


public class JiraTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestAssured.baseURI="http://localhost:8080";
		
		SessionFilter session = new SessionFilter();
		//Login to Jira
		
		String seesionId =given().log().all().relaxedHTTPSValidation().header("Content-Type","application/json")
		.body("{ \"username\": \"karishmakate1998\", \"password\": \"[Jira@123]\" }").
		filters(session).when().post("/rest/auth/1/session")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(seesionId);
		String value = js.get("session.value");
		System.out.println(value);
		
		// add comment to the issue
		String expectedmsg="Hi this is comment for fields";
		
		String respose1=given().log().all().pathParam("key", "RES-1").header("Content-Type","application/json")
		//.header("value",""+value+"")
		.body("{\r\n"
				+ "    \"body\": \""+expectedmsg+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session).when().post("/rest/api/2/issue/{key}/comment").then().log().all().extract().response().asString();
		JsonPath js2= new JsonPath(respose1);
		String CommnentID =js2.get("id").toString();
		
		
		//Create issue
		
		String response =	given().log().all()
				.header("Content-Type","application/json")
		.body("{\r\n"
				+ "    \"fields\": {\r\n"
				+ "        \"project\": {\r\n"
				+ "            \"key\": \"RES\"\r\n"
				+ "        },\r\n"
				+ "        \"summary\": \"Automation first issue created\",\r\n"
				+ "        \"issuetype\": {\r\n"
				+ "            \"name\": \"Bug\"\r\n"
				+ "        },\r\n"
				+ "        \"description\": \"Script issue\"\r\n"
				+ "    }\r\n"
				+ "}")
		.filter(session).when().log().all().post("/rest/api/2/issue").then().assertThat().statusCode(201).extract().response().asString();
		
		System.out.println(response);
		
		// Add attachment to the JIRA
		
		given().log().all()
		.filter(session)
		.pathParam("key", "RES-1")
		.header("X-Atlassian-Token","no-check")
		.header("Content-Type","multipart/form-data")
		.multiPart("file", new File("C:\\Users\\HP\\OneDrive\\Desktop\\API\\APITestingProject\\src\\main\\java\\files\\Jira.txt"))
		.when().post("/rest/api/2/issue/{key}/attachments").then().assertThat().statusCode(200)
		.extract().response().asString();
		
		//Get Issue
		
		String getissueCommnet = given().log().all().filter(session).pathParam("key", "RES-1")
				.queryParam("fields", "comment")
				.when().get("/rest/api/2/issue/{key}")
				.then().log().all().extract().response().asString();
		
		JsonPath js1 = new JsonPath(getissueCommnet);
		int commentSize= js1.get("fields.comment.comments.size()");
		for(int i=0;i<commentSize;i++) {
			String expectedcomment=js1.get("fields.comment.comments["+i+"].id").toString();
			//System.out.println(expectedcomment);
			
			if(expectedcomment.equalsIgnoreCase(CommnentID)) {
				String actualmsg=js1.get("fields.comment.comments["+i+"].body").toString();
				System.out.println(actualmsg);
				Assert.assertEquals(actualmsg, expectedmsg);
			}
		}
			
			
			
			
			
					

	}
	

}
