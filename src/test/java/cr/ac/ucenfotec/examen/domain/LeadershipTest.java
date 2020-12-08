package cr.ac.ucenfotec.examen.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cr.ac.ucenfotec.examen.web.rest.TestUtil;

public class LeadershipTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Leadership.class);
        Leadership leadership1 = new Leadership();
        leadership1.setId(1L);
        Leadership leadership2 = new Leadership();
        leadership2.setId(leadership1.getId());
        assertThat(leadership1).isEqualTo(leadership2);
        leadership2.setId(2L);
        assertThat(leadership1).isNotEqualTo(leadership2);
        leadership1.setId(null);
        assertThat(leadership1).isNotEqualTo(leadership2);
    }
}
