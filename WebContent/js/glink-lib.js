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