package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Product} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ProductResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter mainTitle;

    private StringFilter subTitle;

    private StringFilter price;

    private StringFilter createBy;

    private InstantFilter createdTime;

    private InstantFilter updatedTime;

    private IntegerFilter status;

    private Boolean distinct;

    public ProductCriteria() {}

    public ProductCriteria(ProductCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.mainTitle = other.optionalMainTitle().map(StringFilter::copy).orElse(null);
        this.subTitle = other.optionalSubTitle().map(StringFilter::copy).orElse(null);
        this.price = other.optionalPrice().map(StringFilter::copy).orElse(null);
        this.createBy = other.optionalCreateBy().map(StringFilter::copy).orElse(null);
        this.createdTime = other.optionalCreatedTime().map(InstantFilter::copy).orElse(null);
        this.updatedTime = other.optionalUpdatedTime().map(InstantFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(IntegerFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ProductCriteria copy() {
        return new ProductCriteria(this);
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

    public StringFilter getMainTitle() {
        return mainTitle;
    }

    public Optional<StringFilter> optionalMainTitle() {
        return Optional.ofNullable(mainTitle);
    }

    public StringFilter mainTitle() {
        if (mainTitle == null) {
            setMainTitle(new StringFilter());
        }
        return mainTitle;
    }

    public void setMainTitle(StringFilter mainTitle) {
        this.mainTitle = mainTitle;
    }

    public StringFilter getSubTitle() {
        return subTitle;
    }

    public Optional<StringFilter> optionalSubTitle() {
        return Optional.ofNullable(subTitle);
    }

    public StringFilter subTitle() {
        if (subTitle == null) {
            setSubTitle(new StringFilter());
        }
        return subTitle;
    }

    public void setSubTitle(StringFilter subTitle) {
        this.subTitle = subTitle;
    }

    public StringFilter getPrice() {
        return price;
    }

    public Optional<StringFilter> optionalPrice() {
        return Optional.ofNullable(price);
    }

    public StringFilter price() {
        if (price == null) {
            setPrice(new StringFilter());
        }
        return price;
    }

    public void setPrice(StringFilter price) {
        this.price = price;
    }

    public StringFilter getCreateBy() {
        return createBy;
    }

    public Optional<StringFilter> optionalCreateBy() {
        return Optional.ofNullable(createBy);
    }

    public StringFilter createBy() {
        if (createBy == null) {
            setCreateBy(new StringFilter());
        }
        return createBy;
    }

    public void setCreateBy(StringFilter createBy) {
        this.createBy = createBy;
    }

    public InstantFilter getCreatedTime() {
        return createdTime;
    }

    public Optional<InstantFilter> optionalCreatedTime() {
        return Optional.ofNullable(createdTime);
    }

    public InstantFilter createdTime() {
        if (createdTime == null) {
            setCreatedTime(new InstantFilter());
        }
        return createdTime;
    }

    public void setCreatedTime(InstantFilter createdTime) {
        this.createdTime = createdTime;
    }

    public InstantFilter getUpdatedTime() {
        return updatedTime;
    }

    public Optional<InstantFilter> optionalUpdatedTime() {
        return Optional.ofNullable(updatedTime);
    }

    public InstantFilter updatedTime() {
        if (updatedTime == null) {
            setUpdatedTime(new InstantFilter());
        }
        return updatedTime;
    }

    public void setUpdatedTime(InstantFilter updatedTime) {
        this.updatedTime = updatedTime;
    }

    public IntegerFilter getStatus() {
        return status;
    }

    public Optional<IntegerFilter> optionalStatus() {
        return Optional.ofNullable(status);
    }

    public IntegerFilter status() {
        if (status == null) {
            setStatus(new IntegerFilter());
        }
        return status;
    }

    public void setStatus(IntegerFilter status) {
        this.status = status;
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
        final ProductCriteria that = (ProductCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(mainTitle, that.mainTitle) &&
            Objects.equals(subTitle, that.subTitle) &&
            Objects.equals(price, that.price) &&
            Objects.equals(createBy, that.createBy) &&
            Objects.equals(createdTime, that.createdTime) &&
            Objects.equals(updatedTime, that.updatedTime) &&
            Objects.equals(status, that.status) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mainTitle, subTitle, price, createBy, createdTime, updatedTime, status, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalMainTitle().map(f -> "mainTitle=" + f + ", ").orElse("") +
            optionalSubTitle().map(f -> "subTitle=" + f + ", ").orElse("") +
            optionalPrice().map(f -> "price=" + f + ", ").orElse("") +
            optionalCreateBy().map(f -> "createBy=" + f + ", ").orElse("") +
            optionalCreatedTime().map(f -> "createdTime=" + f + ", ").orElse("") +
            optionalUpdatedTime().map(f -> "updatedTime=" + f + ", ").orElse("") +
            optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
