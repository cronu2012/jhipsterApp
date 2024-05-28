package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CountryMark} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CountryMarkDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String relType;

    private CountryDTO country;

    private MarkDTO mark;

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

    public MarkDTO getMark() {
        return mark;
    }

    public void setMark(MarkDTO mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CountryMarkDTO)) {
            return false;
        }

        CountryMarkDTO countryMarkDTO = (CountryMarkDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, countryMarkDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CountryMarkDTO{" +
            "id=" + getId() +
            ", relType='" + getRelType() + "'" +
            ", country=" + getCountry() +
            ", mark=" + getMark() +
            "}";
    }
}
