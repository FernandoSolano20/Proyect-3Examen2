package cr.ac.ucenfotec.examen.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cr.ac.ucenfotec.examen.web.rest.TestUtil;

public class DeparmentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Deparment.class);
        Deparment deparment1 = new Deparment();
        deparment1.setId(1L);
        Deparment deparment2 = new Deparment();
        deparment2.setId(deparment1.getId());
        assertThat(deparment1).isEqualTo(deparment2);
        deparment2.setId(2L);
        assertThat(deparment1).isNotEqualTo(deparment2);
        deparment1.setId(null);
        assertThat(deparment1).isNotEqualTo(deparment2);
    }
}
