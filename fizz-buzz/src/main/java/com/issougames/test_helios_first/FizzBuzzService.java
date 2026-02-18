package com.issougames.test_helios_first;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FizzBuzzService {

	public List<String> generate(FizzBuzzRequest request) {
		List<String> values = new ArrayList<>(request.limit());

		for (int i = 1; i <= request.limit(); i++) {
			boolean multipleOfInt1 = i % request.int1() == 0;
			boolean multipleOfInt2 = i % request.int2() == 0;

			if (multipleOfInt1 && multipleOfInt2) {
				values.add(request.str1() + request.str2());
			} else if (multipleOfInt1) {
				values.add(request.str1());
			} else if (multipleOfInt2) {
				values.add(request.str2());
			} else {
				values.add(Integer.toString(i));
			}
		}

		return values;
	}
}
