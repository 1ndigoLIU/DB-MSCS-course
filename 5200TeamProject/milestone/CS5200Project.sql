DROP SCHEMA IF EXISTS CS5200Project;
CREATE SCHEMA CS5200Project;
USE CS5200Project;

-- CREATE TABLE statements go here
CREATE TABLE Player (
    accountID INT AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    `status` VARCHAR(50) NOT NULL,
    CONSTRAINT pk_Player_accountID PRIMARY KEY (accountID)
);

CREATE TABLE `Character` (
    characterID INT AUTO_INCREMENT,
    accountID INT NOT NULL,
    firstName VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    CONSTRAINT pk_Character_characterID PRIMARY KEY (characterID),
    CONSTRAINT fk_Character_accountID FOREIGN KEY (accountID)
		REFERENCES Player(accountID)
        ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT uq_Character_firstName_lastName UNIQUE (firstName, lastName)
);

CREATE TABLE Job (
    jobID INT AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    CONSTRAINT pk_Job_jobID PRIMARY KEY (jobID)
);

CREATE TABLE CharacterJob (
    characterID INT,
    jobID INT,
    `level` INT NOT NULL,
    experiencePoint INT NOT NULL,
    CONSTRAINT pk_CharacterJob_characterID_jobID PRIMARY KEY (characterID, jobID),
    CONSTRAINT fk_CharacterJob_characterID FOREIGN KEY (characterID)
		REFERENCES `Character`(characterID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_CharacterJob_jobID FOREIGN KEY (jobID)
		REFERENCES Job(jobID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Currency (
    currencyID INT AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    maxAmount INT NOT NULL,
    weeklyCap INT,
    CONSTRAINT pk_Currency_currencyID PRIMARY KEY (currencyID)
);

CREATE TABLE CharacterCurrency (
    characterID INT,
    currencyID INT,
    amount INT NOT NULL DEFAULT 0,
    earnedWithinWeek INT DEFAULT 0,
    CONSTRAINT pk_CharacterCurrency_characterID_currencyID PRIMARY KEY (characterID, currencyID),
    CONSTRAINT fk_CharacterCurrency_characterID FOREIGN KEY (characterID)
		REFERENCES `Character`(characterID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_CharacterCurrency_currencyID FOREIGN KEY (currencyID)
		REFERENCES Currency(currencyID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE EquipmentSlotType (
    slotType VARCHAR(100),
	CONSTRAINT pk_EquipmentSlotType_slotType PRIMARY KEY (slotType)
);

CREATE TABLE Item (
    itemID INT AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    maxStackSize INT NOT NULL,
    soldable BOOLEAN NOT NULL,
    vendorPrice DECIMAL(10, 2) NOT NULL,
    CONSTRAINT pk_Item_itemID PRIMARY KEY (itemID)
);

CREATE TABLE Gear (
    itemID INT,
    itemLevel INT NOT NULL,
    slotType VARCHAR(100) NOT NULL,
    requiredLevel INT NOT NULL,
    defenseRating INT NOT NULL,
    magicDefenseRating INT NOT NULL,
    CONSTRAINT pk_Gear_itemID PRIMARY KEY (itemID),
    CONSTRAINT fk_Gear_itemID FOREIGN KEY (itemID)
		REFERENCES Item(itemID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_Gear_slotType FOREIGN KEY (slotType) 
		REFERENCES EquipmentSlotType(slotType)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Weapon (
    itemID INT,
    itemLevel INT NOT NULL,
    slotType VARCHAR(100) NOT NULL,
    requiredLevel INT NOT NULL,
    damage INT NOT NULL,
    autoAttack DECIMAL(5, 2) NOT NULL,
    attackDelay DECIMAL(5, 2) NOT NULL,
	CONSTRAINT pk_Weapon_itemID PRIMARY KEY (itemID),
    CONSTRAINT fk_Weapon_itemID FOREIGN KEY (itemID)
		REFERENCES Item(itemID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_Weapon_slotType FOREIGN KEY (slotType)
		REFERENCES EquipmentSlotType(slotType)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Consumable (
    itemID INT,
    itemLevel INT NOT NULL,
    `description` TEXT NOT NULL,
    CONSTRAINT pk_Consumable_itemID PRIMARY KEY (itemID),
    CONSTRAINT fk_Consumable_itemID FOREIGN KEY (itemID)
		REFERENCES Item(itemID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE InventorySlot (
    slotID INT AUTO_INCREMENT,
    characterID INT NOT NULL,
    itemID INT NOT NULL,
    slotNumber INT NOT NULL,
    itemCount INT NOT NULL,
    CONSTRAINT pk_InventorySlot_slotID PRIMARY KEY (slotID),
    CONSTRAINT fk_InventorySlot_characterID FOREIGN KEY (characterID)
		REFERENCES `Character`(characterID)
		ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_InventorySlot_itemID FOREIGN KEY (itemID)
		REFERENCES Item(itemID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT uq_InventorySlot_characterID_slotNumber UNIQUE (characterID, slotNumber)
);

CREATE TABLE ItemEquipableJob (
    itemID INT,
    jobID INT,
    CONSTRAINT pk_ItemEquipableJob_itemID_jobID PRIMARY KEY (itemID, jobID),
    CONSTRAINT fk_ItemEquipableJob_itemID FOREIGN KEY (itemID)
		REFERENCES Item(itemID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_ItemEquipableJob_jobID FOREIGN KEY (jobID)
		REFERENCES Job(jobID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Attribute (
    attrName VARCHAR(100),
    CONSTRAINT pk_Attribute_attrName PRIMARY KEY (attrName)
);

CREATE TABLE ItemAttributeBonus (
    itemID INT,
    attrName VARCHAR(100),
    bonus INT NOT NULL,
    consumable BOOLEAN NOT NULL,
    bonusCap INT,
    CONSTRAINT pk_ItemAttributeBonus_itemID_attrName PRIMARY KEY (itemID, attrName),
    CONSTRAINT fk_ItemAttributeBonus_itemID FOREIGN KEY (itemID)
		REFERENCES Item(itemID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_ItemAttributeBonus_attrName FOREIGN KEY (attrName)
		REFERENCES Attribute(attrName)
        ON DELETE CASCADE ON UPDATE CASCADE,
	CHECK ((consumable = TRUE AND bonusCap IS NOT NULL) OR (consumable = FALSE AND bonusCap IS NULL))
);

CREATE TABLE CharacterAttribute (
    characterID INT,
    attrName VARCHAR(100),
    `value` INT NOT NULL,
    CONSTRAINT pk_CharacterAttribute_characterID_attrName PRIMARY KEY (characterID, attrName),
    CONSTRAINT fk_CharacterAttribute_characterID FOREIGN KEY (characterID)
		REFERENCES `Character`(characterID)
        ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_CharacterAttribute_attrName FOREIGN KEY (attrName)
		REFERENCES Attribute(attrName)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE CharacterEquipment (
    characterID INT,
    slotType VARCHAR(100),
    itemID INT NOT NULL,
    CONSTRAINT pk_CharacterEquipment_characterID_slotType PRIMARY KEY (characterID, slotType),
    CONSTRAINT fk_CharacterEquipment_characterID FOREIGN KEY (characterID)
		REFERENCES `Character`(characterID)
		ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_CharacterEquipment_slotType FOREIGN KEY (slotType)
		REFERENCES EquipmentSlotType(slotType)
		ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_CharacterEquipment_itemID FOREIGN KEY (itemID)
		REFERENCES Item(itemID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- INSERT sample data statements go here
INSERT INTO Player (`name`, email, `status`) VALUES
    ('Alice', 'alice@example.com', 'active'),
    ('Bob', 'bob@example.com', 'active'),
    ('Charlie', 'charlie@example.com', 'inactive'),
    ('Dave', 'dave@example.com', 'inactive'),
    ('Eve', 'eve@example.com', 'active'),
    ('Emma', 'frank@example.com', 'inactive'),
    ('Grace', 'grace@example.com', 'active'),
    ('Heidi', 'heidi@example.com', 'inactive'),
    ('Ivan', 'ivan@example.com', 'active'),
    ('Judy', 'judy@example.com', 'active');

INSERT INTO `Character` (accountID, firstName, lastName) VALUES
    (1, 'Alice', 'Smith'),
    (2, 'Bob', 'Jones'),
    (3, 'Charlie', 'Brown'),
    (4, 'Dave', 'Johnson'),
    (5, 'Eve', 'Williams'),
    (5, 'Eve', 'Taylor'),
    (5, 'Eve', 'Davis'),
    (6, 'Emma', 'White'),
    (6, 'Emma', 'Harris'),
    (6, 'Emma', 'Walker'),
    (7, 'Grace', 'Clark'),
    (8, 'Heidi', 'Lewis'),
    (9, 'Ivan', 'Hall'),
    (10, 'Judy', 'Allen');

INSERT INTO Job (`name`) VALUES
    ('Paladin'),
    ('Warrior'),
    ('White Mage'),
    ('Hunter'),
    ('Ranger');

INSERT INTO CharacterJob (characterID, jobID, `level`, experiencePoint) VALUES
    (1, 1, 10, 5),
    (2, 2, 8, 12),
    (3, 3, 15, 20),
    (4, 4, 12, 18),
    (5, 5, 20, 25),
    (6, 1, 7, 10),
    (7, 3, 9, 15),
    (8, 2, 5, 8),
    (9, 4, 14, 22),
    (10, 5, 11, 17),
    (11, 1, 13, 19),
    (12, 2, 6, 9),
    (13, 3, 10, 14),
    (14, 4, 8, 13),
    (5, 1, 18, 30),
    (6, 2, 15, 28),
    (7, 2, 20, 40),
    (8, 4, 12, 20),
    (9, 5, 14, 22),
    (10, 1, 15, 25);

INSERT INTO Currency (`name`, maxAmount, weeklyCap) VALUES
    ('Gold', 2000, null),
    ('Silver', 2000, null),
    ('Bronze', 2000, null),
    ('Diamond', 2000, 450),
    ('Ruby', 20000, 1000),
    ('Platinum', 5000, 500),
    ('Emerald', 3000, 700),
    ('Sapphire', 4000, null),
    ('Obsidian', 6000, 800),
    ('Amethyst', 10000, 1200);
    
INSERT INTO CharacterCurrency (characterID, currencyID, amount, earnedWithinWeek) VALUES
    (1, 1, 500, 0),
    (2, 2, 800, 0),
    (3, 3, 1200, 0),
    (4, 4, 1500, 200),
    (5, 5, 1800, 500),
    (6, 1, 1000, 100),
    (7, 2, 700, 50),
    (8, 3, 900, 75),
    (9, 4, 1100, 150),
    (10, 5, 1200, 300),
    (8, 1, 800, 50),
    (8, 4, 600, 100),
    (9, 2, 1500, 300),
    (9, 5, 2000, 500),
    (10, 3, 1000, 200);

INSERT INTO EquipmentSlotType (slotType) VALUES
	('Main Hand'),
	('Head'),
	('Body'),
	('Hands'),
	('Legs'),
	('Feet');
    
INSERT INTO Item (`name`, maxStackSize, soldable, vendorPrice) VALUES
	-- Adding weapons
    ('Iron Sword', 1, true, 100),
    ('Steel Sword', 1, true, 200),
    ('Magic Wand', 1, true, 150),
    ('Battle Axe', 1, true, 250),
    ('Long Bow', 1, true, 180),
    ('Crossbow', 1, true, 220),
    ('Katana', 1, true, 300),
    ('War Hammer', 1, true, 280),
    ('Rapier', 1, true, 210),
    ('Great Sword', 1, true, 350),
    -- Adding gears
    ('Iron Helmet', 1, true, 80),
    ('Steel Helmet', 1, true, 120),
    ('Magic Hat', 1, true, 150),
    ('Leather Armor', 1, true, 200),
    ('Steel Armor', 1, true, 300),
    ('Mage Robe', 1, true, 250),
    ('Iron Gloves', 1, true, 60),
    ('Steel Gloves', 1, true, 90),
    ('Old Gloves', 1, true, 90),
    ('Leather Pants', 1, true, 110),
    ('Mage Pants', 1, true, 130),
    ('Old Pants', 1, true, 130),
    ('Leather Boots', 1, true, 70),
    ('Steel Boots', 1, true, 100),
    ('Mage Boots', 1, true, 120),
    -- Adding 10 consumable items
    ('Health Potion', 99, true, 50),
    ('Mana Potion', 99, true, 60),
    ('Stamina Elixir', 99, true, 80),
    ('Antidote', 99, true, 40),
    ('Strength Tonic', 99, true, 100),
    ('Defense Potion', 99, true, 90),
    ('Speed Boost', 99, true, 70),
    ('Magic Elixir', 99, true, 120),
    ('Fire Resistance Potion', 99, true, 75),
    ('Water Breathing Potion', 99, true, 110);
    
INSERT INTO Gear (itemID, itemLevel, slotType, requiredLevel, defenseRating, magicDefenseRating) VALUES
    (11, 5, 'Head', 3, 10, 5),
    (12, 10, 'Head', 5, 15, 8),
    (13, 15, 'Head', 7, 20, 12),
    (14, 8, 'Body', 4, 18, 6),
    (15, 20, 'Body', 10, 30, 15),
    (16, 25, 'Body', 12, 40, 20),
    (17, 6, 'Hands', 3, 8, 4),
    (18, 12, 'Hands', 6, 12, 6),
    (19, 18, 'Hands', 9, 20, 10),
    (20, 8, 'Legs', 4, 10, 5),
    (21, 14, 'Legs', 7, 18, 8),
    (22, 20, 'Legs', 10, 25, 12),
    (23, 5, 'Feet', 3, 6, 3),
    (24, 10, 'Feet', 5, 10, 5),
    (25, 15, 'Feet', 8, 15, 7);
    
INSERT INTO Weapon (itemID, itemLevel, slotType, requiredLevel, damage, autoAttack, attackDelay) VALUES
    (1, 10, 'Main Hand', 5, 25, 12.5, 2.0),
    (2, 15, 'Main Hand', 8, 35, 14.0, 2.5),
    (3, 12, 'Main Hand', 6, 28, 13.0, 1.8),
    (4, 20, 'Main Hand', 10, 40, 15.5, 3.0),
    (5, 18, 'Main Hand', 9, 32, 13.5, 2.2),
    (6, 22, 'Main Hand', 11, 38, 14.8, 2.4),
    (7, 25, 'Main Hand', 12, 45, 16.0, 2.8),
    (8, 23, 'Main Hand', 11, 42, 15.3, 3.1),
    (9, 17, 'Main Hand', 9, 30, 13.2, 2.0),
    (10, 28, 'Main Hand', 14, 50, 17.5, 3.5);
    
INSERT INTO Consumable (itemID, itemLevel, description) VALUES
    (26, 5, 'Restores 50 HP instantly'),
    (27, 5, 'Restores 50 MP instantly'),
    (28, 10, 'Restores stamina over 30 seconds'),
    (29, 3, 'Cures poison status'),
    (30, 8, 'Increases strength by 10% for 5 minutes'),
    (31, 7, 'Increases defense by 15% for 5 minutes'),
    (32, 6, 'Boosts speed by 20% for 3 minutes'),
    (33, 12, 'Restores 100 MP instantly'),
    (34, 9, 'Grants fire resistance for 10 minutes'),
    (35, 11, 'Allows breathing underwater for 5 minutes');

INSERT INTO InventorySlot (characterID, itemID, slotNumber, itemCount) VALUES
    -- Adding 1 item for each character
    (1, 1, 1, 1),    -- Alice: Iron Sword
    (2, 2, 1, 1),    -- Bob: Steel Sword
    (3, 3, 1, 1),    -- Charlie: Magic Wand
    (4, 4, 1, 1),    -- Dave: Battle Axe
    (5, 5, 1, 1),    -- Eve (1st character): Long Bow
    (6, 6, 1, 1),    -- Eve (2nd character): Crossbow
    (7, 7, 1, 1),    -- Eve (3rd character): Katana
    (8, 8, 1, 1),    -- Emma (1st character): War Hammer
    (9, 9, 1, 1),    -- Emma (2nd character): Rapier
    (10, 10, 1, 1),  -- Emma (3rd character): Great Sword
    (11, 11, 1, 1),  -- Grace: Iron Helmet
    (12, 12, 1, 1),  -- Heidi: Steel Helmet
    (13, 13, 1, 1),  -- Ivan: Magic Hat
    (14, 14, 1, 1),  -- Judy: Leather Armor
    -- Adding 5 items for each of Emma's characters
    (8, 15, 2, 3),   -- Emma (1st character): Steel Armor
    (8, 16, 3, 1),   -- Emma (1st character): Mage Robe
    (8, 27, 4, 5),   -- Emma (1st character): Mana Potion
    (8, 30, 5, 2),   -- Emma (1st character): Strength Tonic
    (8, 32, 6, 1),   -- Emma (1st character): Speed Boost
    (9, 17, 2, 2),   -- Emma (2nd character): Iron Gloves
    (9, 18, 3, 1),   -- Emma (2nd character): Steel Gloves
    (9, 29, 4, 4),   -- Emma (2nd character): Antidote
    (9, 31, 5, 1),   -- Emma (2nd character): Defense Potion
    (9, 33, 6, 3),   -- Emma (2nd character): Magic Elixir
    (10, 19, 2, 3),  -- Emma (3rd character): Leather Pants
    (10, 20, 3, 2),  -- Emma (3rd character): Mage Pants
    (10, 28, 4, 5),  -- Emma (3rd character): Stamina Elixir
    (10, 34, 5, 1),  -- Emma (3rd character): Fire Resistance Potion
    (10, 35, 6, 2);  -- Emma (3rd character): Water Breathing Potion

INSERT INTO ItemEquipableJob (itemID, jobID) VALUES
    (1, 1),   -- Iron Sword can be used by Paladin
    (2, 2),   -- Steel Sword can be used by Warrior
    (3, 3),   -- Magic Wand can be used by White Mage
    (5, 4),   -- Long Bow can be used by Samurai
    (6, 5),   -- Crossbow can be used by Monk
    (14, 1),  -- Leather Armor can be used by Paladin
    (15, 2),  -- Steel Armor can be used by Warrior
    (16, 3),  -- Mage Robe can be used by White Mage
    (17, 5),  -- Iron Gloves can be used by Monk
    (18, 4);  -- Steel Gloves can be used by Samurai

INSERT INTO Attribute (attrName) VALUES
	('Strength'),
	('Dexterity'),
	('Intelligence'),
	('Mind'),
	('Vitality');
    
INSERT INTO ItemAttributeBonus (itemID, attrName, bonus, consumable, bonusCap) VALUES
    (1, 'Strength', 5, FALSE, null),         -- Iron Sword increases Strength by 5
    (2, 'Vitality', 3, FALSE, null),         -- Steel Sword increases Vitality by 3
    (3, 'Intelligence', 8, FALSE, null),     -- Magic Wand increases Intelligence by 8
    (14, 'Strength', 4, FALSE, null),        -- Leather Armor increases Strength by 4
    (15, 'Vitality', 6, FALSE, null),        -- Steel Armor increases Vitality by 6
    (16, 'Mind', 10, FALSE, null),           -- Mage Robe increases Mind by 10
    (17, 'Dexterity', 3, FALSE, null),       -- Iron Gloves increase Dexterity by 3
    (18, 'Dexterity', 5, FALSE, null),       -- Steel Gloves increase Dexterity by 5

    -- Adding attributes for consumable items
    (26, 'Vitality', 15, TRUE, 10),          -- Health Potion increases Vitality by 15% temporarily
    (27, 'Mind', 20, TRUE, 18),              -- Mana Potion increases Mind by 20% temporarily
    (28, 'Vitality', 25, TRUE, 22),          -- Stamina Elixir increases Vitality by 25% temporarily
    (30, 'Strength', 10, TRUE, 15),          -- Strength Tonic increases Strength by 10% temporarily
    (31, 'Vitality', 18, TRUE, 20),          -- Defense Potion increases Vitality by 18% temporarily
    (32, 'Dexterity', 12, TRUE, 10),         -- Speed Boost increases Dexterity by 12% temporarily
    (34, 'Mind', 22, TRUE, 18),              -- Fire Resistance Potion increases Mind by 22% temporarily
    (35, 'Vitality', 20, TRUE, 25);          -- Water Breathing Potion increases Vitality by 20% temporarily

INSERT INTO CharacterAttribute (characterID, attrName, `value`) VALUES
    (1, 'Strength', 10),
    (2, 'Dexterity', 12),
    (3, 'Intelligence', 15),
    (4, 'Vitality', 18),
    (5, 'Mind', 20),
    (6, 'Strength', 13),
    (7, 'Dexterity', 11),
    (8, 'Strength', 16),
    (8, 'Dexterity', 14),
    (8, 'Intelligence', 18),
    (8, 'Mind', 12),
    (8, 'Vitality', 15),
    (9, 'Strength', 15),
    (9, 'Dexterity', 17),
    (9, 'Intelligence', 19),
    (9, 'Mind', 14),
    (9, 'Vitality', 18),
    (10, 'Strength', 14),
    (10, 'Dexterity', 16),
    (10, 'Intelligence', 20),
    (10, 'Mind', 13),
    (10, 'Vitality', 17),
    (11, 'Vitality', 9),
    (12, 'Mind', 10),
    (13, 'Dexterity', 8),
    (14, 'Strength', 11);

INSERT INTO CharacterEquipment (characterID, slotType, itemID) VALUES
    (1, 'Main Hand', 1),
    (2, 'Main Hand', 2),
    (3, 'Main Hand', 3),
    (4, 'Main Hand', 4),
    (5, 'Main Hand', 5),
    (6, 'Main Hand', 6),
    (7, 'Main Hand', 7),
    (8, 'Main Hand', 8),
    (8, 'Head', 11),
    (8, 'Body', 14),
    (8, 'Hands', 17),
    (8, 'Legs', 20),
    (8, 'Feet', 23),
    (9, 'Main Hand', 9),
    (9, 'Head', 12),
    (9, 'Body', 15),
    (9, 'Hands', 18),
    (9, 'Legs', 21),
    (9, 'Feet', 24),
    (10, 'Main Hand', 10),
    (10, 'Head', 13),
    (10, 'Body', 16),
    (10, 'Hands', 17),
    (10, 'Legs', 22),
    (10, 'Feet', 25);
