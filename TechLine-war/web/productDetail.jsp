<%-- 
    Document   : productDetail
    Created on : Dec 16, 2017, 11:48:43 AM
    Author     : Tien
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Detail</title>
        <!-- bootstrap -->
        <link href="resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">      
        <link href="resource/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">		
        <link href="resource/themes/css/bootstrappage.css" rel="stylesheet"/>

        <!-- global styles -->
        <link href="resource/themes/css/main.css" rel="stylesheet"/>
        <link href="resource/themes/css/jquery.fancybox.css" rel="stylesheet"/>
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet" />
        <link href="http://fonts.googleapis.com/css?family=Arvo" rel="stylesheet" type="text/css" />
        <!-- scripts -->
        <script src="resource/themes/js/jquery-1.7.2.min.js"></script>
        <script src="resource/bootstrap/js/bootstrap.min.js"></script>				
        <script src="resource/themes/js/superfish.js"></script>	
        <script src="resource/themes/js/jquery.scrolltotop.js"></script>
        <script src="resource/themes/js/jquery.fancybox.js"></script>
        <script src="resource/themes/js/login-register.js" type="text/javascript"></script>

    </head>
    <body>		
        <div id="top-bar" class="container">
            <div class="row">
                <div class="span4">
                    <form method="POST">
                        <input type="text" class="input-block-level search-query" Placeholder="eg. Sony">
                    </form>
                </div>
                <div class="span8">
                    <div class="account pull-right">
                        <ul class="user-menu">	
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
                            <a href="${role=="admin"?"admin/indexadmin.jsp":"AccountServlet?action=accountDetail"}">Xin chào, <%= session.getAttribute("user")%></a></li>                                                              
                            <li><a class="btn" href="AccountServlet?action=Logout">Log out</a></li>
                                <%
                                    }
                                %>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div id="wrapper" class="container">
            <section class="navbar main-menu">
                <div class="navbar-inner main-menu">				
                    <a href="index.html" class="logo pull-left"><img src="resource/themes/images/logo.png" class="site_logo" alt=""></a>
                    <nav id="menu" class="pull-right">
                        <ul>
                            <c:forEach items="${listCategories}" var="item">
                                <li>
                                    <a href="viewServlet?action=cateDetail&idCate=${item.categoryId}">${item.categoryName}</a>					
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                </div>
            </section>
            <section class="header_text sub">
                <img class="pageBanner" src="resource/themes/images/pageBanner.png" alt="New products" >
                <h4><span>${product.productName}</span></h4>
            </section>
            <section class="main-content">				
                <div class="row">						
                    <div class="span9">
                        <div class="row">
                            <div class="span4">
                                <a href="https://images-na.ssl-images-amazon.com/images/I/41%2B8ufOMeeL._SS150_.jpg" class="thumbnail" data-fancybox-group="group1" title="Description 1"><img alt="" src="https://images-na.ssl-images-amazon.com/images/I/41%2B8ufOMeeL._SS150_.jpg"></a>												
                                <ul class="thumbnails small">								
                                    <li class="span1">
                                        <a href="https://images-na.ssl-images-amazon.com/images/I/41%2B8ufOMeeL._SS150_.jpg" class="thumbnail" data-fancybox-group="group1" title="Description 2"><img src="https://images-na.ssl-images-amazon.com/images/I/41%2B8ufOMeeL._SS150_.jpg" alt=""></a>
                                    </li>								
                                    <li class="span1">
                                        <a href="https://images-na.ssl-images-amazon.com/images/I/41%2B8ufOMeeL._SS150_.jpg" class="thumbnail" data-fancybox-group="group1" title="Description 3"><img src="https://images-na.ssl-images-amazon.com/images/I/41%2B8ufOMeeL._SS150_.jpg" alt=""></a>
                                    </li>													
                                    <li class="span1">
                                        <a href="https://images-na.ssl-images-amazon.com/images/I/41%2B8ufOMeeL._SS150_.jpg" class="thumbnail" data-fancybox-group="group1" title="Description 4"><img src="https://images-na.ssl-images-amazon.com/images/I/41%2B8ufOMeeL._SS150_.jpg" alt=""></a>
                                    </li>
                                    <li class="span1">
                                        <a href="https://images-na.ssl-images-amazon.com/images/I/41%2B8ufOMeeL._SS150_.jpg" class="thumbnail" data-fancybox-group="group1" title="Description 5"><img src="https://images-na.ssl-images-amazon.com/images/I/41%2B8ufOMeeL._SS150_.jpg" alt=""></a>
                                    </li>
                                </ul>
                            </div>
                            <div class="span5">
                                <address>
                                    <strong>Product Code:</strong> <span>${product.productId}</span><br>
                                    <strong>Brand:</strong> <span>${product.brandId.brandName}</span><br>
                                    <strong>Weight:</strong> <span>${product.productWeight}(cm)</span><br>
                                    <strong>Width:</strong> <span>${product.productWidth}(cm)</span><br>
                                    <strong>Heigth:</strong> <span>${product.productHeigth}(cm)</span><br>
                                    <strong>Length:</strong> <span>${product.productLength}(cm)</span><br>
                                    <strong>Rating Points:</strong> <span style="color: yellow"><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i></span><br>
                                    <strong>Availability:</strong> ${product.productQuantity>0?'<span style="color: green;">- Còn Hàng</span>':'<span style="color: red;">Out Of Stock -</span>'}<br>								
                                </address>									
                                <h4>Price: <strong style="color: red;">&#36;${product.productPrice}</strong> <strong><strike> $587.50</strike></strong></h4>
                            </div>
                            <div class="span5">
                                <form class="form-inline">                                    
                                    <label>Qty:</label>
                                    <input type="text" class="span1" placeholder="1">
                                    <button class="btn btn-inverse" type="submit">Add to cart</button>
                                </form>
                            </div>							
                        </div>
                        <div class="row">
                            <div class="span9">
                                <ul class="nav nav-tabs" id="myTab">
                                    <li class="active"><a href="#home">Description</a></li>
                                    <li class=""><a href="#profile">Summary</a></li>
                                </ul>							 
                                <div class="tab-content">
                                    <div class="tab-pane active" id="home">${product.productDesc}</div>
                                    <div class="tab-pane" id="profile">${product.productSummary}</div>
                                </div>							
                            </div>
                            <div class="span9">
                                <h4 class="title">
                                    <span class="pull-left"><span class="text"><strong>Comment</strong></span></span>
                                    <span class="pull-right">
                                    </span>
                                </h4>
                                <div class="accordion-panel"><!---.accordion-panel--->
                                    <div id="comments-section"><!---Start #comments-section Binh luan cua khach hang ve san pham--->
                                        <c:forEach items="${product.productsCommentCollection}" var="productComment">
                                            <h4>${productComment.userId.userId}</h4>
                                            <p>${productComment.commentContent}</p>
                                        </c:forEach>
                                    </div><!---End #comments-section--->
                                    <div id="write-review-rating"><!---Start #write-review-rating Phan viet binh luan va cham sao cho product--->
                                        <form method="post" action="BookServlet">
                                            <input type="hidden" name="bookDetailID" value="${product.productId}">
                                            <h4>Comment</h4>
                                            <h5>Comment is limited to 500 words</h5>
                                            <textarea name="comment" rows="9" cols="200" style="margin: 0px 0px 10px; width: 845px; height: 181px;"></textarea>
                                            <div class="clearfix"></div>
                                            <button type="submit" id="submit-review" style="height:50px;" value="Comment" name="action"><i class="fa fa-check-circle"></i> Send Comment</button>
                                        </form>
                                    </div><br>  <!---End #write-review-rating--->
                                </div><!---.accordion-panel end--->       
                            </div>  
                            <div class="span12">	
                                <br>
                                <h4 class="title">
                                    <span class="pull-left"><span class="text"><strong>Related</strong> Products</span></span>
                                    <span class="pull-right">
                                        <a class="left button" href="#myCarousel-1" data-slide="prev"></a><a class="right button" href="#myCarousel-1" data-slide="next"></a>
                                    </span>
                                </h4>
                                <div id="myCarousel-1" class="carousel slide">
                                    <div class="carousel-inner">
                                        <div class="active item">
                                            <ul class="thumbnails listing-products">
                                                <li class="span3">
                                                    <div class="product-box">
                                                        <span class="sale_tag"></span>												
                                                        <a href="product_detail.html"><img alt="" src="https://images-na.ssl-images-amazon.com/images/I/41%2B8ufOMeeL._SS150_.jpg"></a><br/>
                                                        <a href="product_detail.html" class="title" style="height: 60px;">Wuam ultrices rutrum</a><br/>
                                                        <p class="price">$341</p>
                                                    </div>
                                                </li>
                                                <li class="span3">
                                                    <div class="product-box">
                                                        <span class="sale_tag"></span>												
                                                        <a href="product_detail.html"><img alt="" src="https://images-na.ssl-images-amazon.com/images/I/41%2B8ufOMeeL._SS150_.jpg"></a><br/>
                                                        <a href="product_detail.html" class="title" style="height: 60px;">Fusce id molestie massa</a><br/>
                                                        <p class="price">$341</p>
                                                    </div>
                                                </li>       
                                                <li class="span3">
                                                    <div class="product-box">												
                                                        <a href="product_detail.html"><img alt="" src="https://images-na.ssl-images-amazon.com/images/I/41%2B8ufOMeeL._SS150_.jpg"></a><br/>
                                                        <a href="product_detail.html" class="title" style="height: 60px;">Praesent tempor sem</a><br/>
                                                        <p class="price">$28</p>
                                                    </div>
                                                </li>
                                                <li class="span3">
                                                    <div class="product-box">												
                                                        <a href="product_detail.html"><img alt="" src="https://images-na.ssl-images-amazon.com/images/I/41%2B8ufOMeeL._SS150_.jpg"></a><br/>
                                                        <a href="product_detail.html" class="title" style="height: 60px;">Praesent tempor sem</a><br/>
                                                        <p class="price">$28</p>
                                                    </div>
                                                </li>
                                            </ul>
                                        </div>
                                        <div class="item">
                                            <ul class="thumbnails listing-products">
                                                <li class="span3">
                                                    <div class="product-box">
                                                        <span class="sale_tag"></span>												
                                                        <a href="product_detail.html"><img alt="" src="resource/themes/images/ladies/1.jpg"></a><br/>
                                                        <a href="product_detail.html" class="title">Fusce id molestie massa</a><br/>
                                                        <a href="#" class="category">Phasellus consequat</a>
                                                        <p class="price">$341</p>
                                                    </div>
                                                </li>
                                                <li class="span3">
                                                    <div class="product-box">
                                                        <span class="sale_tag"></span>												
                                                        <a href="product_detail.html"><img alt="" src="resource/themes/images/ladies/1.jpg"></a><br/>
                                                        <a href="product_detail.html" class="title">Fusce id molestie massa</a><br/>
                                                        <a href="#" class="category">Phasellus consequat</a>
                                                        <p class="price">$341</p>
                                                    </div>
                                                </li>
                                                <li class="span3">
                                                    <div class="product-box">												
                                                        <a href="product_detail.html"><img alt="" src="resource/themes/images/ladies/2.jpg"></a><br/>
                                                        <a href="product_detail.html">Praesent tempor sem</a><br/>
                                                        <a href="#" class="category">Erat gravida</a>
                                                        <p class="price">$28</p>
                                                    </div>
                                                </li>
                                                <li class="span3">
                                                    <div class="product-box">
                                                        <span class="sale_tag"></span>												
                                                        <a href="product_detail.html"><img alt="" src="resource/themes/images/ladies/3.jpg"></a><br/>
                                                        <a href="product_detail.html" class="title">Wuam ultrices rutrum</a><br/>
                                                        <a href="#" class="category">Suspendisse aliquet</a>
                                                        <p class="price">$341</p>
                                                    </div>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
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
                                                    <img alt="" src="https://images-na.ssl-images-amazon.com/images/I/41%2B8ufOMeeL._SS150_.jpg"><br/>
                                                    <a href="product_detail.html" class="title" style="height: 60px;">Kingston DataTraveler 100 G3</a><br/>
                                                    <p class="price">$261</p>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                    <div class="item">
                                        <ul class="thumbnails listing-products">
                                            <li class="span3">
                                                <div class="product-box">												
                                                    <img alt="" src="https://images-na.ssl-images-amazon.com/images/I/41%2B8ufOMeeL._SS150_.jpg"><br/>
                                                    <a href="product_detail.html" class="title" style="height: 60px;">Ring Video Doorbell Pro</a><br/>
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
                                        <img src="https://images-na.ssl-images-amazon.com/images/I/41%2B8ufOMeeL._SS150_.jpg" alt="Praesent tempor sem sodales">
                                    </a>
                                    <a href="#">Praesent tempor sem</a>
                                </li>
                                <li>
                                    <a href="#" title="Luctus quam ultrices rutrum">
                                        <img src="https://images-na.ssl-images-amazon.com/images/I/41%2B8ufOMeeL._SS150_.jpg" alt="Luctus quam ultrices rutrum">
                                    </a>
                                    <a href="#">Luctus quam ultrices rutrum</a>
                                </li>
                                <li>
                                    <a href="#" title="Fusce id molestie massa">
                                        <img src="https://images-na.ssl-images-amazon.com/images/I/41%2B8ufOMeeL._SS150_.jpg" alt="Fusce id molestie massa">
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
        <script>
                                $(function() {
                                    $('#myTab a:first').tab('show');
                                    $('#myTab a').click(function(e) {
                                        e.preventDefault();
                                        $(this).tab('show');
                                    })
                                })
                                $(document).ready(function() {
                                    $('.thumbnail').fancybox({
                                        openEffect: 'none',
                                        closeEffect: 'none'
                                    });

                                    $('#myCarousel-2').carousel({
                                        interval: 2500
                                    });
                                });
        </script>
    </body>
</html>
