package BasicPack;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class OAuth2 {

	//	public static void main(String[] args) throws InterruptedException {
	//		// TODO Auto-generated method stub
	//		
	////		System.setProperty("webdriver.chrome.driver", "C:\\Users\\HP\\OneDrive\\Desktop\\API\\APITestingProject\\driver\\chromedriver-win64\\chromedriver.exe");
	////		WebDriver driver = new ChromeDriver();
	////		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
	////		driver.findElement(By.cssSelector("input[type='email']")).sendKeys("karishmakate31@gmail.com");
	////		driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
	////		Thread.sleep(500);
	////		driver.findElement(By.cssSelector("input[type='password']")).sendKeys("Rohit$Karu123");
	////		driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
	////		Thread.sleep(500);
	//		String url= "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AfJohXkvFlu5BbyHLLomS9bS_G13_0BsteD8GKELoud6VSAEyYYi0saCXRgbtnA_6IajOg&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent";
	//		
	//		String partialCode =url.split("code=")[1];
	//		String code =partialCode.split("&scope")[0];
	//		System.out.println(code);
	//		
	//		
	//		
	//		
	//		
	//		
	//		String accessTokenResponse=given().log().all().urlEncodingEnabled(false)
	//		.queryParams("code", code)
	//		.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
	//		.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
	//		.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
	//		.queryParams("grant_type", "authorization_code")
	//		.when().log().all().post("https://www.googleapis.com/oauth2/v4/token").then().assertThat().statusCode(200)
	//		.extract().response().asString();
	//		
	//		JsonPath js= new JsonPath(accessTokenResponse);
	//		String access_token=js.get("access_token");
	//		
	//		
	//		
	//
	//		
	//		String response =given().queryParam("access_token", access_token)
	//				.when().log().all().get("https://rahulshettyacademy.com/getCourse.php")
	//				.then().toString();
	//		System.out.println(response);
	//				
	//		
	//
	//	}



	public static void main(String[] args) throws InterruptedException {

		// TODO Auto-generated method stub

		String url ="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AfJohXkvFlu5BbyHLLomS9bS_G13_0BsteD8GKELoud6VSAEyYYi0saCXRgbtnA_6IajOg&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent";



		String partialcode=url.split("code=")[1];

		String code=partialcode.split("&scope")[0];

		System.out.println(code);

		String response =

				given() 

				.urlEncodingEnabled(false)

				.queryParams("code",code)



				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")

				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")

				.queryParams("grant_type", "authorization_code")

				.queryParams("state", "verifyfjdss")

				.queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")

				// .queryParam("scope", "email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email")



				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")

				.when().log().all()

				.post("https://www.googleapis.com/oauth2/v4/token").asString();

		 System.out.println(response);

		JsonPath jsonPath = new JsonPath(response);

		String accessToken = jsonPath.getString("access_token");

		System.out.println("accessToken is: "+accessToken);

		String r2=    given().contentType("application/json").

				queryParams("access_token", accessToken).expect().defaultParser(Parser.JSON)

				.when()

				.get("https://rahulshettyacademy.com/getCourse.php")

				.asString();

		System.out.println(r2);



	}

}
