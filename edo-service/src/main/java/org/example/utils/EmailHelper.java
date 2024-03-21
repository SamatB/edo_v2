package org.example.utils;

import jakarta.validation.constraints.NotNull;
import org.example.dto.EmailDto;
import org.example.dto.ParticipantDto;
import org.example.entity.Participant;

/**
 * Вспомогательный класс для работы с электронной почтой.
 */

public class EmailHelper {

    /**
     * Метод для генерации DTO для отправки электронной почты с уведомлением о приходе Листа Согласования.
     * @param participant сущность участника согласования
     * @param appealNumber номер обращения
     * @return DTO для электронной почты с уведомлением для участника
     */
    public static EmailDto getAgreementListEmailDto(Participant participant, String appealNumber) {
        EmailDto emailDto = new EmailDto();
        String emailText = "Добрый день, " + participant.getEmployee().getFioNominative() + "! На ваше имя пришёл Лист Согласования по обращению номер " + appealNumber + ".";

        emailDto.setSubject("Уведомление");
        emailDto.setBody(emailText);
        emailDto.setTo(participant.getEmployee().getEmail());

        return emailDto;
    }

    public static EmailDto getAgreementListEmailDto(@NotNull ParticipantDto participant, @NotNull String appealNumber) {
        EmailDto emailDto = new EmailDto();
        String emailText = "Добрый день, " + participant.getEmployeeDto().getFioNominative() + "! На ваше имя пришёл Лист Согласования по обращению номер " + appealNumber + ".";

        emailDto.setSubject("Уведомление");
        emailDto.setBody(emailText);
        emailDto.setTo(participant.getEmployeeDto().getEmail());

        return emailDto;
    }
}
