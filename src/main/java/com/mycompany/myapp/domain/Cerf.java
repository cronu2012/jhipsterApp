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
 * A Cerf.
 */
@Entity
@Table(name = "cerf")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cerf implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 30)
    @Column(name = "cerf_no", length = 30)
    private String cerfNo;

    @Size(max = 20)
    @Column(name = "cerf_ver", length = 20)
    private String cerfVer;

    @Size(max = 10)
    @Column(name = "status", length = 10)
    private String status;

    @Lob
    @Column(name = "pdf")
    private byte[] pdf;

    @Column(name = "pdf_content_type")
    private String pdfContentType;

    @Column(name = "issu_dt")
    private LocalDate issuDt;

    @Column(name = "exp_dt")
    private LocalDate expDt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cerf")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cerf", "prod" }, allowSetters = true)
    private Set<CerfProd> cerfProds = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cerf")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cerf", "std" }, allowSetters = true)
    private Set<CerfStd> cerfStds = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cerf")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cerf", "mark" }, allowSetters = true)
    private Set<CerfMark> cerfMarks = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cerf")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cerf", "company" }, allowSetters = true)
    private Set<CerfCompany> cerfCompanies = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cerf")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "prod", "cerf", "company" }, allowSetters = true)
    private Set<FeeProdCerfCompany> feeProdCerfCompanies = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cerf")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "country", "cerf" }, allowSetters = true)
    private Set<CountryCert> countryCerts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cerf id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCerfNo() {
        return this.cerfNo;
    }

    public Cerf cerfNo(String cerfNo) {
        this.setCerfNo(cerfNo);
        return this;
    }

    public void setCerfNo(String cerfNo) {
        this.cerfNo = cerfNo;
    }

    public String getCerfVer() {
        return this.cerfVer;
    }

    public Cerf cerfVer(String cerfVer) {
        this.setCerfVer(cerfVer);
        return this;
    }

    public void setCerfVer(String cerfVer) {
        this.cerfVer = cerfVer;
    }

    public String getStatus() {
        return this.status;
    }

    public Cerf status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte[] getPdf() {
        return this.pdf;
    }

    public Cerf pdf(byte[] pdf) {
        this.setPdf(pdf);
        return this;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }

    public String getPdfContentType() {
        return this.pdfContentType;
    }

    public Cerf pdfContentType(String pdfContentType) {
        this.pdfContentType = pdfContentType;
        return this;
    }

    public void setPdfContentType(String pdfContentType) {
        this.pdfContentType = pdfContentType;
    }

    public LocalDate getIssuDt() {
        return this.issuDt;
    }

    public Cerf issuDt(LocalDate issuDt) {
        this.setIssuDt(issuDt);
        return this;
    }

    public void setIssuDt(LocalDate issuDt) {
        this.issuDt = issuDt;
    }

    public LocalDate getExpDt() {
        return this.expDt;
    }

    public Cerf expDt(LocalDate expDt) {
        this.setExpDt(expDt);
        return this;
    }

    public void setExpDt(LocalDate expDt) {
        this.expDt = expDt;
    }

    public Set<CerfProd> getCerfProds() {
        return this.cerfProds;
    }

    public void setCerfProds(Set<CerfProd> cerfProds) {
        if (this.cerfProds != null) {
            this.cerfProds.forEach(i -> i.setCerf(null));
        }
        if (cerfProds != null) {
            cerfProds.forEach(i -> i.setCerf(this));
        }
        this.cerfProds = cerfProds;
    }

    public Cerf cerfProds(Set<CerfProd> cerfProds) {
        this.setCerfProds(cerfProds);
        return this;
    }

    public Cerf addCerfProd(CerfProd cerfProd) {
        this.cerfProds.add(cerfProd);
        cerfProd.setCerf(this);
        return this;
    }

    public Cerf removeCerfProd(CerfProd cerfProd) {
        this.cerfProds.remove(cerfProd);
        cerfProd.setCerf(null);
        return this;
    }

    public Set<CerfStd> getCerfStds() {
        return this.cerfStds;
    }

    public void setCerfStds(Set<CerfStd> cerfStds) {
        if (this.cerfStds != null) {
            this.cerfStds.forEach(i -> i.setCerf(null));
        }
        if (cerfStds != null) {
            cerfStds.forEach(i -> i.setCerf(this));
        }
        this.cerfStds = cerfStds;
    }

    public Cerf cerfStds(Set<CerfStd> cerfStds) {
        this.setCerfStds(cerfStds);
        return this;
    }

    public Cerf addCerfStd(CerfStd cerfStd) {
        this.cerfStds.add(cerfStd);
        cerfStd.setCerf(this);
        return this;
    }

    public Cerf removeCerfStd(CerfStd cerfStd) {
        this.cerfStds.remove(cerfStd);
        cerfStd.setCerf(null);
        return this;
    }

    public Set<CerfMark> getCerfMarks() {
        return this.cerfMarks;
    }

    public void setCerfMarks(Set<CerfMark> cerfMarks) {
        if (this.cerfMarks != null) {
            this.cerfMarks.forEach(i -> i.setCerf(null));
        }
        if (cerfMarks != null) {
            cerfMarks.forEach(i -> i.setCerf(this));
        }
        this.cerfMarks = cerfMarks;
    }

    public Cerf cerfMarks(Set<CerfMark> cerfMarks) {
        this.setCerfMarks(cerfMarks);
        return this;
    }

    public Cerf addCerfMark(CerfMark cerfMark) {
        this.cerfMarks.add(cerfMark);
        cerfMark.setCerf(this);
        return this;
    }

    public Cerf removeCerfMark(CerfMark cerfMark) {
        this.cerfMarks.remove(cerfMark);
        cerfMark.setCerf(null);
        return this;
    }

    public Set<CerfCompany> getCerfCompanies() {
        return this.cerfCompanies;
    }

    public void setCerfCompanies(Set<CerfCompany> cerfCompanies) {
        if (this.cerfCompanies != null) {
            this.cerfCompanies.forEach(i -> i.setCerf(null));
        }
        if (cerfCompanies != null) {
            cerfCompanies.forEach(i -> i.setCerf(this));
        }
        this.cerfCompanies = cerfCompanies;
    }

    public Cerf cerfCompanies(Set<CerfCompany> cerfCompanies) {
        this.setCerfCompanies(cerfCompanies);
        return this;
    }

    public Cerf addCerfCompany(CerfCompany cerfCompany) {
        this.cerfCompanies.add(cerfCompany);
        cerfCompany.setCerf(this);
        return this;
    }

    public Cerf removeCerfCompany(CerfCompany cerfCompany) {
        this.cerfCompanies.remove(cerfCompany);
        cerfCompany.setCerf(null);
        return this;
    }

    public Set<FeeProdCerfCompany> getFeeProdCerfCompanies() {
        return this.feeProdCerfCompanies;
    }

    public void setFeeProdCerfCompanies(Set<FeeProdCerfCompany> feeProdCerfCompanies) {
        if (this.feeProdCerfCompanies != null) {
            this.feeProdCerfCompanies.forEach(i -> i.setCerf(null));
        }
        if (feeProdCerfCompanies != null) {
            feeProdCerfCompanies.forEach(i -> i.setCerf(this));
        }
        this.feeProdCerfCompanies = feeProdCerfCompanies;
    }

    public Cerf feeProdCerfCompanies(Set<FeeProdCerfCompany> feeProdCerfCompanies) {
        this.setFeeProdCerfCompanies(feeProdCerfCompanies);
        return this;
    }

    public Cerf addFeeProdCerfCompany(FeeProdCerfCompany feeProdCerfCompany) {
        this.feeProdCerfCompanies.add(feeProdCerfCompany);
        feeProdCerfCompany.setCerf(this);
        return this;
    }

    public Cerf removeFeeProdCerfCompany(FeeProdCerfCompany feeProdCerfCompany) {
        this.feeProdCerfCompanies.remove(feeProdCerfCompany);
        feeProdCerfCompany.setCerf(null);
        return this;
    }

    public Set<CountryCert> getCountryCerts() {
        return this.countryCerts;
    }

    public void setCountryCerts(Set<CountryCert> countryCerts) {
        if (this.countryCerts != null) {
            this.countryCerts.forEach(i -> i.setCerf(null));
        }
        if (countryCerts != null) {
            countryCerts.forEach(i -> i.setCerf(this));
        }
        this.countryCerts = countryCerts;
    }

    public Cerf countryCerts(Set<CountryCert> countryCerts) {
        this.setCountryCerts(countryCerts);
        return this;
    }

    public Cerf addCountryCert(CountryCert countryCert) {
        this.countryCerts.add(countryCert);
        countryCert.setCerf(this);
        return this;
    }

    public Cerf removeCountryCert(CountryCert countryCert) {
        this.countryCerts.remove(countryCert);
        countryCert.setCerf(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cerf)) {
            return false;
        }
        return getId() != null && getId().equals(((Cerf) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cerf{" +
            "id=" + getId() +
            ", cerfNo='" + getCerfNo() + "'" +
            ", cerfVer='" + getCerfVer() + "'" +
            ", status='" + getStatus() + "'" +
            ", pdf='" + getPdf() + "'" +
            ", pdfContentType='" + getPdfContentType() + "'" +
            ", issuDt='" + getIssuDt() + "'" +
            ", expDt='" + getExpDt() + "'" +
            "}";
    }
}
