package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
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
    private Appeal appeal;
}
