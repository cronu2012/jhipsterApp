package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.Wcc412ViewTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class Wcc412ViewTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wcc412View.class);
        Wcc412View wcc412View1 = getWcc412ViewSample1();
        Wcc412View wcc412View2 = new Wcc412View();
        assertThat(wcc412View1).isNotEqualTo(wcc412View2);

        wcc412View2.setId(wcc412View1.getId());
        assertThat(wcc412View1).isEqualTo(wcc412View2);

        wcc412View2 = getWcc412ViewSample2();
        assertThat(wcc412View1).isNotEqualTo(wcc412View2);
    }
}
