'use strict';

$(function() {
	$.fn.extend({
		exists : function() {
			return this.length > 0;
		},
		doClick : function() {
			if (this.length === 1) this[0].click();
		}
	});

});


$(function() {
	$("form").on("keypress", function(e) {
		if (e.keyCode == 13) {
			return false;
		}
	});

	var _previous;
	$("span.autocomplete").on("keyup", "input", function(e) {
		if (e.keyCode == 38) {
			$(this).parent().find("li.select").prev().addClass("select").next().removeClass("select");
		} else if (e.keyCode == 40) {
			$(this).parent().find("li.select").next().addClass("select").prev().removeClass("select");
		} else if (e.keyCode == 13) {
			$(this).parent().find("li.select a").doClick();
		} else if ($(this).val().length >= 3) {
			var parent = $(this).parent(".autocomplete");
			var url = $(this).attr("data-href")
			if (_previous) _previous.abort();
			_previous = $.ajax({
				url : $(this).attr("data-source") + "?" + $(this).attr("name") + "=" + $(this).val(),
				method : 'GET',
				success : function(data) {
					parent.addClass("open");
					parent.find("ul").replaceWith(data);
				}
			});
		}
	});

	$("span.autocomplete").each(function() {
		$(this).append($("<ul>"));
	});

	$("body").on("click", function(e){
		if(!$(e.target).parents("span.autocomplete").exists()) {
			$("span.autocomplete.open").removeClass("open");
		}
	});

});

$(function() {
	$("header nav input").first().focus();
});