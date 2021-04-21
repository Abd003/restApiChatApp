<?php
 //$con=mysqli_connect("localhost","root","" ); 
 //$con=mysqli_connect("localhost", "root", "", "chatapp");
 //or die("i cannot connect to the database".mysql_errno());
 //mysqli_select_db($con,"chatapp");
 //mysqli_query($con,"SET NAMES 'utf8'");

 $con = new mysqli("localhost", "root", "", "chatapp");


// Check connection
if ($con -> connect_errno) {
  echo "Failed to connect to MySQL: " . $con -> connect_error;
  exit();
}
else
{
	//echo "Connected to MySQL!!!";
}

?>