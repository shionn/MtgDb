package tcg.edition;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import tcg.db.dao.EditionListDao;
import tcg.db.dbo.Edition;
import tcg.db.dbo.EditionType;

@Controller
public class EditionController implements Comparator<Edition> {
	@Autowired
	private SqlSession session;

	@RequestMapping("/e")
	public ModelAndView editions() {
		EditionListDao dao = session.getMapper(EditionListDao.class);
		List<Edition> editions = dao.listEditions();
		List<List<EditionGroup>> groups = new ArrayList<>();
		groups.add(Arrays.asList(
				typeAndChild(editions, EditionType.core,
						Arrays.asList(EditionType.promo, EditionType.memorabilia)),
				typeAndChild(editions, EditionType.starter, Arrays.asList(EditionType.promo))));
		groups.add(buildExpantion(editions));
		groups.add(Arrays.asList(
				typeAndChild(editions, EditionType.archenemy, Arrays.asList(EditionType.promo)),
				typeAndChild(editions, EditionType.commander,
						Arrays.asList(EditionType.memorabilia)),
				typeAndChild(editions, EditionType.funny, Arrays.asList(EditionType.promo)),
				typeAndChild(editions, EditionType.planechase, Arrays.asList(EditionType.promo)),
				typeAndChild(editions, EditionType.vanguard, Arrays.asList(EditionType.promo))));
		groups.add(Arrays.asList(
				typeAndChild(editions, EditionType.fromthevault, Arrays.asList(EditionType.promo)), //
				typeAndChild(editions, EditionType.spellbook, Arrays.asList(EditionType.promo)), //
				typeAndChild(editions, EditionType.dueldeck, Arrays.asList()), //
				typeAndChild(editions, EditionType.premiumdeck, Arrays.asList()), //
				nameds(editions, "World Championship Decks", "World Championship Decks.*"),
				typeAndChild(editions, EditionType.box, Arrays.asList(EditionType.promo)), //
				typeAndChild(editions, EditionType.draftinnovation,
						Arrays.asList(EditionType.promo)), //
				typeAndChild(editions, EditionType.masters,
						Arrays.asList(EditionType.masterpiece)),
				block(editions, "Judge Gift Cards"), block(editions, "Friday Night Magic"),
				block(editions, "Magic Player Rewards"), block(editions, "Arena League")));
		groups.add(Arrays.asList(typeAndChild(editions, EditionType.token, Arrays.asList())));
		return new ModelAndView("editions") //
				.addObject("groups", groups) //
				.addObject("editions", editions);
	}



	private EditionGroup typeAndChild(List<Edition> editions, EditionType type,
			List<EditionType> subTypes) {
		EditionGroup group = new EditionGroup();
		group.setName(type.getDisplayName());
		group.setEditions(editions.stream().filter(e -> e.getType() == type).collect(toList()));
		editions.removeAll(group.getEditions());

		addChild(editions, subTypes, group);

		Collections.sort(group.getEditions(), this);
		return group;
	}

	private void addChild(List<Edition> editions, List<EditionType> subTypes, EditionGroup group) {
		List<String> codes = group.getEditions().stream().map(e -> e.getCode())
				.filter(Objects::nonNull).collect(toList());
		group.getEditions().addAll(editions.stream().filter(e -> codes.contains(e.getParentCode()))
				.filter(e -> subTypes.contains(e.getType())).collect(toList()));
		editions.removeAll(group.getEditions());
	}

	private List<EditionGroup> buildExpantion(List<Edition> editions) {
		List<String> blockCodes = editions.stream()
				.filter(e -> e.getType() == EditionType.expansion).sorted(this)
				.map(e -> e.getBlock()).distinct().collect(toList());
		return blockCodes.stream().map(name -> {
			EditionGroup group = new EditionGroup();
			group.setName(name == null ? "Out block" : name);
			group.setEditions(editions.stream()
					.filter(e -> name == null && e.getBlock() == null
							&& e.getType() == EditionType.expansion
							|| name != null && name.equals(e.getBlock()))
					.sorted(this).collect(toList()));
			editions.removeAll(group.getEditions());
			addChild(editions, Arrays.asList(EditionType.promo, EditionType.memorabilia), group);
			Collections.sort(group.getEditions(), this);
			return group;
		}).collect(toList());
	}

	private EditionGroup block(List<Edition> editions, String name) {
		EditionGroup group = new EditionGroup();
		group.setName(name);
		group.setEditions(
				editions.stream().filter(e -> name.equals(e.getBlock())).collect(toList()));
		editions.removeAll(group.getEditions());
		Collections.sort(group.getEditions(), this);
		return group;
	}

	private EditionGroup nameds(List<Edition> editions, String name, String regex) {
		EditionGroup group = new EditionGroup();
		group.setName(name);
		group.setEditions(
				editions.stream().filter(e -> e.getName().matches(regex)).collect(toList()));
		editions.removeAll(group.getEditions());
		Collections.sort(group.getEditions(), this);
		return group;
	}

	@Override
	public int compare(Edition o1, Edition o2) {
		int compare = -o1.getReleaseDate().compareTo(o2.getReleaseDate());
		if (compare == 0) {
			compare = Integer.compare(o1.getType().ordinal(), o2.getType().ordinal());
		}
		return compare;
	}

}
