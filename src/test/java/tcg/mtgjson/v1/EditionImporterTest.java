package tcg.mtgjson.v1;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import tcg.card.formater.CardFormater;

@RunWith(MockitoJUnitRunner.class)
public class EditionImporterTest {

	@Spy
	private CardFormater formater;

	@InjectMocks
	private EditionImporter importer = new EditionImporter();
	@Test
	public void testBuildAssistance() throws Exception {
		List<String> assistances = importer.assistances("foô Bar'Foo qïx");
		assertThat(assistances).contains("foo", "bar", "qix").contains("foo barfoo qix")
				.containsOnlyOnce("foo");
	}


}
