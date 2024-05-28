package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CerfProd} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CerfProdDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String relType;

    private CerfDTO cerf;

    private ProdDTO prod;

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

    public CerfDTO getCerf() {
        return cerf;
    }

    public void setCerf(CerfDTO cerf) {
        this.cerf = cerf;
    }

    public ProdDTO getProd() {
        return prod;
    }

    public void setProd(ProdDTO prod) {
        this.prod = prod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CerfProdDTO)) {
            return false;
        }

        CerfProdDTO cerfProdDTO = (CerfProdDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cerfProdDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CerfProdDTO{" +
            "id=" + getId() +
            ", relType='" + getRelType() + "'" +
            ", cerf=" + getCerf() +
            ", prod=" + getProd() +
            "}";
    }
}
