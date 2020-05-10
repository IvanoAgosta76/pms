function checkDate() {
	
	const startDate = moment($("#startDate").val(), "YYYY-MM-DD");
	const endDate = moment($("#endDate").val(), "YYYY-MM-DD");
	const days = endDate.diff(startDate, 'days');

	if (days < 0) {
		alert("Start date must be equal or greater than end date.");
		$("#endtDate").val($("#startDate").val());
		return;
	}
	fillPlannedHours(days);

}

function checkHours() {
	
	const startDate = moment($("#startDate").val(), "YYYY-MM-DD");
	const endDate = moment($("#endDate").val(), "YYYY-MM-DD");
	const hours = endDate.diff(startDate, 'hours') + 24;
	const plannedHours =  $("#plannedHours").val();
	
	if(plannedHours>hours) {
		$("#plannedHours").val(hours);
		alert("Planned hours cannot be exceeded the hours of the selected days...")
	}
	
}

function fillPlannedHours(days) {
	$("#plannedHours").val($("#workHoursForDay").val() * (days));
}