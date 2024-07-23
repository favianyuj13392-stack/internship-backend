package ucb.edu.bo.internship.internship_backend.dto;

public class KPISDto {
    //Estudiantes
    private Long countEstudiantes;
    private Long countEstudiantesPorCarrera;
    private Long countEstudiantesPorFecha;
    //Pasantias
    private Long countPasantiasActivas;
    private Long countPasantiasPendientes;
    private Long countPasantiasPorCarrera;
    private Long countPasantiasPorEmpresa;
    private Long countPasantiasPorSector;
    private Long countPasantiasPorArea;
    //Aplicaciones
    private Long countAplicacionesPasantia;
    private Long countAplicacionesPasantiaPorCarrera;
    private Long countAplicacionesPasantiaPorEmpresa;
    private Long countAplicacionesPasantiaPorSector;
    private Long countAplicacionesPasantiaPorArea;
    private Long countAplicacionesPasantiaAceptadas;
    private Long countPasantiasQueNoAceptaronANingunEstudiante;
    //Curriculum
    private Long countCurriculums;
    private Double promedioCurriculumsPorEstudiante;
    //Empresas
    private Long countEmpresas;
    private Long countEmpresasPorSector;
    //Usuarios
    private Long countUsuariosEmpresa;
    private Long countUsuariosPorEmpresa;

    public KPISDto(Long countEstudiantes, Long countEstudiantesPorCarrera, Long countEstudiantesPorFecha, Long countPasantiasActivas, Long countPasantiasPendientes, Long countPasantiasPorCarrera, Long countPasantiasPorEmpresa, Long countPasantiasPorSector, Long countPasantiasPorArea, Long countAplicacionesPasantia, Long countAplicacionesPasantiaPorCarrera, Long countAplicacionesPasantiaPorEmpresa, Long countAplicacionesPasantiaPorSector, Long countAplicacionesPasantiaPorArea, Long countAplicacionesPasantiaAceptadas, Long countPasantiasQueNoAceptaronANingunEstudiante, Long countCurriculums, Double promedioCurriculumsPorEstudiante, Long countEmpresas, Long countEmpresasPorSector, Long countUsuariosEmpresa, Long countUsuariosPorEmpresa) {
        this.countEstudiantes = countEstudiantes;
        this.countEstudiantesPorCarrera = countEstudiantesPorCarrera;
        this.countEstudiantesPorFecha = countEstudiantesPorFecha;
        this.countPasantiasActivas = countPasantiasActivas;
        this.countPasantiasPendientes = countPasantiasPendientes;
        this.countPasantiasPorCarrera = countPasantiasPorCarrera;
        this.countPasantiasPorEmpresa = countPasantiasPorEmpresa;
        this.countPasantiasPorSector = countPasantiasPorSector;
        this.countPasantiasPorArea = countPasantiasPorArea;
        this.countAplicacionesPasantia = countAplicacionesPasantia;
        this.countAplicacionesPasantiaPorCarrera = countAplicacionesPasantiaPorCarrera;
        this.countAplicacionesPasantiaPorEmpresa = countAplicacionesPasantiaPorEmpresa;
        this.countAplicacionesPasantiaPorSector = countAplicacionesPasantiaPorSector;
        this.countAplicacionesPasantiaPorArea = countAplicacionesPasantiaPorArea;
        this.countAplicacionesPasantiaAceptadas = countAplicacionesPasantiaAceptadas;
        this.countPasantiasQueNoAceptaronANingunEstudiante = countPasantiasQueNoAceptaronANingunEstudiante;
        this.countCurriculums = countCurriculums;
        this.promedioCurriculumsPorEstudiante = promedioCurriculumsPorEstudiante;
        this.countEmpresas = countEmpresas;
        this.countEmpresasPorSector = countEmpresasPorSector;
        this.countUsuariosEmpresa = countUsuariosEmpresa;
        this.countUsuariosPorEmpresa = countUsuariosPorEmpresa;
    }

    public Long getCountEstudiantes() {
        return countEstudiantes;
    }

    public void setCountEstudiantes(Long countEstudiantes) {
        this.countEstudiantes = countEstudiantes;
    }

    public Long getCountEstudiantesPorCarrera() {
        return countEstudiantesPorCarrera;
    }

    public void setCountEstudiantesPorCarrera(Long countEstudiantesPorCarrera) {
        this.countEstudiantesPorCarrera = countEstudiantesPorCarrera;
    }

    public Long getCountEstudiantesPorFecha() {
        return countEstudiantesPorFecha;
    }

    public void setCountEstudiantesPorFecha(Long countEstudiantesPorFecha) {
        this.countEstudiantesPorFecha = countEstudiantesPorFecha;
    }

    public Long getCountPasantiasActivas() {
        return countPasantiasActivas;
    }

    public void setCountPasantiasActivas(Long countPasantiasActivas) {
        this.countPasantiasActivas = countPasantiasActivas;
    }

    public Long getCountPasantiasPendientes() {
        return countPasantiasPendientes;
    }

    public void setCountPasantiasPendientes(Long countPasantiasPendientes) {
        this.countPasantiasPendientes = countPasantiasPendientes;
    }

    public Long getCountPasantiasPorCarrera() {
        return countPasantiasPorCarrera;
    }

    public void setCountPasantiasPorCarrera(Long countPasantiasPorCarrera) {
        this.countPasantiasPorCarrera = countPasantiasPorCarrera;
    }

    public Long getCountPasantiasPorEmpresa() {
        return countPasantiasPorEmpresa;
    }

    public void setCountPasantiasPorEmpresa(Long countPasantiasPorEmpresa) {
        this.countPasantiasPorEmpresa = countPasantiasPorEmpresa;
    }

    public Long getCountPasantiasPorSector() {
        return countPasantiasPorSector;
    }

    public void setCountPasantiasPorSector(Long countPasantiasPorSector) {
        this.countPasantiasPorSector = countPasantiasPorSector;
    }

    public Long getCountPasantiasPorArea() {
        return countPasantiasPorArea;
    }

    public void setCountPasantiasPorArea(Long countPasantiasPorArea) {
        this.countPasantiasPorArea = countPasantiasPorArea;
    }

    public Long getCountAplicacionesPasantia() {
        return countAplicacionesPasantia;
    }

    public void setCountAplicacionesPasantia(Long countAplicacionesPasantia) {
        this.countAplicacionesPasantia = countAplicacionesPasantia;
    }

    public Long getCountAplicacionesPasantiaPorCarrera() {
        return countAplicacionesPasantiaPorCarrera;
    }

    public void setCountAplicacionesPasantiaPorCarrera(Long countAplicacionesPasantiaPorCarrera) {
        this.countAplicacionesPasantiaPorCarrera = countAplicacionesPasantiaPorCarrera;
    }

    public Long getCountAplicacionesPasantiaPorEmpresa() {
        return countAplicacionesPasantiaPorEmpresa;
    }

    public void setCountAplicacionesPasantiaPorEmpresa(Long countAplicacionesPasantiaPorEmpresa) {
        this.countAplicacionesPasantiaPorEmpresa = countAplicacionesPasantiaPorEmpresa;
    }

    public Long getCountAplicacionesPasantiaPorSector() {
        return countAplicacionesPasantiaPorSector;
    }

    public void setCountAplicacionesPasantiaPorSector(Long countAplicacionesPasantiaPorSector) {
        this.countAplicacionesPasantiaPorSector = countAplicacionesPasantiaPorSector;
    }

    public Long getCountAplicacionesPasantiaPorArea() {
        return countAplicacionesPasantiaPorArea;
    }

    public void setCountAplicacionesPasantiaPorArea(Long countAplicacionesPasantiaPorArea) {
        this.countAplicacionesPasantiaPorArea = countAplicacionesPasantiaPorArea;
    }

    public Long getCountAplicacionesPasantiaAceptadas() {
        return countAplicacionesPasantiaAceptadas;
    }

    public void setCountAplicacionesPasantiaAceptadas(Long countAplicacionesPasantiaAceptadas) {
        this.countAplicacionesPasantiaAceptadas = countAplicacionesPasantiaAceptadas;
    }

    public Long getCountPasantiasQueNoAceptaronANingunEstudiante() {
        return countPasantiasQueNoAceptaronANingunEstudiante;
    }

    public void setCountPasantiasQueNoAceptaronANingunEstudiante(Long countPasantiasQueNoAceptaronANingunEstudiante) {
        this.countPasantiasQueNoAceptaronANingunEstudiante = countPasantiasQueNoAceptaronANingunEstudiante;
    }

    public Long getCountCurriculums() {
        return countCurriculums;
    }

    public void setCountCurriculums(Long countCurriculums) {
        this.countCurriculums = countCurriculums;
    }

    public Double getPromedioCurriculumsPorEstudiante() {
        return promedioCurriculumsPorEstudiante;
    }

    public void setPromedioCurriculumsPorEstudiante(Double promedioCurriculumsPorEstudiante) {
        this.promedioCurriculumsPorEstudiante = promedioCurriculumsPorEstudiante;
    }

    public Long getCountEmpresas() {
        return countEmpresas;
    }

    public void setCountEmpresas(Long countEmpresas) {
        this.countEmpresas = countEmpresas;
    }

    public Long getCountEmpresasPorSector() {
        return countEmpresasPorSector;
    }

    public void setCountEmpresasPorSector(Long countEmpresasPorSector) {
        this.countEmpresasPorSector = countEmpresasPorSector;
    }

    public Long getCountUsuariosEmpresa() {
        return countUsuariosEmpresa;
    }

    public void setCountUsuariosEmpresa(Long countUsuariosEmpresa) {
        this.countUsuariosEmpresa = countUsuariosEmpresa;
    }

    public Long getCountUsuariosPorEmpresa() {
        return countUsuariosPorEmpresa;
    }

    public void setCountUsuariosPorEmpresa(Long countUsuariosPorEmpresa) {
        this.countUsuariosPorEmpresa = countUsuariosPorEmpresa;
    }

    @Override
    public String toString() {
        return "KPISDto{" +
                "countEstudiantes=" + countEstudiantes +
                ", countEstudiantesPorCarrera=" + countEstudiantesPorCarrera +
                ", countEstudiantesPorFecha=" + countEstudiantesPorFecha +
                ", countPasantiasActivas=" + countPasantiasActivas +
                ", countPasantiasPendientes=" + countPasantiasPendientes +
                ", countPasantiasPorCarrera=" + countPasantiasPorCarrera +
                ", countPasantiasPorEmpresa=" + countPasantiasPorEmpresa +
                ", countPasantiasPorSector=" + countPasantiasPorSector +
                ", countPasantiasPorArea=" + countPasantiasPorArea +
                ", countAplicacionesPasantia=" + countAplicacionesPasantia +
                ", countAplicacionesPasantiaPorCarrera=" + countAplicacionesPasantiaPorCarrera +
                ", countAplicacionesPasantiaPorEmpresa=" + countAplicacionesPasantiaPorEmpresa +
                ", countAplicacionesPasantiaPorSector=" + countAplicacionesPasantiaPorSector +
                ", countAplicacionesPasantiaPorArea=" + countAplicacionesPasantiaPorArea +
                ", countAplicacionesPasantiaAceptadas=" + countAplicacionesPasantiaAceptadas +
                ", countPasantiasQueNoAceptaronANingunEstudiante=" + countPasantiasQueNoAceptaronANingunEstudiante +
                ", countCurriculums=" + countCurriculums +
                ", promedioCurriculumsPorEstudiante=" + promedioCurriculumsPorEstudiante +
                ", countEmpresas=" + countEmpresas +
                ", countEmpresasPorSector=" + countEmpresasPorSector +
                ", countUsuariosEmpresa=" + countUsuariosEmpresa +
                ", countUsuariosPorEmpresa=" + countUsuariosPorEmpresa +
                '}';
    }
}
