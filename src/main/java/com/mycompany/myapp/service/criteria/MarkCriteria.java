package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Mark} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.MarkResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /marks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MarkCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter markNo;

    private StringFilter enName;

    private StringFilter chName;

    private LongFilter cerfMarkId;

    private LongFilter stickerMarkId;

    private LongFilter countryMarkId;

    private Boolean distinct;

    public MarkCriteria() {}

    public MarkCriteria(MarkCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.markNo = other.optionalMarkNo().map(StringFilter::copy).orElse(null);
        this.enName = other.optionalEnName().map(StringFilter::copy).orElse(null);
        this.chName = other.optionalChName().map(StringFilter::copy).orElse(null);
        this.cerfMarkId = other.optionalCerfMarkId().map(LongFilter::copy).orElse(null);
        this.stickerMarkId = other.optionalStickerMarkId().map(LongFilter::copy).orElse(null);
        this.countryMarkId = other.optionalCountryMarkId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public MarkCriteria copy() {
        return new MarkCriteria(this);
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

    public StringFilter getMarkNo() {
        return markNo;
    }

    public Optional<StringFilter> optionalMarkNo() {
        return Optional.ofNullable(markNo);
    }

    public StringFilter markNo() {
        if (markNo == null) {
            setMarkNo(new StringFilter());
        }
        return markNo;
    }

    public void setMarkNo(StringFilter markNo) {
        this.markNo = markNo;
    }

    public StringFilter getEnName() {
        return enName;
    }

    public Optional<StringFilter> optionalEnName() {
        return Optional.ofNullable(enName);
    }

    public StringFilter enName() {
        if (enName == null) {
            setEnName(new StringFilter());
        }
        return enName;
    }

    public void setEnName(StringFilter enName) {
        this.enName = enName;
    }

    public StringFilter getChName() {
        return chName;
    }

    public Optional<StringFilter> optionalChName() {
        return Optional.ofNullable(chName);
    }

    public StringFilter chName() {
        if (chName == null) {
            setChName(new StringFilter());
        }
        return chName;
    }

    public void setChName(StringFilter chName) {
        this.chName = chName;
    }

    public LongFilter getCerfMarkId() {
        return cerfMarkId;
    }

    public Optional<LongFilter> optionalCerfMarkId() {
        return Optional.ofNullable(cerfMarkId);
    }

    public LongFilter cerfMarkId() {
        if (cerfMarkId == null) {
            setCerfMarkId(new LongFilter());
        }
        return cerfMarkId;
    }

    public void setCerfMarkId(LongFilter cerfMarkId) {
        this.cerfMarkId = cerfMarkId;
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

    public LongFilter getCountryMarkId() {
        return countryMarkId;
    }

    public Optional<LongFilter> optionalCountryMarkId() {
        return Optional.ofNullable(countryMarkId);
    }

    public LongFilter countryMarkId() {
        if (countryMarkId == null) {
            setCountryMarkId(new LongFilter());
        }
        return countryMarkId;
    }

    public void setCountryMarkId(LongFilter countryMarkId) {
        this.countryMarkId = countryMarkId;
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
        final MarkCriteria that = (MarkCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(markNo, that.markNo) &&
            Objects.equals(enName, that.enName) &&
            Objects.equals(chName, that.chName) &&
            Objects.equals(cerfMarkId, that.cerfMarkId) &&
            Objects.equals(stickerMarkId, that.stickerMarkId) &&
            Objects.equals(countryMarkId, that.countryMarkId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, markNo, enName, chName, cerfMarkId, stickerMarkId, countryMarkId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MarkCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalMarkNo().map(f -> "markNo=" + f + ", ").orElse("") +
            optionalEnName().map(f -> "enName=" + f + ", ").orElse("") +
            optionalChName().map(f -> "chName=" + f + ", ").orElse("") +
            optionalCerfMarkId().map(f -> "cerfMarkId=" + f + ", ").orElse("") +
            optionalStickerMarkId().map(f -> "stickerMarkId=" + f + ", ").orElse("") +
            optionalCountryMarkId().map(f -> "countryMarkId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
