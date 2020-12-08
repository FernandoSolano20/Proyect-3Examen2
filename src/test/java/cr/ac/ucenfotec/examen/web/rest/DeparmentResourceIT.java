package cr.ac.ucenfotec.examen.web.rest;

import cr.ac.ucenfotec.examen.ExamenApp;
import cr.ac.ucenfotec.examen.domain.Deparment;
import cr.ac.ucenfotec.examen.repository.DeparmentRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cr.ac.ucenfotec.examen.domain.enumeration.Status;
/**
 * Integration tests for the {@link DeparmentResource} REST controller.
 */
@SpringBootTest(classes = ExamenApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DeparmentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Status DEFAULT_STATE = Status.Activo;
    private static final Status UPDATED_STATE = Status.Desactivo;

    @Autowired
    private DeparmentRepository deparmentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeparmentMockMvc;

    private Deparment deparment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deparment createEntity(EntityManager em) {
        Deparment deparment = new Deparment()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .state(DEFAULT_STATE);
        return deparment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deparment createUpdatedEntity(EntityManager em) {
        Deparment deparment = new Deparment()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .state(UPDATED_STATE);
        return deparment;
    }

    @BeforeEach
    public void initTest() {
        deparment = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeparment() throws Exception {
        int databaseSizeBeforeCreate = deparmentRepository.findAll().size();
        // Create the Deparment
        restDeparmentMockMvc.perform(post("/api/deparments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deparment)))
            .andExpect(status().isCreated());

        // Validate the Deparment in the database
        List<Deparment> deparmentList = deparmentRepository.findAll();
        assertThat(deparmentList).hasSize(databaseSizeBeforeCreate + 1);
        Deparment testDeparment = deparmentList.get(deparmentList.size() - 1);
        assertThat(testDeparment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeparment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDeparment.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createDeparmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deparmentRepository.findAll().size();

        // Create the Deparment with an existing ID
        deparment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeparmentMockMvc.perform(post("/api/deparments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deparment)))
            .andExpect(status().isBadRequest());

        // Validate the Deparment in the database
        List<Deparment> deparmentList = deparmentRepository.findAll();
        assertThat(deparmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = deparmentRepository.findAll().size();
        // set the field null
        deparment.setName(null);

        // Create the Deparment, which fails.


        restDeparmentMockMvc.perform(post("/api/deparments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deparment)))
            .andExpect(status().isBadRequest());

        List<Deparment> deparmentList = deparmentRepository.findAll();
        assertThat(deparmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = deparmentRepository.findAll().size();
        // set the field null
        deparment.setDescription(null);

        // Create the Deparment, which fails.


        restDeparmentMockMvc.perform(post("/api/deparments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deparment)))
            .andExpect(status().isBadRequest());

        List<Deparment> deparmentList = deparmentRepository.findAll();
        assertThat(deparmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = deparmentRepository.findAll().size();
        // set the field null
        deparment.setState(null);

        // Create the Deparment, which fails.


        restDeparmentMockMvc.perform(post("/api/deparments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deparment)))
            .andExpect(status().isBadRequest());

        List<Deparment> deparmentList = deparmentRepository.findAll();
        assertThat(deparmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDeparments() throws Exception {
        // Initialize the database
        deparmentRepository.saveAndFlush(deparment);

        // Get all the deparmentList
        restDeparmentMockMvc.perform(get("/api/deparments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deparment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }
    
    @Test
    @Transactional
    public void getDeparment() throws Exception {
        // Initialize the database
        deparmentRepository.saveAndFlush(deparment);

        // Get the deparment
        restDeparmentMockMvc.perform(get("/api/deparments/{id}", deparment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deparment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDeparment() throws Exception {
        // Get the deparment
        restDeparmentMockMvc.perform(get("/api/deparments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeparment() throws Exception {
        // Initialize the database
        deparmentRepository.saveAndFlush(deparment);

        int databaseSizeBeforeUpdate = deparmentRepository.findAll().size();

        // Update the deparment
        Deparment updatedDeparment = deparmentRepository.findById(deparment.getId()).get();
        // Disconnect from session so that the updates on updatedDeparment are not directly saved in db
        em.detach(updatedDeparment);
        updatedDeparment
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .state(UPDATED_STATE);

        restDeparmentMockMvc.perform(put("/api/deparments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeparment)))
            .andExpect(status().isOk());

        // Validate the Deparment in the database
        List<Deparment> deparmentList = deparmentRepository.findAll();
        assertThat(deparmentList).hasSize(databaseSizeBeforeUpdate);
        Deparment testDeparment = deparmentList.get(deparmentList.size() - 1);
        assertThat(testDeparment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeparment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDeparment.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDeparment() throws Exception {
        int databaseSizeBeforeUpdate = deparmentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeparmentMockMvc.perform(put("/api/deparments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deparment)))
            .andExpect(status().isBadRequest());

        // Validate the Deparment in the database
        List<Deparment> deparmentList = deparmentRepository.findAll();
        assertThat(deparmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeparment() throws Exception {
        // Initialize the database
        deparmentRepository.saveAndFlush(deparment);

        int databaseSizeBeforeDelete = deparmentRepository.findAll().size();

        // Delete the deparment
        restDeparmentMockMvc.perform(delete("/api/deparments/{id}", deparment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Deparment> deparmentList = deparmentRepository.findAll();
        assertThat(deparmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
