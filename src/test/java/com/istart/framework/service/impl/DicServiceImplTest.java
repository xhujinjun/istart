package com.istart.framework.service.impl;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.istart.framework.IstartApp;
import com.istart.framework.domain.Dic;
import com.istart.framework.domain.DicType;
import com.istart.framework.service.DicService;
import com.istart.framework.web.rest.DicResource;
import com.istart.framework.web.rest.search.SearchDic;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IstartApp.class)
@WebAppConfiguration
@IntegrationTest
@Transactional
public class DicServiceImplTest {
	private final Logger log = LoggerFactory.getLogger(DicServiceImplTest.class);

	@Inject DicService dicService;
	@Test
	public void testSave() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindOne() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearch() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchByDb() {
		SearchDic searchDic = new SearchDic();
		searchDic.setDicTypeCode("DIC.001.0001");
		searchDic.setDicCode("1");
		Dic dic = dicService.searchByDb(searchDic);
		log.info(dic.toString());
	}
	@Test
	public void testSearchByDicTypeCodeAndDicCode(){
		String dicTypeCode = "DIC.001.0001";
		String dicCode = "1";
		DicType dic = dicService.searchByDicTypeCodeAndDicCode(dicTypeCode, dicCode);
		log.info(dic.toString());
	}
}
