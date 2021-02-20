package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(name = "firstName")
    private WebElement firstName;
    @FindBy(name = "lastName")
    private WebElement lastName;
    @FindBy(name = "username")
    private WebElement username;
    @FindBy(name = "password")
    private WebElement password;
    @FindBy(id = "signUp")
    private WebElement signupLink;

    public SignupPage(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public void signUpUser(String firstName, String lastName, String userName, String password){
        this.firstName.sendKeys(firstName);
        this.lastName.sendKeys(lastName);
        this.username.sendKeys(userName);
        this.password.sendKeys(password);
        this.signupLink.click();
    }
}
