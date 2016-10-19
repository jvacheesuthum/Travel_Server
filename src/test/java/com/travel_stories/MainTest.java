package com.travel_stories;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by jam414 on 19/10/16.
 */
public class MainTest {
    String test = "CrossLanguageK";
    Printer p = new Printer("CrossLanguageK");
    Jimmy jim = new Jimmy(p.toString());

    @Test
    public void testcrooslanguage(){
        assertEquals(test,jim.nameIs());
    }

}
