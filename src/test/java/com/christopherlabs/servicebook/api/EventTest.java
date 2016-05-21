package com.christopherlabs.servicebook.api;

/**
 * Created by cwilliamson on 4/30/16.
 */

import org.junit.*;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.testng.annotations.BeforeTest;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.core.IsEqual.equalTo;

public class EventTest {

    @BeforeClass
    public static void setupServer() {
        ServicebookApiApplication app = new ServicebookApiApplication();
        try {
            app.run(new String[] { "server", "src/main/resources/api.yml"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCRUD() {
        //create
        String body = "{\n" +
                "  \"data\": {\n" +
                "    \"type\": \"user\",\n" +
                "    \"attributes\": {\n" +
                "      \"name\": \"Test Event\",\n" +
                "      \"address\": \"123 My Street\",\n" +
                "      \"city\": \"My City\",\n" +
                "      \"state\": \"State\",\n" +
                "      \"country\": \"USA\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Response response = given().header("Content-Type", "application/vnd.api+json").and()
                .request().body(body)
                .then().expect().response().statusCode(201)
                .body("data.attributes.name", equalTo("Test Event"),
                        "data.attributes.address", equalTo("123 My Street"),
                        "data.attributes.city", equalTo("My City"),
                        "data.attributes.state", equalTo("State"),
                        "data.attributes.country", equalTo("USA"))
                .when().post("/event");

        JsonPath jsonPath = new JsonPath(response.asInputStream());
        int id = jsonPath.getInt("data.id");

        //retrieve
        expect().response().statusCode(200)
                .body("data.attributes.name", equalTo("Test Event"),
                "data.attributes.address", equalTo("123 My Street"),
                "data.attributes.city", equalTo("My City"),
                "data.attributes.state", equalTo("State"),
                "data.attributes.country", equalTo("USA"))
                .when().get("/event/" + id);

        //update
        String patchBody = "{\n" +
                "  \"data\": {\n" +
                "    \"type\": \"event\",\n" +
                "    \"id\": \"" + id + "\",\n" +
                "    \"attributes\": {\n" +
                "        \"name\": \"My Event\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        given().header("Content-Type", "application/vnd.api+json").and()
                .request().body(patchBody)
                .then().expect().response().statusCode(204)
                .when().patch("/event/" + id);

        //delete
        expect().response().statusCode(204).when().delete("/event/" + id);

    }

    @Test
    public void testSearch() {

        //create
        String body = "{\n" +
                "  \"data\": {\n" +
                "    \"type\": \"user\",\n" +
                "    \"attributes\": {\n" +
                "      \"name\": \"Test Event\",\n" +
                "      \"address\": \"123 My Street\",\n" +
                "      \"city\": \"My City\",\n" +
                "      \"state\": \"State\",\n" +
                "      \"country\": \"USA\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Response response = given().header("Content-Type", "application/vnd.api+json").and()
                .request().body(body)
                .then().expect().response().statusCode(201)
                .body("data.attributes.name", equalTo("Test Event"),
                        "data.attributes.address", equalTo("123 My Street"),
                        "data.attributes.city", equalTo("My City"),
                        "data.attributes.state", equalTo("State"),
                        "data.attributes.country", equalTo("USA"))
                .when().post("/event");

        JsonPath jsonPath = new JsonPath(response.asInputStream());
        int id = jsonPath.getInt("data.id");

        expect().response().statusCode(200)
                .body("data[0].attributes.name", equalTo("Test Event"))
                .when().get("/event?filter[event.name][search]=event");

        //delete
        expect().response().statusCode(204).when().delete("/event/" + id);

    }
}
