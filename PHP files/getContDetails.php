<?php

include "conn.php";
$responce=array();
if(isset($_POST["contactID"]))
{
	$contactID=$_POST["contactID"];
	//$query="SELECT `id` FROM `users` WHERE `email` =".$email;
	$query="SELECT firstName, lastName FROM contacts WHERE contactID LIKE '%{$contactID}%'";
	
	$res=mysqli_query($con,$query);
	$responce=array();
	if($res)
	{
		if ($row = mysqli_fetch_array($res))
		{
			
			$responce["firstName"]=$row['firstName'];
			$responce["lastName"]=$row['lastName'];
			$responce["reqmsg"]="Contact Found!";
			$responce["reqcode"]="1";
		}
		else
		{
			$responce["id"]="NA";
			$responce["reqmsg"]="User Not Found!";
			$responce["reqcode"]="0";
		}
		
	}
	else
	{
		$responce["id"]="NA";
		$responce["reqmsg"]="Contact Not Found!";
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