package cr.ac.ucenfotec.examen.web.rest;

import cr.ac.ucenfotec.examen.domain.Deparment;
import cr.ac.ucenfotec.examen.repository.DeparmentRepository;
import cr.ac.ucenfotec.examen.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link cr.ac.ucenfotec.examen.domain.Deparment}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DeparmentResource {

    private final Logger log = LoggerFactory.getLogger(DeparmentResource.class);

    private static final String ENTITY_NAME = "deparment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeparmentRepository deparmentRepository;

    public DeparmentResource(DeparmentRepository deparmentRepository) {
        this.deparmentRepository = deparmentRepository;
    }

    /**
     * {@code POST  /deparments} : Create a new deparment.
     *
     * @param deparment the deparment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deparment, or with status {@code 400 (Bad Request)} if the deparment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/deparments")
    public ResponseEntity<Deparment> createDeparment(@Valid @RequestBody Deparment deparment) throws URISyntaxException {
        log.debug("REST request to save Deparment : {}", deparment);
        if (deparment.getId() != null) {
            throw new BadRequestAlertException("A new deparment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Deparment result = deparmentRepository.save(deparment);
        return ResponseEntity.created(new URI("/api/deparments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deparments} : Updates an existing deparment.
     *
     * @param deparment the deparment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deparment,
     * or with status {@code 400 (Bad Request)} if the deparment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deparment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/deparments")
    public ResponseEntity<Deparment> updateDeparment(@Valid @RequestBody Deparment deparment) throws URISyntaxException {
        log.debug("REST request to update Deparment : {}", deparment);
        if (deparment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Deparment result = deparmentRepository.save(deparment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deparment.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /deparments} : get all the deparments.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deparments in body.
     */
    @GetMapping("/deparments")
    public List<Deparment> getAllDeparments(@RequestParam(required = false) String filter) {
        if ("leader-is-null".equals(filter)) {
            log.debug("REST request to get all Deparments where leader is null");
            return StreamSupport
                .stream(deparmentRepository.findAll().spliterator(), false)
                .filter(deparment -> deparment.getLeader() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Deparments");
        return deparmentRepository.findAll();
    }

    /**
     * {@code GET  /deparments/:id} : get the "id" deparment.
     *
     * @param id the id of the deparment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deparment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/deparments/{id}")
    public ResponseEntity<Deparment> getDeparment(@PathVariable Long id) {
        log.debug("REST request to get Deparment : {}", id);
        Optional<Deparment> deparment = deparmentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(deparment);
    }

    /**
     * {@code DELETE  /deparments/:id} : delete the "id" deparment.
     *
     * @param id the id of the deparment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/deparments/{id}")
    public ResponseEntity<Void> deleteDeparment(@PathVariable Long id) {
        log.debug("REST request to delete Deparment : {}", id);
        deparmentRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
