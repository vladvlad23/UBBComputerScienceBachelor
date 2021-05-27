<?php
	$name = $_POST["name"];
	$username = $_POST["username"];
	$password = $_POST["password"];
	$age = $_POST["age"];
	$role = $_POST["role"];
	$profile = $_POST["profile"];
	$email = $_POST["email"];
	$webpage = $_POST["webpage"];

	$con = mysqli_connect("localhost","root","","laboratoryweek9");
	if (!$con) {
		die('Could not connect: ' . mysqli_error());
	}


	$statement = mysqli_prepare($con, "insert into Users(name,username,password,age,role,profile,email,webpage) values(?,?,?,?,?,?,?,?)") or die(mysqli_error($con));
	mysqli_stmt_bind_param($statement,'sssissss',$name,$username,$password,$age,$role,$profile,$email,$webpage) or die(mysqli_error($con));
	mysqli_stmt_execute($statement) or die (mysqli_error($con));
	
	echo "<h1>Operation Finished</h1>";
	
	mysqli_close($con);
?> 