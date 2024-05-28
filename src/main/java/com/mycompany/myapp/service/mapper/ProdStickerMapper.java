package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Prod;
import com.mycompany.myapp.domain.ProdSticker;
import com.mycompany.myapp.domain.Sticker;
import com.mycompany.myapp.service.dto.ProdDTO;
import com.mycompany.myapp.service.dto.ProdStickerDTO;
import com.mycompany.myapp.service.dto.StickerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProdSticker} and its DTO {@link ProdStickerDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProdStickerMapper extends EntityMapper<ProdStickerDTO, ProdSticker> {
    @Mapping(target = "prod", source = "prod", qualifiedByName = "prodId")
    @Mapping(target = "sticker", source = "sticker", qualifiedByName = "stickerId")
    ProdStickerDTO toDto(ProdSticker s);

    @Named("prodId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProdDTO toDtoProdId(Prod prod);

    @Named("stickerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StickerDTO toDtoStickerId(Sticker sticker);
}
