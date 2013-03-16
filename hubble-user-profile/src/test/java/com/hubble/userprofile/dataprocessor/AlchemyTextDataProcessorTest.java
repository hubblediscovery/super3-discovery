/**
 * Copyright 2012, Hubble Apps.
 */
package com.hubble.userprofile.dataprocessor;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author narenathmaraman
 * 
 */
public class AlchemyTextDataProcessorTest {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	private TextDataProcessor dataProc = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		dataProc = new AlchemyTextDataProcessor(
				"b4ea1528e0292b7ba2077ae143c831ed1cf95580");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void extractKeywordsSimpleTest() throws Exception {

		List<String> keywords = dataProc
				.getKeywords("Page : Argo, About : Argo - Available February 19 , Desc : Based on real events, the dramatic "
						+ "thriller “Argo” chronicles the life-or-death covert operation to rescue six Americans, which unfolded behind "
						+ "the scenes of the Iran hostage crisis, focusing on the little-known role that the CIA and Hollywood played—information "
						+ "that was not declassified until many years after the event. Academy Award® winner Ben Affleck (“The Town,” “Good Will "
						+ "Hunting”) directs and stars in the film, which is produced by Oscar® nominee Grant Heslov (“Good Night, and Good Luck.”),"
						+ " Affleck, and Oscar® winner George Clooney (“Syriana”).On November 4, 1979, as the Iranian revolution reaches its boiling "
						+ "point, militants storm the U.S. Embassy in Tehran, taking 52 Americans hostage.  But, in the midst of the chaos, "
						+ "six Americans manage to slip away and find refuge in the home of Canadian Ambassador Ken Taylor.  "
						+ "Knowing it is only a matter of time before the six are found out and likely killed, the Canadian and American "
						+ "governments ask the CIA to intervene.  The CIA turns to their top “exfiltration” specialist, Tony Mendez, to come up "
						+ "with a plan to get the six Americans safely out of the country.  A plan so incredible, it could only happen in the movies."
						+ "“Argo” also stars Bryan Cranston (TV’s “Breaking Bad”), Oscar® winner Alan Arkin (“Little Miss Sunshine”), and John Goodman "
						+ "(“Trouble With the Curve”).  The main cast also includes Victor Garber, Tate Donovan, Clea DuVall, Scoot McNairy, Rory Cochrane, "
						+ "Christopher Denham, Kerry Bishé, Kyle Chandler and Chris Messina.");
		System.out.println(keywords);
		assertNotNull("Keywords is null, alchemyapi keyword extraction fails",
				keywords);
	}

	@Test
	public void extractKeywordsNullTest() throws Exception {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Enter some text to analyze");
		dataProc.getKeywords(null);

	}
}
