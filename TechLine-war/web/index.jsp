<%-- 
    Document   : index
    Created on : Dec 16, 2017, 10:46:32 AM
    Author     : Tien
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
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
        <script src="resource/themes/js/login-register.js" type="text/javascript"></script>
        <script src="resource/themes/js/date-of-birth.js" type="text/javascript"></script>
    </head>
    <body>
        <c:if test="${not empty registMess}">
            <script>
                window.addEventListener("load", function() {
                    alert("${registMess}");
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
                                    <li><a href="viewServlet?action=homeSeller">Hi, ${user.fullname}</a></li>  
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
                                    <form method="post" action="addCustomerServlet">
                                        <b style="color: red;" id="note1"></b>
                                        <input id="email" class="input-xlarge" type="text" placeholder="Username" name="txtUsername" pattern=".{5,30}" required title="Username contains 5 to 30 characters"><br/>
                                        <b style="color: red;" id="note2"></b>
                                        <input id="Regispassword" class="input-xlarge" type="password" placeholder="Password" name="txtPassword" pattern=".{5,20}" required title="Password contains 5 to 20 characters"><br/>
                                        <input id="Regispassword_confirmation" class="input-xlarge" type="password" placeholder="Repeat Password" name="password_confirmation" pattern=".{5,20}" required title="Repeat password contains 5 to 20 characters" onBlur="checkPass()"><br/>
                                        <input id="Regispassword" class="input-xlarge" type="email" placeholder="Email" name="txtEmail" required="true"><br/>
                                        <input id="Regispassword" class="input-xlarge" type="text" placeholder="Full name" name="txtFullname" required="true"><br/>
                                        <input id="Regispassword" class="input-xlarge"  pattern='\d{9,15}' type="tel" placeholder="Phone" name="txtPhone" required title="Phone contains 9 to 15 characters"><br/>
                                        <input type="radio" name="role" value="customer" checked> Customer 
                                        <input type="radio" name="role" value="seller" > Seller <br/>
                                        <button class="btn btn-inverse" style="width:285px;" type="submit" name="action" value="register">Create An Account</button>
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
        <div id="wrapper" class="container">
            <section class="navbar main-menu">
                <div class="navbar-inner main-menu">				
                    <nav id="menu" class="pull-right">
                        <ul>
                            <li>
                              <a href="RedirecServlet?action=backToHome">Home</a>	          
                            </li>
                            <c:forEach items="${listCategories}" var="item">
                                <li>
                                    <a href="viewServlet?action=cateDetail&idCate=${item.categoryId}">${item.categoryName}</a>	
                                    <ul>
                                        <c:forEach items="${item.productTypesCollection}" var="type">
                                            <li><a href="viewServlet?action=typeDetail&idType=${type.typeId}">${type.typeName}</a></li>	
                                            </c:forEach>
                                    </ul>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                </div>
            </section>
            <section  class="homepage-slider" id="home-slider">
                <div class="flexslider">
                    <ul class="slides">
                        <li>
                            <img src="resource/themes/images/carousel/banner-1.jpg" alt="" />
                        </li>
                        <li>
                            <img src="resource/themes/images/carousel/banner-2.jpg" alt="" />
                            <div class="intro">
                                <h1>Mid season sale</h1>
                                <p><span>Up to 50% Off</span></p>
                                <p><span>On selected items online and in stores</span></p>
                            </div>
                        </li>
                    </ul>
                </div>			
            </section>
            <section class="header_text">
                We stand for top quality templates. Our genuine developers always optimized bootstrap commercial templates. 
                <br/>Don't miss to use our cheap abd best bootstrap templates.
            </section>
            <section class="main-content">
                <div class="row">
                    <div class="span12">													
                        <div class="row">
                            <div class="span12">
                                <h4 class="title">
                                    <span class="pull-left"><span class="text"><span class="line">News <strong>Products</strong></span></span></span>
                                    <span class="pull-right">
                                        <a class="left button" href="#myCarousel" data-slide="prev"></a><a class="right button" href="#myCarousel" data-slide="next"></a>
                                    </span>
                                </h4>
                                <div id="myCarousel" class="myCarousel carousel slide">
                                    <div class="carousel-inner">
                                        <div class="active item">
                                            <ul class="thumbnails">	
                                                <c:forEach items="${ListProductByDatePost1}" var="item">
                                                    <li class="span3" style="line-height: none">
                                                        <div class="product-box" >
                                                            <span class="sale_tag"></span>
                                                            <p><a href="viewServlet?action=productDetail&idProduct=${item.productId}"><img src="${item.productImage[0]}" alt="${item.productName}" style="width: 200px; height: 200px" /></a></p>
                                                            <a href="viewServlet?action=productDetail&idProduct=${item.productId}" class="title" style="height: 60px;">${item.productName}</a><br/>
                                                            <p class="price">&#36;${item.productPrice}</p>
                                                            <div>
                                                                <a class="btn btn-inverse" href="viewServlet?action=productDetail&idProduct=${item.productId}">Detail</a>
                                                                <button class="btn btn-inverse" type="submit">Add to cart</button>
                                                            </div>
                                                        </div>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                        <div class="item">
                                            <ul class="thumbnails">
                                                <c:forEach items="${ListProductByDatePost2}" var="item2">
                                                    <li class="span3" style="line-height: none">
                                                        <div class="product-box">
                                                            <p><a href="viewServlet?action=productDetail&idProduct=${item2.productId}"><img src="${item2.productImage[0]}" alt="${item2.productName}" style="width: 200px; height: 200px" /></a></p>
                                                            <a href="viewServlet?action=productDetail&idProduct=${item2.productId}" class="title" style="height: 60px;">${item2.productName}</a><br/>
                                                            <p class="price">&#36;${item2.productPrice}</p>
                                                            <div>
                                                                <a class="btn btn-inverse" href="viewServlet?action=productDetail&idProduct=${item2.productId}">Detail</a>
                                                                <button class="btn btn-inverse" type="submit">Add to cart</button>
                                                            </div>
                                                        </div>
                                                    </li>
                                                </c:forEach>

                                            </ul>
                                        </div>
                                    </div>							
                                </div>
                            </div>						
                        </div>
                        <br/>
                        <div class="row">
                            <div class="span12">
                                <h4 class="title">
                                    <span class="pull-left"><span class="text"><span class="line">Discount <strong>Products</strong></span></span></span>
                                    <span class="pull-right">
                                        <a class="left button" href="#myCarousel-2" data-slide="prev"></a><a class="right button" href="#myCarousel-2" data-slide="next"></a>
                                    </span>
                                </h4>
                                <div id="myCarousel-2" class="myCarousel carousel slide">
                                    <div class="carousel-inner">
                                        <div class="active item">
                                            <ul class="thumbnails">
                                                <c:forEach items="${ListProductByDiscount1}" var="itemDiscount">
                                                    <li class="span3" style="line-height: none">
                                                        <div class="product-box" style="line-height: none">
                                                            <span class="sale_tag"></span>
                                                            <p><a href="viewServlet?action=productDetail&idProduct=${itemDiscount.productId}"><img src="${itemDiscount.productImage[0]}" alt="${itemDiscount.productName}" style="width: 200px; height: 200px" /></a></p>
                                                            <a href="viewServlet?action=productDetail&idProduct=${itemDiscount.productId}" class="title" style="height: 60px;">${itemDiscount.productName}</a><br/>
                                                            <p class="price">&#36;${itemDiscount.productPrice}</p>
                                                            <div>
                                                                <a class="btn btn-inverse" href="viewServlet?action=productDetail&idProduct=${itemDiscount.productId}">Detail</a>
                                                                <button class="btn btn-inverse" type="submit">Add to cart</button>
                                                            </div>
                                                        </div>
                                                    </li>
                                                </c:forEach>    
                                            </ul>
                                        </div>
                                        <div class="item">
                                            <ul class="thumbnails">
                                                <c:forEach items="${ListProductByDiscount2}" var="itemDiscount2">
                                                    <li class="span3" style="line-height: none">
                                                        <div class="product-box" >
                                                            <p><a href="viewServlet?action=productDetail&idProduct=${itemDiscount2.productId}"><img src="${itemDiscount2.productImage[0]}" alt="${itemDiscount2.productName}" style="width: 200px; height: 200px"/></a></p>
                                                            <a href="viewServlet?action=productDetail&idProduct=${itemDiscount2.productId}" class="title" style="height: 60px;">${itemDiscount2.productName}</a><br/>
                                                            <p class="price">&#36;${itemDiscount2.productPrice}</p>
                                                            <div>
                                                                <a class="btn btn-inverse" href="viewServlet?action=productDetail&idProduct=${itemDiscount2.productId}">Detail</a>
                                                                <button class="btn btn-inverse" type="submit">Add to cart</button>
                                                            </div>
                                                        </div>
                                                    </li>     
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </div>							
                                </div>
                            </div>						
                        </div>
                        <div class="row">
                            <div class="span12">
                                <h4 class="title">
                                    <span class="pull-left"><span class="text"><span class="line">Seller <strong>Products</strong></span></span></span>
                                    <span class="pull-right">
                                        <a class="left button" href="#myCarousel-3" data-slide="prev"></a><a class="right button" href="#myCarousel-3" data-slide="next"></a>
                                    </span>
                                </h4>
                                <div id="myCarousel-3" class="myCarousel carousel slide">
                                    <div class="carousel-inner">
                                        <div class="active item">
                                            <ul class="thumbnails">
                                                <c:forEach items="${ListProductByDiscount1}" var="itemDiscount">
                                                    <li class="span3" style="line-height: none">
                                                        <div class="product-box" >
                                                            <span class="sale_tag"></span>
                                                            <p><a href="viewServlet?action=productDetail&idProduct=${itemDiscount.productId}"><img src="https://images-na.ssl-images-amazon.com/images/I/41%2B8ufOMeeL._SS150_.jpg" alt="" /></a></p>
                                                            <a href="viewServlet?action=productDetail&idProduct=${itemDiscount.productId}" class="title" style="height: 60px;">${itemDiscount.productName}</a><br/>
                                                            <p class="price">&#36;${itemDiscount.productPrice}</p>
                                                            <div>
                                                                <a class="btn btn-inverse" href="viewServlet?action=productDetail&idProduct=${itemDiscount.productId}">Detail</a>
                                                                <button class="btn btn-inverse" type="submit">Add to cart</button>
                                                            </div>
                                                        </div>
                                                    </li>
                                                </c:forEach>    
                                            </ul>
                                        </div>
                                        <div class="item">
                                            <ul class="thumbnails">
                                                <c:forEach items="${ListProductByDiscount2}" var="itemDiscount2">
                                                    <li class="span3">
                                                        <div class="product-box" style="line-height: none">
                                                            <p><a href="viewServlet?action=productDetail&idProduct=${itemDiscount2.productId}"><img src="https://images-na.ssl-images-amazon.com/images/I/41%2B8ufOMeeL._SS150_.jpg" alt="" /></a></p>
                                                            <a href="viewServlet?action=productDetail&idProduct=${itemDiscount2.productId}" class="title" style="height: 60px;">${itemDiscount2.productName}</a><br/>
                                                            <p class="price">&#36;${itemDiscount2.productPrice}</p>
                                                            <div>
                                                                <a class="btn btn-inverse" href="viewServlet?action=productDetail&idProduct=${itemDiscount2.productId}">Detail</a>
                                                                <button class="btn btn-inverse" type="submit">Add to cart</button>
                                                            </div>
                                                        </div>
                                                    </li>     
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </div>							
                                </div>
                            </div>						
                        </div>
                        <div class="row feature_box">						
                            <div class="span4">
                                <div class="service">
                                    <div class="responsive">	
                                        <img src="resource/themes/images/feature_img_2.png" alt="" />
                                        <h4>MODERN <strong>DESIGN</strong></h4>
                                        <p>Lorem Ipsum is simply dummy text of the printing and printing industry unknown printer.</p>									
                                    </div>
                                </div>
                            </div>
                            <div class="span4">	
                                <div class="service">
                                    <div class="customize">			
                                        <img src="resource/themes/images/feature_img_1.png" alt="" />
                                        <h4>FREE <strong>SHIPPING</strong></h4>
                                        <p>Lorem Ipsum is simply dummy text of the printing and printing industry unknown printer.</p>
                                    </div>
                                </div>
                            </div>
                            <div class="span4">
                                <div class="service">
                                    <div class="support">	
                                        <img src="resource/themes/images/feature_img_3.png" alt="" />
                                        <h4>24/7 LIVE <strong>SUPPORT</strong></h4>
                                        <p>Lorem Ipsum is simply dummy text of the printing and printing industry unknown printer.</p>
                                    </div>
                                </div>
                            </div>	
                        </div>		
                    </div>				
                </div>
            </section>
            <section class="our_client">
                <h4 class="title"><span class="text">Manufactures</span></h4>
                <div class="row">					
                    <div class="span2">
                        <a href="#"><img alt="" src="resource/themes/images/clients/14.png" style="width: 120px; height: 45px;"></a>
                    </div>
                    <div class="span2">
                        <a href="#"><img alt="" src="resource/themes/images/clients/35.png" style="width: 120px; height: 45px;"></a>
                    </div>
                    <div class="span2">
                        <a href="#"><img alt="" src="resource/themes/images/clients/1.png" style="width: 120px; height: 45px;"></a>
                    </div>
                    <div class="span2">
                        <a href="#"><img alt="" src="resource/themes/images/clients/2.png" style="width: 120px; height: 45px;"></a>
                    </div>
                    <div class="span2">
                        <a href="#"><img alt="" src="resource/themes/images/clients/3.png" style="width: 120px; height: 45px;"></a>
                    </div>
                    <div class="span2">
                        <a href="#"><img alt="" src="resource/themes/images/clients/4.png" style="width: 120px; height: 45px;"></a>
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
