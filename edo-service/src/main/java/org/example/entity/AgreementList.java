package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import java.time.ZonedDateTime;
import java.util.Set;

import static org.example.entity.MatchingBlock.APPROVAL_BLOCK_PARTICIPANTS;
import static org.example.entity.MatchingBlock.APPROVAL_BLOCK_SIGNERS;

/**
 * Класс, представляющий Листа согласования.
 */
@Entity
@Table(name = "agreement_list")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class AgreementList extends BaseEntity {
    /**
     * Дата создания
     */
    @NotNull
    @Column(name = "creation_date")
    @CreationTimestamp
    private ZonedDateTime creationDate;

    /**
     * Дата направления на согласование
     */
    @Column(name = "sent_approval_date")
    private ZonedDateTime sentApprovalDate;

    /**
     * Дата подписания
     */
    @Column(name = "sign_date")
    private ZonedDateTime signDate;

    /**
     * Дата возврата на доработку
     */
    @Column(name = "returned_date")
    private ZonedDateTime returnedDate;

    /**
     * Дата обработки возврата
     */
    @Column(name = "refund_processing_date")
    private ZonedDateTime refundProcessingDate;

    /**
     * Дата архивности
     */
    @Column(name = "archive_date")
    private ZonedDateTime archiveDate;

    /**
     * Комментарий инициатора
     */
    @Column(name = "comment")
    private String comment;

    /**
     * Appeal (Обращение)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appeal_id")
    @Fetch(FetchMode.JOIN)
    private Appeal appeal;


    /**
     * Инициатор запуска листа согласования
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id")
    @Fetch(FetchMode.JOIN)
    private Participant initiator;


    /**
     * Блоки подписантов
     */

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "agreementList")
    @Where(clause = APPROVAL_BLOCK_SIGNERS)
    @Fetch(FetchMode.JOIN)
    private Set<MatchingBlock> signatory;


    /**
     * Блоки согласующих
     */

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "agreementList")
    @Where(clause = APPROVAL_BLOCK_PARTICIPANTS)
    @Fetch(FetchMode.JOIN)
    private Set<MatchingBlock> coordinating;
}
