package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Std.
 */
@Entity
@Table(name = "std")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Std implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "std_no")
    private String stdNo;

    @Column(name = "std_ver")
    private String stdVer;

    @Column(name = "en_name")
    private String enName;

    @Column(name = "ch_name")
    private String chName;

    @Size(max = 10)
    @Column(name = "status", length = 10)
    private String status;

    @Column(name = "issu_dt")
    private LocalDate issuDt;

    @Column(name = "exp_dt")
    private LocalDate expDt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "std")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "prod", "std" }, allowSetters = true)
    private Set<ProdStd> prodStds = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "std")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cerf", "std" }, allowSetters = true)
    private Set<CerfStd> cerfStds = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "std")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "country", "std" }, allowSetters = true)
    private Set<CountryStd> countryStds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Std id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStdNo() {
        return this.stdNo;
    }

    public Std stdNo(String stdNo) {
        this.setStdNo(stdNo);
        return this;
    }

    public void setStdNo(String stdNo) {
        this.stdNo = stdNo;
    }

    public String getStdVer() {
        return this.stdVer;
    }

    public Std stdVer(String stdVer) {
        this.setStdVer(stdVer);
        return this;
    }

    public void setStdVer(String stdVer) {
        this.stdVer = stdVer;
    }

    public String getEnName() {
        return this.enName;
    }

    public Std enName(String enName) {
        this.setEnName(enName);
        return this;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getChName() {
        return this.chName;
    }

    public Std chName(String chName) {
        this.setChName(chName);
        return this;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    public String getStatus() {
        return this.status;
    }

    public Std status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getIssuDt() {
        return this.issuDt;
    }

    public Std issuDt(LocalDate issuDt) {
        this.setIssuDt(issuDt);
        return this;
    }

    public void setIssuDt(LocalDate issuDt) {
        this.issuDt = issuDt;
    }

    public LocalDate getExpDt() {
        return this.expDt;
    }

    public Std expDt(LocalDate expDt) {
        this.setExpDt(expDt);
        return this;
    }

    public void setExpDt(LocalDate expDt) {
        this.expDt = expDt;
    }

    public Set<ProdStd> getProdStds() {
        return this.prodStds;
    }

    public void setProdStds(Set<ProdStd> prodStds) {
        if (this.prodStds != null) {
            this.prodStds.forEach(i -> i.setStd(null));
        }
        if (prodStds != null) {
            prodStds.forEach(i -> i.setStd(this));
        }
        this.prodStds = prodStds;
    }

    public Std prodStds(Set<ProdStd> prodStds) {
        this.setProdStds(prodStds);
        return this;
    }

    public Std addProdStd(ProdStd prodStd) {
        this.prodStds.add(prodStd);
        prodStd.setStd(this);
        return this;
    }

    public Std removeProdStd(ProdStd prodStd) {
        this.prodStds.remove(prodStd);
        prodStd.setStd(null);
        return this;
    }

    public Set<CerfStd> getCerfStds() {
        return this.cerfStds;
    }

    public void setCerfStds(Set<CerfStd> cerfStds) {
        if (this.cerfStds != null) {
            this.cerfStds.forEach(i -> i.setStd(null));
        }
        if (cerfStds != null) {
            cerfStds.forEach(i -> i.setStd(this));
        }
        this.cerfStds = cerfStds;
    }

    public Std cerfStds(Set<CerfStd> cerfStds) {
        this.setCerfStds(cerfStds);
        return this;
    }

    public Std addCerfStd(CerfStd cerfStd) {
        this.cerfStds.add(cerfStd);
        cerfStd.setStd(this);
        return this;
    }

    public Std removeCerfStd(CerfStd cerfStd) {
        this.cerfStds.remove(cerfStd);
        cerfStd.setStd(null);
        return this;
    }

    public Set<CountryStd> getCountryStds() {
        return this.countryStds;
    }

    public void setCountryStds(Set<CountryStd> countryStds) {
        if (this.countryStds != null) {
            this.countryStds.forEach(i -> i.setStd(null));
        }
        if (countryStds != null) {
            countryStds.forEach(i -> i.setStd(this));
        }
        this.countryStds = countryStds;
    }

    public Std countryStds(Set<CountryStd> countryStds) {
        this.setCountryStds(countryStds);
        return this;
    }

    public Std addCountryStd(CountryStd countryStd) {
        this.countryStds.add(countryStd);
        countryStd.setStd(this);
        return this;
    }

    public Std removeCountryStd(CountryStd countryStd) {
        this.countryStds.remove(countryStd);
        countryStd.setStd(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Std)) {
            return false;
        }
        return getId() != null && getId().equals(((Std) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Std{" +
            "id=" + getId() +
            ", stdNo='" + getStdNo() + "'" +
            ", stdVer='" + getStdVer() + "'" +
            ", enName='" + getEnName() + "'" +
            ", chName='" + getChName() + "'" +
            ", status='" + getStatus() + "'" +
            ", issuDt='" + getIssuDt() + "'" +
            ", expDt='" + getExpDt() + "'" +
            "}";
    }
}
