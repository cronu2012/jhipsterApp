package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ProdCountry} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProdCountryDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String relType;

    private ProdDTO prod;

    private CountryDTO country;

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

    public ProdDTO getProd() {
        return prod;
    }

    public void setProd(ProdDTO prod) {
        this.prod = prod;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProdCountryDTO)) {
            return false;
        }

        ProdCountryDTO prodCountryDTO = (ProdCountryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, prodCountryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProdCountryDTO{" +
            "id=" + getId() +
            ", relType='" + getRelType() + "'" +
            ", prod=" + getProd() +
            ", country=" + getCountry() +
            "}";
    }
}
