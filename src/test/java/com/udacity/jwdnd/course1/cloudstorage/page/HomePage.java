package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class HomePage {

    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "addNote")
    private WebElement addNoteButton;

    @FindBy(id = "addCredential")
    private WebElement addCredentialButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleDisplay;

    @FindBy(id = "note-description")
    private  WebElement noteDescriptionDisplay;

    @FindBy(css = ".note-title")
    private List<WebElement> noteTitles;

    @FindBy(css = ".note-description")
    private List<WebElement> noteDescriptions;

    @FindBy(id = "noteSubmit")
    private WebElement noteSubmitButton;

    @FindBy(id = "noteEdit")
    private List<WebElement> noteEditButtons;

    @FindBy(id = "noteDelete")
    private List<WebElement> noteDeleteButtons;

    @FindBy(id = "credential-url")
    private WebElement credentialURLDisplay;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameDisplay;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordDisplay;

    @FindBy(css = ".credential-url")
    private List<WebElement> credentialUrls;

    @FindBy(css = ".credential-username")
    private List<WebElement> credentialUsernames;

    @FindBy(css = ".credential-password")
    private List<WebElement> credentialPasswords;

    @FindBy(id = "credentialSubmit")
    private WebElement credentialsSubmitButton;

    @FindBy(id = "credentialEdit")
    private List<WebElement> credentialsEditButtons;

    @FindBy(id = "credentialDelete")
    private List<WebElement> credentialsDeleteButtons;

    private WebDriver webDriver;

    public HomePage(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    private void waitForVisibility(WebElement element) throws Error {
        new WebDriverWait(this.webDriver, 10).until(ExpectedConditions.visibilityOf(element));
    }

    public void logout(){
        this.waitForVisibility(this.logoutButton);
        this.onClick(this.logoutButton);
    }

    public void goToNotesTab(){
        this.waitForVisibility(this.notesTab);
        this.onClick(this.notesTab);
    }

    public void goToCredentialsTab(){
        this.waitForVisibility(this.credentialsTab);
        this.onClick(credentialsTab);
    }

    public void addNewNote(String noteTitle, String noteDescription){
        this.goToNotesTab();
        this.waitForVisibility(this.addNoteButton);
        this.onClick(this.addNoteButton);
        this.saveNote(noteTitle, noteDescription);
    }

    public List<String> getNoteTitles() {
        List<String> results = new ArrayList<>();
        for (WebElement ele : this.noteTitles) {
            results.add(ele.getAttribute("innerHTML"));
        }
        return results;
    }

    public List<String> getNoteDescriptions() {
        List<String> results = new ArrayList<>();
        for (WebElement ele : this.noteDescriptions) {
            results.add(ele.getAttribute("innerHTML"));
        }
        return results;
    }

    private void saveNote(String noteTitle, String noteDescription) {
        this.waitForVisibility(this.noteTitleDisplay);
        this.noteTitleDisplay.clear();
        this.noteTitleDisplay.sendKeys(noteTitle);

        this.noteDescriptionDisplay.clear();
        this.noteDescriptionDisplay.sendKeys(noteDescription);
        this.onClick(this.noteSubmitButton);
    }

    public void editNotes(int index, String noteTitle, String noteDescription) {
        this.waitForVisibility(this.noteEditButtons.get(index));
        this.onClick(this.noteEditButtons.get(index));
        this.saveNote(noteTitle, noteDescription);
    }

    public void deleteNote(int index) {
        this.onClick(noteDeleteButtons.get(index));
    }

    public void addNewCredential(String url,String username,String password) {
        this.goToCredentialsTab();
        this.waitForVisibility(this.addCredentialButton);
        this.onClick(this.addCredentialButton);
        this.saveCredential(url,username,password);
    }

    private void saveCredential(String url, String username, String password) {
        this.waitForVisibility(this.credentialURLDisplay);
        this.credentialURLDisplay.clear();
        this.credentialURLDisplay.sendKeys(url);

        this.credentialUsernameDisplay.clear();
        this.credentialUsernameDisplay.sendKeys(username);

        this.credentialPasswordDisplay.clear();
        this.credentialPasswordDisplay.sendKeys(password);
        this.onClick(this.credentialsSubmitButton);
    }

    public void editCredentials(int index, String url, String username, String password) {
        this.waitForVisibility(this.credentialsEditButtons.get(index));
        this.onClick(this.credentialsEditButtons.get(index));
        this.saveCredential(url, username, password);
    }

    public void deleteCredential(int index) {
        this.goToCredentialsTab();
        this.waitForVisibility(this.credentialsDeleteButtons.get(index));
        this.onClick(this.credentialsDeleteButtons.get(index));
    }

    public List<String> getCredentialUrls() {
        List<String> results = new ArrayList<String>();
        for (WebElement ele : this.credentialUrls) {
            results.add(ele.getAttribute("innerHTML"));
        }
        return results;
    }

    public List<String> getCredentialUsernames() {
        List<String> results = new ArrayList<String>();
        for (WebElement ele : this.credentialUsernames) {
            results.add(ele.getAttribute("innerHTML"));
        }
        return results;
    }

    public List<String> getCredentialPasswords() {
        List<String> results = new ArrayList<String>();
        for (WebElement ele : this.credentialPasswords) {
            results.add(ele.getAttribute("innerHTML"));
        }
        return results;
    }

    private void onClick(WebElement webElement) {
        ((JavascriptExecutor) this.webDriver).executeScript("arguments[0].click();", webElement);
    }
}
