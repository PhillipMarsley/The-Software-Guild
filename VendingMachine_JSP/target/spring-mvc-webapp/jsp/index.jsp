<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Vending Machine</title>

        <!-- css -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-grid.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">       
    </head>

    <body>
        <div id='divHeader'>
            <h1>Vending Machine</h1>
        </div>

        <hr/>

        <div class='container-fluid'>
            <div class='row'>

                <!--
                    ===================================================================
                    Left Side
                    ===================================================================
                -->
                <div id='divItems' class='container firstHalf' style='width:70%'>
                    <c:set var="counter" value="0"></c:set>
                    <c:forEach var="item" items="${itemList}">
                        <c:if test="${counter % 3 == 0}">
                            <div class='row'>
                            </c:if>

                            <form id='${item.id}' action='getItemNum' method="POST">
                                <input type='hidden' name='num' value='${item.id}'>
                            </form>

                            <div class='col itemBox' onclick='document.getElementById(${item.id}).submit()'>
                                <div class='row itemBoxRow number'>${item.id}</div>
                                <div class='row itemBoxRow'>${item.name}</div>
                                <div class='row itemBoxRow'>$${item.cost}</div>
                                <div class='row itemBoxRow quantity'>Quantity Left: ${item.quanity}</div>
                            </div>

                            <c:if test="${counter % 3 == 2}">
                            </div>
                        </c:if>
                        <c:set var="counter" value="${counter + 1}"></c:set>
                    </c:forEach>
                </div>

                <!--
                    ===================================================================
                    Right Side
                    ===================================================================
                -->
                <div id='divControls' class='container secondHalf' style='width:30%'>
                    <div id='divChangeInput'>
                        <h3>Total $ In</h3>
                        <form id='formChangeInput' method="POST">
                            <div class='row'>
                                <div class='col'>
                                    <input id='inputTotalChange' type='text' name='totalChange' value='$${total}' readonly>
                                </div>
                            </div>
                            <div class='row'>
                                <div class='col'>
                                    <button type='submit' formaction='addToonie'>Add Toonie</button>
                                </div>
                                <div class='col'>
                                    <button type='submit' formaction='addLoonie'>Add Loonie</button>
                                </div>
                            </div>
                            <div class='row'>
                                <div class='col'>
                                    <button type='submit' formaction='addQuater'>Add Quarter</button>
                                </div>
                                <div class='col'>
                                    <button type='submit' formaction='addDime'>Add Dime</button>
                                </div>
                            </div>
                            <div class='row'>
                                <div class='col'>
                                    <button type='submit' formaction='addNickle'>Add Nickel</button>
                                </div>
                                <div class='col'>
                                    <button type='submit' formaction='addPenny'>Add Penny</button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <hr/>
                    <div id='divPurchase'>
                        <h3>Messages</h3>
                        <form id='formPurchase' method='POST'>
                            <div class='row'>
                                <div class='col'>
                                    <input id='inputMessage' type='text' name='message' value='${message}' readonly>
                                </div>
                            </div>
                            <div class='row'>
                                <div class='col'>
                                    <label>Item:</label>
                                </div>
                                <div class='col'>
                                    <input id='inputItem' type='text' name='item' value='${itemNum}' readonly>
                                </div>
                            </div>
                            <div class='row'>
                                <div class='col'>
                                    <button type='submit' formaction='makePurchase'>Make Purchase</button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <hr/>
                    <div id='divChangeRecieve'>
                        <h3>Change</h3>
                        <form id='formChangeRecieved' method='POST'>
                            <div class='row'>
                                <input id='inputChangeRecieved' class='form-control marquee' type='text' name='changeRecieved' value='${change}' readonly>
                            </div>
                            <div class='row'>
                                <div class='col'>
                                    <button id='returnChangeButton' type='submit' formaction='returnChange'>Change Return</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>

