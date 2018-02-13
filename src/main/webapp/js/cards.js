$(function() {
	$("a.ajax").on("click", function() {
		$.ajax({
			url : $(this).attr("href"),
			method : 'GET'
		});
		return false;
	});
});