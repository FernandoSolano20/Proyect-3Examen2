package cr.ac.ucenfotec.examen.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import cr.ac.ucenfotec.examen.domain.enumeration.Status;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "second_last_name", nullable = false)
    private String secondLastName;

    @NotNull
    @Column(name = "sex", nullable = false)
    private String sex;

    @NotNull
    @Column(name = "birthday_date", nullable = false)
    private ZonedDateTime birthdayDate;

    @NotNull
    @Column(name = "entry_date", nullable = false)
    private ZonedDateTime entryDate;

    @NotNull
    @Column(name = "position", nullable = false)
    private String position;

    @NotNull
    @Min(value = 1)
    @Column(name = "salary", nullable = false)
    private Integer salary;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private Status state;

    @OneToMany(mappedBy = "employee")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Leadership> leaders = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "employees", allowSetters = true)
    private Deparment deparment;

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

    public Employee name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public Employee lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public Employee secondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
        return this;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getSex() {
        return sex;
    }

    public Employee sex(String sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public ZonedDateTime getBirthdayDate() {
        return birthdayDate;
    }

    public Employee birthdayDate(ZonedDateTime birthdayDate) {
        this.birthdayDate = birthdayDate;
        return this;
    }

    public void setBirthdayDate(ZonedDateTime birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public ZonedDateTime getEntryDate() {
        return entryDate;
    }

    public Employee entryDate(ZonedDateTime entryDate) {
        this.entryDate = entryDate;
        return this;
    }

    public void setEntryDate(ZonedDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public String getPosition() {
        return position;
    }

    public Employee position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getSalary() {
        return salary;
    }

    public Employee salary(Integer salary) {
        this.salary = salary;
        return this;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Status getState() {
        return state;
    }

    public Employee state(Status state) {
        this.state = state;
        return this;
    }

    public void setState(Status state) {
        this.state = state;
    }

    public Set<Leadership> getLeaders() {
        return leaders;
    }

    public Employee leaders(Set<Leadership> leaderships) {
        this.leaders = leaderships;
        return this;
    }

    public Employee addLeader(Leadership leadership) {
        this.leaders.add(leadership);
        leadership.setEmployee(this);
        return this;
    }

    public Employee removeLeader(Leadership leadership) {
        this.leaders.remove(leadership);
        leadership.setEmployee(null);
        return this;
    }

    public void setLeaders(Set<Leadership> leaderships) {
        this.leaders = leaderships;
    }

    public Deparment getDeparment() {
        return deparment;
    }

    public Employee deparment(Deparment deparment) {
        this.deparment = deparment;
        return this;
    }

    public void setDeparment(Deparment deparment) {
        this.deparment = deparment;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", secondLastName='" + getSecondLastName() + "'" +
            ", sex='" + getSex() + "'" +
            ", birthdayDate='" + getBirthdayDate() + "'" +
            ", entryDate='" + getEntryDate() + "'" +
            ", position='" + getPosition() + "'" +
            ", salary=" + getSalary() +
            ", state='" + getState() + "'" +
            "}";
    }
}
