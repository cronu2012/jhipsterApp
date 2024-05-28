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
 * A Sticker.
 */
@Entity
@Table(name = "sticker")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sticker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "sticker_no", length = 20)
    private String stickerNo;

    @Lob
    @Column(name = "img")
    private byte[] img;

    @Column(name = "img_content_type")
    private String imgContentType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sticker")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "sticker", "mark" }, allowSetters = true)
    private Set<StickerMark> stickerMarks = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sticker")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "prod", "sticker" }, allowSetters = true)
    private Set<ProdSticker> prodStickers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sticker id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStickerNo() {
        return this.stickerNo;
    }

    public Sticker stickerNo(String stickerNo) {
        this.setStickerNo(stickerNo);
        return this;
    }

    public void setStickerNo(String stickerNo) {
        this.stickerNo = stickerNo;
    }

    public byte[] getImg() {
        return this.img;
    }

    public Sticker img(byte[] img) {
        this.setImg(img);
        return this;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getImgContentType() {
        return this.imgContentType;
    }

    public Sticker imgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
        return this;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }

    public Set<StickerMark> getStickerMarks() {
        return this.stickerMarks;
    }

    public void setStickerMarks(Set<StickerMark> stickerMarks) {
        if (this.stickerMarks != null) {
            this.stickerMarks.forEach(i -> i.setSticker(null));
        }
        if (stickerMarks != null) {
            stickerMarks.forEach(i -> i.setSticker(this));
        }
        this.stickerMarks = stickerMarks;
    }

    public Sticker stickerMarks(Set<StickerMark> stickerMarks) {
        this.setStickerMarks(stickerMarks);
        return this;
    }

    public Sticker addStickerMark(StickerMark stickerMark) {
        this.stickerMarks.add(stickerMark);
        stickerMark.setSticker(this);
        return this;
    }

    public Sticker removeStickerMark(StickerMark stickerMark) {
        this.stickerMarks.remove(stickerMark);
        stickerMark.setSticker(null);
        return this;
    }

    public Set<ProdSticker> getProdStickers() {
        return this.prodStickers;
    }

    public void setProdStickers(Set<ProdSticker> prodStickers) {
        if (this.prodStickers != null) {
            this.prodStickers.forEach(i -> i.setSticker(null));
        }
        if (prodStickers != null) {
            prodStickers.forEach(i -> i.setSticker(this));
        }
        this.prodStickers = prodStickers;
    }

    public Sticker prodStickers(Set<ProdSticker> prodStickers) {
        this.setProdStickers(prodStickers);
        return this;
    }

    public Sticker addProdSticker(ProdSticker prodSticker) {
        this.prodStickers.add(prodSticker);
        prodSticker.setSticker(this);
        return this;
    }

    public Sticker removeProdSticker(ProdSticker prodSticker) {
        this.prodStickers.remove(prodSticker);
        prodSticker.setSticker(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sticker)) {
            return false;
        }
        return getId() != null && getId().equals(((Sticker) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sticker{" +
            "id=" + getId() +
            ", stickerNo='" + getStickerNo() + "'" +
            ", img='" + getImg() + "'" +
            ", imgContentType='" + getImgContentType() + "'" +
            "}";
    }
}
