package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;
	private String baseUrl;

	private WebDriver driver;

	private final String NOTE_TITLE = "Test Title!";
	private final String NOTE_DESCRIPTION = "Test Description.";
	private final String CREDENTIAL_URL = "www.udacity.com";
	private final String CREDENTIAL_USERNAME = "rohitRaj";
	private final String CREDENTIAL_PASSWORD = "password";

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		this.baseUrl = "http://localhost:" + this.port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			this.driver.quit();
		}
	}

	// Basic signup, login, and unauthorized access tests
	@Test
	public void testLoginPage() {
		this.driver.get(this.baseUrl + "/login");
		Assertions.assertEquals("Login", this.driver.getTitle());
	}

	@Test
	public void testNoHomeAccessWithoutLogin(){
		this.driver.get(this.baseUrl + "/home");
		assertNotEquals("Home", this.driver.getTitle());
	}

	@Test
	public void testRedirectToLoginIfNotSignedIn() {
		this.driver.get(baseUrl + "/home");
		Assertions.assertEquals("Login", this.driver.getTitle());
	}

	@Test
	public void testSignupPage() {
		this.driver.get(this.baseUrl + "/signup");
		Assertions.assertEquals("Sign Up", this.driver.getTitle());
	}

	@Test
	public void testSignupLoginSuccess(){
		this.signUp();
		this.logIn();
		assertEquals("Home", this.driver.getTitle());
	}

	@Test
	public void testLogoutSuccess(){
		this.signUp();
		this.logIn();
		this.logOut();
		assertEquals("Login", this.driver.getTitle());
	}

	@Test
	public void testLoginFailsIfNotSignUp() {
		this.driver.get(this.baseUrl + "/login");
		LoginPage loginPage = new LoginPage(this.driver);
		loginPage.login("admin1", "password");

		WebElement errorMessage = this.driver.findElement(By.id("error"));

		Assertions.assertEquals("Login", this.driver.getTitle());
		Assertions.assertEquals("Invalid username or password", errorMessage.getText());
	}

	// Note creation, viewing, editing, and deletion tests
	@Test
	public void testSaveNote(){
		this.signUp();
		this.logIn();

		HomePage homePage = this.addNote(this.NOTE_TITLE, this.NOTE_DESCRIPTION);

		List<String> noteTitles = homePage.getNoteTitles();
		List<String> noteDescriptions = homePage.getNoteDescriptions();

		assertEquals(this.NOTE_TITLE, noteTitles.get(0));
		assertEquals(this.NOTE_DESCRIPTION, noteDescriptions.get(0));

		// Cleanup
		homePage.deleteNote(0);
	}

	@Test
	public void testEditNote() {
		this.signUp();
		this.logIn();

		String updateNoteTitle = "Updated Test Title!";
		String updateNoteDescription = "Updated Test Description.";

		// Add new note
		HomePage homePage = this.addNote(this.NOTE_TITLE, this.NOTE_DESCRIPTION);

		// Edit note
		homePage.editNotes(0, updateNoteTitle, updateNoteDescription);
		this.driver.get(this.baseUrl + "/home");

		// Read all notes
		List<String> noteTitles = homePage.getNoteTitles();
		List<String> noteDescriptions = homePage.getNoteDescriptions();

		// Test
		assertEquals(updateNoteTitle, noteTitles.get(0));
		assertEquals(updateNoteDescription, noteDescriptions.get(0));

		// Cleanup
		homePage.deleteNote(0);
	}

	@Test
	public void testDeleteNote() {
		this.signUp();
		this.logIn();

		// Add new note
		HomePage homePage = this.addNote(this.NOTE_TITLE, this.NOTE_DESCRIPTION);

		// Read all notes
		List<String> noteTitles = homePage.getNoteTitles();
		List<String> noteDescriptions = homePage.getNoteDescriptions();

		// Assert there is only 1 element in both title and description
		assertEquals(1, noteTitles.size());
		assertEquals(1, noteDescriptions.size());

		// Delete note
		homePage.deleteNote(0);
		noteTitles = homePage.getNoteTitles();
		noteDescriptions = homePage.getNoteDescriptions();

		// Assert that no note is there
		assertEquals(0, noteTitles.size());
		assertEquals(0, noteDescriptions.size());
	}

	private HomePage addNote(String noteTitle, String noteDescription){
		HomePage homePage = new HomePage(this.driver);
		homePage.addNewNote(noteTitle, noteDescription);

		this.driver.get(this.baseUrl + "/home");
		homePage.goToNotesTab();

		return homePage;
	}

	// Credential creation, viewing, editing, and deletion tests
	@Test
	public void testSaveCredential() {
		this.signUp();
		this.logIn();

		// Create
		HomePage homePage = this.addCredential(this.CREDENTIAL_URL, this.CREDENTIAL_USERNAME, this.CREDENTIAL_PASSWORD);

		// Read all credentials
		List<String> credentialUrls = homePage.getCredentialUrls();
		List<String> credentialUsernames = homePage.getCredentialUsernames();
		List<String> credentialPasswords = homePage.getCredentialPasswords();

		// Test
		assertEquals(CREDENTIAL_URL, credentialUrls.get(0));
		assertEquals(CREDENTIAL_USERNAME, credentialUsernames.get(0));
		assertNotEquals(CREDENTIAL_PASSWORD, credentialPasswords.get(0)); // must not show unencrypted password

		// Cleanup
		homePage.deleteCredential(0);
	}

	@Test
	public void testEditCredential() {
		this.signUp();
		this.logIn();

		String newUrl = "www.google.com";
		String newUsername = "admin";
		String newPassword = "123";

		// Create
		HomePage homePage = this.addCredential(this.CREDENTIAL_URL, this.CREDENTIAL_USERNAME, this.CREDENTIAL_PASSWORD);
		String oldEncryptedPassword = homePage.getCredentialPasswords().get(0);

		// Edit
		homePage.editCredentials(0, newUrl, newUsername, newPassword);
		this.driver.get(this.baseUrl + "/home");

		// Read all credentials
		List<String> credentialUrls = homePage.getCredentialUrls();
		List<String> credentialUsernames = homePage.getCredentialUsernames();
		List<String> credentialPasswords = homePage.getCredentialPasswords();

		// Test
		assertEquals(newUrl, credentialUrls.get(0));
		assertEquals(newUsername, credentialUsernames.get(0));
		assertNotEquals(oldEncryptedPassword, credentialPasswords.get(0)); // Old and new encrypted password will be different
		assertNotEquals(newPassword, credentialPasswords.get(0)); // Actual and Encrypted password is not same

		// Cleanup
		homePage.deleteCredential(0);
	}

	@Test
	public void testDeleteCredential() {
		this.signUp();
		this.logIn();

		// Create
		HomePage homePage = this.addCredential(this.CREDENTIAL_URL, this.CREDENTIAL_USERNAME, this.CREDENTIAL_PASSWORD);

		// Read all credentials
		List<String> credentialUrls = homePage.getCredentialUrls();
		List<String> credentialUsernames = homePage.getCredentialUsernames();
		List<String> credentialPasswords = homePage.getCredentialPasswords();

		// Assert there is only 1 element in both url, username and password
		assertEquals(1, credentialUrls.size());
		assertEquals(1, credentialUsernames.size());
		assertEquals(1, credentialPasswords.size());

		// Delete
		homePage.deleteCredential(0);
		credentialUrls = homePage.getCredentialUrls();
		credentialUsernames = homePage.getCredentialUsernames();
		credentialPasswords = homePage.getCredentialPasswords();

		// Assert that no note is there
		assertEquals(0, credentialUrls.size());
		assertEquals(0, credentialUsernames.size());
		assertEquals(0, credentialPasswords.size());
	}

	private HomePage addCredential(String credentialUrl, String credentialUsername, String credentialPassword){
		HomePage homePage = new HomePage(this.driver);
		homePage.addNewCredential(credentialUrl, credentialUsername, credentialPassword);

		this.driver.get(this.baseUrl + "/home");
		homePage.goToCredentialsTab();

		return homePage;
	}

	private void signUp() {
		this.driver.get(this.baseUrl + "/signup");
		SignupPage signupPage = new SignupPage(this.driver);
		signupPage.signUpUser("Rohit", "Raj", "admin", "password");
	}

	private void logIn(){
		this.driver.get(this.baseUrl + "/login");
		LoginPage loginPage = new LoginPage(this.driver);
		loginPage.login("admin", "password");
	}

	private void logOut(){
		HomePage homePage = new HomePage(this.driver);
		homePage.logout();
	}
}
