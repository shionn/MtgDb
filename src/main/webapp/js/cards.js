'use strict';

$(function() {
	var error = 0;
	$("div.priceloading").on("animationiteration", function(e) {
		$.ajax({
			url : $(this).attr("data-source"),
			method : 'GET',
			context : this,
			success : function(data) {
				$(this).closest("article").find("ul").replaceWith(data);
				$(".hide").removeClass("hide");
				$(this).remove();
			},
			error : function(e) {
				if (error++>12) {
					$(this).remove();
				}
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
