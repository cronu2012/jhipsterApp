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
 * A Mark.
 */
@Entity
@Table(name = "mark")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Mark implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "mark_no", length = 20)
    private String markNo;

    @Size(max = 100)
    @Column(name = "en_name", length = 100)
    private String enName;

    @Size(max = 100)
    @Column(name = "ch_name", length = 100)
    private String chName;

    @Lob
    @Column(name = "img")
    private byte[] img;

    @Column(name = "img_content_type")
    private String imgContentType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mark")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cerf", "mark" }, allowSetters = true)
    private Set<CerfMark> cerfMarks = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mark")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "sticker", "mark" }, allowSetters = true)
    private Set<StickerMark> stickerMarks = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mark")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "country", "mark" }, allowSetters = true)
    private Set<CountryMark> countryMarks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Mark id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarkNo() {
        return this.markNo;
    }

    public Mark markNo(String markNo) {
        this.setMarkNo(markNo);
        return this;
    }

    public void setMarkNo(String markNo) {
        this.markNo = markNo;
    }

    public String getEnName() {
        return this.enName;
    }

    public Mark enName(String enName) {
        this.setEnName(enName);
        return this;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getChName() {
        return this.chName;
    }

    public Mark chName(String chName) {
        this.setChName(chName);
        return this;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    public byte[] getImg() {
        return this.img;
    }

    public Mark img(byte[] img) {
        this.setImg(img);
        return this;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getImgContentType() {
        return this.imgContentType;
    }

    public Mark imgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
        return this;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }

    public Set<CerfMark> getCerfMarks() {
        return this.cerfMarks;
    }

    public void setCerfMarks(Set<CerfMark> cerfMarks) {
        if (this.cerfMarks != null) {
            this.cerfMarks.forEach(i -> i.setMark(null));
        }
        if (cerfMarks != null) {
            cerfMarks.forEach(i -> i.setMark(this));
        }
        this.cerfMarks = cerfMarks;
    }

    public Mark cerfMarks(Set<CerfMark> cerfMarks) {
        this.setCerfMarks(cerfMarks);
        return this;
    }

    public Mark addCerfMark(CerfMark cerfMark) {
        this.cerfMarks.add(cerfMark);
        cerfMark.setMark(this);
        return this;
    }

    public Mark removeCerfMark(CerfMark cerfMark) {
        this.cerfMarks.remove(cerfMark);
        cerfMark.setMark(null);
        return this;
    }

    public Set<StickerMark> getStickerMarks() {
        return this.stickerMarks;
    }

    public void setStickerMarks(Set<StickerMark> stickerMarks) {
        if (this.stickerMarks != null) {
            this.stickerMarks.forEach(i -> i.setMark(null));
        }
        if (stickerMarks != null) {
            stickerMarks.forEach(i -> i.setMark(this));
        }
        this.stickerMarks = stickerMarks;
    }

    public Mark stickerMarks(Set<StickerMark> stickerMarks) {
        this.setStickerMarks(stickerMarks);
        return this;
    }

    public Mark addStickerMark(StickerMark stickerMark) {
        this.stickerMarks.add(stickerMark);
        stickerMark.setMark(this);
        return this;
    }

    public Mark removeStickerMark(StickerMark stickerMark) {
        this.stickerMarks.remove(stickerMark);
        stickerMark.setMark(null);
        return this;
    }

    public Set<CountryMark> getCountryMarks() {
        return this.countryMarks;
    }

    public void setCountryMarks(Set<CountryMark> countryMarks) {
        if (this.countryMarks != null) {
            this.countryMarks.forEach(i -> i.setMark(null));
        }
        if (countryMarks != null) {
            countryMarks.forEach(i -> i.setMark(this));
        }
        this.countryMarks = countryMarks;
    }

    public Mark countryMarks(Set<CountryMark> countryMarks) {
        this.setCountryMarks(countryMarks);
        return this;
    }

    public Mark addCountryMark(CountryMark countryMark) {
        this.countryMarks.add(countryMark);
        countryMark.setMark(this);
        return this;
    }

    public Mark removeCountryMark(CountryMark countryMark) {
        this.countryMarks.remove(countryMark);
        countryMark.setMark(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mark)) {
            return false;
        }
        return getId() != null && getId().equals(((Mark) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Mark{" +
            "id=" + getId() +
            ", markNo='" + getMarkNo() + "'" +
            ", enName='" + getEnName() + "'" +
            ", chName='" + getChName() + "'" +
            ", img='" + getImg() + "'" +
            ", imgContentType='" + getImgContentType() + "'" +
            "}";
    }
}
