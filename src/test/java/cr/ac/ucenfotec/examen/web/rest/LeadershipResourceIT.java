package cr.ac.ucenfotec.examen.web.rest;

import cr.ac.ucenfotec.examen.ExamenApp;
import cr.ac.ucenfotec.examen.domain.Leadership;
import cr.ac.ucenfotec.examen.repository.LeadershipRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static cr.ac.ucenfotec.examen.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LeadershipResource} REST controller.
 */
@SpringBootTest(classes = ExamenApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LeadershipResourceIT {

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private LeadershipRepository leadershipRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeadershipMockMvc;

    private Leadership leadership;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leadership createEntity(EntityManager em) {
        Leadership leadership = new Leadership()
            .startDate(DEFAULT_START_DATE);
        return leadership;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leadership createUpdatedEntity(EntityManager em) {
        Leadership leadership = new Leadership()
            .startDate(UPDATED_START_DATE);
        return leadership;
    }

    @BeforeEach
    public void initTest() {
        leadership = createEntity(em);
    }

    @Test
    @Transactional
    public void createLeadership() throws Exception {
        int databaseSizeBeforeCreate = leadershipRepository.findAll().size();
        // Create the Leadership
        restLeadershipMockMvc.perform(post("/api/leaderships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leadership)))
            .andExpect(status().isCreated());

        // Validate the Leadership in the database
        List<Leadership> leadershipList = leadershipRepository.findAll();
        assertThat(leadershipList).hasSize(databaseSizeBeforeCreate + 1);
        Leadership testLeadership = leadershipList.get(leadershipList.size() - 1);
        assertThat(testLeadership.getStartDate()).isEqualTo(DEFAULT_START_DATE);
    }

    @Test
    @Transactional
    public void createLeadershipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leadershipRepository.findAll().size();

        // Create the Leadership with an existing ID
        leadership.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeadershipMockMvc.perform(post("/api/leaderships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leadership)))
            .andExpect(status().isBadRequest());

        // Validate the Leadership in the database
        List<Leadership> leadershipList = leadershipRepository.findAll();
        assertThat(leadershipList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = leadershipRepository.findAll().size();
        // set the field null
        leadership.setStartDate(null);

        // Create the Leadership, which fails.


        restLeadershipMockMvc.perform(post("/api/leaderships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leadership)))
            .andExpect(status().isBadRequest());

        List<Leadership> leadershipList = leadershipRepository.findAll();
        assertThat(leadershipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLeaderships() throws Exception {
        // Initialize the database
        leadershipRepository.saveAndFlush(leadership);

        // Get all the leadershipList
        restLeadershipMockMvc.perform(get("/api/leaderships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leadership.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))));
    }
    
    @Test
    @Transactional
    public void getLeadership() throws Exception {
        // Initialize the database
        leadershipRepository.saveAndFlush(leadership);

        // Get the leadership
        restLeadershipMockMvc.perform(get("/api/leaderships/{id}", leadership.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leadership.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingLeadership() throws Exception {
        // Get the leadership
        restLeadershipMockMvc.perform(get("/api/leaderships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeadership() throws Exception {
        // Initialize the database
        leadershipRepository.saveAndFlush(leadership);

        int databaseSizeBeforeUpdate = leadershipRepository.findAll().size();

        // Update the leadership
        Leadership updatedLeadership = leadershipRepository.findById(leadership.getId()).get();
        // Disconnect from session so that the updates on updatedLeadership are not directly saved in db
        em.detach(updatedLeadership);
        updatedLeadership
            .startDate(UPDATED_START_DATE);

        restLeadershipMockMvc.perform(put("/api/leaderships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLeadership)))
            .andExpect(status().isOk());

        // Validate the Leadership in the database
        List<Leadership> leadershipList = leadershipRepository.findAll();
        assertThat(leadershipList).hasSize(databaseSizeBeforeUpdate);
        Leadership testLeadership = leadershipList.get(leadershipList.size() - 1);
        assertThat(testLeadership.getStartDate()).isEqualTo(UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingLeadership() throws Exception {
        int databaseSizeBeforeUpdate = leadershipRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeadershipMockMvc.perform(put("/api/leaderships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leadership)))
            .andExpect(status().isBadRequest());

        // Validate the Leadership in the database
        List<Leadership> leadershipList = leadershipRepository.findAll();
        assertThat(leadershipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLeadership() throws Exception {
        // Initialize the database
        leadershipRepository.saveAndFlush(leadership);

        int databaseSizeBeforeDelete = leadershipRepository.findAll().size();

        // Delete the leadership
        restLeadershipMockMvc.perform(delete("/api/leaderships/{id}", leadership.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Leadership> leadershipList = leadershipRepository.findAll();
        assertThat(leadershipList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
