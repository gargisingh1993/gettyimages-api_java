package com.gettyimages;

import cucumber.api.java.en.Given;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Shared test class, primarily used for authentication calls.
 */
public class SharedContext {

    public static final String GETTYIMAGESAPI_ENV_NAME_APIKEY = "GettyImagesApi_ApiKey";
    public static final String GETTYIMAGESAPI_ENV_NAME_APISECRET = "GettyImagesApi_ApiSecret";
    public static final String GETTYIMAGESAPI_ENV_NAME_USERNAME = "GettyImagesApi_UserName";
    public static final String GETTYIMAGESAPI_ENV_NAME_USERPASSWORD = "GettyImagesApi_UserPassword";
    public static final String BASEURL = "https://api.gettyimages.com";
    public static Map<String, String> ApiProperties;

    static {
        ApiProperties = new HashMap<>();
        GetEnvironmentVariables();
    }

    @Given("^I have an apikey$")
    public void i_have_an_apikey() throws Throwable {
        assertTrue("Expected API Key to exist", SharedContext.ApiKeyExists());
    }

    @Given("^an apisecret$")
    public void an_apisecret() throws Throwable {
        assertTrue("Expected API Secret to exist", SharedContext.ApiSecretExists());
    }

    @Given("^a username$")
    public void a_username() throws Throwable {
        assertTrue("Expected Username to exist", SharedContext.UsernameExists());
    }

    @Given("^a password$")
    public void a_password() throws Throwable {
        assertTrue("Expected User Password to exist", SharedContext.UserPasswordExists());
    }

    public static ApiClient GetApiClientWithResourceOwnerCredentials() {
        return ApiClient.GetApiClientWithResourceOwnerCredentials(
                GetProp(SharedContext.GETTYIMAGESAPI_ENV_NAME_APIKEY),
                GetProp(SharedContext.GETTYIMAGESAPI_ENV_NAME_APISECRET),
                GetProp(SharedContext.GETTYIMAGESAPI_ENV_NAME_USERNAME),
                GetProp(SharedContext.GETTYIMAGESAPI_ENV_NAME_USERPASSWORD)
        );
    }

    public static boolean ApiKeyExists() {
        return ApiProperties.containsKey(GETTYIMAGESAPI_ENV_NAME_APIKEY);
    }

    public static boolean ApiSecretExists() {
        return ApiProperties.containsKey(GETTYIMAGESAPI_ENV_NAME_APISECRET);
    }

    public static boolean UsernameExists() {
        return ApiProperties.containsKey(GETTYIMAGESAPI_ENV_NAME_USERNAME);
    }

    public static boolean UserPasswordExists() {
        return ApiProperties.containsKey(GETTYIMAGESAPI_ENV_NAME_USERPASSWORD);
    }

    public static String GetProp(String gettyimagesapiEnvName) {
        return SharedContext.ApiProperties.get(gettyimagesapiEnvName);
    }

    private static void GetEnvironmentVariables() {
        List<String> environmentVariables = new ArrayList<String>() {{
            add(GETTYIMAGESAPI_ENV_NAME_APIKEY);
            add(GETTYIMAGESAPI_ENV_NAME_APISECRET);
            add(GETTYIMAGESAPI_ENV_NAME_USERNAME);
            add(GETTYIMAGESAPI_ENV_NAME_USERPASSWORD);
        }};
        for (String key : environmentVariables) {
            String val;
            val = System.getenv(key);
            if (val == null || val.length() == 0) {
                fail("Environment variable does not exist: " + key);
            }
            ApiProperties.put(key, val);
        }
    }


}
