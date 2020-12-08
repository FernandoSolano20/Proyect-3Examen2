package cr.ac.ucenfotec.examen.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import cr.ac.ucenfotec.examen.domain.enumeration.Status;

/**
 * A Deparment.
 */
@Entity
@Table(name = "deparment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Deparment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private Status state;

    @OneToMany(mappedBy = "deparment")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Employee> employees = new HashSet<>();

    @OneToOne(mappedBy = "deparment")
    @JsonIgnore
    private Leadership leader;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Deparment name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Deparment description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getState() {
        return state;
    }

    public Deparment state(Status state) {
        this.state = state;
        return this;
    }

    public void setState(Status state) {
        this.state = state;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public Deparment employees(Set<Employee> employees) {
        this.employees = employees;
        return this;
    }

    public Deparment addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.setDeparment(this);
        return this;
    }

    public Deparment removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.setDeparment(null);
        return this;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Leadership getLeader() {
        return leader;
    }

    public Deparment leader(Leadership leadership) {
        this.leader = leadership;
        return this;
    }

    public void setLeader(Leadership leadership) {
        this.leader = leadership;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deparment)) {
            return false;
        }
        return id != null && id.equals(((Deparment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Deparment{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
