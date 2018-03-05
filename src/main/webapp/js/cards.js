'use strict';

$(function() {
	$("div.priceloading").on("animationend", function(e) {
		if (e.target != this)
			return;
		$.ajax({
			url : $(this).attr("data-source"),
			method : 'GET',
			context : this,
			success : function(data) {
				$(this).closest("article").find("ul").replaceWith(data);
				$(".hide").removeClass("hide");
			}
		});
	});
	
	$(document).on("keydown", function(e) {
		if (e.key === '+' && e.ctrlKey) {
			if (e.shiftKey) {
				$(".portlet.prices a.add-side").doClick();
			} else {
				$(".portlet.prices a.add-main").doClick();
			}
			return false;
		}
	});

});
