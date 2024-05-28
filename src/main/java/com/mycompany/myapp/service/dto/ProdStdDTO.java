package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ProdStd} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProdStdDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String relType;

    private ProdDTO prod;

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

    public ProdDTO getProd() {
        return prod;
    }

    public void setProd(ProdDTO prod) {
        this.prod = prod;
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
        if (!(o instanceof ProdStdDTO)) {
            return false;
        }

        ProdStdDTO prodStdDTO = (ProdStdDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, prodStdDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProdStdDTO{" +
            "id=" + getId() +
            ", relType='" + getRelType() + "'" +
            ", prod=" + getProd() +
            ", std=" + getStd() +
            "}";
    }
}
