package org.springbootapp;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springbootapp.entity.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProjectCdwApplicationTests {

	@Autowired
	private RoleEntity r1;
	@Autowired
	private RoleEntity r2;

	@Test
	void test() {
//		r1 = new RoleEntity("AAA");
//		r2 = new RoleEntity("AAA");
//		if (r1.equals(r2)) {
//			System.out.println("OK");
//		} else {
//			System.out.println("ERR");
//		}

	}

}
