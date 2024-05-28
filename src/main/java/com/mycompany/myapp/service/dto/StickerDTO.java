package com.mycompany.myapp.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Sticker} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StickerDTO implements Serializable {

    private Long id;

    @Size(max = 20)
    private String stickerNo;

    @Lob
    private byte[] img;

    private String imgContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStickerNo() {
        return stickerNo;
    }

    public void setStickerNo(String stickerNo) {
        this.stickerNo = stickerNo;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getImgContentType() {
        return imgContentType;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StickerDTO)) {
            return false;
        }

        StickerDTO stickerDTO = (StickerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, stickerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StickerDTO{" +
            "id=" + getId() +
            ", stickerNo='" + getStickerNo() + "'" +
            ", img='" + getImg() + "'" +
            "}";
    }
}
