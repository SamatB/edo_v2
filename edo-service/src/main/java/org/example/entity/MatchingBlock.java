package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.utils.MatchingBlockType;

/**
 * Блок согласования
 */

@Entity
@Table(name = "matching_block")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MatchingBlock extends BaseEntity {


    /**
     * Номер по порядку согласования и отображения.
     */

    @NotNull
    @Column(name = "number")
    private Long number;


    /**
     * Тип блока согласования(параллельный, очередный).
     */

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private MatchingBlockType matchingBlockType;

}
