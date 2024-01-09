package BasicPack;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers.*;
import org.testng.Assert;

import POJO.CreateCartOrder;
import POJO.LoginRequest;
import POJO.LoginResponse;
import POJO.Orders;
import POJO.ProductResponse;
public class EcommerceWebTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
	// BaseUrl
	RequestSpecification rec=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
	
	//Login and post request and get the response
	LoginRequest loginRequest =new LoginRequest();
	loginRequest.setUserEmail("karishma@gmail.com");
	loginRequest.setUserPassword("Hello@123");
	RequestSpecification reqLogin=given().log().all().spec(rec).body(loginRequest);
	LoginResponse loginResponse=reqLogin.when().log().all().post("/api/ecom/auth/login").then().log().all().extract().response().as(LoginResponse.class);
	String token =loginResponse.getToken();
	System.out.println(token);
	String userId=loginResponse.getUserId();
	System.out.println(userId);
	
	
	//Add Product
	
	RequestSpecification addPruductBaseUrl= (RequestSpecification) new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
			.addHeader("Authorization", token).build();
	
	RequestSpecification requesAaddProduct =given().log().all().spec(addPruductBaseUrl).param("productName", "Cat")
			.param("productAddedBy", userId).param("productCategory", "fashion")
			.param("productSubCategory", "shirts")
			.param("productPrice", "6789")
			.param("productDescription", "Addias Originals")
			.param("productFor", "women")
			.multiPart("productImage",new File("C:\\Users\\HP\\Downloads\\images.jpg"));
	ProductResponse resProduct =requesAaddProduct.when().log().all().post("/api/ecom/product/add-product").then().log().all().extract().as(ProductResponse.class);
	String productID= resProduct.getProductId();
	System.out.println(productID);
	
	//Create Cart
	
	RequestSpecification creatPruductBaseUrl= (RequestSpecification) new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
			.addHeader("Authorization", token).setContentType(ContentType.JSON).build();

	CreateCartOrder addToCart= new CreateCartOrder();
	addToCart.setCountry("India");
	addToCart.setProductOrderedId(productID);
	
	List<CreateCartOrder> orderDetaiList = new ArrayList<CreateCartOrder>();
	orderDetaiList.add(addToCart);
	
	Orders order =new Orders();
	order.setOrders(orderDetaiList);
	
	
	String Response= given().log().all().spec(creatPruductBaseUrl).body(order).when().log().all().post("/api/ecom/order/create-order").then().log().all().assertThat().statusCode(201).extract().response().asString();
	System.out.println(Response);
	
	
	//Delete Order

	
	RequestSpecification deleteProdBaseReq=	new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
			.addHeader("authorization", token).setContentType(ContentType.JSON)
			.build();

			RequestSpecification deleteProdReq =given().log().all().spec(deleteProdBaseReq).pathParam("productId",productID);

			String deleteProductResponse = deleteProdReq.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all().
			extract().response().asString();

			JsonPath js1 = new JsonPath(deleteProductResponse);

			Assert.assertEquals("Product Deleted Successfully",js1.get("message"));
	
	
	
	
	

	
	
	
	}

}
