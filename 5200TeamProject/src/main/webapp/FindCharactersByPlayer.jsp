    <%--
      Created by IntelliJ IDEA.
      User: Xihao Liu
      Date: 2024/12/1
      Time: 13:13
      To change this template use File | Settings | File Templates.
    --%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

    <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
             pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Game Characters</title>
    </head>
    <body>
    <h1>Game Character Information</h1>
    <form action="characters" method="post">
    <table border="1">
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Equipments</th>
            <th>Attributes</th>
            <th>Inventory</th>
        </tr>
        <c:forEach items="${characters}" var="character">
            <tr>
                <td><c:out value="${character.getFirstName()}" /></td>
                <td><c:out value="${character.getLastName()}" /></td>
                <td>
                    <!-- View Equipments Action Button -->
                    <form action="characters" method="post">
                        <input type="hidden" name="action" value="viewEquipments">
                        <input type="hidden" name="id" value="${character.getPlayer().getAccountId()}" />
                        <input type="hidden" name="characterId" value="${character.getCharacterID()}" />
                        <input type="submit" name="action" value="View Equipments" />
                    </form>
                </td>
                <td>
                    <!-- View Attributes Action Button -->
                    <form action="characters" method="post">
                        <input type="hidden" name="action" value="viewAttributes">
                        <input type="hidden" name="id" value="${character.getPlayer().getAccountId()}" />
                        <input type="hidden" name="characterId" value="${character.getCharacterID()}" />
                        <input type="submit" name="action" value="View Attributes" />
                    </form>
                </td>
                <td>
                    <!-- View Inventory Action Button -->
                    <form action="characters" method="post">
                        <input type="hidden" name="action" value="viewInventory">
                        <input type="hidden" name="id" value="${character.getPlayer().getAccountId()}" />
                        <input type="hidden" name="characterId" value="${character.getCharacterID()}" />
                        <input type="submit" name="action" value="View Inventory" />
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    </form>

    <br/><br/>
    <c:if test="${not empty selectedCharacter and action eq 'viewEquipments'}">
        <h2>Equipment Details for Character: <c:out value="${selectedCharacter.getFirstName()}" /> <c:out value="${selectedCharacter.getLastName()}" /></h2>
        <c:if test="${not empty weapon}">
        <h3>Equipment Weapon:</h3>
        <table border="1">
            <tr>
                <th>Weapon Name</th>
                <th>Slot Type</th>
                <th>Saleable</th>
                <th>Vendor Price</th>
                <th>Item Level</th>
                <th>Required Level</th>
                <th>Auto Attack</th>
                <th>Attack Delay</th>
            </tr>
            <tr>
                <td><c:out value="${weapon.getName()}" /></td>
                <td><c:out value="${weapon.getSlotType()}" /></td>
                <td><c:out value="${weapon.getSoldable()}" /></td>
                <td><c:out value="${weapon.getVendorPrice()}" /></td>
                <td><c:out value="${weapon.getItemLevel()}" /></td>
                <td><c:out value="${weapon.getRequiredLevel()}" /></td>
                <td><c:out value="${weapon.getAutoAttack()}" /></td>
                <td><c:out value="${weapon.getAttackDelay()}" /></td>
            </tr>
        </table>
        </c:if>
        <h3>Equipment Gears:</h3>
        <table border="1">
            <tr>
                <th>Gear Name</th>
                <th>Slot Type</th>
                <th>Saleable</th>
                <th>Vendor Price</th>
                <th>Item Level</th>
                <th>Required Level</th>
                <th>Physical Defense</th>
                <th>Magic Defense</th>
            </tr>
            <c:forEach items="${gears}" var="gear">
                <tr>
                    <td><c:out value="${gear.getName()}" /></td>
                    <td><c:out value="${gear.getSlotType()}" /></td>
                    <td><c:out value="${gear.getSoldable()}" /></td>
                    <td><c:out value="${gear.getVendorPrice()}" /></td>
                    <td><c:out value="${gear.getItemLevel()}" /></td>
                    <td><c:out value="${gear.getRequiredLevel()}" /></td>
                    <td><c:out value="${gear.getDefenseRating()}" /></td>
                    <td><c:out value="${gear.getMagicDefenseRating()}" /></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <c:if test="${not empty selectedCharacter and action eq 'viewAttributes'}">
        <h2>Attributes Details for Character: <c:out value="${selectedCharacter.getFirstName()}" /> <c:out value="${selectedCharacter.getLastName()}" /></h2>
        <table border="1">
            <tr>
                <th>Attribute Name</th>
                <th>Value</th>
            </tr>
            <c:forEach items="${selectedAttributes}" var="attr">
                <tr>
                    <td><c:out value="${attr.getAttrName()}" /></td>
                    <td><c:out value="${attr.getValue()}" /></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <c:if test="${not empty selectedCharacter and action eq 'viewInventory'}">
        <h2>Weapons in Inventory of Character: <c:out value="${selectedCharacter.getFirstName()}" /> <c:out value="${selectedCharacter.getLastName()}" /></h2>
        <table border="1">
            <tr>
                <th>Slot Number</th>
                <th>Weapon Name</th>
                <th>Count</th>
                <th>Max Stack Size</th>
                <th>Saleable</th>
                <th>Vendor Price</th>
                <th>Equip Slot</th>
                <th>Item Level</th>
                <th>Required Level</th>
                <th>Auto Attack</th>
                <th>Attack Delay</th>
            </tr>
            <c:forEach items="${inventoryWeapons}" var="weapon">
                <tr>
                    <td><c:out value="${inventorySlots[weapon.key].getSlotNumber()}" /></td>
                    <td><c:out value="${inventoryWeapons[weapon.key].getName()}" /></td>
                    <td><c:out value="${inventorySlots[weapon.key].getItemCount()}" /></td>
                    <td><c:out value="${inventoryWeapons[weapon.key].getMaxStackSize()}" /></td>
                    <td><c:out value="${inventoryWeapons[weapon.key].getSoldable()}" /></td>
                    <td><c:out value="${inventoryWeapons[weapon.key].getVendorPrice()}" /></td>
                    <td><c:out value="${inventoryWeapons[weapon.key].getSlotType()}" /></td>
                    <td><c:out value="${inventoryWeapons[weapon.key].getItemLevel()}" /></td>
                    <td><c:out value="${inventoryWeapons[weapon.key].getRequiredLevel()}" /></td>
                    <td><c:out value="${inventoryWeapons[weapon.key].getAutoAttack()}" /></td>
                    <td><c:out value="${inventoryWeapons[weapon.key].getAttackDelay()}" /></td>
                </tr>
            </c:forEach>
        </table>

        <h2>Gears in Inventory of Character: <c:out value="${selectedCharacter.getFirstName()}" /> <c:out value="${selectedCharacter.getLastName()}" /></h2>
        <table border="1">
            <tr>
                <th>Slot Number</th>
                <th>Gear Name</th>
                <th>Count</th>
                <th>Max Stack Size</th>
                <th>Saleable</th>
                <th>Vendor Price</th>
                <th>Equip Slot</th>
                <th>Item Level</th>
                <th>Required Level</th>
                <th>Physical Defense</th>
                <th>Magic Defense</th>
            </tr>
            <c:forEach items="${inventoryGears}" var="gear">
                <tr>
                    <td><c:out value="${inventorySlots[gear.key].getSlotNumber()}" /></td>
                    <td><c:out value="${inventoryGears[gear.key].getName()}" /></td>
                    <td><c:out value="${inventorySlots[gear.key].getItemCount()}" /></td>
                    <td><c:out value="${inventoryGears[gear.key].getMaxStackSize()}" /></td>
                    <td><c:out value="${inventoryGears[gear.key].getSoldable()}" /></td>
                    <td><c:out value="${inventoryGears[gear.key].getVendorPrice()}" /></td>
                    <td><c:out value="${inventoryGears[gear.key].getSlotType()}" /></td>
                    <td><c:out value="${inventoryGears[gear.key].getItemLevel()}" /></td>
                    <td><c:out value="${inventoryGears[gear.key].getRequiredLevel()}" /></td>
                    <td><c:out value="${inventoryGears[gear.key].getDefenseRating()}" /></td>
                    <td><c:out value="${inventoryGears[gear.key].getMagicDefenseRating()}" /></td>
                </tr>
            </c:forEach>
        </table>

        <h2>Consumables in Inventory of Character: <c:out value="${selectedCharacter.getFirstName()}" /> <c:out value="${selectedCharacter.getLastName()}" /></h2>
        <table border="1">
            <tr>
                <th>Slot Number</th>
                <th>Consumable Name</th>
                <th>Count</th>
                <th>Max Stack Size</th>
                <th>Saleable</th>
                <th>Vendor Price</th>
                <th>Item Level</th>
                <th>Description</th>
                <th>Update Count</th>
            </tr>
            <c:forEach items="${inventoryConsumables}" var="item">
                <tr>
                    <td><c:out value="${inventorySlots[item.key].getSlotNumber()}" /></td>
                    <td><c:out value="${inventoryConsumables[item.key].getName()}" /></td>
                    <td><c:out value="${inventorySlots[item.key].getItemCount()}" /></td>
                    <td><c:out value="${inventoryConsumables[item.key].getMaxStackSize()}" /></td>
                    <td><c:out value="${inventoryConsumables[item.key].getSoldable()}" /></td>
                    <td><c:out value="${inventoryConsumables[item.key].getVendorPrice()}" /></td>
                    <td><c:out value="${inventoryConsumables[item.key].getItemLevel()}" /></td>
                    <td><c:out value="${inventoryConsumables[item.key].getDescription()}" /></td>
                    <!-- Adding a form for updating the item count -->
                    <td>
                        <form action="characters" method="post">
                            <input type="hidden" name="action" value="updateItemCount">
                            <input type="hidden" name="id" value="${selectedCharacter.getPlayer().getAccountId()}" />
                            <input type="hidden" name="inventorySlotID" value="${inventorySlots[item.key].getSlotID()}" />
                            <input type="number" name="newCount" min="0" value="${inventorySlots[item.key].getItemCount()}" />
                            <input type="submit" name="action" value="Update Count" />
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>


    <c:if test="${action eq 'updateItemCount'}">
        <!-- Display success message -->
        <c:if test="${not empty messages.updateSuccess}">
            <div style="color: green;">
                <c:out value="${messages.updateSuccess}" />
            </div>
        </c:if>

        <!-- Display error message -->
        <c:if test="${not empty messages.error}">
            <div style="color: red;">
                <c:out value="${messages.error}" />
            </div>
        </c:if>
    </c:if>
    </body>
    </html>
