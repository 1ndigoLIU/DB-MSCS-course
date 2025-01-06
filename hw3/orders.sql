DROP SCHEMA IF EXISTS HW3_Orders;
CREATE SCHEMA HW3_Orders;
USE HW3_Orders;

-- CREATE TABLE statements go here
CREATE TABLE StateCodes (
	stateCode VARCHAR(2),
	CONSTRAINT pk_StateCodes_stateCode PRIMARY KEY (stateCode)
);

CREATE TABLE Item (
	itemNumber INT AUTO_INCREMENT,
	`description` VARCHAR(255) NOT NULL,
	unitCost DECIMAL(65, 2) NOT NULL,
	taxable BOOL NOT NULL,
	CONSTRAINT pk_Item_itemNumber PRIMARY KEY (itemNumber)
);

CREATE TABLE Customer (
	customerID INT AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	streetAddress VARCHAR(255) NOT NULL,
	city VARCHAR(255) NOT NULL,
	stateCode VARCHAR(2) NOT NULL,
	zip VARCHAR(5) NOT NULL,
	CONSTRAINT pk_Customer_customerID PRIMARY KEY (customerID),
	CONSTRAINT fk_Customer_stateCode FOREIGN KEY (stateCode)
		REFERENCES StateCodes(statecode)
		ON UPDATE CASCADE
);

CREATE TABLE `Order` (
	orderNumber INT AUTO_INCREMENT,
	customerID INT NOT NULL,
	orderDate TIMESTAMP NOT NULL,
	customerMessage VARCHAR(255),
	taxRate	DECIMAL(5,4) NOT NULL,
	totalCost DECIMAL(65, 2) NOT NULL,
	CONSTRAINT pk_Order_orderNumber PRIMARY KEY (orderNumber),
	CONSTRAINT fk_Order_customerID FOREIGN KEY (customerID)
		REFERENCES Customer(customerID)
		ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE OrderItem (
	orderNumber INT,
	itemNumber INT,
	quantity INT NOT NULL,
	totalCost DECIMAL(65, 2) NOT NULL,
	CONSTRAINT pk_OrderItem_orderNumber_itemNumber PRIMARY KEY (orderNumber, itemNumber),
	CONSTRAINT fk_OrderItem_orderNumber FOREIGN KEY (orderNumber)
		REFERENCES `Order`(orderNumber)
		ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_OrderItem_itemNumber FOREIGN KEY (itemNumber)
		REFERENCES Item(itemNumber)
		ON UPDATE CASCADE ON DELETE CASCADE
);

-- Problem 1:
INSERT INTO StateCodes (stateCode)
	VALUES ('IL');
INSERT INTO Customer (`name`, streetAddress, city, stateCode, zip) 
	VALUES ('Robert Mitchell', '123 Main St', 'Anytown', 'IL', '51236');

-- Add extra records for testing queries
INSERT INTO `hw3_orders`.`statecodes` (`stateCode`) VALUES ('WA');
INSERT INTO `hw3_orders`.`customer` (`customerID`, `name`, `streetAddress`, `city`, `stateCode`, `zip`) VALUES ('2', 'Xihao Liu', 'addreess2', 'SEA', 'WA', '98133');
INSERT INTO `hw3_orders`.`customer` (`customerID`, `name`, `streetAddress`, `city`, `stateCode`, `zip`) VALUES ('3', 'Cong Ming', 'add3', 'SEA', 'WA', '98188');
INSERT INTO `hw3_orders`.`item` (`itemNumber`, `description`, `unitCost`, `taxable`) VALUES ('1', 'a', '1', FALSE);
INSERT INTO `hw3_orders`.`item` (`itemNumber`, `description`, `unitCost`, `taxable`) VALUES ('2', '1', '2', FALSE);
INSERT INTO `hw3_orders`.`item` (`itemNumber`, `description`, `unitCost`, `taxable`) VALUES ('3', 'c', '3', FALSE);
INSERT INTO `hw3_orders`.`item` (`itemNumber`, `description`, `unitCost`, `taxable`) VALUES ('4', 'd', '4', FALSE);
INSERT INTO `hw3_orders`.`item` (`itemNumber`, `description`, `unitCost`, `taxable`) VALUES ('5', 'e', '5', FALSE);
INSERT INTO `hw3_orders`.`item` (`itemNumber`, `description`, `unitCost`, `taxable`) VALUES ('6', 'f', '6', FALSE);
INSERT INTO `hw3_orders`.`order` (`orderNumber`, `customerID`, `orderDate`, `taxRate`, `totalCost`) VALUES ('1', '1', '2024-10-15 08:34:12', '0.1', '3');
INSERT INTO `hw3_orders`.`order` (`orderNumber`, `customerID`, `orderDate`, `taxRate`, `totalCost`) VALUES ('2', '2', '2023-12-07 19:21:45', '0.1', '7');
INSERT INTO `hw3_orders`.`order` (`orderNumber`, `customerID`, `orderDate`, `taxRate`, `totalCost`) VALUES ('3', '1', '2025-01-23 03:47:59', '0.1', '1');
INSERT INTO `hw3_orders`.`order` (`orderNumber`, `customerID`, `orderDate`, `taxRate`, `totalCost`) VALUES ('4', '1', '2023-11-12 14:53:33', '0.1', '1');
INSERT INTO `hw3_orders`.`order` (`orderNumber`, `customerID`, `orderDate`, `taxRate`, `totalCost`) VALUES ('5', '3', '2024-02-28 23:10:07', '0.1', '3');
INSERT INTO `hw3_orders`.`orderitem` (`orderNumber`, `itemNumber`, `quantity`, `totalCost`) VALUES ('1', '1', '1', '1');
INSERT INTO `hw3_orders`.`orderitem` (`orderNumber`, `itemNumber`, `quantity`, `totalCost`) VALUES ('1', '2', '1', '2');
INSERT INTO `hw3_orders`.`orderitem` (`orderNumber`, `itemNumber`, `quantity`, `totalCost`) VALUES ('2', '1', '1', '1');
INSERT INTO `hw3_orders`.`orderitem` (`orderNumber`, `itemNumber`, `quantity`, `totalCost`) VALUES ('2', '3', '1', '3');
INSERT INTO `hw3_orders`.`orderitem` (`orderNumber`, `itemNumber`, `quantity`, `totalCost`) VALUES ('2', '6', '1', '3');
INSERT INTO `hw3_orders`.`orderitem` (`orderNumber`, `itemNumber`, `quantity`, `totalCost`) VALUES ('3', '1', '1', '1');
INSERT INTO `hw3_orders`.`orderitem` (`orderNumber`, `itemNumber`, `quantity`, `totalCost`) VALUES ('4', '1', '1', '1');
INSERT INTO `hw3_orders`.`orderitem` (`orderNumber`, `itemNumber`, `quantity`, `totalCost`) VALUES ('5', '3', '1', '3');
    
-- Problem 2:
SELECT orderNumber, `name` AS customerName, city, stateCode AS state, zip AS zipCode
	FROM `Order` INNER JOIN Customer USING (customerID)
    ORDER BY orderDate;

-- Problem 3:
SELECT customerID, `name` AS customerName, SUM(totalCost) AS totalAmountSpent
	FROM `Order` RIGHT OUTER JOIN Customer USING (customerID)
	GROUP BY customerID;

-- Problem 4:
SELECT itemNumber, COUNT(DISTINCT customerID) AS customerNumber
	FROM Customer INNER JOIN `Order` USING (customerID)
		INNER JOIN OrderItem USING (orderNumber)
	GROUP BY itemNumber;

-- Problem 5:
SELECT zip AS zipCode, stateCode AS state, SUM(totalCost) AS totalOrderValue
	FROM Customer INNER JOIN `Order` USING (customerID)
    GROUP BY zip, stateCode;
