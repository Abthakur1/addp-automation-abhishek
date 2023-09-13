package api.Tests;

import org.testng.annotations.DataProvider;

public class TestData {

    // getting the test data for pokemon validation
    @DataProvider(name = "pokemonTestData")
    public static Object[][] getPokemonData() {
        Object[][] data = new Object[5][2];

        // set -1 : pokemon number and expected name
        data[0][0] = 812;
        data[0][1] = "rillaboom";

        // set -2 : pokemon number and expected name
        data[1][0] = 326;
        data[1][1] = "grumpig";

        // set -3 : pokemon number and expected name
        data[2][0] = 242;
        data[2][1] = "blissey";

        // set -4 : pokemon number and expected name
        data[3][0] = 3;
        data[3][1] = "venusaur";

        // set -5 : pokemon number and expected name
        data[4][0] = 10;
        data[4][1] = "caterpie";
        return  data;
    }

    // getting the test data for games validation
    @DataProvider(name = "pokemonGenerationTestData")
    public static Object[][] getPokemonGenerationData() {
        Object[][] data = new Object[2][3];

        // set -1 : generation and expected game and version_groups name
        data[0][0] = 5;
        data[0][1] = "unova"; // expected game
        data[0][2] = "black-white"; // expected version_groups

        // set -2 : generation and expected game and version_groups name
        data[1][0] = 6;
        data[1][1] = "kalos";
        data[1][2] = "x-y";
        return  data;
    }

}
