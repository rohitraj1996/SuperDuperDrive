package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id="inputUsername")
    private WebElement username;
    @FindBy(id="inputPassword")
    private WebElement password;
    @FindBy(id="loginButton")
    private WebElement loginButton;

    public LoginPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void login(String userName, String password){
        this.username.sendKeys(userName);
        this.password.sendKeys(password);
        this.loginButton.click();
    }
}
