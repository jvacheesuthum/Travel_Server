package com.travel_stories;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by jam414 on 19/10/16.
 */
public class MainTest {
    String test = "CrossLanguageWorks";
    Printer p = new Printer("CrossLanguageWorks");
    Jimmy jim = new Jimmy(p.toString());

    @Test
    public void testcrooslanguage(){
        assertEquals(test,jim.nameIs());
    }

}
