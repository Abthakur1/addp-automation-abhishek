package api.Tests;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class PokeApiTest {

    // setting global variables ( can be set to config file as well)
    String url = "https://pokeapi.co";
    String resource1 = "/api/v2/pokemon";
    String verifyAttribute1 = "name";
    String verifyAttribute2 = "url";

    // base url setup
    @BeforeTest
    public void setBaseUrl() {
        RestAssured.baseURI = url;
    }

    // test the pokemon name, url with the pokemon number
    @Test(dataProvider = "pokemonTestData", dataProviderClass= TestData.class)
    public void testPokemon(int pokemonNumber, String nameToVerify) {

        Response resp = given().queryParam("limit", 100000).queryParam("offset", 0).when().get(resource1)
                .then().extract().response();
        // log the response as String
        System.out.println(resp.asString());

        //retrieving the results from response - parse using jsonPath() and its returning the list of LinkedHashMap
        List<LinkedHashMap> listOfResults = resp.jsonPath().getList("results");

        //getting the list of pokemon names corresponding to the pokemon number
        List<Object> pokemonNameList = listOfResults.stream().filter(it -> it.get(verifyAttribute2).equals(url+resource1+"/" +pokemonNumber +"/"))
                .map(it->it.get(verifyAttribute1)).collect(Collectors.toList());
        // loging the corresponding name of the pokemon with the pokemon number
        System.out.println("Name of the pokemon with number "+pokemonNumber + " is:"+ pokemonNameList.get(0));

        // verify that the pokemon number aligned with the expected name ( given in the Test data)
        Assert.assertEquals(pokemonNameList.get(0),nameToVerify );

        //retrieve the url list from the response on the pokemon number
        List<Object> pokemonUrlList= listOfResults.stream().filter(it -> it.get(verifyAttribute2).equals(url+resource1+"/" +pokemonNumber +"/"))
                .map(it->it.get(verifyAttribute2)).collect(Collectors.toList());

        // logging the pokemon number with respective url
        System.out.println("url of the pokemon with number "+pokemonNumber + " is:"+ pokemonUrlList.get(0));

        //verify that the url contains correct pokemon number
        boolean result = pokemonUrlList.get(0).toString().contains("/"+pokemonNumber+"/");
        Assert.assertTrue(result);
    }

    // Test To verify that games aligned correctly with the generation and verify the total sets in the version group.
    // Test to verify the name of version group with the given generation ( Fetch from test data)
    @Test(dataProvider = "pokemonGenerationTestData", dataProviderClass= TestData.class)
    public void testGameGeneration(int generation, String nameToVerify, String versionGroupName) {

        Response resp1 = given().when().get("/api/v2/generation/"+generation)
                .then().extract().response();
        // log the response as String
        System.out.println(resp1.asString());

        // fetch the main_region from response
        LinkedHashMap mainRegion =  resp1.jsonPath().get("main_region");

        // log game mapping with the corresponding generation
        System.out.println( "The game of generation" +generation + " is set to: "+ mainRegion.get(verifyAttribute1));

        // verify that the game is mapped correctly with the generation ( given in Test data)
        Assert.assertEquals(mainRegion.get(verifyAttribute1), nameToVerify);

        // fetch the version_groups list
        List<LinkedHashMap> list = resp1.jsonPath().getList("version_groups");

        // verify that there are two resources in the version groups array
        System.out.println("total number of resources in the version group: "+ list.size());
        Assert.assertEquals(list.size(), 2);

        // verify the resources under version_groups in the response
        List versionGroupNameList = list.stream().map(it->it.get(verifyAttribute1)).collect(Collectors.toList());
        System.out.println(versionGroupNameList.get(0));
        boolean flag = false;
        for(int i = 0 ; i<versionGroupNameList.size();i++){
            if(versionGroupNameList.get(i).toString().equals(versionGroupName)){
                flag = true;
                break;
            }
        }
        Assert.assertTrue(flag);

    }
}
