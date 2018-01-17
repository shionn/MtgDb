$(function() {
	var _previous;
	$("span.autocomplete").on("keyup", "input", function(e) {
		if ($(this).val().length >= 3) {
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
//					if (data.length == 1) {
//						$(parent.find("input[type=text]").attr("data-target")).val(data[0].id);
//						if (e.keyCode == 13) {
//							parent.parents("form").trigger('submit');
//						}
//					}

					var ul = $("<ul>");
					$.each(data, function() {
						ul.append($("<li>").append($("<a>").attr("href", url+this.id).text(this.name)));
					});
					parent.find("ul").replaceWith(ul);
				}
			});
		}
	});

	$("div.autocomplete").on("click","a",function(e){
		e.preventDefault();
		var root = $(this).parents("div.autocomplete");
		root.removeClass("open");
		$(root.find("input[type=text]").attr("data-target")).val($(e.target).attr("href").substring(1));
		root.find("input[type=text]").val($(e.target).text());
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