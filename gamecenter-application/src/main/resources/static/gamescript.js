function getUsername() {
	var username = getCookie("username");
	if (username != null) {
		alert("Logged in.")
		document.getElementById("usernameVal").style.visibility = "visible";
	} else {
		document.getElementById("usernameVal").style.visibility = "hidden";
		alert("wat")
	}
}