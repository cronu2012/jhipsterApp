package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CerfStd} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CerfStdDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String relType;

    private CerfDTO cerf;

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

    public CerfDTO getCerf() {
        return cerf;
    }

    public void setCerf(CerfDTO cerf) {
        this.cerf = cerf;
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
        if (!(o instanceof CerfStdDTO)) {
            return false;
        }

        CerfStdDTO cerfStdDTO = (CerfStdDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cerfStdDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CerfStdDTO{" +
            "id=" + getId() +
            ", relType='" + getRelType() + "'" +
            ", cerf=" + getCerf() +
            ", std=" + getStd() +
            "}";
    }
}
