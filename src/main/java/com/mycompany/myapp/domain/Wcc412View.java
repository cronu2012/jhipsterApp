package com.mycompany.myapp.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Wcc412View.
 */
@Entity
@Table(name = "wcc_412_view")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Wcc412View implements Serializable {

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

    @Column(name = "country_id")
    private Long countryId;

    @Column(name = "prod_no")
    private String prodNo;

    @Column(name = "prod_ch_name")
    private String prodChName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Wcc412View id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCerfId() {
        return this.cerfId;
    }

    public Wcc412View cerfId(Long cerfId) {
        this.setCerfId(cerfId);
        return this;
    }

    public void setCerfId(Long cerfId) {
        this.cerfId = cerfId;
    }

    public String getCountryChName() {
        return this.countryChName;
    }

    public Wcc412View countryChName(String countryChName) {
        this.setCountryChName(countryChName);
        return this;
    }

    public void setCountryChName(String countryChName) {
        this.countryChName = countryChName;
    }

    public String getCerfNo() {
        return this.cerfNo;
    }

    public Wcc412View cerfNo(String cerfNo) {
        this.setCerfNo(cerfNo);
        return this;
    }

    public void setCerfNo(String cerfNo) {
        this.cerfNo = cerfNo;
    }

    public String getCerfVer() {
        return this.cerfVer;
    }

    public Wcc412View cerfVer(String cerfVer) {
        this.setCerfVer(cerfVer);
        return this;
    }

    public void setCerfVer(String cerfVer) {
        this.cerfVer = cerfVer;
    }

    public String getCerfStatus() {
        return this.cerfStatus;
    }

    public Wcc412View cerfStatus(String cerfStatus) {
        this.setCerfStatus(cerfStatus);
        return this;
    }

    public void setCerfStatus(String cerfStatus) {
        this.cerfStatus = cerfStatus;
    }

    public Long getCountryId() {
        return this.countryId;
    }

    public Wcc412View countryId(Long countryId) {
        this.setCountryId(countryId);
        return this;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getProdNo() {
        return this.prodNo;
    }

    public Wcc412View prodNo(String prodNo) {
        this.setProdNo(prodNo);
        return this;
    }

    public void setProdNo(String prodNo) {
        this.prodNo = prodNo;
    }

    public String getProdChName() {
        return this.prodChName;
    }

    public Wcc412View prodChName(String prodChName) {
        this.setProdChName(prodChName);
        return this;
    }

    public void setProdChName(String prodChName) {
        this.prodChName = prodChName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wcc412View)) {
            return false;
        }
        return getId() != null && getId().equals(((Wcc412View) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wcc412View{" +
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
