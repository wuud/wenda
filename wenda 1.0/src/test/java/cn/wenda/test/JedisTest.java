package cn.wenda.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

public class JedisTest {

	@Test
	public void test() {
//		Map<Integer,String> map=new HashMap<>();
//		map.put(1, "sss");
//		map.put(2, "sdf");
//		map.put(3, "HUi");
//		System.out.println(map);
		List<String> list = new ArrayList<>();
		list.add("1");
		list.add("2");
		list.add("3");
		for (String s : list) {
			System.out.println(s);
		}

	}
	@Transactional
	public void tx() {
	}

}
