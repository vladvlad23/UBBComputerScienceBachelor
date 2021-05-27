<?php
	$con = mysqli_connect("localhost","root","","laboratoryweek9");
	if (!$con) {
		die('Could not connect: ' . mysqli_error());
	}
	
	$result = mysqli_query($con, "SELECT * FROM User");

	echo "<table border='1'>
	<tr>
		<th>Name</th>
		<th>Username</th>
		<th>Password</th>
		<th>Age</th>
		<th>Role</th>
		<th>Profile</th>
		<th>Email</th>
		<th>Webpage</th>
	</tr>";

	$jsonData = array();
	while ($row = mysqli_fetch_array($result)) {
	    echo "<tr>";
		echo "<td>". $row['name'] . "</td>";
		echo "<td>". $row['username'] . "</td>";
	    echo "<td>". $row['password']  . "</td>";
		echo "<td>". $row['age'] . "</td>";
		echo "<td>". $row['role'] . "</td>";
		echo "<td>". $row['profile'] . "</td>";
	    echo "<td>". $row['email']  . "</td>";
		echo "<td>". $row['webpage']  . "</td>";
	}

	mysqli_close($con);

?>