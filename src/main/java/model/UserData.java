package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserData {

    private String name;
    private String email;
    private String password;

    public static UserData getRandomRegisterData(){
        Faker faker = new Faker();
        return new UserData(
                faker.name().name() + faker.name().lastName(),
                faker.name().username() +  RandomStringUtils.randomAlphanumeric(3) + "@" + RandomStringUtils.randomAlphabetic(5) + "." + RandomStringUtils.randomAlphabetic(2),
                RandomStringUtils.randomAlphanumeric(5) + "!"
        );
    }
}
