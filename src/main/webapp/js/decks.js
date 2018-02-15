'use strict';

$(function() {
	$("a.ajax.add-one, a.ajax.rm-one, a.ajax.rm-all").on("click", function(e) {
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

	$("table").on("click", "a.ajax.mv", function(e) {
		e.preventDefault();
		$.ajax({
			url : $(this).attr("href"),
			method : 'GET',
			context : this,
			success : function(r) {
				$.each($(r).find("tr"), function() {
					var source = $("tr[data-card="+$(this).attr("data-card")+"][data-category=" + $(this).attr("data-category") + "]");
					if(source.exists()) {
						source.replaceWith($(this));
					} else {
						alert("todo");
					}
				});
			}
		});
	});

});