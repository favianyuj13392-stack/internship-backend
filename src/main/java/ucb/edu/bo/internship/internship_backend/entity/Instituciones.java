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
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 *
 * @author danielaldazosa
 */
@Entity
@Table(name = "instituciones")
@NamedQueries({
    @NamedQuery(name = "Instituciones.findAll", query = "SELECT i FROM Instituciones i"),
    @NamedQuery(name = "Instituciones.findByIdinstituciones", query = "SELECT i FROM Instituciones i WHERE i.idinstituciones = :idinstituciones"),
    @NamedQuery(name = "Instituciones.findByNombre", query = "SELECT i FROM Instituciones i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "Instituciones.findByDescripcion", query = "SELECT i FROM Instituciones i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "Instituciones.findByDireccion", query = "SELECT i FROM Instituciones i WHERE i.direccion = :direccion"),
    @NamedQuery(name = "Instituciones.findByFotoinstitucion", query = "SELECT i FROM Instituciones i WHERE i.fotoinstitucion = :fotoinstitucion"),
    @NamedQuery(name = "Instituciones.findByCorreo", query = "SELECT i FROM Instituciones i WHERE i.correo = :correo"),
    @NamedQuery(name = "Instituciones.findByLogoempresa", query = "SELECT i FROM Instituciones i WHERE i.logoempresa = :logoempresa")})
public class Instituciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idinstituciones")
    private Integer idinstituciones;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "direccion")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "fotoinstitucion")
    private String fotoinstitucion;
    @Basic(optional = false)
    @Column(name = "correo")
    private String correo;
    @Basic(optional = false)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "sectores")
    private Object sectores;
    @Basic(optional = false)
    @Column(name = "logoempresa")
    private String logoempresa;
    @Basic(optional = false)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "fotos")
    private Object fotos;
    @Basic(optional = false)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "redessociales")
    private Object redessociales;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "institucionesIdinstituciones", fetch = FetchType.LAZY)
    private List<Usuariosinstituciones> usuariosinstitucionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "institucionesIdinstituciones", fetch = FetchType.LAZY)
    private List<Pasantias> pasantiasList;

    public Instituciones() {
    }

    public Instituciones(Integer idinstituciones) {
        this.idinstituciones = idinstituciones;
    }

    public Instituciones(Integer idinstituciones, String nombre, String descripcion, String direccion, String fotoinstitucion, String correo, Object sectores, String logoempresa, Object fotos, Object redessociales) {
        this.idinstituciones = idinstituciones;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.fotoinstitucion = fotoinstitucion;
        this.correo = correo;
        this.sectores = sectores;
        this.logoempresa = logoempresa;
        this.fotos = fotos;
        this.redessociales = redessociales;
    }

    public Integer getIdinstituciones() {
        return idinstituciones;
    }

    public void setIdinstituciones(Integer idinstituciones) {
        this.idinstituciones = idinstituciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFotoinstitucion() {
        return fotoinstitucion;
    }

    public void setFotoinstitucion(String fotoinstitucion) {
        this.fotoinstitucion = fotoinstitucion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Object getSectores() {
        return sectores;
    }

    public void setSectores(Object sectores) {
        this.sectores = sectores;
    }

    public String getLogoempresa() {
        return logoempresa;
    }

    public void setLogoempresa(String logoempresa) {
        this.logoempresa = logoempresa;
    }

    public Object getFotos() {
        return fotos;
    }

    public void setFotos(Object fotos) {
        this.fotos = fotos;
    }

    public Object getRedessociales() {
        return redessociales;
    }

    public void setRedessociales(Object redessociales) {
        this.redessociales = redessociales;
    }

    public List<Usuariosinstituciones> getUsuariosinstitucionesList() {
        return usuariosinstitucionesList;
    }

    public void setUsuariosinstitucionesList(List<Usuariosinstituciones> usuariosinstitucionesList) {
        this.usuariosinstitucionesList = usuariosinstitucionesList;
    }

    public List<Pasantias> getPasantiasList() {
        return pasantiasList;
    }

    public void setPasantiasList(List<Pasantias> pasantiasList) {
        this.pasantiasList = pasantiasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idinstituciones != null ? idinstituciones.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Instituciones)) {
            return false;
        }
        Instituciones other = (Instituciones) object;
        if ((this.idinstituciones == null && other.idinstituciones != null) || (this.idinstituciones != null && !this.idinstituciones.equals(other.idinstituciones))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucb.edu.bo.internship.internship_backend.entity.Instituciones[ idinstituciones=" + idinstituciones + " ]";
    }
    
}
