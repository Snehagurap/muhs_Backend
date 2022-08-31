package bean;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Token {

    @NotNull(message = "Token is mandatory")
    private String token;
}
