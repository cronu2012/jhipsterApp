package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CerfCompany} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CerfCompanyDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String relType;

    private CerfDTO cerf;

    private CompanyDTO company;

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

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CerfCompanyDTO)) {
            return false;
        }

        CerfCompanyDTO cerfCompanyDTO = (CerfCompanyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cerfCompanyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CerfCompanyDTO{" +
            "id=" + getId() +
            ", relType='" + getRelType() + "'" +
            ", cerf=" + getCerf() +
            ", company=" + getCompany() +
            "}";
    }
}
