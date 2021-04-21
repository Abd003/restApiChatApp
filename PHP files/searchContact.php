<?php

include "conn.php";
$responce=array();
if(isset($_POST["firstName"],$_POST["lastName"]))
{
	$firstName=$_POST["firstName"];
	$lastName=$_POST["lastName"];
	//$query="SELECT `id` FROM `users` WHERE `email` =".$email;
	$query="SELECT contactID FROM contacts WHERE firstName LIKE '%{$firstName}%' && lastName LIKE '%{$lastName}'";
	
	$res=mysqli_query($con,$query);
	$responce=array();
	if($res)
	{
		//$responce['users']=array();
		if ($row = mysqli_fetch_array($res))
		{
			
			$responce["contactID"]=$row['contactID'];
			$responce["reqmsg"]="Contact Found!";
			$responce["reqcode"]="1";
		
			//$user=array();
			//$user['id']=$row['id'];
			//$user['email']=$row['email'];
			//$user['password']=$row['password'];
			//array_push($responce["users"],$user);
		}
		else
		{
			$responce["id"]="NA";
			$responce["reqmsg"]="Contact Not Found!";
			$responce["reqcode"]="0";
		}
		
	}
	//$x=json_encode($responce);
	//echo $x;
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