<%-- 
    Document   : search
    Created on : Dec 27, 2017, 11:27:31 PM
    Author     : tatyuki1209
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
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
            <section class="header_text sub">
                <img class="pageBanner" src="https://cdn.pbrd.co/images/H29wWUf.png" alt="New products" >
                <h4><span>Result for keywords "${keyword}"</span></h4>
            </section>
            <section class="main-content">				
                <div class="row">
                    <div class="span12">					
                        <h4 class="title"><span class="text"><strong>Search</strong> Filter</span></h4>
                        <div class="item">
                            <ul class="thumbnails listing-products">
                                <form method="POST" action="searchProductsServlet">
                                    <li class="span3">
                                        <select class="form-control" name="txtTypeName" onchange="">
                                            <c:forEach items="${listTypeSearch}" var="type">
                                                <option value=${type.typeId} ${type.typeId == typeIdSelected ? 'selected' : ''}>${type.typeName}</option>
                                            </c:forEach>
                                        </select>
                                    </li>
                                    <li class="span3"><input id="minValue" type="number" min="0" step="any" class="search-query" Placeholder="Min price" name="txtMin" value="${strMin}"></li>
                                    <li class="span3"><input id="maxValue" type="number" min="0.01" step="any" class="search-query" Placeholder="Max price" name="txtMax" value="${strMax}"></li>
                                    <li class="span3"><button name="action" class="btn-success btn" value="filter" onclick=" return checkMinMax();">Search</button></li>
                                </form>
                            </ul>
                        </div>
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>Image</th>
                                    <th>Product Name</th>
                                    <th>Quantity</th>
                                    <th>Unit Price</th>
                                    <th>Brand</th>
                                    <th>Add to Cart</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${listProductSearch}" var="product">
                                    <tr>
                                        <td><a href="viewServlet?action=productDetail&idProduct=${product.productId}"><img alt="" src="https://images-na.ssl-images-amazon.com/images/I/41%2B8ufOMeeL._SS150_.jpg" style="width:200px; height:200px;"></a></td>
                                        <td><a href="viewServlet?action=productDetail&idProduct=${product.productId}">${product.productName}</a></td>
                                        <td><input type="text" placeholder="1" class="input-mini"></td>
                                        <td>$${product.productPrice}</td>
                                        <td>${product.brandId.brandName}</td>
                                        <td><button class="btn btn-inverse" type="submit" id="checkout">Add</button></td>
                                    </tr>			  
                                </c:forEach>                         	  
                            </tbody>
                        </table>			                					
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
