package io.ghost.emirozer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class FilterTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testFilterItPositive() {
        String responseMsg = target.path("filter").queryParam("input", "This is certainly a clean input!").queryParam("lang", "en").request().get(String.class);
        assertEquals("This is certainly a clean input!", responseMsg);
    }

    @Test
    public void testFilterItOneWordNegative() {
        String responseMsg = target.path("filter").queryParam("input", "shit").queryParam("lang", "en").request().get(String.class);
        assertEquals("****", responseMsg);
    }

    @Test
    public void testFilterItSentenceNegative() {
        String responseMsg = target.path("filter").queryParam("input", "Emir is an asshole").queryParam("lang", "en").request().get(String.class);
        assertEquals("Emir is an *******", responseMsg);
    }

    @Test
    public void testFilterItSentenceNegativeMid() {
        String responseMsg = target.path("filter").queryParam("input", "Emir is an asshole for sure!").queryParam("lang", "en").request().get(String.class);
        assertEquals("Emir is an ******* for sure!", responseMsg);
    }

    @Test
    public void testFilterItPositiveOneWord() {
        String responseMsg = target.path("filter").queryParam("input", "input").queryParam("lang", "en").request().get(String.class);
        assertEquals("input", responseMsg);
    }

    @Test
    public void testFilterItMultipleCurses() {
        String responseMsg = target.path("filter").queryParam("input", "shit asshole").queryParam("lang", "en").request().get(String.class);
        assertEquals("**** *******", responseMsg);
    }

    @Test
    public void testFilterItMultipleCursesInLongSentence() {
        String responseMsg = target.path("filter").queryParam("input", "shit emir hehehe asshole").queryParam("lang", "en").request().get(String.class);
        assertEquals("**** emir hehehe *******", responseMsg);
    }

    @Test
    public void testFilterItMultipleCursesInLongSentenceLeetVersion() {
        String responseMsg = target.path("filter").queryParam("input", "sh1t emir hehehe 4ssh0l3").queryParam("lang", "en").request().get(String.class);
        assertEquals("**** emir hehehe *******", responseMsg);
    }

	@Test
	public void testFilterItOneWordNegativeLeetVersion() {
		String responseMsg = target.path("filter").queryParam("input", "sh1t").queryParam("lang", "en").request().get(String.class);
		assertEquals("****", responseMsg);
	}

	@Test
	public void testFilterItOneWordDiffLanguage() {
		String responseMsg = target.path("filter").queryParam("input", "caralho").queryParam("lang", "pt").request().get(String.class);
		assertEquals("*******", responseMsg);
	}

	@Test
	public void testFilterItOneWordDiffLanguageWithoutLangParam() {
		String responseMsg = target.path("filter").queryParam("input", "caralho").queryParam("lang", "").request().get(String.class);
		assertEquals("*******", responseMsg);
	}

	@Test
	public void testFilterItOneWordDiffLanguageTR() {
		String responseMsg = target.path("filter").queryParam("input", "siktir").queryParam("lang", "tr").request().get(String.class);
		assertEquals("******", responseMsg);
	}

	@Test
	public void testFilterItOneWordDiffLanguageWithoutLangParamTR() {
		String responseMsg = target.path("filter").queryParam("input", "siktir").queryParam("lang", "").request().get(String.class);
		assertEquals("******", responseMsg);
	}



}
