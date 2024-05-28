package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Wcc412View} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Wcc412ViewDTO implements Serializable {

    private Long id;

    private Long cerfId;

    private String countryChName;

    private String cerfNo;

    private String cerfVer;

    private String cerfStatus;

    private Long countryId;

    private String prodNo;

    private String prodChName;

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

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getProdNo() {
        return prodNo;
    }

    public void setProdNo(String prodNo) {
        this.prodNo = prodNo;
    }

    public String getProdChName() {
        return prodChName;
    }

    public void setProdChName(String prodChName) {
        this.prodChName = prodChName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wcc412ViewDTO)) {
            return false;
        }

        Wcc412ViewDTO wcc412ViewDTO = (Wcc412ViewDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, wcc412ViewDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wcc412ViewDTO{" +
            "id=" + getId() +
            ", cerfId=" + getCerfId() +
            ", countryChName='" + getCountryChName() + "'" +
            ", cerfNo='" + getCerfNo() + "'" +
            ", cerfVer='" + getCerfVer() + "'" +
            ", cerfStatus='" + getCerfStatus() + "'" +
            ", countryId=" + getCountryId() +
            ", prodNo='" + getProdNo() + "'" +
            ", prodChName='" + getProdChName() + "'" +
            "}";
    }
}
