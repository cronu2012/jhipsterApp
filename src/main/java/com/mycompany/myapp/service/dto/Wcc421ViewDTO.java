package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Wcc421View} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Wcc421ViewDTO implements Serializable {

    private Long id;

    private Long cerfId;

    private String countryChName;

    private String cerfNo;

    private String cerfVer;

    private String cerfStatus;

    private String companyChName;

    private String relType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCerfId() {
        return cerfId;
    }

    public void setCerfId(Long cerfId) {
        this.cerfId = cerfId;
    }

    public String getCountryChName() {
        return countryChName;
    }

    public void setCountryChName(String countryChName) {
        this.countryChName = countryChName;
    }

    public String getCerfNo() {
        return cerfNo;
    }

    public void setCerfNo(String cerfNo) {
        this.cerfNo = cerfNo;
    }

    public String getCerfVer() {
        return cerfVer;
    }

    public void setCerfVer(String cerfVer) {
        this.cerfVer = cerfVer;
    }

    public String getCerfStatus() {
        return cerfStatus;
    }

    public void setCerfStatus(String cerfStatus) {
        this.cerfStatus = cerfStatus;
    }

    public String getCompanyChName() {
        return companyChName;
    }

    public void setCompanyChName(String companyChName) {
        this.companyChName = companyChName;
    }

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wcc421ViewDTO)) {
            return false;
        }

        Wcc421ViewDTO wcc421ViewDTO = (Wcc421ViewDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, wcc421ViewDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wcc421ViewDTO{" +
            "id=" + getId() +
            ", cerfId=" + getCerfId() +
            ", countryChName='" + getCountryChName() + "'" +
            ", cerfNo='" + getCerfNo() + "'" +
            ", cerfVer='" + getCerfVer() + "'" +
            ", cerfStatus='" + getCerfStatus() + "'" +
            ", companyChName='" + getCompanyChName() + "'" +
            ", relType='" + getRelType() + "'" +
            "}";
    }
}
