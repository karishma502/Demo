package BasicPack;

import org.testng.annotations.Test;

import files.payload;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;


public class DynamicJson {

	@Test
	public void addBook() {
		
		RestAssured.basePath="http://216.10.245.166";
		String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(payload.addBook("yiii","678"))
		.when().post("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(response);
	}

}

