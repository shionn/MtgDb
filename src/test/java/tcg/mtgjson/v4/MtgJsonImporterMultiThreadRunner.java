package tcg.mtgjson.v4;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import tcg.card.formater.CardFormater;
import tcg.db.factory.SqlSessionFactory;
import tcg.mtgjson.v4.api.MtgJsonSet;

public class MtgJsonImporterMultiThreadRunner {

	public static void main(String[] args) throws InterruptedException {
		new MtgJsonImporterMultiThreadRunner().start();
	}

	private MtgJsonClient client = new MtgJsonClient();

	private void start() throws InterruptedException {
		MtgJsonImporter importer = new MtgJsonImporter();
		importer.setClient(client);
		importer.setFactory(new SqlSessionFactory());
		importer.setFormater(new CardFormater());

		List<MtgJsonSet> sets = Arrays.asList(client.setList());
		Collections.shuffle(sets);
		@SuppressWarnings("unchecked")
		List<Callable<String>> tasks = sets.stream()
				.map(MtgJsonSet::getCode).map(code -> new Callable<String>() {
					@Override
					public String call() {
						importer.importEdition(code);
						return code;
					}
				}).map(s -> (Callable<String>) s).collect(Collectors.toList());

		ExecutorService executor = Executors.newFixedThreadPool(10);
		for (Future<String> futur : executor.invokeAll(tasks)) {
			try {
				System.out.println("done : " + futur.get());
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		executor.shutdown();
	}
}
