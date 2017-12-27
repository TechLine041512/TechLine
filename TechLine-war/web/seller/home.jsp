<%-- 
    Document   : addProduct
    Created on : Dec 27, 2017, 1:42:24 AM
    Author     : tatyuki1209
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Home Page</title>

        <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
        <meta name="viewport" content="width=device-width" />

        <!-- Bootstrap core CSS     -->
        <link href="../resource/assets/css/bootstrap.min.css" rel="stylesheet" />

        <!--  Material Dashboard CSS    -->
        <link href="../resource/assets/css/material-dashboard.css" rel="stylesheet"/>

        <!--  CSS for Demo Purpose, don't include it in your project     -->
        <link href="../resource/assets/css/demo.css" rel="stylesheet" />

        <!--     Fonts and icons     -->
        <link href="http://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css" rel="stylesheet">
        <link href='http://fonts.googleapis.com/css?family=Roboto:400,700,300|Material+Icons' rel='stylesheet' type='text/css'>

        <!--Richtext-->
        <script src="http://js.nicedit.com/nicEdit-latest.js" type="text/javascript"></script>
        <script type="text/javascript">bkLib.onDomLoaded(nicEditors.allTextAreas);</script>
    </head>
    <body>

        <div class="wrapper">
            <div class="sidebar" data-color="purple" data-image="../resource/assets/img/sidebar-1.jpg">
                <!--
                Tip 1: You can change the color of the sidebar using: data-color="purple | blue | green | orange | red"

                Tip 2: you can also add an image using data-image tag
                -->

                <div class="logo">
                    <a href="home.jsp" class="simple-text">
                        <img src="../resource/assets/img/tim_80x80.png"/>
                    </a>
                </div>

                <div class="sidebar-wrapper">
                    <ul class="nav">
                        <li class="active">
                            <a href="home.jsp">
                                <i class="material-icons">dashboard</i>
                                <p>Profile</p>
                            </a>
                        </li>   
                        <li>
                            <a href="product.jsp">
                                <i class="material-icons">content_paste</i>
                                <p>Product List</p>
                            </a>
                        </li>
                        <li>
                            <a href="sell.jsp">
                                <i class="material-icons">location_on</i>
                                <p>Order List</p>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>

            <div class="main-panel">
                <div class="content"> 
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-8">
                                <div class="card">
                                    <div class="card-header" data-background-color="purple">
                                        <h4 class="title">Profile Seller</h4>
                                        <p class="category">Tech Line</p>
                                    </div>
                                    <div class="card-content">
                                        <form action="AccountServlet" method="post">
                                            <input type="hidden" name="txtUserID" value="${information.userID}"/>
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="form-group label-floating">
                                                        <label class="control-label">Email</label>
                                                        <input type="email" class="form-control" value="ringu@gmail.com" name="txtEmail">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <div class="form-group label-floating">
                                                        <label class="control-label">Full Name</label>
                                                        <input type="text" class="form-control" value="ringu" name="txtName">
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group label-floating">
                                                        <label class="control-label">Phone</label>
                                                        <input type="text" class="form-control" value="0909882230" name="txtPhone">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="form-group label-floating">
                                                        <label class="control-label">Store Name</label>
                                                        <input type="text" class="form-control" value="Reajas Company" name="txtStoreName">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <div class="form-group label-floating">
                                                        <label>Approved Date</label>
                                                        <input type="datetime-local" class="form-control"  name="txtApprovedDate">
                                                        </textarea>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group label-floating">
                                                        <label>Approved Place</label>
                                                        <input type="text" class="form-control" value="Reajas Company" name="txtApprovedPlace">
                                                        </textarea>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-8">
                                                    <div class="form-group label-floating">
                                                        <label class="control-label">Store Address</label>
                                                        <input type="text" class="form-control" value="171 Phạm Văn Đồng q.Gò Vấp" name="txtName">
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group label-floating">
                                                        <label class="control-label">Store Icon</label>
                                                        <input type="text" class="form-control" value="" name="txtName">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="form-group label-floating">
                                                        <label class="control-label">Store Summary</label>
                                                        <textarea name="comment" rows="9" cols="200" style="margin: 0px 0px 10px; width: 845px; height: 181px;">Công ty RJ - team Active - Leader RaeJas</textarea>
                                                    </div>
                                                </div>
                                            </div>
                                            <button type="submit" class="btn btn-primary" value="updateProfile">Save</button>
                                            <button type="submit" class="btn btn-primary" value="updateProfile">Cancel</button>
                                            <div class="clearfix"></div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="card card-profile">                                  
                                    <div class="card-avatar">
                                        <a href="#pablo">
                                            <img class="img" src="http://simpleicon.com/wp-content/uploads/shop-5-64x64.png" alt="Avatar"/>
                                        </a>
                                    </div>

                                    <div class="content">
                                        <h4 class="card-title">
                                            Reajas Company<br/>
                                            <small>171 Phạm Văn Đồng q.Gò Vấp</small>
                                        </h4>
                                        <p class="card-content">
                                            Công ty RJ - team Active - Leader RaeJas
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>    
                </div>

                <footer class="footer">
                    <div class="container-fluid">
                        <nav class="pull-left">
                            <ul>
                                <li>
                                    <a href="#">
                                        Home
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        Company
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        Portfolio
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        Blog
                                    </a>
                                </li>
                            </ul>
                        </nav>
                        <p class="copyright pull-right">
                            &copy; <script>document.write(new Date().getFullYear())</script> <a href="http://www.creative-tim.com">Creative Tim</a>, made with love for a better web
                        </p>
                    </div>
                </footer>
            </div>
        </div>

    </body>

    <!--   Core JS Files   -->
    <script src="../resource/assets/js/jquery-3.1.0.min.js" type="text/javascript"></script>
    <script src="../resource/assets/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="../resource/assets/js/material.min.js" type="text/javascript"></script>

    <!--  Notifications Plugin    -->
    <script src="../resource/assets/js/bootstrap-notify.js"></script>

    <!--  Google Maps Plugin    -->
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js"></script>

    <!-- Material Dashboard javascript methods -->
    <script src="../resource/assets/js/material-dashboard.js"></script>

    <!-- Material Dashboard DEMO methods, don't include it in your project! -->
    <script src="../resource/assets/js/demo.js"></script>


</html>

