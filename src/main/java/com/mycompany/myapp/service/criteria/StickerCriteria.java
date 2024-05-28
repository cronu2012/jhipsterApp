package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Sticker} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.StickerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /stickers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StickerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter stickerNo;

    private LongFilter stickerMarkId;

    private LongFilter prodStickerId;

    private Boolean distinct;

    public StickerCriteria() {}

    public StickerCriteria(StickerCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.stickerNo = other.optionalStickerNo().map(StringFilter::copy).orElse(null);
        this.stickerMarkId = other.optionalStickerMarkId().map(LongFilter::copy).orElse(null);
        this.prodStickerId = other.optionalProdStickerId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public StickerCriteria copy() {
        return new StickerCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getStickerNo() {
        return stickerNo;
    }

    public Optional<StringFilter> optionalStickerNo() {
        return Optional.ofNullable(stickerNo);
    }

    public StringFilter stickerNo() {
        if (stickerNo == null) {
            setStickerNo(new StringFilter());
        }
        return stickerNo;
    }

    public void setStickerNo(StringFilter stickerNo) {
        this.stickerNo = stickerNo;
    }

    public LongFilter getStickerMarkId() {
        return stickerMarkId;
    }

    public Optional<LongFilter> optionalStickerMarkId() {
        return Optional.ofNullable(stickerMarkId);
    }

    public LongFilter stickerMarkId() {
        if (stickerMarkId == null) {
            setStickerMarkId(new LongFilter());
        }
        return stickerMarkId;
    }

    public void setStickerMarkId(LongFilter stickerMarkId) {
        this.stickerMarkId = stickerMarkId;
    }

    public LongFilter getProdStickerId() {
        return prodStickerId;
    }

    public Optional<LongFilter> optionalProdStickerId() {
        return Optional.ofNullable(prodStickerId);
    }

    public LongFilter prodStickerId() {
        if (prodStickerId == null) {
            setProdStickerId(new LongFilter());
        }
        return prodStickerId;
    }

    public void setProdStickerId(LongFilter prodStickerId) {
        this.prodStickerId = prodStickerId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StickerCriteria that = (StickerCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(stickerNo, that.stickerNo) &&
            Objects.equals(stickerMarkId, that.stickerMarkId) &&
            Objects.equals(prodStickerId, that.prodStickerId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stickerNo, stickerMarkId, prodStickerId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StickerCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalStickerNo().map(f -> "stickerNo=" + f + ", ").orElse("") +
            optionalStickerMarkId().map(f -> "stickerMarkId=" + f + ", ").orElse("") +
            optionalProdStickerId().map(f -> "prodStickerId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
