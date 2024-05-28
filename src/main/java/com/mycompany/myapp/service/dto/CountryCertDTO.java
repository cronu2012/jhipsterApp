package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CountryCert} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CountryCertDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String relType;

    private CountryDTO country;

    private CerfDTO cerf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    public CerfDTO getCerf() {
        return cerf;
    }

    public void setCerf(CerfDTO cerf) {
        this.cerf = cerf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CountryCertDTO)) {
            return false;
        }

        CountryCertDTO countryCertDTO = (CountryCertDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, countryCertDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CountryCertDTO{" +
            "id=" + getId() +
            ", relType='" + getRelType() + "'" +
            ", country=" + getCountry() +
            ", cerf=" + getCerf() +
            "}";
    }
}
