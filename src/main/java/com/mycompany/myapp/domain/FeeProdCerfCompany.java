package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FeeProdCerfCompany.
 */
@Entity
@Table(name = "fee_prod_cerf_company")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FeeProdCerfCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fee")
    private Long fee;

    @Size(max = 10)
    @Column(name = "fee_type", length = 10)
    private String feeType;

    @Column(name = "fee_dt")
    private LocalDate feeDt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "prodCountries", "prodStds", "cerfProds", "feeProdCerfCompanies", "prodStickers" }, allowSetters = true)
    private Prod prod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "cerfProds", "cerfStds", "cerfMarks", "cerfCompanies", "feeProdCerfCompanies", "countryCerts" },
        allowSetters = true
    )
    private Cerf cerf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "cerfCompanies", "feeProdCerfCompanies" }, allowSetters = true)
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FeeProdCerfCompany id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFee() {
        return this.fee;
    }

    public FeeProdCerfCompany fee(Long fee) {
        this.setFee(fee);
        return this;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public String getFeeType() {
        return this.feeType;
    }

    public FeeProdCerfCompany feeType(String feeType) {
        this.setFeeType(feeType);
        return this;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public LocalDate getFeeDt() {
        return this.feeDt;
    }

    public FeeProdCerfCompany feeDt(LocalDate feeDt) {
        this.setFeeDt(feeDt);
        return this;
    }

    public void setFeeDt(LocalDate feeDt) {
        this.feeDt = feeDt;
    }

    public Prod getProd() {
        return this.prod;
    }

    public void setProd(Prod prod) {
        this.prod = prod;
    }

    public FeeProdCerfCompany prod(Prod prod) {
        this.setProd(prod);
        return this;
    }

    public Cerf getCerf() {
        return this.cerf;
    }

    public void setCerf(Cerf cerf) {
        this.cerf = cerf;
    }

    public FeeProdCerfCompany cerf(Cerf cerf) {
        this.setCerf(cerf);
        return this;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public FeeProdCerfCompany company(Company company) {
        this.setCompany(company);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FeeProdCerfCompany)) {
            return false;
        }
        return getId() != null && getId().equals(((FeeProdCerfCompany) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeeProdCerfCompany{" +
            "id=" + getId() +
            ", fee=" + getFee() +
            ", feeType='" + getFeeType() + "'" +
            ", feeDt='" + getFeeDt() + "'" +
            "}";
    }
}
