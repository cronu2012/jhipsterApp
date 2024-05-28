package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProdStd.
 */
@Entity
@Table(name = "prod_std")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProdStd implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 100)
    @Column(name = "rel_type", length = 100)
    private String relType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "prodCountries", "prodStds", "cerfProds", "feeProdCerfCompanies", "prodStickers" }, allowSetters = true)
    private Prod prod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "prodStds", "cerfStds", "countryStds" }, allowSetters = true)
    private Std std;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProdStd id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRelType() {
        return this.relType;
    }

    public ProdStd relType(String relType) {
        this.setRelType(relType);
        return this;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }

    public Prod getProd() {
        return this.prod;
    }

    public void setProd(Prod prod) {
        this.prod = prod;
    }

    public ProdStd prod(Prod prod) {
        this.setProd(prod);
        return this;
    }

    public Std getStd() {
        return this.std;
    }

    public void setStd(Std std) {
        this.std = std;
    }

    public ProdStd std(Std std) {
        this.setStd(std);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProdStd)) {
            return false;
        }
        return getId() != null && getId().equals(((ProdStd) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProdStd{" +
            "id=" + getId() +
            ", relType='" + getRelType() + "'" +
            "}";
    }
}
