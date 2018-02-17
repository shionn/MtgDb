'use strict';

$(function() {
	$("body").on("click", "a.ajax.add-one, a.ajax.rm-one, a.ajax.rm-all", function(e) {
		e.preventDefault();
		$.ajax({
			url : $(this).attr("href"),
			method : 'GET',
			context : this,
			success : function(r) {
				if (r.qty>0) {
					$(this).closest("tr").find("td").first().text(r.qty);
				} else {
					$(this).closest("tr").remove();
				}
			}
		});
	});

	$("article.deck-table").on("click", "a.ajax.mv", function(e) {
		e.preventDefault();
		$.ajax({
			url : $(this).attr("href"),
			method : 'GET',
			context : this,
			success : function(r) {
				$.each($(this).attr("data-update").split(','), function(){
					$(this).replaceWith($(r).find(this));
				});
//				$("table").replaceWith($(r).find("table"));
//				$("section.deck-title").replaceWith($(r).find("section.deck-title"))
			}
		});
	});

});