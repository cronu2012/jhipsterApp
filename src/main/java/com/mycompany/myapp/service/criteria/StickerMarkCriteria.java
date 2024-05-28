package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.StickerMark} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.StickerMarkResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sticker-marks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StickerMarkCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter relType;

    private LongFilter stickerId;

    private LongFilter markId;

    private Boolean distinct;

    public StickerMarkCriteria() {}

    public StickerMarkCriteria(StickerMarkCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.relType = other.optionalRelType().map(StringFilter::copy).orElse(null);
        this.stickerId = other.optionalStickerId().map(LongFilter::copy).orElse(null);
        this.markId = other.optionalMarkId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public StickerMarkCriteria copy() {
        return new StickerMarkCriteria(this);
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

    public StringFilter getRelType() {
        return relType;
    }

    public Optional<StringFilter> optionalRelType() {
        return Optional.ofNullable(relType);
    }

    public StringFilter relType() {
        if (relType == null) {
            setRelType(new StringFilter());
        }
        return relType;
    }

    public void setRelType(StringFilter relType) {
        this.relType = relType;
    }

    public LongFilter getStickerId() {
        return stickerId;
    }

    public Optional<LongFilter> optionalStickerId() {
        return Optional.ofNullable(stickerId);
    }

    public LongFilter stickerId() {
        if (stickerId == null) {
            setStickerId(new LongFilter());
        }
        return stickerId;
    }

    public void setStickerId(LongFilter stickerId) {
        this.stickerId = stickerId;
    }

    public LongFilter getMarkId() {
        return markId;
    }

    public Optional<LongFilter> optionalMarkId() {
        return Optional.ofNullable(markId);
    }

    public LongFilter markId() {
        if (markId == null) {
            setMarkId(new LongFilter());
        }
        return markId;
    }

    public void setMarkId(LongFilter markId) {
        this.markId = markId;
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
        final StickerMarkCriteria that = (StickerMarkCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(relType, that.relType) &&
            Objects.equals(stickerId, that.stickerId) &&
            Objects.equals(markId, that.markId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, relType, stickerId, markId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StickerMarkCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalRelType().map(f -> "relType=" + f + ", ").orElse("") +
            optionalStickerId().map(f -> "stickerId=" + f + ", ").orElse("") +
            optionalMarkId().map(f -> "markId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
