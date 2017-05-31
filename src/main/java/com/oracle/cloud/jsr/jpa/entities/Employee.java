package com.oracle.cloud.jsr.jpa.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * Model class for JPA. The JAXB annotations aid in XML marshaling. Thanks to
 * Eclipselink Moxy, instances of this class can be marshaled to JSON based on
 * the JAXB metadata
 *
 */
@Entity
@Table(name = "EMPLOYEES")
@XmlRootElement

public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "EMP_ID")
    private Integer empId; 
    
    @Column(name = "FULLNAME")
    @Basic(optional = false)
    private String fullName;
    
    @Column(name = "EMAIL")
    @Basic(optional = false)
    private String email;
    
    public Employee() {
    }

    public Employee(Integer empId, String fullName, String email) {
        this.empId = empId;
        this.fullName = fullName;
        this.email = email;
    }

    public Employee(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Employee{" + "empId=" + empId + ", fullName=" + fullName + ", email=" + email + '}';
    }

}
