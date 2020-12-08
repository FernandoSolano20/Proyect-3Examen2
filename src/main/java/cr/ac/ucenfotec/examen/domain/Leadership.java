package cr.ac.ucenfotec.examen.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Leadership.
 */
@Entity
@Table(name = "leadership")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Leadership implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private ZonedDateTime startDate;

    @OneToOne
    @JoinColumn(unique = true)
    private Deparment deparment;

    @ManyToOne
    @JsonIgnoreProperties(value = "leaders", allowSetters = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public Leadership startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public Deparment getDeparment() {
        return deparment;
    }

    public Leadership deparment(Deparment deparment) {
        this.deparment = deparment;
        return this;
    }

    public void setDeparment(Deparment deparment) {
        this.deparment = deparment;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Leadership employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Leadership)) {
            return false;
        }
        return id != null && id.equals(((Leadership) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Leadership{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            "}";
    }
}
