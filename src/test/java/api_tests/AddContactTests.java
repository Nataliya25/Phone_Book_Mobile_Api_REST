package api_tests;

import config.ContactController;
import dto.ContactDtoLombok;
import dto.ContactsDto;
import dto.ErrorMessageDto;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static helper.RandomUtils.generateEmail;
import static helper.RandomUtils.generateString;

public class AddContactTests extends ContactController {
    SoftAssert softAssert = new SoftAssert();

    @Test
    public void addContactPositiveTest() {

        ContactDtoLombok contactNew = ContactDtoLombok.builder()
                .name("new name" + generateString(5))
                .lastName("new last name" + generateString(5))
                .email("new_mail" + generateEmail(7))
                .phone("1234567890")
                .address("new address" + generateString(5))
                .description("new desc")
                .build();

        Response response = addContactResponse(contactNew, tokenDto.getToken());

        //System.out.println("RequestBody: " + contactNew);
        //System.out.println("ResponseBody: " + response.getBody().print());
        softAssert.assertEquals(response.getStatusCode(), 200);
        //softAssert.assertTrue(response.getBody().toString().contains("Contact was added!"));
        softAssert.assertAll();
    }

    @Test
    public void addContactNegativeTest_FormatError_400() {

        ContactDtoLombok contactNew = ContactDtoLombok.builder()
                .name( generateString(0))
                .lastName("new last name" + generateString(5))
                .email("new_mail" + generateEmail(7))
                .phone("1234567890")
                .address("new address" + generateString(5))
                .description("new desc")
                .build();

        Response response = addContactResponse(contactNew, tokenDto.getToken());

        //System.out.println("RequestBody: " + contactNew);
        //System.out.println("ResponseBody: " + response.getBody().print());
        softAssert.assertEquals(response.getStatusCode(), 400);
        ErrorMessageDto errorMessageDto = ErrorMessageDto.builder().build();
        if (response.getStatusCode() == 400) {
            errorMessageDto = response.as(ErrorMessageDto.class);
        }
        System.out.println("Error message: " + errorMessageDto.getError());
        softAssert.assertTrue(errorMessageDto.getError().contains("Bad Request"));
        softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("must not be blank"));
        softAssert.assertAll();
    }

    @Test
    public void addContactNegativeTest_Unauthorized_401() {

        ContactDtoLombok contactNew = ContactDtoLombok.builder()
                .name( generateString(7))
                .lastName("new last name" + generateString(5))
                .email("new_mail" + generateEmail(7))
                .phone("1234567890")
                .address("new address" + generateString(5))
                .description("new desc")
                .build();

        Response response = addContactResponse(contactNew, "token");

        System.out.println("RequestBody: " + contactNew);
        System.out.println("ResponseBody: " + response.getBody().print());
        softAssert.assertEquals(response.getStatusCode(), 401);
        ErrorMessageDto errorMessageDto = ErrorMessageDto.builder().build();
        if (response.getStatusCode() == 401) {
            errorMessageDto = response.as(ErrorMessageDto.class);
        }
        System.out.println("Error message: " + errorMessageDto.getError());
        //softAssert.assertTrue(errorMessageDto.getError().contains("Bad Request"));
        //softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("must not be blank"));
        softAssert.assertAll();
    }
}
