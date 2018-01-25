'use strict';

$(function() {
	$.fn.extend({
		exists : function() {
			return this.length > 0;
		},
		doClick : function() {
			if (this.length === 1)
				this[0].click();
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
		} else if (e.keyCode == 27) {
			$(this).parent().removeClass("open")
		} else if ($(this).val().length >= $(this).attr("data-length")) {
			var parent = $(this).parent(".autocomplete");
			if (_previous)
				_previous.abort();
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

	$("body").on("click", function(e) {
		if (!$(e.target).parents("span.autocomplete").exists()) {
			$("span.autocomplete.open").removeClass("open");
		}
	});

});

$(function() {
	$("header nav input").first().focus();
	$("body .advanced-search input").first().focus();
});

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

$(function() {
	$(document).on("keydown", function(e){
		if (e.keyCode == 112) {
			$("header nav input").first().focus();
			return false;
		}
		if (e.keyCode == 113) {
			$("header nav a.as").doClick();
			return false;
		}
		if (e.keyCode == 114) {
			$("header nav a.t").doClick();
			return false;
		}
		if (e.keyCode == 115) {
			$("header nav a.d").doClick();
			return false;
		}
	});
});