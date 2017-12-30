<%-- 
    Document   : typeDetail
    Created on : Dec 17, 2017, 2:12:32 AM
    Author     : Tien
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Type Detail</title>
        <!-- bootstrap -->
        <link href="resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">      
        <link href="resource/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">		
        <link href="resource/themes/css/bootstrappage.css" rel="stylesheet"/>

        <!-- global styles -->
        <link href="resource/themes/css/flexslider.css" rel="stylesheet"/>
        <link href="resource/themes/css/main.css" rel="stylesheet"/>

        <!-- scripts -->
        <script src="resource/themes/js/jquery-1.7.2.min.js"></script>
        <script src="resource/bootstrap/js/bootstrap.min.js"></script>				
        <script src="resource/themes/js/superfish.js"></script>	
        <script src="resource/themes/js/jquery.scrolltotop.js"></script>
        <script src="resource/resource/themes/js/login-register.js" type="text/javascript"></script>
        <script src="resource/themes/js/login-register.js" type="text/javascript"></script>

        <!--[if lt IE 9]>			
                <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
                <script src="js/respond.min.js"></script>
        <![endif]-->
    </head>
    <body>		
        <div id="top-bar" class="container">
            <div class="row">
                <div class="span4">
                    <form method="POST" action="searchProductsServlet">
                        <input type="text" name="txtProductName" class="search-query" Placeholder="eg Sony">
                        <button value="Search" name="action" class="btn-success">Search</button>
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
                                <li><a href="customer/home.jsp">Hi, ${user.fullname}</a></li>  
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
                                    <form method="post" action="AccountServlet">
                                        <input id="username" class="input-xlarge" pattern="[A-Za-z0-9@a-z.com]{2,30}" type="text" name="username" required="true"><br/>
                                        <input id="password" class="input-xlarge" pattern="[A-Za-z0-9]{2,30}" type="password"  name="password" required="true"><br/>
                                        <input class="btn btn-inverse" style="width:285px;" type="submit" name="action" value="Login" onclick="checkLogin();">
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="box">
                            <div class="content registerBox" style="display:none;">
                                <div class="form">
                                    <form method="post" action="register.html">
                                        <b style="color: red;" id="note1"></b>
                                        <input id="email" class="form-control" type="text" placeholder="Username" name="username" onBlur="checkEmail()" required="true"><br/>
                                        <b style="color: red;" id="note2"></b>
                                        <input id="Regispassword" class="form-control" pattern="[A-Za-z0-9]{6,20}" type="password" placeholder="Password" name="password" required="true"><br/>
                                        <input id="Regispassword_confirmation" class="form-control" pattern="[A-Za-z0-9]{6,20}" type="password" placeholder="Repeat Password" name="password_confirmation" required="true" onBlur="checkPass()"><br/>
                                        <input class="btn btn-default btn-register" id="btnRegister" value="Create account" name="action" type="submit">
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <div class="forgot login-footer">
                            <span>Looking to 
                                <a href="javascript: showRegisterForm();">Đăng Ký</a>
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
        <div id="wrapper" class="container">
            <section class="navbar main-menu">
                <div class="navbar-inner main-menu">				
                    <a href="index.html" class="logo pull-left"><img src="resource/themes/images/logo.png" class="site_logo" alt=""></a>
                    <nav id="menu" class="pull-right">
                        <ul>
                            <li><a href="categoryDetail.jsp">Woman</a>					
                                <ul>
                                    <li><a href="typeDetail.jsp">Lacinia nibh</a></li>									
                                    <li><a href="typeDetail.jsp">Eget molestie</a></li>
                                    <li><a href="typeDetail.jsp">Varius purus</a></li>									
                                </ul>
                            </li>															
                            <li><a href="categoryDetail.jsp">Man</a></li>			
                            <li><a href="categoryDetail.jsp">Sport</a>
                                <ul>									
                                    <li><a href="typeDetail.jsp">Gifts and Tech</a></li>
                                    <li><a href="typeDetail.jsp">Ties and Hats</a></li>
                                    <li><a href="typeDetail.jsp">Cold Weather</a></li>
                                </ul>
                            </li>							
                            <li><a href="categoryDetail.jsp">Hangbag</a></li>
                            <li><a href="categoryDetail.jsp">Best Seller</a></li>
                            <li><a href="categoryDetail.jsp">Top Seller</a></li>
                        </ul>
                    </nav>
                </div>
            </section>	
            <section class="header_text sub">
                <img class="pageBanner" src="resource/themes/images/pageBanner.png" alt="New products" >
                <h4><span>(Title) Type Product Detail</span></h4>
            </section>
            <section class="main-content">

                <div class="row">						
                    <div class="span9">								
                        <ul class="thumbnails listing-products">
                            <li class="span3">
                                <div class="product-box">
                                    <span class="sale_tag"></span>												
                                    <a href="product_detail.html"><img alt="" src="resource/themes/images/ladies/9.jpg"></a><br/>
                                    <a href="product_detail.html" class="title">Fusce id molestie massa</a><br/>
                                    <a href="#" class="category">Phasellus consequat</a>
                                    <p class="price">$341</p>
                                </div>
                            </li>       
                            <li class="span3">
                                <div class="product-box">												
                                    <a href="product_detail.html"><img alt="" src="resource/themes/images/ladies/8.jpg"></a><br/>
                                    <a href="product_detail.html" class="title">Praesent tempor sem</a><br/>
                                    <a href="#" class="category">Erat gravida</a>
                                    <p class="price">$28</p>
                                </div>
                            </li>
                            <li class="span3">
                                <div class="product-box">
                                    <span class="sale_tag"></span>												
                                    <a href="product_detail.html"><img alt="" src="resource/themes/images/ladies/7.jpg"></a><br/>
                                    <a href="product_detail.html" class="title">Wuam ultrices rutrum</a><br/>
                                    <a href="#" class="category">Suspendisse aliquet</a>
                                    <p class="price">$341</p>
                                </div>
                            </li>
                            <li class="span3">
                                <div class="product-box">												
                                    <span class="sale_tag"></span>
                                    <a href="product_detail.html"><img alt="" src="resource/themes/images/ladies/6.jpg"></a><br/>
                                    <a href="product_detail.html" class="title">Praesent tempor sem sodales</a><br/>
                                    <a href="#" class="category">Nam imperdiet</a>
                                    <p class="price">$49</p>
                                </div>
                            </li>
                            <li class="span3">
                                <div class="product-box">                                        												
                                    <a href="product_detail.html"><img alt="" src="resource/themes/images/ladies/1.jpg"></a><br/>
                                    <a href="product_detail.html" class="title">Fusce id molestie massa</a><br/>
                                    <a href="#" class="category">Congue diam congue</a>
                                    <p class="price">$35</p>
                                </div>
                            </li>       
                            <li class="span3">
                                <div class="product-box">												
                                    <a href="product_detail.html"><img alt="" src="resource/themes/images/ladies/2.jpg"></a><br/>
                                    <a href="product_detail.html" class="title">Tempor sem sodales</a><br/>
                                    <a href="#" class="category">Gravida placerat</a>
                                    <p class="price">$61</p>
                                </div>
                            </li>
                            <li class="span3">
                                <div class="product-box">												
                                    <a href="product_detail.html"><img alt="" src="resource/themes/images/ladies/3.jpg"></a><br/>
                                    <a href="product_detail.html" class="title">Quam ultrices rutrum</a><br/>
                                    <a href="#" class="category">Orci et nisl iaculis</a>
                                    <p class="price">$233</p>
                                </div>
                            </li>
                            <li class="span3">
                                <div class="product-box">												
                                    <a href="product_detail.html"><img alt="" src="resource/themes/images/ladies/4.jpg"></a><br/>
                                    <a href="product_detail.html" class="title">Tempor sem sodales</a><br/>
                                    <a href="#" class="category">Urna nec lectus mollis</a>
                                    <p class="price">$134</p>
                                </div>
                            </li>
                            <li class="span3">
                                <div class="product-box">												
                                    <a href="product_detail.html"><img alt="" src="resource/themes/images/ladies/5.jpg"></a><br/>
                                    <a href="product_detail.html" class="title">Luctus quam ultrices</a><br/>
                                    <a href="#" class="category">Suspendisse aliquet</a>
                                    <p class="price">$261</p>
                                </div>
                            </li>
                        </ul>								
                        <hr>
                        <div class="pagination pagination-small pagination-centered">
                            <ul>
                                <li><a href="#">Prev</a></li>
                                <li class="active"><a href="#">1</a></li>
                                <li><a href="#">2</a></li>
                                <li><a href="#">3</a></li>
                                <li><a href="#">4</a></li>
                                <li><a href="#">Next</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="span3 col">
                        <div class="block">
                            <h4 class="title">
                                <span class="pull-left"><span class="text">Randomize</span></span>
                                <span class="pull-right">
                                    <a class="left button" href="#myCarousel" data-slide="prev"></a><a class="right button" href="#myCarousel" data-slide="next"></a>
                                </span>
                            </h4>
                            <div id="myCarousel" class="carousel slide">
                                <div class="carousel-inner">
                                    <div class="active item">
                                        <ul class="thumbnails listing-products">
                                            <li class="span3">
                                                <div class="product-box">
                                                    <span class="sale_tag"></span>												
                                                    <img alt="" src="resource/themes/images/ladies/1.jpg"><br/>
                                                    <a href="product_detail.html" class="title">Fusce id molestie massa</a><br/>
                                                    <a href="#" class="category">Suspendisse aliquet</a>
                                                    <p class="price">$261</p>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                    <div class="item">
                                        <ul class="thumbnails listing-products">
                                            <li class="span3">
                                                <div class="product-box">												
                                                    <img alt="" src="resource/themes/images/ladies/2.jpg"><br/>
                                                    <a href="product_detail.html" class="title">Tempor sem sodales</a><br/>
                                                    <a href="#" class="category">Urna nec lectus mollis</a>
                                                    <p class="price">$134</p>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="block">								
                            <h4 class="title"><strong>Best</strong> Seller</h4>								
                            <ul class="small-product">
                                <li>
                                    <a href="#" title="Praesent tempor sem sodales">
                                        <img src="resource/themes/images/ladies/3.jpg" alt="Praesent tempor sem sodales">
                                    </a>
                                    <a href="#">Praesent tempor sem</a>
                                </li>
                                <li>
                                    <a href="#" title="Luctus quam ultrices rutrum">
                                        <img src="resource/themes/images/ladies/4.jpg" alt="Luctus quam ultrices rutrum">
                                    </a>
                                    <a href="#">Luctus quam ultrices rutrum</a>
                                </li>
                                <li>
                                    <a href="#" title="Fusce id molestie massa">
                                        <img src="resource/themes/images/ladies/5.jpg" alt="Fusce id molestie massa">
                                    </a>
                                    <a href="#">Fusce id molestie massa</a>
                                </li>   
                            </ul>
                        </div>
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
    </body>
</html>
