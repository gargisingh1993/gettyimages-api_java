package com.gettyimages.searchimages;

import com.gettyimages.ImageSearchFactory;
import com.gettyimages.SdkException;
import com.gettyimages.search.EditorialSegment;
import com.gettyimages.search.GraphicalStyles;
import com.gettyimages.search.ImagesSearch;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONArray;
import org.json.JSONObject;

import static org.junit.Assert.*;

/**
 * Implements image-search.feature
 */
public class ImageSearchContext {

    private ImagesSearch imagesSearch;
    private JSONObject searchResult;
    private long resultCount;
    private JSONArray resultImages;

    @When("^I configure my search for (.*) images$")
    public void i_configure_my_search_for_an_image_family(String imageFamily) throws Throwable {
        imagesSearch = ImageSearchFactory.getInstance().createImageSearchController(imageFamily);
    }

    @When("^I specify that I only want to return (.*) with my search results$")
    public void i_specify_that_I_only_want_to_return_a_response_field_with_my_search_results(String responseField) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        imagesSearch.WithResponseField(responseField);
    }

    @When("^I specify (.*) editorial segment$")
    public void I_specify_an_editorial_segment(String segment) throws Throwable {
        imagesSearch.WithEditorialSegment(EditorialSegment.valueOf(segment.toUpperCase()));
    }

    @When("^I search for (.*)")
    public void i_search_for_a_phrase(String phrase) throws Throwable {
        try {
            String searchResultStr = imagesSearch.WithPhrase(phrase)
                    .ExecuteAsync();
            searchResult = new JSONObject(searchResultStr);
        } catch (SdkException e) {
            fail("Search is supposed to successfully execute, but instead received: " + e.getLocalizedMessage());
        }
    }

    @Then("^I get a response back that has my images$")
    public void i_get_a_response_back_that_has_my_images() throws Throwable {
        assertNotNull(searchResult);
        resultCount = searchResult.getLong("result_count");
        resultImages = searchResult.getJSONArray("images");
        assertNotNull("Expected response to include a result count", resultCount);
        if(resultCount > 0) {
            assertTrue("Expected image count to b greater than zero", resultImages.length() > 0);
        }
    }

    @Then("^only required return fields plus (.*) are returned$")
    public void only_required_return_fields_plus_response_field_are_returned(String responseField) throws Throwable {
        String responseFieldValue = resultImages.getJSONObject(0).getString(responseField);
        assertTrue(responseFieldValue != null && responseFieldValue.length() > 0);
    }

    @When("^I specify a graphical (.*)$")
    public void I_specify_a_graphical_style(String style) throws Throwable {
        imagesSearch.WithGraphicalStyle(GraphicalStyles.valueOf(style.toUpperCase()));
    }
}
