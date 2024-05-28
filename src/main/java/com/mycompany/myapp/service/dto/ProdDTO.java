package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Prod} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProdDTO implements Serializable {

    private Long id;

    @Size(max = 30)
    private String prodNo;

    @Size(max = 100)
    private String enName;

    @Size(max = 100)
    private String chName;

    @Size(max = 20)
    private String hsCode;

    @Size(max = 20)
    private String cccCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProdNo() {
        return prodNo;
    }

    public void setProdNo(String prodNo) {
        this.prodNo = prodNo;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getChName() {
        return chName;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    public String getHsCode() {
        return hsCode;
    }

    public void setHsCode(String hsCode) {
        this.hsCode = hsCode;
    }

    public String getCccCode() {
        return cccCode;
    }

    public void setCccCode(String cccCode) {
        this.cccCode = cccCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProdDTO)) {
            return false;
        }

        ProdDTO prodDTO = (ProdDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, prodDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProdDTO{" +
            "id=" + getId() +
            ", prodNo='" + getProdNo() + "'" +
            ", enName='" + getEnName() + "'" +
            ", chName='" + getChName() + "'" +
            ", hsCode='" + getHsCode() + "'" +
            ", cccCode='" + getCccCode() + "'" +
            "}";
    }
}
