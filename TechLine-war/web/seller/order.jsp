<%-- 
    Document   : customer
    Created on : Dec 17, 2017, 3:07:56 AM
    Author     : Tien
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order</title>

        <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
        <meta name="viewport" content="width=device-width" />


        <!-- Bootstrap core CSS     -->
        <link href="resource/assets/css/bootstrap.min.css" rel="stylesheet" />

        <!--  Material Dashboard CSS    -->
        <link href="resource/assets/css/material-dashboard.css" rel="stylesheet"/>

        <!--  CSS for Demo Purpose, don't include it in your project     -->
        <link href="resource/assets/css/demo.css" rel="stylesheet" />

        <!--     Fonts and icons     -->
        <link href="http://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css" rel="stylesheet">
        <link href='http://fonts.googleapis.com/css?family=Roboto:400,700,300|Material+Icons' rel='stylesheet' type='text/css'>
    </head>
    <body>

        <div class="wrapper">
            <div class="sidebar" data-color="purple" data-image="resource/assets/img/sidebar-1.jpg">
                <!--
                Tip 1: You can change the color of the sidebar using: data-color="purple | blue | green | orange | red"

                Tip 2: you can also add an image using data-image tag
                -->

                <div class="logo">
                    <a href="RedirectServlet?action=backToHome" class="simple-text">
                        <img src="resource/assets/img/tim_80x80.png"/>
                    </a>
                </div>

                <div class="sidebar-wrapper">
                    <ul class="nav">
                        <li>
                            <a href="viewServlet?action=homeSeller">
                                <i class="material-icons">dashboard</i>
                                <p>Profile</p>
                            </a>
                        </li>   
                        <li>
                            <a href="viewServlet?action=sellerProduct">
                                <i class="material-icons">content_paste</i>
                                <p>List Product of Seller </p>
                            </a>
                        </li>
                        <li class="active">
                            <a href="viewServlet?action=sellerOrder">
                                <i class="material-icons">location_on</i>
                                <p>List Order </p>
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
                        <div class="row" style="text-align: center;">                       
                            <a class="btn-instagram btn" value="permissions" href="editType.jsp">Delete</a>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="card card-plain">
                                    <div class="card-header" data-background-color="purple">
                                        <h4 class="title">Order List</h4>
                                        <p class="category">Line Tech</p>
                                    </div>
                                    
                                    ${directPath}
                                    <div class="card-content table-responsive">
                                        <table class="table table-hover">
                                            <thead class="text-primary">
                                            <th>Order ID</th>
                                            <th>Buyer</th>   
                                            <th>Date</th>
                                            <th>Note</th>
                                            <th>Total Price</th>
                                            <th>Status</th>
                                            </thead>
                                            <tbody>
                                             <!--   <c:forEach items="${product}" var="prd">
                                                    <c:forEach items="${listOrder}" var="order">
                                                        <c:forEach items="${order.orderDetailsCollection}" var="oDC">
                                                            <c:if test="${order.orderStatus eq 'Done'} && ${oDC.products.productId == prd.productId}">
                                                                <tr style="background-color: yellow; color: #ffffff;"> 
                                                                <td>${order.orderMId}</td>
                                                                <td>${order.userId.fullname}</td>
                                                                <td><fmt:formatDate pattern="dd/MM/yyyy kk:mm:ss" value="${order.dateOrdered}"/></td>
                                                                <td>${order.orderNote}</td>
                                                                <td>${order.orderTotalPrice}</td>
                                                                <td>${order.orderStatus}</td>
                                                            </tr>
                                                            </c:if>
                                                        </c:forEach>
                                                    </c:forEach> -->
                                                </c:forEach>
                                              <!--  <c:forEach items="${product}" var="prd">
                                                    <c:forEach items="${listOrder}" var="order">
                                                        <c:if test="${order.orderStatus eq 'Done'} && ${(order.orderDetailsCollection.products.productId)== ( prd.productId)}">
                                                            <tr style="background-color: #33ff33; color: #ffffff;"> 
                                                                <td>${order.orderMId}</td>
                                                                <td>${order.userId.fullname}</td>
                                                                <td><fmt:formatDate pattern="dd/MM/yyyy kk:mm:ss" value="${order.dateOrdered}"/></td>
                                                                <td>${order.orderNote}</td>
                                                                <td>${order.orderTotalPrice}</td>
                                                                <td>${order.orderStatus}</td>
                                                            </tr>
                                                        </c:if>     
                                                       <c:if test="${order.orderStatus eq 'Processing'} && ${order.productId eq prd.productId} ">
                                                            <tr style="background-color: yellow; color: #ffffff;"> 
                                                                <td>${order.orderMId}</td>
                                                                <td>${order.userId.fullname}</td>
                                                                <td><fmt:formatDate pattern="dd/MM/yyyy kk:mm:ss" value="${order.dateOrdered}"/></td>
                                                                <td>${order.orderNote}</td>
                                                                <td>${order.orderTotalPrice}</td>
                                                                <td>${order.orderStatus}</td>
                                                            </tr>
                                                        </c:if>     
                                                        <c:if test="${order.orderStatus eq 'Delivery'} && ${order.productId eq prd.productId} ">
                                                            <tr style="background-color: blue; color: #ffffff;"> 
                                                                <td>${order.orderMId}</td>
                                                                <td>${order.userId.fullname}</td>
                                                                <td><fmt:formatDate pattern="dd/MM/yyyy kk:mm:ss" value="${order.dateOrdered}"/></td>
                                                                <td>${order.orderNote}</td>
                                                                <td>${order.orderTotalPrice}</td>
                                                                <td>${order.orderStatus}</td>
                                                            </tr>
                                                        </c:if>     
                                                        <c:if test="${order.orderStatus eq 'Cancel'} && ${order.productId eq prd.productId}">
                                                            <tr style="background-color: red; color: #ffffff;"> 
                                                                <td>${order.orderMId}</td>
                                                                <td>${order.userId.fullname}</td>
                                                                <td><fmt:formatDate pattern="dd/MM/yyyy kk:mm:ss" value="${order.dateOrdered}"/></td>
                                                                <td>${order.orderNote}</td>
                                                                <td>${order.orderTotalPrice}</td>
                                                                <td>${order.orderStatus}</td>
                                                            </tr>
                                                        </c:if> -->    
                                                    </c:forEach>  
                                                </c:forEach>
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
    <script src="resource/assets/js/jquery-3.1.0.min.js" type="text/javascript"></script>
    <script src="resource/assets/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="resource/assets/js/material.min.js" type="text/javascript"></script>

    <!--  Charts Plugin -->
    <script src="resource/assets/js/chartist.min.js"></script>

    <!--  Notifications Plugin    -->
    <script src="resource/assets/js/bootstrap-notify.js"></script>

    <!--  Google Maps Plugin    -->
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js"></script>

    <!-- Material Dashboard javascript methods -->
    <script src="resource/assets/js/material-dashboard.js"></script>

    <!-- Material Dashboard DEMO methods, don't include it in your project! -->
    <script src="resource/assets/js/demo.js"></script>

</html>
