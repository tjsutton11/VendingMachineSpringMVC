<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Vending Machine Spring MVC</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">      
    </head>
    <body>
        <div class="container">
            <h1>Vending Machine</h1>
            <hr/>

            <div class="row">
                <div id="snacksDiv" class="col-md-6">
                    <c:set var="counter" value="0"></c:set>
                    <c:forEach var="currentSnack" items="${snackList}">
                        
                        <c:if test="${counter % 3 == 0}">
                            <div class='row'>
                        </c:if>
                                
                        <form id="${currentSnack.itemId}" method="POST" action="getItemId">
                            <input type="hidden" name="num" value="${currentSnack.itemId}">        
                        </form>
                                
                        <div class="snack card col-md-3" id="snack[snackId]">
                            <div class="card-body" onclick='document.getElementById(${currentSnack.itemId}).submit()'>
                                <p class="left"><c:out value="${currentSnack.itemId}"/></p>
                                <h5 class="card-title"><c:out value="${currentSnack.name}"/></h5>
                                <h4>$<c:out value="${currentSnack.price}"/></h4>
                                <p>Quantity: <c:out value="${currentSnack.inventory}"/></p>
                            </div>
                        </div>
                            
                        <c:if test="${counter % 3 == 2}">
                            </div>
                        </c:if>
                            
                        <c:set var="counter" value="${counter + 1}"></c:set>
                    </c:forEach>
                </div>

                <div id="interfaceDiv" class="col-md-6">

                    <div id="moneyDiv">
                      <h3>Total $ Added</h3>
                       <form id="userAddMoneyForm" method="POST">
                          <input type="text" id="moneyIn" value='$${userBalance}' readonly>
                      
                          <div class="row">
                            <button id="dollarButton" type="submit" formaction="addDollar">Add Dollar</button>
                            <button id="quarterButton" type="submit" formaction="addQuarter">Add Quarter</button>
                          </div>
                          <div class="row">
                            <button id="dimeButton" type="submit" formaction="addDime">Add Dime</button>
                            <button id="nickelButton" type="submit" formaction="addNickel">Add Nickel</button>
                          </div>
                      </form>
                      <hr>
                    </div>

                <div id="itemDiv">
                    <h3>Messages</h3> 
                    <form id="makePurchaseForm" class="fom-horizontal" role="form" method="POST">
                        <div class="form-group">

                            <div class="row">
                                <label for="messagesDisplay" class="control-label col-md-2">Messages</label>
                                <input type="text" id="messagesDisplay" value="${messageBox}" class="col-md-12" readonly>
                            </div>

                            <div class="row">
                                <label for="itemChoice" class="control-label col-md-2">Item: </label>
                                <input type="number" id="itemChoice" class="col-md-4" value='${itemId}' readonly>
                            </div>
                            <div class="row">
                                <button type="submit" id="purchaseButton" formaction="purchaseItem">Make Purchase</button>
                            </div>
                        </div>
                    </form>
                    <hr>
                </div>

                    <div id="changeDiv">
                      <h3>Change</h3>
                      <form method="POST" id="changeReturnForm">
                          <div class="row">
                            <input type="text" id="changeDisplay" value="${changeDisplay}" class="col-md-12" readonly>
                          </div>
                          <div class="row">
                              <button type="submit" id="changeButton" formaction="changeReturn">Change Return</button>
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

