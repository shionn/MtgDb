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

$(function(){
	$("header nav.menu ul a").first().on("click", function() {
		$(this).closest("ul").toggleClass("open");
		return false;
	});
	$("body").on("click",function() {
		$("nav.menu ul.open").removeClass("menu");
	});
});

$(function() {
	$("body").on("click", "div.btn-select button", function(){
		if (!$(this).closest("div.btn-select.open").exists()) {
			$("div.btn-select.open").removeClass("open");
		}
		$(this).closest("div.btn-select").toggleClass("open");
	});
	$("body").on("click", "div.btn-select a", function() {
		var root = $(this).closest("div.btn-select");
		if (!$(this).hasClass("ajax") && !$(this).hasClass("modal")) {
			root.find("button").text($(this).text());
		}
		root.removeClass("open");
		root.find("input[type=hidden]").val($(this).attr("href").substring(1));
		return $(this).attr("href").substring(0,1) !== "#"
	});
	$("body").on("click", function(e) {
		if (!$(e.target).closest("div.btn-select.open").exists()) {
			$("div.btn-select.open").removeClass("open");
		}
	});
});

$(function() {
	$("body").on("change", "input[type=file]", function(e) {
		var filename = $(this).val();
		filename = filename.substring(filename.lastIndexOf("\\")+1);
		$(this).closest("div.btn").find("span").text(filename);
	});
});

$(function() {
	$("header nav input").first().focus();
	$("body .advanced-search input").first().focus();
});

$(function() {
	$(document).on("keydown", function(e) {
		if (e.ctrlKey) {
			if (e.key === 's') {
				$("header nav input").first().focus();
				return false;
			}
		}
	});
});

$(function() {
	$("body").on("click", "a.ajax", function(e) {
		e.preventDefault();
		$.ajax({
			url : $(this).attr("href"),
			method : 'GET',
			context : this,
			success : function(r) {
				$.each($(this).attr("data-update").split(','), function(){
					$(this).replaceWith($(r).find(this));
				});
			}
		});
		return false;
	});

	$("body").on("submit", "form.ajax", function(e) {
		e.preventDefault();
		$.ajax({
			url : $(this).attr("action")+"?"+$(this).serialize(),
			method : 'GET',
			context : this,
			success : function(r) {
				$.each($(this).attr("data-update").split(','), function(){
					$(this).replaceWith($(r).find(this));
				});
			}
		});
		return false;
	});
});

$(function() {
	$("body").on("click", "a.modal", function(){
		$.ajax({
			url : $(this).attr("href"),
			method : 'GET',
			success : function(r) {
				$("div.modal").html(r);
				$("div.modal").addClass("open");
			}
		});
		return false;
	});
	$("div.modal").on("click", function(e) {
		if($(e.target).hasClass("modal") && $(e.target).hasClass("open")) {
			$(this).removeClass("open");
		}
	});
	$("body").on("click", ".closeModal", function(e) {
		$("div.modal.open").removeClass("open");
	});
});

$(function(){
	$("section.cube").on("mouseenter", "ul li a, ul li a i", function() {
		$(this).closest("li").addClass("over");
	});
	$("section.cube").on("mouseout", "ul li a, ul li a i", function() {
		$(this).closest("li").removeClass("over");
	});
});

$(function() {
	$("body").on("click", "a.hide-on-click", function() {
		$(this).hide();
	});

})
