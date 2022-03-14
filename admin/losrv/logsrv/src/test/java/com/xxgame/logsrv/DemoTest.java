package com.xxgame.logsrv;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
	
    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
//    @Test
    public void add() throws Exception {
    	String uri = "/operator/add";
    	
    	MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
    	params.add("id", "4");
    	params.add("defaultKey", "112");
    	params.add("chargeKey", "222");
    	
    	MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri).params(params).accept(MediaType.APPLICATION_JSON)).andReturn();
    	
        int status = mvcResult.getResponse().getStatus();  
        String content = mvcResult.getResponse().getContentAsString();
        
        Assert.assertTrue("错误，正确的返回值为200", status == 200); 
        Assert.assertFalse("错误，正确的返回值为200", status != 200);
        Assert.assertFalse("错误，反回数据为空", content == null);
    }
    
//    @Test
    public void update() throws Exception {
    	String uri = "/operator/update";
    	
    	MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
    	params.add("id", "4");
    	params.add("defaultKey", "111");
    	params.add("chargeKey", "222");
    	
    	MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri).params(params).accept(MediaType.APPLICATION_JSON)).andReturn();
    	
        int status = mvcResult.getResponse().getStatus();  
        String content = mvcResult.getResponse().getContentAsString();
        
        Assert.assertTrue("错误，正确的返回值为200", status == 200); 
        Assert.assertFalse("错误，正确的返回值为200", status != 200);
        Assert.assertFalse("错误，反回数据为空", content == null);
    }
    
    @Test
    public void getOperators() throws Exception {
    	String uri = "/operator/getoperators";
    	
    	MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
    	params.add("lastId", "3");
    	params.add("size", "2");
    	
    	MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri).params(params).accept(MediaType.APPLICATION_JSON)).andReturn();
    	
        int status = mvcResult.getResponse().getStatus();  
        String content = mvcResult.getResponse().getContentAsString();
        
        Assert.assertTrue("错误，正确的返回值为200", status == 200); 
        Assert.assertFalse("错误，正确的返回值为200", status != 200);
        Assert.assertFalse("错误，反回数据为空", content == null);
        
        System.out.println(content);
    }

}
