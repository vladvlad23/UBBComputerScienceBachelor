<?php
	$con = mysqli_connect("localhost","root","","laboratoryweek9");
	if (!$con) {
		die('Could not connect: ' . mysqli_error());
	}

	$id = $_GET["name"];

	$result = mysqli_query($con, "SELECT * FROM User");

	$jsonData = array();
	while ($row = mysqli_fetch_array($result)) {
	    $jsonData['name'] = $row['name'];
		$jsonData['username'] = $row['username'];
		$jsonData['age'] = $row['age'];
		$jsonData['role'] = $row['role'];
		$jsonData['profile'] = $row['profile'];
	    $jsonData['password'] = $row['password'];
	    $jsonData['email'] = $row['email'];
		$jsonData['webpage'] = $row['webpage'];
	}
	echo json_encode($jsonData);

	mysqli_close($con);
?> 

