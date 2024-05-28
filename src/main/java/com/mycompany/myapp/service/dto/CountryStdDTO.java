package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CountryStd} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CountryStdDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String relType;

    private CountryDTO country;

    private StdDTO std;

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

    public StdDTO getStd() {
        return std;
    }

    public void setStd(StdDTO std) {
        this.std = std;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CountryStdDTO)) {
            return false;
        }

        CountryStdDTO countryStdDTO = (CountryStdDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, countryStdDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CountryStdDTO{" +
            "id=" + getId() +
            ", relType='" + getRelType() + "'" +
            ", country=" + getCountry() +
            ", std=" + getStd() +
            "}";
    }
}
