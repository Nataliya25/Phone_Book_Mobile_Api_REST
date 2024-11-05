package api_tests;

import config.AuthenticationController;
import dto.ErrorMessageDTO;
import dto.UserDto;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static helper.PropertiesReader.getProperty;
import static helper.RandomUtils.generateEmail;

public class LoginTests extends AuthenticationController {

    SoftAssert softAssert = new SoftAssert();

    @Test

    public void loginPositiveTest(){
        UserDto user = new UserDto(getProperty("data.properties", "email"),
                getProperty("data.properties", "password"));

        Assert.assertEquals(requestLogin(user,LOGIN_PATH)
                .getStatusCode(),200);
    }

    @Test

    public void loginNegativeTest_WrongEmail(){
        UserDto user = new UserDto(generateEmail(12), getProperty("data.properties", "password"));
        Response response = requestLogin(user, LOGIN_PATH);
        ErrorMessageDTO errorMessageDTO = response.getBody().as(ErrorMessageDTO.class);
        System.out.println(errorMessageDTO);
        softAssert.assertTrue(errorMessageDTO.getError().equals("Unauthorized"));
        softAssert.assertTrue(errorMessageDTO.getMessage().toString().contains("Login or Password incorrect"));
        softAssert.assertEquals(response.getStatusCode(),401);
        softAssert.assertAll();
    }
}
