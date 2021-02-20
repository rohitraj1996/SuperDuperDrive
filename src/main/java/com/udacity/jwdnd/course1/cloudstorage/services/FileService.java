package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;
    private UserMapper userMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public List<File> getFiles(String username){
        return fileMapper.getFiles(userMapper.getUser(username).getUserId());
    }

    public File getFileById(Integer fileId, String username){
        return fileMapper.getFileById(fileId, userMapper.getUser(username).getUserId());
    }

    public File getFileByName(String fileName, String username){
        return fileMapper.getFileByName(fileName, userMapper.getUser(username).getUserId());
    }

    public boolean insertFile(String username, MultipartFile file){
        try{
            File newFile = new File();
            newFile.setUserId(userMapper.getUser(username).getUserId());
            newFile.setFileName(file.getOriginalFilename());
            newFile.setFileSize(String.valueOf(file.getSize()));
            newFile.setContentType(file.getContentType());
            newFile.setFileData(file.getBytes());
            fileMapper.insertFile(newFile);

        } catch (Exception e){
            return false;
        }
        return true;
    }

    public int deleteFile(Integer fileId){
        return fileMapper.deleteFile(fileId);
    }
}
