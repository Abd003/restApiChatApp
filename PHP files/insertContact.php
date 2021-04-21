<?php

include "conn.php";

$responce=array();
if(isset($_POST["firstName"],$_POST["lastName"],$_POST["date"],$_POST["gender"],$_POST["bio"]))
{
	$firstName=$_POST["firstName"];
	$lastName=$_POST["lastName"];
	$date=$_POST["date"];
	$gender=$_POST["gender"];
	$bio=$_POST["bio"];
	$query="INSERT INTO contacts(firstName, lastName,date,gender,bio) VALUES ( '".$firstName."', '".$lastName."', '".$date."', '".$gender."', '".$bio."')";
	$res=mysqli_query($con,$query);
	if($res)
	{
		$responce["id"]=mysqli_insert_id($con);
		$responce["reqmsg"]="Contact Inserted!";
		$responce["reqcode"]="1";
	}
	else
	{
		$responce["id"]="NA";
		$responce["reqmsg"]="Error Inserting Contact!";
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