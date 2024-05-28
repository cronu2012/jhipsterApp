package com.mycompany.myapp.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Cerf} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CerfDTO implements Serializable {

    private Long id;

    @Size(max = 30)
    private String cerfNo;

    @Size(max = 20)
    private String cerfVer;

    @Size(max = 10)
    private String status;

    @Lob
    private byte[] pdf;

    private String pdfContentType;

    private LocalDate issuDt;

    private LocalDate expDt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte[] getPdf() {
        return pdf;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }

    public String getPdfContentType() {
        return pdfContentType;
    }

    public void setPdfContentType(String pdfContentType) {
        this.pdfContentType = pdfContentType;
    }

    public LocalDate getIssuDt() {
        return issuDt;
    }

    public void setIssuDt(LocalDate issuDt) {
        this.issuDt = issuDt;
    }

    public LocalDate getExpDt() {
        return expDt;
    }

    public void setExpDt(LocalDate expDt) {
        this.expDt = expDt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CerfDTO)) {
            return false;
        }

        CerfDTO cerfDTO = (CerfDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cerfDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CerfDTO{" +
            "id=" + getId() +
            ", cerfNo='" + getCerfNo() + "'" +
            ", cerfVer='" + getCerfVer() + "'" +
            ", status='" + getStatus() + "'" +
            ", pdf='" + getPdf() + "'" +
            ", issuDt='" + getIssuDt() + "'" +
            ", expDt='" + getExpDt() + "'" +
            "}";
    }
}
