package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.Wcc421ViewTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class Wcc421ViewTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wcc421View.class);
        Wcc421View wcc421View1 = getWcc421ViewSample1();
        Wcc421View wcc421View2 = new Wcc421View();
        assertThat(wcc421View1).isNotEqualTo(wcc421View2);

        wcc421View2.setId(wcc421View1.getId());
        assertThat(wcc421View1).isEqualTo(wcc421View2);

        wcc421View2 = getWcc421ViewSample2();
        assertThat(wcc421View1).isNotEqualTo(wcc421View2);
    }
}
