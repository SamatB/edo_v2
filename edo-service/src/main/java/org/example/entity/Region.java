package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * сущность Region представляет регион и федеральный округ
 */
@Entity
@Table(name = "regions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Region extends BaseEntity {

    /**
     * Наименование региона
     */
    @NotNull
    @Column(name = "region_name")
    private String regionName;

    /**
     * Наименование федерального округа
     */
    @NotNull
    @Column(name = "federal_district_name")
    private String federalDistrictName;

}
