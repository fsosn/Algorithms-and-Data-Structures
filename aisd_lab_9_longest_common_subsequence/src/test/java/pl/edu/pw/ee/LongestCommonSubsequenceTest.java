package pl.edu.pw.ee;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LongestCommonSubsequenceTest 
{
    @Test
    public void testDisplay(){
        LongestCommonSubsequence lcs;
        lcs = new LongestCommonSubsequence("FRANCISZKAŃSKA", "WATYKAŃSKI");
        
        lcs.display();
    }
    @Test
    public void testLcs(){
        LongestCommonSubsequence lcs;
        lcs = new LongestCommonSubsequence("FRANCISZKAŃSKA", "WATYKAŃSKI");
        
        String result = lcs.findLCS();
        System.out.println(result);
        
        assertEquals("AKAŃSK", result);
    }
}
