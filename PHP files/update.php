<?php

include "conn.php";
$responce=array();
if(isset($_POST["id"],$_POST["name"],$_POST["phno"],$_POST["email"]))
{
	$id=$_POST["id"];
	$name=$_POST["name"];
	$phno=$_POST["phno"];
	$email=$_POST["email"];
	$query="UPDATE `contacts` SET `name`='$name',`phono`='$phno',`email`='$email' WHERE `id`=".$id;
	$res=mysqli_query($con,$query);
	if($res)
	{
		$responce["id"]=$id;
		$responce["reqmsg"]="Contact Updated!";
		$responce["reqcode"]="1";
	}
	else
	{
		$responce["id"]=$id;
		$responce["reqmsg"]="Error Updating Contact!";
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