package pokeapi;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ApiTest {
    private final static String URL = "https://pokeapi.co/";

    @Test
    public void checkWeight() {
        Integer rattata = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL + "api/v2/pokemon/rattata")
                .then()
                .extract().response().jsonPath().getInt("weight");

        Integer pidgeotto = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL + "api/v2/pokemon/pidgeotto")
                .then()
                .extract().response().jsonPath().getInt("weight");

        if (rattata < pidgeotto) {
            Assertions.assertNotEquals(rattata, pidgeotto);
        }
    }

    @Test
    public void checkAbility() {
        List<String> rattataAbilityName = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL + "api/v2/pokemon/rattata")
                .then()
                .extract().response().jsonPath().get("abilities.ability.name");;

        String actual = "";

        for (int i = 0; i < rattataAbilityName.size(); i++) {
            System.out.println(rattataAbilityName.get(i));
            if ("run-away".equals(rattataAbilityName.get(i))) {
                actual = rattataAbilityName.get(i);
                break;
            } else {
                actual = "not found";
            }
        }
        Assertions.assertEquals("run-away", actual);

    }

    @Test
    public void checkLimit() {
        List<String> pokemon = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL + "api/v2/pokemon?limit=10")
                .then()
                .body("results.name", notNullValue())
                .extract().response().jsonPath().get("results");

        if (pokemon.size() == 10) {
            Assertions.assertEquals(10, pokemon.size());
        }
    }
}
