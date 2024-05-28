package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CerfMark} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CerfMarkDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String relType;

    private CerfDTO cerf;

    private MarkDTO mark;

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

    public CerfDTO getCerf() {
        return cerf;
    }

    public void setCerf(CerfDTO cerf) {
        this.cerf = cerf;
    }

    public MarkDTO getMark() {
        return mark;
    }

    public void setMark(MarkDTO mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CerfMarkDTO)) {
            return false;
        }

        CerfMarkDTO cerfMarkDTO = (CerfMarkDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cerfMarkDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CerfMarkDTO{" +
            "id=" + getId() +
            ", relType='" + getRelType() + "'" +
            ", cerf=" + getCerf() +
            ", mark=" + getMark() +
            "}";
    }
}
