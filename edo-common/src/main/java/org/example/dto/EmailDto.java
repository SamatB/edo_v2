package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO-класс для передачи письма электронной почты.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Email")
public class EmailDto implements Serializable {
    @Schema(description = "Идентификатор получателя")
    private Long employeeId;

    @Schema(description = "Адрес электронной почты отправителя")
    private String from;

    @Schema(description = "Адрес электронной почты получателя")
    private String to;

    @Schema(description = "Тема письма")
    private String subject;

    @Schema(description = "Тело письма")
    private String body;
}
