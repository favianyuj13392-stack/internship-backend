package ucb.edu.bo.internship.internship_backend.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ucb.edu.bo.internship.internship_backend.bl.MinioBl;
import ucb.edu.bo.internship.internship_backend.dto.ImagenResponseDto;
import ucb.edu.bo.internship.internship_backend.dto.ResponseDto;
import ucb.edu.bo.internship.internship_backend.service.MinioService;

@RequestMapping("/api/v1")
@RestController
public class FileController {

    private final MinioBl fileBl;
    private final MinioService minioService;
    public FileController(MinioBl fileBl, MinioService minioService) {
        this.fileBl = fileBl;
        this.minioService = minioService;
    }
    @PostMapping("/files/upload") // cambiar a private
    public ResponseDto<ImagenResponseDto> handleFileUpload(@RequestParam("file") MultipartFile file) {
        ResponseDto<ImagenResponseDto> responseDto = new ResponseDto<>();
        try {
            responseDto = minioService.uploadFileImage(file);
            return responseDto;
        } catch (Exception e) {
            responseDto.setCode("500");
            responseDto.setErrorMessage("Error al subir el archivo");
            return responseDto;
        }
    }

    @GetMapping("/files/download/{idFile}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String idFile) {
        try {
            return minioService.downloadFile(idFile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
