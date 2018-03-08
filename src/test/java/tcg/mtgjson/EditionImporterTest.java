package tcg.mtgjson;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

public class EditionImporterTest {

	@Test
	public void testBuildAssistance() throws Exception {
		List<String> assistances = new EditionImporter().assistances("foô Bar'Foo qïx");
		assertThat(assistances).contains("foo", "bar", "qix").contains("foo barfoo qix")
				.containsOnlyOnce("foo");
	}


}
