package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.enums.ApprovalBlockParticipantType;
import org.example.utils.MatchingBlockType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


import java.util.Set;

import static jakarta.persistence.EnumType.STRING;

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
     * Блок участников согласования
     */
    public static final String APPROVAL_BLOCK_PARTICIPANTS = "approval_block_type = 'PARTICIPANT_BLOCK'";

    /**
     * Блок подписантов
     */
    public static final String APPROVAL_BLOCK_SIGNERS = "approval_block_type = 'SIGNER_BLOCK'";

    /**
     * Участники согласования
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "matchingBlock")
    @Fetch(FetchMode.JOIN)
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
    @Enumerated(STRING)
    @Column(name = "type")
    private MatchingBlockType matchingBlockType;


    /**
     * Связь с сущностью AgreementList.
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agreement_list_id")
    private AgreementList agreementList;

    /**
     * Тип блока в листе согласования
     */
    @Column(name = "approval_block_type")
    @Enumerated(STRING)
    private ApprovalBlockParticipantType approvalBlockType;
}
