$(document).ready(function() {
	$("a.languages").click(function() {
		window.location.replace('?lang=' + $(this).attr('lang'));
	});
});