package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.utils.ParticipantType;

import java.time.ZonedDateTime;


/**
 * Сущность Participant представляет участника согласования
 */
@Entity
@Table(name = "participant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Participant extends BaseEntity {

    /**
     * Тип участника согласования
     */
    @NotNull
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ParticipantType type;

    /**
     * Дата создания участнкиа
     */
    @NotNull
    @Column(name = "create_date")
    private ZonedDateTime createDate;

    /**
     * Дата, до которой должно быть исполнено
     */
    @NotNull
    @Column(name = "until_date")
    private ZonedDateTime untilDate;

    /**
     * Дата получения обращения
     */
    @NotNull
    @Column(name = "accept_date")
    private ZonedDateTime acceptDate;

    /**
     * Дата завершения действия
     */
    @NotNull
    @Column(name = "expired_date")
    private ZonedDateTime expiredDate;

    /**
     * Номер по порядку согласования и порядку отображения на UI
     */
    @NotNull
    @Size(min = 1)
    @Column(name = "number")
    private Long number;

    /**
     * Связь с сотрудником
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    /**
     * Связь с блоком согласующих
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matching_block_id")
    private MatchingBlock matchingBlock;
}
