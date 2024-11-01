package dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor


public class ResponseMessageDto {
    public String getMessage;
    private String message;

}
