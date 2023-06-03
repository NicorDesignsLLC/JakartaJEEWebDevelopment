<%@ page session="false"
	import="java.util.Map, com.nicordesigns.FileAttachment, com.nicordesigns.Registration"%>
<!DOCTYPE html>
<html>
<head>
<title>Charity Registration</title>
<style>
/* Add custom spacing between form elements */
.form-group {
	margin-bottom: 20px;
}
</style>
<link rel="stylesheet" href="../css/bootstrap.min.css">
<script src="../js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<h2>Create a Registration</h2>
		<form method="POST" action="charityRegistrationServlet"
			enctype="multipart/form-data">
			<input type="hidden" name="action" value="create">
			<div class="form-group col-xs-4">
				<label for="userName">Your Name:</label> 
				<!-- Using EL for registration.userName -->
				<input type="text" name="userName" id="userName" class="form-control" value="${registration.userName}" required="true">
				<label for="charityInfo">Charity Info:</label> 
				<input type="text" name="charityInfo" class="form-control"> 
				<label for="body">Body:</label>
				<textarea name="body" rows="5" class="form-control"></textarea>

				<label for="file">Attachments:</label> 
				<input type="file" name="file"> 
				<br></br>
				<input type="submit" value="Submit"	class="btn btn-primary">
			</div>
		</form>
	</div>

</body>
</html>
