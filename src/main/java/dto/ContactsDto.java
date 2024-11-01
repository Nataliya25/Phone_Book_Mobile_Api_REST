package dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ContactsDto {
    private ContactDTOLombok [] contacts;
}
