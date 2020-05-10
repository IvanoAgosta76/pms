function showParetoChart(points, yPlanned) {
	var chart = new CanvasJS.Chart("chartContainer", {
		title: {
			text: "Activity Progress"
		},
		axisY: {
			title: "Hours",
			lineColor: "#4F81BC",
			tickColor: "#4F81BC",
			labelFontColor: "#4F81BC"
		},
		axisY2: {
			title: "",
			suffix: "%",
			lineColor: "#C0504E",
			tickColor: "#C0504E",
			labelFontColor: "#C0504E"
		},
		data: [{
			type: "column",
			dataPoints: points
		}]
	});
	chart.render();
	createPareto(chart, yPlanned);
}


function createPareto(chart, yPlanned) {
	var dps = [];
	var yValue, yTotal = 0, yPercent = 0;

	for (var i = 0; i < chart.data[0].dataPoints.length; i++)
		if(yTotal < chart.data[0].dataPoints[i].y)
			yTotal = chart.data[0].dataPoints[i].y;

	for (var i = 0; i < chart.data[0].dataPoints.length; i++) {
		yValue = chart.data[0].dataPoints[i].y;
		yPercent += ( (yValue * 100) / yPlanned );
		dps.push({ label: chart.data[0].dataPoints[i].label, y: yPercent });
	}

	chart.addTo("data", { type: "line", yValueFormatString: "0.##\"%\"", dataPoints: dps });
	chart.data[1].set("axisYType", "secondary", false);
	chart.axisY[0].set("maximum", yTotal);
	chart.axisY2[0].set("maximum", yPercent);
}