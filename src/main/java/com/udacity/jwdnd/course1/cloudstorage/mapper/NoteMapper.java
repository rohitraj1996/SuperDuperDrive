package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    // Select statement(s)
    @Select("SELECT * FROM NOTES where userId = #{userId}")
    List<Note> getNotes(Integer userId);

    // Insert statement(s)
    @Insert("INSERT INTO NOTES(noteTitle, noteDescription, userId) VALUES (" +
            "#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(Note note);

    // Update statement(s)
    @Update("UPDATE NOTES " +
            "SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription} " +
            "WHERE noteId = #{noteId}")
    int updateNote(Note note);

    // Delete statement(s)
    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    int deleteNoteById(Integer noteId);
}
