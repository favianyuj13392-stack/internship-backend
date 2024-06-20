/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucb.edu.bo.internship.internship_backend.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author danielaldazosa
 */
@Entity
@Table(name = "pasantias")
@NamedQueries({
    @NamedQuery(name = "Pasantias.findAll", query = "SELECT p FROM Pasantias p"),
    @NamedQuery(name = "Pasantias.findByIdpasantias", query = "SELECT p FROM Pasantias p WHERE p.idpasantias = :idpasantias"),
    @NamedQuery(name = "Pasantias.findByTitulo", query = "SELECT p FROM Pasantias p WHERE p.titulo = :titulo"),
    @NamedQuery(name = "Pasantias.findByDescripcion", query = "SELECT p FROM Pasantias p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Pasantias.findByFechacierre", query = "SELECT p FROM Pasantias p WHERE p.fechacierre = :fechacierre"),
    @NamedQuery(name = "Pasantias.findByFechaingreso", query = "SELECT p FROM Pasantias p WHERE p.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "Pasantias.findByActivo", query = "SELECT p FROM Pasantias p WHERE p.activo = :activo")})
public class Pasantias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpasantias")
    private Integer idpasantias;
    @Basic(optional = false)
    @Lob
    @Column(name = "areas")
    private Object areas;
    @Basic(optional = false)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Lob
    @Column(name = "requisitos")
    private Object requisitos;
    @Basic(optional = false)
    @Lob
    @Column(name = "funciones")
    private Object funciones;
    @Basic(optional = false)
    @Lob
    @Column(name = "beneficios")
    private Object beneficios;
    @Basic(optional = false)
    @Column(name = "fechacierre")
    @Temporal(TemporalType.DATE)
    private Date fechacierre;
    @Basic(optional = false)
    @Column(name = "fechaingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaingreso;
    @Basic(optional = false)
    @Column(name = "activo")
    private boolean activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pasantiasIdpasantias", fetch = FetchType.LAZY)
    private List<Aplicacionespasantias> aplicacionespasantiasList;
    @JoinColumn(name = "instituciones_idinstituciones", referencedColumnName = "idinstituciones")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Instituciones institucionesIdinstituciones;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pasantiasIdpasantias", fetch = FetchType.LAZY)
    private List<Pasantiascarreras> pasantiascarrerasList;

    public Pasantias() {
    }

    public Pasantias(Integer idpasantias) {
        this.idpasantias = idpasantias;
    }

    public Pasantias(Integer idpasantias, Object areas, String titulo, String descripcion, Object requisitos, Object funciones, Object beneficios, Date fechacierre, Date fechaingreso, boolean activo) {
        this.idpasantias = idpasantias;
        this.areas = areas;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.requisitos = requisitos;
        this.funciones = funciones;
        this.beneficios = beneficios;
        this.fechacierre = fechacierre;
        this.fechaingreso = fechaingreso;
        this.activo = activo;
    }

    public Integer getIdpasantias() {
        return idpasantias;
    }

    public void setIdpasantias(Integer idpasantias) {
        this.idpasantias = idpasantias;
    }

    public Object getAreas() {
        return areas;
    }

    public void setAreas(Object areas) {
        this.areas = areas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Object getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(Object requisitos) {
        this.requisitos = requisitos;
    }

    public Object getFunciones() {
        return funciones;
    }

    public void setFunciones(Object funciones) {
        this.funciones = funciones;
    }

    public Object getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(Object beneficios) {
        this.beneficios = beneficios;
    }

    public Date getFechacierre() {
        return fechacierre;
    }

    public void setFechacierre(Date fechacierre) {
        this.fechacierre = fechacierre;
    }

    public Date getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(Date fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<Aplicacionespasantias> getAplicacionespasantiasList() {
        return aplicacionespasantiasList;
    }

    public void setAplicacionespasantiasList(List<Aplicacionespasantias> aplicacionespasantiasList) {
        this.aplicacionespasantiasList = aplicacionespasantiasList;
    }

    public Instituciones getInstitucionesIdinstituciones() {
        return institucionesIdinstituciones;
    }

    public void setInstitucionesIdinstituciones(Instituciones institucionesIdinstituciones) {
        this.institucionesIdinstituciones = institucionesIdinstituciones;
    }

    public List<Pasantiascarreras> getPasantiascarrerasList() {
        return pasantiascarrerasList;
    }

    public void setPasantiascarrerasList(List<Pasantiascarreras> pasantiascarrerasList) {
        this.pasantiascarrerasList = pasantiascarrerasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpasantias != null ? idpasantias.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pasantias)) {
            return false;
        }
        Pasantias other = (Pasantias) object;
        if ((this.idpasantias == null && other.idpasantias != null) || (this.idpasantias != null && !this.idpasantias.equals(other.idpasantias))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucb.edu.bo.internship.internship_backend.entity.Pasantias[ idpasantias=" + idpasantias + " ]";
    }
    
}
