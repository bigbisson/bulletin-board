package com.indran.bulletin_board;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class BulletinBoardApplicationTests {

	@Test
	void mainMethodRuns() {
		assertDoesNotThrow(() -> new BulletinBoardApplication());
	}

}
