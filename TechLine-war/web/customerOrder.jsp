<%-- 
    Document   : customer
    Created on : Jan 1, 2018, 1:35:08 PM
    Author     : tatyuki1209
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order's Customer</title>
        <link href="resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">      
        <link href="resource/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">

        <link href="resource/themes/css/bootstrappage.css" rel="stylesheet"/>
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet" />

        <!-- global styles -->
        <link href="resource/themes/css/flexslider.css" rel="stylesheet"/>
        <link href="resource/themes/css/main.css" rel="stylesheet"/>
        <link href="resource/themes/css/my-style.css" rel="stylesheet"/>    
        <!-- scripts -->
        <script src="resource/themes/js/jquery-1.7.2.min.js"></script>
        <script src="resource/bootstrap/js/bootstrap.min.js"></script>				
        <script src="resource/themes/js/superfish.js"></script>	
        <script src="resource/themes/js/jquery.scrolltotop.js"></script>
        <script src="resource/themes/js/login-register.js" type="text/javascript"></script>

    </head>
    <body>	
        <c:if test="${not empty message}">
            <script>
                window.addEventListener("load", function() {
                    alert("${message}");
                })
            </script>
        </c:if>
        <div id="top-bar" class="container">
            <div class="row">
                <div class="span4">
                    <form method="POST" action="searchProductsServlet">
                        <input type="text" name="txtProductName" class="search-query" Placeholder="eg Sony">
                        <button value="Search" name="action" class="btn-success btn">Search</button>
                    </form>
                </div>
                <div class="span8">
                    <div class="account pull-right">
                        <ul class="user-menu">	
                            <li><a class="btn" href="cart.jsp">Cart</a></li>
                                <%
                                    if (session.getAttribute("user") == null) {
                                %>
                            <li><a class="btn" data-toggle="modal" href="javascript:void(0)" onclick="openLoginModal();">Log in</a></li>
                                <%
                                    }
                                %>
                                <%
                                    if (session.getAttribute("user") != null) {
                                %>
                                <c:if test="${user.role=='admin'}">
                                    <li><a href="viewServlet?action=homeAdmin">Hi, ${user.fullname}</a></li>  
                                </c:if>

                                <c:if test="${user.role=='seller'}">
                                    <li><a href="seller/home.jsp">Hi, ${user.fullname}</a></li>  
                                </c:if>

                                <c:if test="${user.role=='customer'}">
                                    <li><a href="viewServlet?action=homeCustomer">Hi, ${user.fullname}</a></li>  
                                </c:if>
                            <li><a class="btn" href="viewServlet?action=Logout">Log out</a></li>
                                <%
                                    }
                                %>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <!--Phần dialog box Login-->
        <div class="modal fade login" id="loginModal">
            <div class="modal-dialog login animated">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">Login with</h4>
                    </div>
                    <div class="modal-body">  
                        <div class="box">
                            <div class="content">
                                <div class="error"></div>
                                <div class="form loginBox">
                                    <form method="post" action="viewServlet">
                                        <input id="username" class="input-xlarge" pattern="[A-Za-z0-9@a-z.com]{2,30}" type="text" name="username" required="true"><br/>
                                        <input id="password" class="input-xlarge" pattern="[A-Za-z0-9]{2,30}" type="password"  name="password" required="true"><br/>
                                        <input class="btn btn-inverse" style="width:285px;" type="submit" name="action" value="Login">
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="box">
                            <div class="content registerBox" style="display:none;">
                                <div class="form">
                                    <form method="post" action="register.html">
                                        <b style="color: red;" id="note1"></b>
                                        <input id="email" class="input-xlarge" type="text" placeholder="Username" name="username" onBlur="checkEmail()" required="true"><br/>
                                        <b style="color: red;" id="note2"></b>
                                        <input id="Regispassword" class="input-xlarge" pattern="[A-Za-z0-9]{6,20}" type="password" placeholder="Password" name="password" required="true"><br/>
                                        <input id="Regispassword_confirmation" class="input-xlarge" pattern="[A-Za-z0-9]{6,20}" type="password" placeholder="Repeat Password" name="password_confirmation" required="true" onBlur="checkPass()"><br/>
                                        <input class="btn btn-inverse" id="btnRegister" value="Create account" name="action" type="submit">
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <div class="forgot login-footer">
                            <span>Looking to 
                                <a href="javascript: showRegisterForm();">Register</a>
                                ?</span>
                        </div>
                        <div class="forgot register-footer" style="display:none">
                            <span>Already have an account?</span>
                            <a href="javascript: showLoginForm();">Login</a>
                        </div>
                    </div>        
                </div>
            </div>
        </div>
        <!--Kết thúc dialog box Login-->
        
        <!--Phần dialog box Change Password-->
        <div class="modal fade login" id="PasswordModal">
            <div class="modal-dialog login animated">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">Change Password</h4>
                    </div>
                    <div class="modal-body">  
                        <div class="box">
                            <div class="content">
                                <div class="error"></div>
                                <div class="form loginBox">
                                    <form method="post" action="viewServlet">
                                        <input id="ChagePassword" class="input-xlarge" pattern="[A-Za-z0-9@a-z.com]{2,30}" type="text" name="username" required="true"><br/>
                                        <input id="ChagePassword_confirmation" class="input-xlarge" pattern="[A-Za-z0-9]{2,30}" type="password"  name="password" required="true"><br/>
                                        <input id="NewPassowrd" class="input-xlarge" pattern="[A-Za-z0-9]{2,30}" type="password"  name="password" required="true"><br/>
                                        <input class="btn btn-inverse" style="width:285px;" type="submit" name="action" value="Change Password">
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <div class="forgot login-footer">
                            <span>Note: Change the password remember</span>
                        </div>

                    </div>        
                </div>
            </div>
        </div>
        <!--Kết thúc dialog box Change Password-->
        <div id="wrapper" class="container">
            <section class="navbar main-menu">
                <div class="navbar-inner main-menu">				
                    <nav id="menu" class="pull-right">
                        <ul>
                            <li>
                              <a href="RedirectServlet?action=backToHome">Home</a>	          
                            </li>
                            <c:forEach items="${listCategories}" var="item">
                                <li>
                                    <a href="viewServlet?action=cateDetail&idCate=${item.categoryId}">${item.categoryName}</a>	
                                    <ul>
                                        <c:forEach items="${item.productTypesCollection}" var="type">
                                            <c:if test="${type.typeStatus}">
                                                <li><a href="viewServlet?action=typeDetail&idType=${type.typeId}">${type.typeName}</a></li>	
                                            </c:if>
                                        </c:forEach>
                                    </ul>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                </div>
            </section>
            <section class="main-content">
                <div class="row">
                    <div class="span12">
                        <div class="my-main"><!-----!!!!!!!!!!!!!!!!!!!!!!!!.my-main Phan than noi dung trang!!!!!!!!!!!!!!!!!!!!!!!!!!!!----->
                <div class="table-cart"><!----- Table cart ----->
                    <h3>Danh sách đặt hàng</h3>
                    <div id="order-list-customer-info">
                        <h4>Name: Tiến</h4>
                    </div>               
                    <div class="clearfix"></div>

                    <div id="order-date-select">
                        <p>Find By Date: <input type="text" id="datepicker" onchange="searchOMbyDate($(this).val());" readonly="" autocomplete=off /> <br>or</p>
                        <p>All Order <input type="button" id="view-all-order" value="All Order" onclick="searchOMbyDate(''); $(this).parent().siblings().children().val('');"></p>																
                    </div>
                    <div class="clearfix"></div>
                    <div class="my-orderlist-table"><!-----   .my-orderlist-table   ------>
                        <table class="orderlist-table">
                            <caption>Infomation Order</caption> 
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Date Order</th>
                                <th>Hour Order</th>
                                <th>Status</th>
                                <th>Price</th>
                            </tr>
                            </thead>                            
                            <tbody class="oMView">
                            <tr>
                                <td class="order-number"><p><a href="#">asdasdasd</a></p></td>
                                <td><p>asdasd</p></td>
                                <td><p>asdasdasd</p></td>
                                <td><p>asdasdasd</p></td>
                                <td><p>asdasdasd</p></td>
                            </tr>
                            </tbody>
                        </table>
                    </div><!-----   .my-orderlist-table end   ------>
                </div><!----- Table Cart end -----> 
                <div class="clearfix"></div>

            </div><!-----.my-main end----->
                    </div>
                </div>
            </section>
            <section class="our_client">
                <h4 class="title"><span class="text">Manufactures</span></h4>
                <div class="row">					
                    <div class="span2">
                        <a href="#"><img alt="" src="resource/themes/images/clients/14.png"></a>
                    </div>
                    <div class="span2">
                        <a href="#"><img alt="" src="resource/themes/images/clients/35.png"></a>
                    </div>
                    <div class="span2">
                        <a href="#"><img alt="" src="resource/themes/images/clients/1.png"></a>
                    </div>
                    <div class="span2">
                        <a href="#"><img alt="" src="resource/themes/images/clients/2.png"></a>
                    </div>
                    <div class="span2">
                        <a href="#"><img alt="" src="resource/themes/images/clients/3.png"></a>
                    </div>
                    <div class="span2">
                        <a href="#"><img alt="" src="resource/themes/images/clients/4.png"></a>
                    </div>
                </div>
            </section>
            <section id="footer-bar">
                <div class="row">
                    <div class="span3">
                        <h4>Navigation</h4>
                        <ul class="nav">
                            <li><a href="./index.html">Homepage</a></li>  
                            <li><a href="./about.html">About Us</a></li>
                            <li><a href="./contact.html">Contac Us</a></li>
                            <li><a href="./cart.html">Your Cart</a></li>
                            <li><a href="./register.html">Login</a></li>							
                        </ul>					
                    </div>
                    <div class="span4">
                        <h4>My Account</h4>
                        <ul class="nav">
                            <li><a href="#">My Account</a></li>
                            <li><a href="#">Order History</a></li>
                            <li><a href="#">Wish List</a></li>
                            <li><a href="#">Newsletter</a></li>
                        </ul>
                    </div>
                    <div class="span5">
                        <p class="logo"><img src="resource/themes/images/logo.png" class="site_logo" alt=""></p>
                        <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. the  Lorem Ipsum has been the industry's standard dummy text ever since the you.</p>
                        <br/>
                        <span class="social_icons">
                            <a class="facebook" href="#">Facebook</a>
                            <a class="twitter" href="#">Twitter</a>
                            <a class="skype" href="#">Skype</a>
                            <a class="vimeo" href="#">Vimeo</a>
                        </span>
                    </div>					
                </div>	
            </section>
            <section id="copyright">
                <span>Copyright 2013 bootstrappage template  All right reserved.</span>
            </section>
        </div>
        <script src="resource/themes/js/common.js"></script>
        <script src="resource/themes/js/jquery.flexslider-min.js"></script>
        <script type="text/javascript">
                                            $(function() {
                                                $(document).ready(function() {
                                                    $('.flexslider').flexslider({
                                                        animation: "fade",
                                                        slideshowSpeed: 4000,
                                                        animationSpeed: 600,
                                                        controlNav: false,
                                                        directionNav: true,
                                                        controlsContainer: ".flex-container" // the container that holds the flexslider
                                                    });
                                                });
                                            });
        </script>
    </body>
</html>

