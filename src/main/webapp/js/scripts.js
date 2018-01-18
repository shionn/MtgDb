'use strict';

$(function() {
	$.fn.extend({
		/**
		 * test si l'element existe
		 */
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
				contentType : 'application/json',
				success : function(data) {
					if (data.length) {
						parent.addClass("open");
					} else {
						parent.addClass("close");
					}
					var ul = $("<ul>");
					$.each(data, function(i,e) {
						ul.append($("<li>").addClass(i===0?"select":"").append($("<a>").attr("href", url+this.id).text(this.name+(this.langs.length>0?'-'+this.langs[0].name:''))));
					});
					parent.find("ul").replaceWith(ul);
				}
			});
		}
	});

//	$("span.autocomplete").on("click","a",function(e){
//		e.preventDefault();
//		var root = $(this).parents("span.autocomplete");
//		root.removeClass("open");
//		$(root.find("input[type=text]").attr("data-target")).val($(e.target).attr("href").substring(1));
//		root.find("input[type=text]").val($(e.target).text());
//	});

	$("span.autocomplete").each(function() {
		$(this).append($("<ul>"));
	});

	$("body").on("click", function(e){
		if(!$(e.target).parents("span.autocomplete").exists()) {
			$("span.autocomplete.open").removeClass("open");
		}
	});

});