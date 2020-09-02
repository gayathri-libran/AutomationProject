package Automation.APITesting;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;


import static com.jayway.restassured.RestAssured.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
public class SpaceXAPI {

	public static String SPACEX_ENDPOINT = "https://api.spacexdata.com/v4/launches/latest";
	public static String CONTENT_TYPE = "application/json; charset=utf-8";
	
	@Test
	public void Test_01_SuccessulNavigation() {
		
		Response resp = when().
				get(SPACEX_ENDPOINT);
		
		resp.then().assertThat().statusCode(200);
		String contentType = resp.then().extract().contentType();
		
		Assert.assertEquals(contentType, CONTENT_TYPE);
		
		System.out.println("The time taken to fetch the response "+
		 resp.timeIn(TimeUnit.MILLISECONDS) + " milliseconds");
	}
	
	@Test
	public void Test_02_Status_Success() {
		Response resp = when().
				get(SPACEX_ENDPOINT);
		
		boolean success = resp.
				then().
				contentType(ContentType.JSON).
				extract().
				path("success");
		Assert.assertTrue(success);
		
		boolean autoUpdate = resp.then().
				contentType(ContentType.JSON).
				extract().
				path("auto_update");
		
		Assert.assertTrue(autoUpdate);
		
		List<String> failures = resp.then().
				contentType(ContentType.JSON).
				extract().
				path("failures");

		Assert.assertFalse(failures.size()>0);
	}

		
	@Test
	public void Test_03_FlightDetails(){
		Response resp = when().
				get(SPACEX_ENDPOINT);
		
		int flightNumber = resp.then().
									contentType(ContentType.JSON).
									extract().
									path("flight_number");
		
		Assert.assertEquals(flightNumber, 101);
		
		List<String> cores = resp.then().
								contentType(ContentType.JSON).
								extract().
								path("cores");
		
		Assert.assertTrue(cores.size()>0);
		
		int coreFlight = resp.then().
				contentType(ContentType.JSON).
				extract().
				path("cores[0].flight");
		Assert.assertEquals(coreFlight, 4);
		
		boolean legs = resp.then().
				contentType(ContentType.JSON).
				extract().
				path("cores[0].legs");
		Assert.assertTrue(legs);
		
		boolean landing_success = resp.then().
				contentType(ContentType.JSON).
				extract().
				path("cores[0].landing_success");
		Assert.assertTrue(landing_success);
		
		boolean landing_attempt = resp.then().
				contentType(ContentType.JSON).
				extract().
				path("cores[0].landing_attempt");
		Assert.assertTrue(landing_attempt);
		
		List<String> ships = resp.then().
				contentType(ContentType.JSON).
				extract().
				path("ships");
		
		Assert.assertTrue(ships.size()>0);
		
	}
}
