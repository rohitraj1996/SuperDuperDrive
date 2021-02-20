package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    // Select Statement(s)
    @Select("SELECT * FROM CREDENTIALS where userId = #{userId}")
    List<Credential> getCredentials(Integer userId);

    // Insert Statement(s)
    @Insert("INSERT INTO CREDENTIALS(url, username, key, password, userId) VALUES (" +
            "#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);

    // Delete Statement(s)
    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    int deleteCredentialById(Integer credentialId);

    // Update Statement(s)
    @Update("UPDATE CREDENTIALS " +
            "SET url = #{url}, key = #{key}, username = #{username}, password = #{password} WHERE credentialId = #{credentialId}")
    int updateCredential(Credential credential);
}
