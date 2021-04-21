<?php
include "conn.php";
$query="SELECT * FROM `messages`";
$res=mysqli_query($con,$query);
$responce=array();
if($res)
{
	$responce['messages']=array();
	while ($row = mysqli_fetch_array($res))
	{
		$user=array();
		$user['messageID']=$row['messageID'];
		$user['sfirstName']=$row['sfirstName'];
		$user['slastName']=$row['slastName'];
		$user['message']=$row['message'];
		$user['rfirstName']=$row['rfirstName'];
		$user['rlastName']=$row['rlastName'];
		array_push($responce["messages"],$user);
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