function transformTableToDataTable(id) {
	// Transform template in DataTable
	var table= $('#' + id).DataTable({
		dom: 'B<"toolbar">lfrtip',
		fixedHeader: true,
		buttons: [
            'copy',
            'excel',
            'csv',
            'print'
	    ]
	});
	$('#' + id + '_wrapper .buttons-html5').removeClass('dt-button');
	$('#' + id + '_wrapper .buttons-print').removeClass('dt-button');
	$('#' + id + '_wrapper .buttons-html5').addClass('btn btn-default btn-xs');
	$('#' + id + '_wrapper .buttons-print').addClass('btn btn-default btn-xs');
	$("div.toolbar").html('<br><br>');
}