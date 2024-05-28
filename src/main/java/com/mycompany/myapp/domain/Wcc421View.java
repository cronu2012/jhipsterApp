package com.mycompany.myapp.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Wcc421View.
 */
@Entity
@Table(name = "wcc_421_view")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Wcc421View implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cerf_id")
    private Long cerfId;

    @Column(name = "country_ch_name")
    private String countryChName;

    @Column(name = "cerf_no")
    private String cerfNo;

    @Column(name = "cerf_ver")
    private String cerfVer;

    @Column(name = "cerf_status")
    private String cerfStatus;

    @Column(name = "company_ch_name")
    private String companyChName;

    @Column(name = "rel_type")
    private String relType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Wcc421View id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCerfId() {
        return this.cerfId;
    }

    public Wcc421View cerfId(Long cerfId) {
        this.setCerfId(cerfId);
        return this;
    }

    public void setCerfId(Long cerfId) {
        this.cerfId = cerfId;
    }

    public String getCountryChName() {
        return this.countryChName;
    }

    public Wcc421View countryChName(String countryChName) {
        this.setCountryChName(countryChName);
        return this;
    }

    public void setCountryChName(String countryChName) {
        this.countryChName = countryChName;
    }

    public String getCerfNo() {
        return this.cerfNo;
    }

    public Wcc421View cerfNo(String cerfNo) {
        this.setCerfNo(cerfNo);
        return this;
    }

    public void setCerfNo(String cerfNo) {
        this.cerfNo = cerfNo;
    }

    public String getCerfVer() {
        return this.cerfVer;
    }

    public Wcc421View cerfVer(String cerfVer) {
        this.setCerfVer(cerfVer);
        return this;
    }

    public void setCerfVer(String cerfVer) {
        this.cerfVer = cerfVer;
    }

    public String getCerfStatus() {
        return this.cerfStatus;
    }

    public Wcc421View cerfStatus(String cerfStatus) {
        this.setCerfStatus(cerfStatus);
        return this;
    }

    public void setCerfStatus(String cerfStatus) {
        this.cerfStatus = cerfStatus;
    }

    public String getCompanyChName() {
        return this.companyChName;
    }

    public Wcc421View companyChName(String companyChName) {
        this.setCompanyChName(companyChName);
        return this;
    }

    public void setCompanyChName(String companyChName) {
        this.companyChName = companyChName;
    }

    public String getRelType() {
        return this.relType;
    }

    public Wcc421View relType(String relType) {
        this.setRelType(relType);
        return this;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wcc421View)) {
            return false;
        }
        return getId() != null && getId().equals(((Wcc421View) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wcc421View{" +
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
