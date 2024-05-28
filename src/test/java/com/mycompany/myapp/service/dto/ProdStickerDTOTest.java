package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProdStickerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProdStickerDTO.class);
        ProdStickerDTO prodStickerDTO1 = new ProdStickerDTO();
        prodStickerDTO1.setId(1L);
        ProdStickerDTO prodStickerDTO2 = new ProdStickerDTO();
        assertThat(prodStickerDTO1).isNotEqualTo(prodStickerDTO2);
        prodStickerDTO2.setId(prodStickerDTO1.getId());
        assertThat(prodStickerDTO1).isEqualTo(prodStickerDTO2);
        prodStickerDTO2.setId(2L);
        assertThat(prodStickerDTO1).isNotEqualTo(prodStickerDTO2);
        prodStickerDTO1.setId(null);
        assertThat(prodStickerDTO1).isNotEqualTo(prodStickerDTO2);
    }
}
