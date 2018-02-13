'use strict';

$(function() {
	$("a.ajax.add-one").on("click", function(e) {
		e.preventDefault();
		$.ajax({
			url : $(this).attr("href"),
			method : 'GET',
			context : this,
			success : function(r) {
				$(this).closest("tr").find("td").first().text(r.qty);
			}
		});
	});
	$("a.ajax.rm-one").on("click", function(e) {
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


});