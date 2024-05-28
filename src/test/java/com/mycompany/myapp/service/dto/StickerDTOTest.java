package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StickerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StickerDTO.class);
        StickerDTO stickerDTO1 = new StickerDTO();
        stickerDTO1.setId(1L);
        StickerDTO stickerDTO2 = new StickerDTO();
        assertThat(stickerDTO1).isNotEqualTo(stickerDTO2);
        stickerDTO2.setId(stickerDTO1.getId());
        assertThat(stickerDTO1).isEqualTo(stickerDTO2);
        stickerDTO2.setId(2L);
        assertThat(stickerDTO1).isNotEqualTo(stickerDTO2);
        stickerDTO1.setId(null);
        assertThat(stickerDTO1).isNotEqualTo(stickerDTO2);
    }
}
