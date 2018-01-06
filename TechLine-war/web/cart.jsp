<%-- 
    Document   : cart
    Created on : Dec 27, 2017, 11:47:20 PM
    Author     : tatyuki1209
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart Page</title>
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
    </head>
    <body>		
        <div id="top-bar" class="container">
            <div class="row">
                <div class="span4">
                    <form method="POST" class="search_form">
                        <input type="text" class="input-block-level search-query" Placeholder="eg. T-sirt">
                    </form>
                </div>
                <div class="span8">
                    <div class="account pull-right">
                        <ul class="user-menu">				
                            <li><a href="#">My Account</a></li>
                            <li><a href="cart.html">Your Cart</a></li>
                            <li><a href="checkout.html">Checkout</a></li>					
                            <li><a href="register.html">Login</a></li>			
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div id="wrapper" class="container">
            <section class="navbar main-menu">
                <div class="navbar-inner main-menu">				
                    <a href="index.html" class="logo pull-left"><img src="themes/images/logo.png" class="site_logo" alt=""></a>
                    <nav id="menu" class="pull-right">
                        <ul>
                            <li><a href="./products.html">Woman</a>					
                                <ul>
                                    <li><a href="./products.html">Lacinia nibh</a></li>									
                                    <li><a href="./products.html">Eget molestie</a></li>
                                    <li><a href="./products.html">Varius purus</a></li>									
                                </ul>
                            </li>															
                            <li><a href="./products.html">Man</a></li>			
                            <li><a href="./products.html">Sport</a>
                                <ul>									
                                    <li><a href="./products.html">Gifts and Tech</a></li>
                                    <li><a href="./products.html">Ties and Hats</a></li>
                                    <li><a href="./products.html">Cold Weather</a></li>
                                </ul>
                            </li>							
                            <li><a href="./products.html">Hangbag</a></li>
                            <li><a href="./products.html">Best Seller</a></li>
                            <li><a href="./products.html">Top Seller</a></li>
                        </ul>
                    </nav>
                </div>
            </section>				
            <section class="header_text sub">
                <img class="pageBanner" src="themes/images/pageBanner.png" alt="New products" >
                <h4><span>Shopping Cart</span></h4>
            </section>
            <section class="main-content">				
                <div class="row">
                    <div class="span9">					
                        <h4 class="title"><span class="text"><strong>Your</strong> Cart</span></h4>
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>Image</th>
                                    <th>Product Name</th>
                                    <th>Quantity</th>
                                    <th>Unit Price</th>
                                    <th>Total</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${cart}" var="item">
                                    <tr>
                                        <td><a href="product_detail.html"><img alt="" src="${item.image}" style="width:200px; height:200px;"></a></td>
                                        <td>${item.name}</td>
                                        <td><input type="text" placeholder="1" class="input-mini" value="${item.quantity}"></td>
                                        <td>$${item.price}</td>
                                        <td>$${item.total}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>			
                        <hr>
                        <p class="cart-total right">
                            <strong>Sub-Total</strong>:	$100.00<br>
                            <strong>Product Discount</strong>: $2.00<br>
                            <strong>Member Discount</strong>: $17.50<br>
                            <strong>SHIPMENT FEE</strong>: $3<br/>
                            <strong>Total</strong>: $119.50<br>
                        </p>
                        <hr/>
                        <p class="buttons center">				
                            <button class="btn" type="button">Back Home</button>
                            <a class="btn btn-inverse" href="googleMap.jsp">Continue</a>
                        </p>					
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
                                                    <a href="product_detail.html"><img alt="" src="themes/images/ladies/2.jpg"></a><br/>
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
                                                    <a href="product_detail.html"><img alt="" src="themes/images/ladies/4.jpg"></a><br/>
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
                        <p class="logo"><img src="themes/images/logo.png" class="site_logo" alt=""></p>
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
        <script src="themes/js/common.js"></script>
        <script>
            $(document).ready(function() {
                $('#checkout').click(function(e) {
                    document.location.href = "checkout.html";
                })
            });
        </script>		
    </body>
</html>
