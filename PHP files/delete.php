<?php

include "conn.php";
$responce=array();
if(isset($_POST["id"]))
{
	$id=$_POST["id"];
	$query="DELETE FROM `contacts` WHERE `id`=".$id;
	$res=mysqli_query($con,$query);
	if($res)
	{
		$responce["id"]=$id;
		$responce["reqmsg"]="Contact Deleted!";
		$responce["reqcode"]="1";
	}
	else
	{
		$responce["id"]=$id;
		$responce["reqmsg"]="Error Deleting Contact!";
		$responce["reqcode"]="0";
	}
}
else
{
	$responce["id"]="NA";
	$responce["reqmsg"]="Incomplete Request!";
	$responce["reqcode"]="0";
}

$x=json_encode($responce);
echo $x;


?>