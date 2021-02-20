package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

@Controller
@RequestMapping("/file")
public class FileController {

    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/view/{fileId}")
    public ResponseEntity<Resource> viewFile(@PathVariable("fileId") Integer fileId, Authentication authentication, Model model){
        String username = authentication.getName();
        File file = fileService.getFileById(fileId, username);
        InputStreamResource inputStreamResource = new InputStreamResource(new ByteArrayInputStream(file.getFileData()));

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFileName())
                .body(inputStreamResource);
    }

    @PostMapping("/upload")
    public String saveFile(@RequestParam("fileUpload") MultipartFile multipartFile, Authentication authentication, Model model){
        String username = authentication.getName();

        try {
            if (multipartFile.getBytes().length == 0){
                model.addAttribute("result","error");
                model.addAttribute("message", "No file content, please select file to upload.");
            } else {
                if(fileService.getFileByName(multipartFile.getOriginalFilename(), username) != null){
                    model.addAttribute("result","error");
                    model.addAttribute("message","Duplicate file name are not allowed");
                    return "result";
                } else {
                    if (fileService.insertFile(username, multipartFile)) {
                        model.addAttribute("result", "success");
                    } else {
                        model.addAttribute("result", "error");
                        model.addAttribute("message", "Upload failed!");
                    }
                }
            }
        } catch (Exception e) {
            model.addAttribute("result", "error");
            model.addAttribute("message", e.getMessage());
        }

        return "result";
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId, Model model){
        fileService.deleteFile(fileId);
        model.addAttribute("result","success");

        return "result";
    }
}
