package org.example.dto;

import lombok.Data;

/**
 * Класс, представляющий преобразованные JSON данные из удаленного хранилища.
 */
@Data
public class ExternalDataDto {
    private String gender;
    private Name name;
    private Location location;
    private String email;
    private Login login;
    private Dob dob;
    private Registered registered;
    private String phone;
    private String cell;
    private Id id;
    private Picture picture;
    private String nat;
    private Company company;
    private boolean is_deleted;

    /**
     * Внутренний класс, представляющий информацию о компании.
     */
    @Data
    public static class Company {
        private Id id;
        private String name;
        private Location location;
        private boolean is_deleted;
    }

    /**
     * Внутренний класс, представляющий имя.
     */
    @Data
    public static class Name {
        private String middle;
        private String first;
        private String last;
    }

    /**
     * Внутренний класс, представляющий адрес местоположения.
     */
    @Data
    public static class Location {
        private Street street;
        private String city;
        private String state;
        private String country;
        private String postcode;
        private Coordinates coordinates;
        private Timezone timezone;
    }

    /**
     * Внутренний класс, представляющий улицу.
     */
    @Data
    public static class Street {
        private String number;
        private String name;
    }

    /**
     * Внутренний класс, представляющий координаты местоположения.
     */
    @Data
    public static class Coordinates {
        private String latitude;
        private String longitude;
    }

    /**
     * Внутренний класс, представляющий информацию о часовом поясе.
     */
    @Data
    public static class Timezone {
        private String offset;
        private String description;
    }

    /**
     * Внутренний класс, представляющий информацию для входа.
     */
    @Data
    public static class Login {
        private String uuid;
        private String username;
        private String password;
        private String salt;
        private String md5;
        private String sha1;
        private String sha256;
    }

    /**
     * Внутренний класс, представляющий дату рождения.
     */
    @Data
    public static class Dob {
        private String date;
        private String age;
    }

    /**
     * Внутренний класс, представляющий зарегистрированную дату.
     */
    @Data
    public static class Registered {
        private String date;
        private String age;
    }

    /**
     * Внутренний класс, представляющий идентификатор.
     */
    @Data
    public static class Id {
        private String name;
        private String value;
    }

    /**
     * Внутренний класс, представляющий изображение.
     */
    @Data
    public static class Picture {
        private String large;
        private String medium;
        private String thumbnail;
    }
}