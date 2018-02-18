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
});
