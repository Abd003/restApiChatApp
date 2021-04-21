<?php

include "conn.php";
$responce=array();
if(isset($_POST["sfirstName"],$_POST["slastName"],$_POST["message"],$_POST["rfirstName"],$_POST["rlastName"]))
{
	$sfirstName=$_POST["sfirstName"];
	$slastName=$_POST["slastName"];
	$message=$_POST["message"];
	$rfirstName=$_POST["rfirstName"];
	$rlastName=$_POST["rlastName"];
	$query="INSERT INTO messages(sfirstName, slastName,message,rfirstName,rlastName) VALUES ( '".$sfirstName."', '".$slastName."', '".$message."', '".$rfirstName."', '".$rlastName."')";
	$res=mysqli_query($con,$query);
	if($res)
	{
		$responce["id"]=mysqli_insert_id($con);
		$responce["reqmsg"]="Message Inserted!";
		$responce["reqcode"]="1";
	}
	else
	{
		$responce["id"]="NA";
		$responce["reqmsg"]="Error Inserting Message!";
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