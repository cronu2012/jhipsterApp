package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Sticker;
import com.mycompany.myapp.service.dto.StickerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sticker} and its DTO {@link StickerDTO}.
 */
@Mapper(componentModel = "spring")
public interface StickerMapper extends EntityMapper<StickerDTO, Sticker> {}
