package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.utils.MatchingBlockType;

import java.util.List;
import java.util.Set;

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
     * Участники согласования
     */

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "matching_block_participant",
            joinColumns = @JoinColumn(name = "matching_block_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private Set<Participant> participants;

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


    /**
     * Связь с сущностью AgreementList.
     */

    @ManyToOne
    @Column(name = "agreement_list_id")
    private AgreementList agreementList;

}
