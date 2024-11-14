package com.fleet.fleet.controllers;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/photos")
@AllArgsConstructor
public class PhotoController {

    private final String uploadDir = "C:\\Users\\ti.02\\Documents\\Projects\\uploadDir\\";

    @GetMapping("/{checklistId}/{questionId}")
    public ResponseEntity<Resource> serveFile(@PathVariable String checklistId, @PathVariable String questionId) {
        try {
            Path filePath = Paths.get(uploadDir).resolve("chk" + checklistId + "qst" + questionId).normalize();

            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists()) {
                String contentType = Files.probeContentType(filePath);

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, contentType)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
