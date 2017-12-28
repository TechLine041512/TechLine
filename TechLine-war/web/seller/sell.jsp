<%-- 
    Document   : customer
    Created on : Dec 17, 2017, 3:07:56 AM
    Author     : Tien
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Users</title>

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
                        <li>
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
                        <li class="active">
                            <a href="sell.jsp">
                                <i class="material-icons">location_on</i>
                                <p>Order List</p>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>

            <div class="main-panel">
                <nav class="navbar navbar-transparent navbar-absolute">
                    <div class="container-fluid">
                        <div class="navbar-header">
                            <button type="button" class="navbar-toggle" data-toggle="collapse">
                                <span class="sr-only">Toggle navigation</span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                            </button>                         
                        </div>
                        <div class="collapse navbar-collapse">
                            <ul class="nav navbar-nav navbar-right">
                                <li>
                                    <a href="#pablo" class="dropdown-toggle" data-toggle="dropdown">
                                        <i class="material-icons">dashboard</i>
                                        <p class="hidden-lg hidden-md">Dashboard</p>
                                    </a>
                                </li>
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                        <i class="material-icons">notifications</i>
                                        <span class="notification">5</span>
                                        <p class="hidden-lg hidden-md">Notifications</p>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a href="#">Mike John responded to your email</a></li>
                                        <li><a href="#">You have 5 new tasks</a></li>
                                        <li><a href="#">You're now friend with Andrew</a></li>
                                        <li><a href="#">Another Notification</a></li>
                                        <li><a href="#">Another One</a></li>
                                    </ul>
                                </li>
                                <li>
                                    <a href="#pablo" class="dropdown-toggle" data-toggle="dropdown">
                                        <i class="material-icons">person</i>
                                        <p class="hidden-lg hidden-md">Profile</p>
                                    </a>
                                </li>
                            </ul>

                            <form class="navbar-form navbar-right" role="search">
                                <div class="form-group  is-empty">
                                    <input type="text" class="form-control" placeholder="Search">
                                    <span class="material-input"></span>
                                </div>
                                <button type="submit" class="btn btn-white btn-round btn-just-icon">
                                    <i class="material-icons">search</i><div class="ripple-container"></div>
                                </button>
                            </form>
                        </div>
                    </div>
                </nav>

                <div class="content">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="card card-plain">
                                    <div class="card-header" data-background-color="purple">
                                        <h4 class="title">Order List</h4>
                                        <p class="category">Line Tech</p>
                                    </div>
                                    <div class="card-content table-responsive">
                                        <table class="table table-hover">
                                            <thead class="text-primary">
                                            <th>Order ID</th>
                                            <th>Buyer</th>
                                            <th>Total Price</th>
                                            <th>Date</th>
                                            <th>Status</th>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>OrderMaster001</td>
                                                    <td>Tuyết</td>
                                                    <td>$200</td>
                                                    <td>27/03/2017</td>
                                                    <td>Delievery</td>
                                                </tr>
                                                <tr>
                                                    <td>OrderMaster002</td>
                                                    <td>Diệu Nhi</td>
                                                    <td>$50</td>
                                                    <td>27/04/2017</td>
                                                    <td>Delievery</td>
                                                </tr>
                                                <tr style="color: green;">
                                                    <td>OrderMaster003</td>
                                                    <td>Nhi</td>
                                                    <td>$300</td>
                                                    <td>27/05/2017</td>
                                                    <td>Done</td>
                                                </tr>
                                                <tr style="color: green;">
                                                    <td>OrderMaster004</td>
                                                    <td>Thi</td>
                                                    <td>$400</td>
                                                    <td>27/06/2017</td>
                                                    <td>Done</td>
                                                </tr>
                                                <tr style="color: red;">
                                                    <td>OrderMaster005</td>
                                                    <td>Uyên</td>
                                                    <td>$600</td>
                                                    <td>27/07/2017</td>
                                                    <td>Cancelled</td>
                                                </tr>
                                            </tbody>
                                        </table>
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
                            </ul>
                        </nav>
                        <p class="copyright pull-right">
                            &copy; <script>document.write(new Date().getFullYear())</script> <a href="#">Line Tech</a>, made with love for a Group TechLine
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

    <!--  Charts Plugin -->
    <script src="../resource/assets/js/chartist.min.js"></script>

    <!--  Notifications Plugin    -->
    <script src="../resource/assets/js/bootstrap-notify.js"></script>

    <!--  Google Maps Plugin    -->
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js"></script>

    <!-- Material Dashboard javascript methods -->
    <script src="../resource/assets/js/material-dashboard.js"></script>

    <!-- Material Dashboard DEMO methods, don't include it in your project! -->
    <script src="../resource/assets/js/demo.js"></script>

</html>
