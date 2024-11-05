package api_tests;

import config.AuthenticationController;
import dto.ErrorMessageDto;
import dto.UserDto;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;

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


        //UserDtoLombok user = UserDtoLombok.builder()
                 //       .username(getProperty("data.properties", "email"))
                  //      .password(getProperty("data.properties", "password"))
                  //      .build();
        //System.out.println(requestRegLogin(user, LOGIN_PATH).getStatusCode());
        //Response response = requestRegLogin(user, LOGIN_PATH);
        //TokenDto tokenDto = response.as(TokenDto.class);
        //softAssert.assertEquals(response.getStatusCode(), 200);
        //softAssert.assertTrue(response.getBody().print().contains("token"));
        //softAssert.assertAll();
    }

    @Test

    public void loginNegativeTest_WrongEmail(){
        UserDto user = new UserDto(generateEmail(12), getProperty("data.properties", "password"));
        Response response = requestLogin(user, LOGIN_PATH);
        ErrorMessageDto errorMessageDTO = response.getBody().as(ErrorMessageDto.class);
        System.out.println(errorMessageDTO);
        softAssert.assertTrue(errorMessageDTO.getError().equals("Unauthorized"));
        softAssert.assertTrue(errorMessageDTO.getMessage().toString().contains("Login or Password incorrect"));
        softAssert.assertEquals(response.getStatusCode(),401);

        System.out.println("Time: "+errorMessageDTO.getTimestamp());
        LocalDate localDate = LocalDate.now();
        softAssert.assertEquals(errorMessageDTO.getTimestamp().split("T")[0],localDate.toString());
        softAssert.assertAll();
    }
}
