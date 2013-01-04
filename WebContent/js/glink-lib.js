/************************************************
 *   Name:  glink-lib.js
 *   Author:  Daniel Strassler
 *   Purpose:  A central location to store all
 *   Javascript functions created specifically
 *   for G-Link.
 ***********************************************/

/************************************************
 *   Name:  getPage
 *   Purpose:  Find the current page and set the
 *   css class to active.
 ***********************************************/

function getPage() {
	var sPath = window.location.pathname;
	var sPage = sPath.substring(sPath.lastIndexOf('/') + 1);
	if (sPage == "scheduled.jsf") {
		document.getElementById("scheduledLink").setAttribute("class","active");
	} else if (sPage == "reports.jsf") {
		document.getElementById("reportsLink").setAttribute("class","active");
	} else {
		document.getElementById("homeLink").setAttribute("class","active");
	}
 }

function getStatus(event) {
    var status = event.status; // Can be "begin", "complete" or "success".
    switch (status) {
        case "begin": // Before the ajax request is sent.
        	document.getElementById("loader").style.display = "block";
        	document.getElementById("reportsForm:go").disabled = "true";
            break;

        case "complete": // After the ajax response is arrived.
            document.getElementById("loader").style.display = "none"; 
        	document.getElementById("reportsForm:go").removeAttribute("disabled");
            break;

        case "success": // After update of HTML DOM based on ajax response..
        	document.getElementById("downloadCSV").style.display="block";
        	if (document.getElementById("reportsForm:reportType").value != "Project With Account Detail*" && document.getElementById("reportsForm:reportType").value != "Queues With Account Detail*" ) {
        		pieChart();
        	} else {
        		hideChart();
        	}
            break;
    }
}

function pieChart(){
	var reportData = document.getElementById("reportsForm:reportData").value;
	if (reportData != null && reportData.length > 3) {
	var data = eval ( reportData );
	//  Clear div prechart draw.
	document.getElementById("piechartdiv").style.display = "block";
	var nodeList = $('piechartdiv').getElementsByClassName( 'jqplot-highlighter-tooltip' );
	for (var i = 0; i < nodeList.length; i++) {
		nodeList[i].empty();
	}
	$('piechartdiv').empty(); 
	var plot1 = jQuery.jqplot ('piechartdiv', [data],
			
			{
	//	seriesColors: ["#FF0000","#00FF00","#0000FF","#FF00FF","#00FFFF","#FFFF00","#FF7F00","","#CC0099","#FF66CC","#FFFF99","FFFF#FF" ],
		seriesDefaults: {
			// Make this a pie chart.
			renderer: jQuery.jqplot.PieRenderer, 
			rendererOptions: {
				// Put data labels on the pie slices.
				// By default, labels show the percentage of the slice.
				sliceMargin: 2,
				diameter: 255,
				dataLabelNudge: 20 ,
		        dataLabels: 'label',
		        showTooltip: true,
				}
		}, 
		legend: { show: false
			},
	highlighter: {
		show: true,
		showTooltip: true,
		  formatString:'%s', 
		  tooltipLocation:'n', 
		  useAxesFormatters:false
		}
			}
	); 
	showChart();
	}
}

function showChart() {
	if(document.getElementById("piechartdiv").style.display == "block") {
		document.getElementById("piechartdiv").style.display = "none";
		document.getElementById("hideChart").style.display = "none";
		document.getElementById("pieProject").style.display = "none";
		document.getElementById("showChart").style.display = "block";
	} else {
		document.getElementById("piechartdiv").style.display = "block";		
		document.getElementById("hideChart").style.display = "block";
		document.getElementById("pieProject").style.display = "block";
		document.getElementById("showChart").style.display = "none";
	}

}

function hideChart() {
	document.getElementById("piechartdiv").style.display = "none";
	document.getElementById("showChart").style.display = "none";
	document.getElementById("hideChart").style.display = "none";
	document.getElementById("pieProject").style.display = "none";
}

