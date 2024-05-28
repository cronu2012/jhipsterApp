package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ProdSticker} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProdStickerDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String relType;

    private ProdDTO prod;

    private StickerDTO sticker;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }

    public ProdDTO getProd() {
        return prod;
    }

    public void setProd(ProdDTO prod) {
        this.prod = prod;
    }

    public StickerDTO getSticker() {
        return sticker;
    }

    public void setSticker(StickerDTO sticker) {
        this.sticker = sticker;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProdStickerDTO)) {
            return false;
        }

        ProdStickerDTO prodStickerDTO = (ProdStickerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, prodStickerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProdStickerDTO{" +
            "id=" + getId() +
            ", relType='" + getRelType() + "'" +
            ", prod=" + getProd() +
            ", sticker=" + getSticker() +
            "}";
    }
}
