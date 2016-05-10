/**
 * 
 */
package com.istart.framework.repository;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.istart.framework.IstartApp;

/**
 * @author Administrator
 * @date 2016年4月19日
 * @company 成都市映潮科技有限公司
 * @version 0.1.0
 * @since 0.1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IstartApp.class)
@WebAppConfiguration
@IntegrationTest
public class UserRepositoryTest {
	 @Inject
	    private UserRepository userRepository;
	/**
	 * Test method for {@link com.istart.framework.repository.UserRepository#findOneByActivationKey(java.lang.String)}.
	 */
	@Test
	public void testFindOneByActivationKey() {
		userRepository.findOneByActivationKey("1");
	}

	/**
	 * Test method for {@link com.istart.framework.repository.UserRepository#findAllByActivatedIsFalseAndCreatedDateBefore(java.time.ZonedDateTime)}.
	 */
	@Test
	public void testFindAllByActivatedIsFalseAndCreatedDateBefore() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.istart.framework.repository.UserRepository#findOneByResetKey(java.lang.String)}.
	 */
	@Test
	public void testFindOneByResetKey() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.istart.framework.repository.UserRepository#findOneByEmail(java.lang.String)}.
	 */
	@Test
	public void testFindOneByEmail() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.istart.framework.repository.UserRepository#findOneByLogin(java.lang.String)}.
	 */
	@Test
	public void testFindOneByLogin() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.istart.framework.repository.UserRepository#findOneById(java.lang.Long)}.
	 */
	@Test
	public void testFindOneById() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.istart.framework.repository.UserRepository#delete(com.istart.framework.domain.User)}.
	 */
	@Test
	public void testDeleteUser() {
		fail("Not yet implemented");
	}

}
