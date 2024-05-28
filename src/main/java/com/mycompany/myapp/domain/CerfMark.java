package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CerfMark.
 */
@Entity
@Table(name = "cerf_mark")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CerfMark implements Serializable {

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
    @JsonIgnoreProperties(value = { "cerfMarks", "stickerMarks", "countryMarks" }, allowSetters = true)
    private Mark mark;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CerfMark id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRelType() {
        return this.relType;
    }

    public CerfMark relType(String relType) {
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

    public CerfMark cerf(Cerf cerf) {
        this.setCerf(cerf);
        return this;
    }

    public Mark getMark() {
        return this.mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public CerfMark mark(Mark mark) {
        this.setMark(mark);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CerfMark)) {
            return false;
        }
        return getId() != null && getId().equals(((CerfMark) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CerfMark{" +
            "id=" + getId() +
            ", relType='" + getRelType() + "'" +
            "}";
    }
}
