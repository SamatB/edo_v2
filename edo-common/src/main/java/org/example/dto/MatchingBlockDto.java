package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.utils.MatchingBlockType;


/**
 * DTO для сущности MatchingBlock
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для сущности MatchingBlock")
public class MatchingBlockDto {


    @Schema(description = "Номер по порядку согласования и отображения")
    private Long number;

    @Schema(description = "Тип блока согласования")
    private MatchingBlockType matchingBlockType;

}
