<?php

include "conn.php";
$responce=array();
if(isset($_POST["contactID"]))
{
	$contactID=$_POST["contactID"];
	//$query="SELECT `id` FROM `users` WHERE `email` =".$email;
	$query="SELECT * FROM contacts WHERE contactID LIKE '%{$contactID}%'";
	
	$res=mysqli_query($con,$query);
	$responce=array();
	if($res)
	{
		//$responce['users']=array();
		//$responce['contacts']=array();
		while ($row = mysqli_fetch_array($res))
		{
			
			//$user=array();
			$responce["contactID"]=$row['contactID'];
			$responce["firstName"]=$row['firstName'];
			$responce["lastName"]=$row['lastName'];
			$responce["date"]=$row['date'];
			$responce["gender"]=$row['gender'];
			$responce["bio"]=$row['bio'];
			$responce["reqmsg"]="User Found!";
			$responce["reqcode"]="1";
			//array_push($responce["contacts"],$user);
		}
		
	}
	else
	{
		$responce["id"]="NA";
		$responce["reqmsg"]="User Not Found!";
		$responce["reqcode"]="0";
	}

	//$x=json_encode($responce);
	//echo $x;

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