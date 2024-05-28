package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Mark;
import com.mycompany.myapp.domain.Sticker;
import com.mycompany.myapp.domain.StickerMark;
import com.mycompany.myapp.service.dto.MarkDTO;
import com.mycompany.myapp.service.dto.StickerDTO;
import com.mycompany.myapp.service.dto.StickerMarkDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link StickerMark} and its DTO {@link StickerMarkDTO}.
 */
@Mapper(componentModel = "spring")
public interface StickerMarkMapper extends EntityMapper<StickerMarkDTO, StickerMark> {
    @Mapping(target = "sticker", source = "sticker", qualifiedByName = "stickerId")
    @Mapping(target = "mark", source = "mark", qualifiedByName = "markId")
    StickerMarkDTO toDto(StickerMark s);

    @Named("stickerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StickerDTO toDtoStickerId(Sticker sticker);

    @Named("markId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MarkDTO toDtoMarkId(Mark mark);
}
