package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StickerMarkDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StickerMarkDTO.class);
        StickerMarkDTO stickerMarkDTO1 = new StickerMarkDTO();
        stickerMarkDTO1.setId(1L);
        StickerMarkDTO stickerMarkDTO2 = new StickerMarkDTO();
        assertThat(stickerMarkDTO1).isNotEqualTo(stickerMarkDTO2);
        stickerMarkDTO2.setId(stickerMarkDTO1.getId());
        assertThat(stickerMarkDTO1).isEqualTo(stickerMarkDTO2);
        stickerMarkDTO2.setId(2L);
        assertThat(stickerMarkDTO1).isNotEqualTo(stickerMarkDTO2);
        stickerMarkDTO1.setId(null);
        assertThat(stickerMarkDTO1).isNotEqualTo(stickerMarkDTO2);
    }
}
