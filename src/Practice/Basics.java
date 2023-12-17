package Practice;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

public class Basics {

	@Test(dataProvider = "BooksData")
	public void BookApiCrud(String isbn , String aisle , String author , String name){
		
		RestAssured.baseURI = "http://216.10.245.166/";

		// Add Book Response

		String AddBookResponse = given().log().all().header("Content-Type", "application/json")
				.body(Payload.addBook(isbn ,aisle ,author , name )).when()
				.post("Library/Addbook.php").then().log().all().statusCode(200).extract().response().asString();

		System.out.println("------");
		System.out.println(AddBookResponse);

		JsonPath js = Resusablemethod.rawtoJson(AddBookResponse);
		String BookId = js.get("ID");

		// Get Book Response
		System.out.println("-------------------Get Book Response--------------------------------------");
		String GetBookResponse = given().log().all().header("Content-Type", "application/json").queryParam("ID", BookId)
				.when().get("/Library/GetBook.php").then().log().all().statusCode(200).extract().response().asString();

		// Delete Book Response
		System.out.println("-------------------Delete Book Response--------------------------------------");
		given().log().all().header("Content-Type", "application/json").body(Payload.DeleteBook(BookId)).when()
				.post("/Library/DeleteBook.php").then().log().all().statusCode(200).extract().response().asString();
		
		// Get Book Response
				System.out.println("-------------------Get Book Response After Delete --------------------------------------");
				String GetBookResponseAfterDelete = given().log().all().header("Content-Type", "application/json").queryParam("ID", BookId)
						.when().get("/Library/GetBook.php").then().log().all().statusCode(404).extract().response().asString();


	}
	
	@DataProvider(name = "BooksData")
	public Object[][] getData()
	{
	//array=collection of elements
	//multidimensional array= collection of arrays 

	return new Object[][] {{"acd", "414", "Tushar Turkane", "Postman Automation with JavaScript"},{"bcd", "413", "Anand Bohara", "Automation BDD Framework From Scratch"}, {"ccd", "412", "Tushar Turkane", "Java Basics"} };

	}
}

