<?php

include "conn.php";

$responce=array();
if(isset($_POST["email"],$_POST["password"]))
{
	$email=$_POST["email"];
	$password=$_POST["password"];
	$query="INSERT INTO users(email, password) VALUES ( '".$email."', '".$password."')";
	$res=mysqli_query($con,$query);
	if($res)
	{
		$responce["id"]=mysqli_insert_id($con);
		$responce["reqmsg"]="User Inserted!";
		$responce["reqcode"]="1";
	}
	else
	{
		$responce["id"]="NA";
		$responce["reqmsg"]="Error Inserting User!";
		$responce["reqcode"]="0";
	}
}
else
{
	$responce["id"]="NA";
	$responce["reqmsg"]="Incomplete Request, Error Inserting!";
	$responce["reqcode"]="0";
}

$x=json_encode($responce);
echo $x;


?>