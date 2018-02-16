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

	$("body").on("click", "table a.ajax.mv", function(e) {
		e.preventDefault();
		$.ajax({
			url : $(this).attr("href"),
			method : 'GET',
			context : this,
			success : function(r) {
				$("table").replaceWith($(r).find("table"));
			}
		});
	});

});