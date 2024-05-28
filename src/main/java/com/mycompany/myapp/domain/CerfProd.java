package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CerfProd.
 */
@Entity
@Table(name = "cerf_prod")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CerfProd implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 100)
    @Column(name = "rel_type", length = 100)
    private String relType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "cerfProds", "cerfStds", "cerfMarks", "cerfCompanies", "feeProdCerfCompanies", "countryCerts" },
        allowSetters = true
    )
    private Cerf cerf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "prodCountries", "prodStds", "cerfProds", "feeProdCerfCompanies", "prodStickers" }, allowSetters = true)
    private Prod prod;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CerfProd id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRelType() {
        return this.relType;
    }

    public CerfProd relType(String relType) {
        this.setRelType(relType);
        return this;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }

    public Cerf getCerf() {
        return this.cerf;
    }

    public void setCerf(Cerf cerf) {
        this.cerf = cerf;
    }

    public CerfProd cerf(Cerf cerf) {
        this.setCerf(cerf);
        return this;
    }

    public Prod getProd() {
        return this.prod;
    }

    public void setProd(Prod prod) {
        this.prod = prod;
    }

    public CerfProd prod(Prod prod) {
        this.setProd(prod);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CerfProd)) {
            return false;
        }
        return getId() != null && getId().equals(((CerfProd) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CerfProd{" +
            "id=" + getId() +
            ", relType='" + getRelType() + "'" +
            "}";
    }
}
