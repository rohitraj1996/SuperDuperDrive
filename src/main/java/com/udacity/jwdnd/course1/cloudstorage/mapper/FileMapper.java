package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    
    // Select statement(s)
    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> getFiles(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId} AND userid = #{userId}")
    public File getFileById(Integer fileId, Integer userId);

    @Select("SELECT * FROM FILES WHERE filename = #{fileName} AND userid = #{userId}")
    public File getFileByName(String fileName, Integer userId);

    // Insert statement(s)
    @Insert("INSERT INTO FILES( fileName, contentType, fileSize, fileData, userId) " +
            "VALUES (#{fileName}, #{contentType}, #{fileSize}, #{fileData}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);

    // Delete statement(s)
    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    public int deleteFile(Integer fileId);
}
