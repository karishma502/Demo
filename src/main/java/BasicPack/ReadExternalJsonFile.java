package BasicPack;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class ReadExternalJsonFile {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(GenrateStringFromJson("C:\\Users\\Karishma\\Downloads\\addbook.json"))
		.when().post("Library/Addbook.php")
		.then().assertThat().statusCode(200).extract().asString();
		
		}
	public static String GenrateStringFromJson(String Path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(Path)));
	}

}
