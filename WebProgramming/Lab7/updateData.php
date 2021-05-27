<?php
	$con = mysqli_connect("localhost","root","","laboratoryweek9");
	if (!$con) {
		die('Could not connect: ' . mysqli_error());
	}
	
	$name = $_GET["name"];
	$username = $_GET["username"];
	$password = $_GET["password"];
	$age = $_GET["age"];
	$role = $_GET["role"];
	$profile = $_GET["profile"];
	$email = $_GET["email"];
	$webpage = $_GET["webpage"];
	$id = $_GET["id"];
	
	$statement = mysqli_prepare($con, "update Users SET 
	name=?,
	username=?,
	password=?,
	age=?,
	role=?,
	profile=?,
	email=?,
	webpage=? WHERE id=?") or die(mysqli_error($con));
	mysqli_stmt_bind_param($statement,'sssissssi',
	$name,
	$username,
	$password,
	$age,
	$role,
	$profile,
	$email,
	$webpage,
	$id) or die(mysqli_error($con));
	
	mysqli_stmt_execute($statement) or die (mysqli_error($con));
	
	echo "<h1>Operation Finished</h1>";
	
	mysqli_close($con);
?>