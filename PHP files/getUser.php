<?php
include "conn.php";
$query="SELECT * FROM `users` ORDER BY `users`.`email` ASC";
$res=mysqli_query($con,$query);
$responce=array();
if($res)
{
	$responce['users']=array();
	while ($row = mysqli_fetch_array($res))
	{
		$user=array();
		$user['id']=$row['id'];
		$user['email']=$row['email'];
		$user['password']=$row['password'];
		array_push($responce["users"],$user);
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