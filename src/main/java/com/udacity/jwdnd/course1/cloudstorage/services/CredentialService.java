package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;
    private UserMapper userMapper;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService, UserMapper userMapper) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
        this.userMapper = userMapper;
    }

    public List<Credential> getCredentials(String username){
        List<Credential> credentials = credentialMapper.getCredentials(userMapper.getUser(username).getUserId());

        for(Credential credential : credentials){
            credential.setEncryptedPassword(credential.getPassword());
            credential.setPassword(encryptionService.decryptValue(credential.getPassword(),credential.getKey()));
        }

        return credentials;
    }

    public int createCredential(String username, Credential credential){
        credential.setUserId(userMapper.getUser(username).getUserId());
        this.setSaltAndPassword(credential);
        return credentialMapper.insertCredential(credential);
    }

    private void setSaltAndPassword(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        credential.setKey(encodedSalt);
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(),credential.getKey()));
    }

    public int updateCredential(Credential credential){
        this.setSaltAndPassword(credential);
        return credentialMapper.updateCredential(credential);
    }

    public int deleteCredential(Integer credentialId){
        return credentialMapper.deleteCredentialById(credentialId);
    }
}
