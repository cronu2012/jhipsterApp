package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Prod.
 */
@Entity
@Table(name = "prod")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Prod implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 30)
    @Column(name = "prod_no", length = 30)
    private String prodNo;

    @Size(max = 100)
    @Column(name = "en_name", length = 100)
    private String enName;

    @Size(max = 100)
    @Column(name = "ch_name", length = 100)
    private String chName;

    @Size(max = 20)
    @Column(name = "hs_code", length = 20)
    private String hsCode;

    @Size(max = 20)
    @Column(name = "ccc_code", length = 20)
    private String cccCode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "prod")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "prod", "country" }, allowSetters = true)
    private Set<ProdCountry> prodCountries = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "prod")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "prod", "std" }, allowSetters = true)
    private Set<ProdStd> prodStds = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "prod")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cerf", "prod" }, allowSetters = true)
    private Set<CerfProd> cerfProds = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "prod")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "prod", "cerf", "company" }, allowSetters = true)
    private Set<FeeProdCerfCompany> feeProdCerfCompanies = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "prod")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "prod", "sticker" }, allowSetters = true)
    private Set<ProdSticker> prodStickers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Prod id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProdNo() {
        return this.prodNo;
    }

    public Prod prodNo(String prodNo) {
        this.setProdNo(prodNo);
        return this;
    }

    public void setProdNo(String prodNo) {
        this.prodNo = prodNo;
    }

    public String getEnName() {
        return this.enName;
    }

    public Prod enName(String enName) {
        this.setEnName(enName);
        return this;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getChName() {
        return this.chName;
    }

    public Prod chName(String chName) {
        this.setChName(chName);
        return this;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    public String getHsCode() {
        return this.hsCode;
    }

    public Prod hsCode(String hsCode) {
        this.setHsCode(hsCode);
        return this;
    }

    public void setHsCode(String hsCode) {
        this.hsCode = hsCode;
    }

    public String getCccCode() {
        return this.cccCode;
    }

    public Prod cccCode(String cccCode) {
        this.setCccCode(cccCode);
        return this;
    }

    public void setCccCode(String cccCode) {
        this.cccCode = cccCode;
    }

    public Set<ProdCountry> getProdCountries() {
        return this.prodCountries;
    }

    public void setProdCountries(Set<ProdCountry> prodCountries) {
        if (this.prodCountries != null) {
            this.prodCountries.forEach(i -> i.setProd(null));
        }
        if (prodCountries != null) {
            prodCountries.forEach(i -> i.setProd(this));
        }
        this.prodCountries = prodCountries;
    }

    public Prod prodCountries(Set<ProdCountry> prodCountries) {
        this.setProdCountries(prodCountries);
        return this;
    }

    public Prod addProdCountry(ProdCountry prodCountry) {
        this.prodCountries.add(prodCountry);
        prodCountry.setProd(this);
        return this;
    }

    public Prod removeProdCountry(ProdCountry prodCountry) {
        this.prodCountries.remove(prodCountry);
        prodCountry.setProd(null);
        return this;
    }

    public Set<ProdStd> getProdStds() {
        return this.prodStds;
    }

    public void setProdStds(Set<ProdStd> prodStds) {
        if (this.prodStds != null) {
            this.prodStds.forEach(i -> i.setProd(null));
        }
        if (prodStds != null) {
            prodStds.forEach(i -> i.setProd(this));
        }
        this.prodStds = prodStds;
    }

    public Prod prodStds(Set<ProdStd> prodStds) {
        this.setProdStds(prodStds);
        return this;
    }

    public Prod addProdStd(ProdStd prodStd) {
        this.prodStds.add(prodStd);
        prodStd.setProd(this);
        return this;
    }

    public Prod removeProdStd(ProdStd prodStd) {
        this.prodStds.remove(prodStd);
        prodStd.setProd(null);
        return this;
    }

    public Set<CerfProd> getCerfProds() {
        return this.cerfProds;
    }

    public void setCerfProds(Set<CerfProd> cerfProds) {
        if (this.cerfProds != null) {
            this.cerfProds.forEach(i -> i.setProd(null));
        }
        if (cerfProds != null) {
            cerfProds.forEach(i -> i.setProd(this));
        }
        this.cerfProds = cerfProds;
    }

    public Prod cerfProds(Set<CerfProd> cerfProds) {
        this.setCerfProds(cerfProds);
        return this;
    }

    public Prod addCerfProd(CerfProd cerfProd) {
        this.cerfProds.add(cerfProd);
        cerfProd.setProd(this);
        return this;
    }

    public Prod removeCerfProd(CerfProd cerfProd) {
        this.cerfProds.remove(cerfProd);
        cerfProd.setProd(null);
        return this;
    }

    public Set<FeeProdCerfCompany> getFeeProdCerfCompanies() {
        return this.feeProdCerfCompanies;
    }

    public void setFeeProdCerfCompanies(Set<FeeProdCerfCompany> feeProdCerfCompanies) {
        if (this.feeProdCerfCompanies != null) {
            this.feeProdCerfCompanies.forEach(i -> i.setProd(null));
        }
        if (feeProdCerfCompanies != null) {
            feeProdCerfCompanies.forEach(i -> i.setProd(this));
        }
        this.feeProdCerfCompanies = feeProdCerfCompanies;
    }

    public Prod feeProdCerfCompanies(Set<FeeProdCerfCompany> feeProdCerfCompanies) {
        this.setFeeProdCerfCompanies(feeProdCerfCompanies);
        return this;
    }

    public Prod addFeeProdCerfCompany(FeeProdCerfCompany feeProdCerfCompany) {
        this.feeProdCerfCompanies.add(feeProdCerfCompany);
        feeProdCerfCompany.setProd(this);
        return this;
    }

    public Prod removeFeeProdCerfCompany(FeeProdCerfCompany feeProdCerfCompany) {
        this.feeProdCerfCompanies.remove(feeProdCerfCompany);
        feeProdCerfCompany.setProd(null);
        return this;
    }

    public Set<ProdSticker> getProdStickers() {
        return this.prodStickers;
    }

    public void setProdStickers(Set<ProdSticker> prodStickers) {
        if (this.prodStickers != null) {
            this.prodStickers.forEach(i -> i.setProd(null));
        }
        if (prodStickers != null) {
            prodStickers.forEach(i -> i.setProd(this));
        }
        this.prodStickers = prodStickers;
    }

    public Prod prodStickers(Set<ProdSticker> prodStickers) {
        this.setProdStickers(prodStickers);
        return this;
    }

    public Prod addProdSticker(ProdSticker prodSticker) {
        this.prodStickers.add(prodSticker);
        prodSticker.setProd(this);
        return this;
    }

    public Prod removeProdSticker(ProdSticker prodSticker) {
        this.prodStickers.remove(prodSticker);
        prodSticker.setProd(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prod)) {
            return false;
        }
        return getId() != null && getId().equals(((Prod) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Prod{" +
            "id=" + getId() +
            ", prodNo='" + getProdNo() + "'" +
            ", enName='" + getEnName() + "'" +
            ", chName='" + getChName() + "'" +
            ", hsCode='" + getHsCode() + "'" +
            ", cccCode='" + getCccCode() + "'" +
            "}";
    }
}
