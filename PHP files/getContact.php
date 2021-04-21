<?php
include "conn.php";
$query="SELECT * FROM `contacts` ORDER BY `contacts`.`firstName` ASC";
$res=mysqli_query($con,$query);
$responce=array();
if($res)
{
	$responce['contacts']=array();
	while ($row = mysqli_fetch_array($res))
	{
		$user=array();
		$user['contactID']=$row['contactID'];
		$user['firstName']=$row['firstName'];
		$user['lastName']=$row['lastName'];
		$user['date']=$row['date'];
		$user['gender']=$row['gender'];
		$user['bio']=$row['bio'];
		array_push($responce["contacts"],$user);
	}
	$responce['success']=1;
	
}
else
{
	$responce['success']=0;
}

$x=json_encode($responce);
echo $x;


?>