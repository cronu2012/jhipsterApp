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
 * A Country.
 */
@Entity
@Table(name = "country")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 10)
    @Column(name = "country_no", length = 10)
    private String countryNo;

    @Size(max = 100)
    @Column(name = "en_name", length = 100)
    private String enName;

    @Size(max = 100)
    @Column(name = "ch_name", length = 100)
    private String chName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "prod", "country" }, allowSetters = true)
    private Set<ProdCountry> prodCountries = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "country", "std" }, allowSetters = true)
    private Set<CountryStd> countryStds = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "country", "cerf" }, allowSetters = true)
    private Set<CountryCert> countryCerts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "country", "mark" }, allowSetters = true)
    private Set<CountryMark> countryMarks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Country id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryNo() {
        return this.countryNo;
    }

    public Country countryNo(String countryNo) {
        this.setCountryNo(countryNo);
        return this;
    }

    public void setCountryNo(String countryNo) {
        this.countryNo = countryNo;
    }

    public String getEnName() {
        return this.enName;
    }

    public Country enName(String enName) {
        this.setEnName(enName);
        return this;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getChName() {
        return this.chName;
    }

    public Country chName(String chName) {
        this.setChName(chName);
        return this;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    public Set<ProdCountry> getProdCountries() {
        return this.prodCountries;
    }

    public void setProdCountries(Set<ProdCountry> prodCountries) {
        if (this.prodCountries != null) {
            this.prodCountries.forEach(i -> i.setCountry(null));
        }
        if (prodCountries != null) {
            prodCountries.forEach(i -> i.setCountry(this));
        }
        this.prodCountries = prodCountries;
    }

    public Country prodCountries(Set<ProdCountry> prodCountries) {
        this.setProdCountries(prodCountries);
        return this;
    }

    public Country addProdCountry(ProdCountry prodCountry) {
        this.prodCountries.add(prodCountry);
        prodCountry.setCountry(this);
        return this;
    }

    public Country removeProdCountry(ProdCountry prodCountry) {
        this.prodCountries.remove(prodCountry);
        prodCountry.setCountry(null);
        return this;
    }

    public Set<CountryStd> getCountryStds() {
        return this.countryStds;
    }

    public void setCountryStds(Set<CountryStd> countryStds) {
        if (this.countryStds != null) {
            this.countryStds.forEach(i -> i.setCountry(null));
        }
        if (countryStds != null) {
            countryStds.forEach(i -> i.setCountry(this));
        }
        this.countryStds = countryStds;
    }

    public Country countryStds(Set<CountryStd> countryStds) {
        this.setCountryStds(countryStds);
        return this;
    }

    public Country addCountryStd(CountryStd countryStd) {
        this.countryStds.add(countryStd);
        countryStd.setCountry(this);
        return this;
    }

    public Country removeCountryStd(CountryStd countryStd) {
        this.countryStds.remove(countryStd);
        countryStd.setCountry(null);
        return this;
    }

    public Set<CountryCert> getCountryCerts() {
        return this.countryCerts;
    }

    public void setCountryCerts(Set<CountryCert> countryCerts) {
        if (this.countryCerts != null) {
            this.countryCerts.forEach(i -> i.setCountry(null));
        }
        if (countryCerts != null) {
            countryCerts.forEach(i -> i.setCountry(this));
        }
        this.countryCerts = countryCerts;
    }

    public Country countryCerts(Set<CountryCert> countryCerts) {
        this.setCountryCerts(countryCerts);
        return this;
    }

    public Country addCountryCert(CountryCert countryCert) {
        this.countryCerts.add(countryCert);
        countryCert.setCountry(this);
        return this;
    }

    public Country removeCountryCert(CountryCert countryCert) {
        this.countryCerts.remove(countryCert);
        countryCert.setCountry(null);
        return this;
    }

    public Set<CountryMark> getCountryMarks() {
        return this.countryMarks;
    }

    public void setCountryMarks(Set<CountryMark> countryMarks) {
        if (this.countryMarks != null) {
            this.countryMarks.forEach(i -> i.setCountry(null));
        }
        if (countryMarks != null) {
            countryMarks.forEach(i -> i.setCountry(this));
        }
        this.countryMarks = countryMarks;
    }

    public Country countryMarks(Set<CountryMark> countryMarks) {
        this.setCountryMarks(countryMarks);
        return this;
    }

    public Country addCountryMark(CountryMark countryMark) {
        this.countryMarks.add(countryMark);
        countryMark.setCountry(this);
        return this;
    }

    public Country removeCountryMark(CountryMark countryMark) {
        this.countryMarks.remove(countryMark);
        countryMark.setCountry(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country)) {
            return false;
        }
        return getId() != null && getId().equals(((Country) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Country{" +
            "id=" + getId() +
            ", countryNo='" + getCountryNo() + "'" +
            ", enName='" + getEnName() + "'" +
            ", chName='" + getChName() + "'" +
            "}";
    }
}
