<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
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
  
  <form method="POST" action="charityRegistrationServlet" enctype="multipart/form-data">
    <input type="hidden" name="action" value="create">
    
    <div class="row">
      <div class="col-xs-12">
        <div class="form-group text-right" >
          <label for="userName">Your Name:</label>
          <input type="text" name="userName" id="userName" class="form-control" value="${registration.userName}" required="true">
        </div>
      </div>
    </div>
    
    <div class="row">
      <div class="col-xs-12">
        <div class="form-group text-right">
          <label for="charityInfo">Charity Info:</label>
          <input type="text" name="charityInfo" class="form-control">
        </div>
      </div>
    </div>
    
    <div class="row">
      <div class="col-xs-12">
        <div class="form-group text-right">
          <label for="body">Body:</label>
          <textarea name="body" rows="5" class="form-control"></textarea>
        </div>
      </div>
    </div>
    
    <div class="row">
      <div class="col-xs-12">
        <div class="form-group text-right">
          <label for="file">Attachments:</label>
          <input type="file" name="file">
        </div>
      </div>
    </div>
    
    <div class="row">
      <div class="col-xs-12">
        <input type="submit" value="Submit" class="btn btn-primary">
      </div>
    </div>
    
  </form>
</div>

    </body>
</html>
