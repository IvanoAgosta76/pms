function createDatatable(id) {
	// Transform template in DataTable
	var table = $('#' + id).DataTable({
		dom : 'B<"toolbar">lfrtip',
		fixedHeader : true,
		buttons : [ 'copy', 'excel', 'csv', {
			extend : 'print',
			footer : true,
			exportOptions : {
				columns: [0,1,2]
			}
		} ]
	});
	$('#' + id + '_wrapper .buttons-html5').removeClass('dt-button');
	$('#' + id + '_wrapper .buttons-print').removeClass('dt-button');
	$('#' + id + '_wrapper .buttons-html5').addClass('btn btn-default btn-xs');
	$('#' + id + '_wrapper .buttons-print').addClass('btn btn-default btn-xs');
	$("div.toolbar").html('<br><br>');
}