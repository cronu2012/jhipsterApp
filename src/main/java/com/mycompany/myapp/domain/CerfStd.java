package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CerfStd.
 */
@Entity
@Table(name = "cerf_std")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CerfStd implements Serializable {

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
    @JsonIgnoreProperties(value = { "prodStds", "cerfStds", "countryStds" }, allowSetters = true)
    private Std std;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CerfStd id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRelType() {
        return this.relType;
    }

    public CerfStd relType(String relType) {
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

    public CerfStd cerf(Cerf cerf) {
        this.setCerf(cerf);
        return this;
    }

    public Std getStd() {
        return this.std;
    }

    public void setStd(Std std) {
        this.std = std;
    }

    public CerfStd std(Std std) {
        this.setStd(std);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CerfStd)) {
            return false;
        }
        return getId() != null && getId().equals(((CerfStd) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CerfStd{" +
            "id=" + getId() +
            ", relType='" + getRelType() + "'" +
            "}";
    }
}
