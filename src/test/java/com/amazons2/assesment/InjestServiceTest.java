package com.amazons2.assesment;

import com.amazons3.assesment.services.impl.IngestService;

import junit.framework.TestCase;

public class InjestServiceTest extends TestCase{
	
	IngestService ingestService = new IngestService();
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public InjestServiceTest( String testName )
    {
        super( testName );
    }
    
    public void testCreateBucket() throws Exception {
    	//ingestService.createBucket("amazontestbucket2");
    }
    
    
}
