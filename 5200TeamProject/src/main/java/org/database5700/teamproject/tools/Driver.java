package org.database5700.teamproject.tools;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import org.database5700.teamproject.dal.*;
import org.database5700.teamproject.model.*;
import org.database5700.teamproject.model.Character;


public class Driver {

  public static void main(String[] args) throws SQLException {

    // DAO instances.
    PlayerDao playerDao = PlayerDao.getInstance();
    CharacterDao characterDao = CharacterDao.getInstance();
    ItemDao itemDao = ItemDao.getInstance();
    CharacterEquipmentDao characterEquipmentDao = CharacterEquipmentDao.getInstance();
    GearDao gearDao = GearDao.getInstance();
    WeaponDao weaponDao = WeaponDao.getInstance();
    ConsumableDao consumableDao = ConsumableDao.getInstance();
    JobDao jobDao = JobDao.getInstance();
    ItemEquipableJobDao itemEquipableJobDao = ItemEquipableJobDao.getInstance();
    CharacterJobDao characterJobDao = CharacterJobDao.getInstance();
    InventorySlotDao inventorySlotDao = InventorySlotDao.getInstance();
    ItemAttributeBonusDao itemAttributeBonusDao = ItemAttributeBonusDao.getInstance();
    CurrencyDao currencyDao = CurrencyDao.getInstance();
    CharacterCurrencyDao characterCurrencyDao = CharacterCurrencyDao.getInstance();
    CharacterAttributeDao characterAttributeDao = CharacterAttributeDao.getInstance();

    // INSERT objects from our model.
    Player player1 = new Player("Alice", "alice@gmail.com", "active");
    player1 = playerDao.create(player1);
    Player player2 = new Player("Bob", "bob@gmail.com", "active");
    player2 = playerDao.create(player2);
    Player player3 = new Player("Charlie", "charlie@gmail.com", "active");
    player3 = playerDao.create(player3);
    Player player4 = new Player("Doge", "Doge@gmail.com", "active");
    player4 = playerDao.create(player4);
    Character character1 = new Character(player1, "Alice1", "Hero1");
    character1 = characterDao.create(character1);
    Character character2 = new Character(player1, "Alice1", "Hero2");
    character2 = characterDao.create(character2);
    Character character3 = new Character(player2, "Bob1", "Hero1");
    character3 = characterDao.create(character3);
    Character character4 = new Character(player2, "Bob1", "Hero2");
    character4 = characterDao.create(character4);
    Character character5 = new Character(player3, "Charlie1", "Hero1");
    character5 = characterDao.create(character5);
    Character character6 = new Character(player3, "Charlie2", "Hero2");
    character6 = characterDao.create(character6);
    Item item1 = new Item("Dragon Scale Armor", 1, true, BigDecimal.valueOf(11.22));
    item1 = itemDao.create(item1);
    Item item2 = new Item("ShadowCloak", 2, true, BigDecimal.valueOf(11.33));
    item2 = itemDao.create(item2);
    Item item3 = new Item("Crystal Helm", 10, false, BigDecimal.valueOf(22.22));
    item3 = itemDao.create(item3);
    Item item4 = new Item("Arrow", 10, false, BigDecimal.valueOf(44.22));
    item4 = itemDao.create(item4);

    Gear gear1 = new Gear("Cool Armor", 1, true, BigDecimal.valueOf(150.00), 5, "Body", 10, 50, 30);
    gear1 = gearDao.create(gear1);
    System.out.println("Created Gear: " + gear1);

    Weapon weapon1 = new Weapon("Excalibur", 1, true, BigDecimal.valueOf(300.00), 10, "Main Hand",
        15, 100, BigDecimal.valueOf(1.5), BigDecimal.valueOf(0.8));
    weapon1 = weaponDao.create(weapon1);
    System.out.println("Created Weapon: " + weapon1);

    Consumable consumable1 = new Consumable("consumable1", 100, true, BigDecimal.valueOf(30.00), 5,
        "this is consumable1");
    consumable1 = consumableDao.create(consumable1);
    ItemAttributeBonus itemAttributeBonus1 = new ItemAttributeBonus(gear1, "Strength", 10, false,
        null);
    itemAttributeBonus1 = itemAttributeBonusDao.create(itemAttributeBonus1);
    ItemAttributeBonus itemAttributeBonus2 = new ItemAttributeBonus(weapon1, "Strength", 7, false,
        null);
    itemAttributeBonus2 = itemAttributeBonusDao.create(itemAttributeBonus2);
    ItemAttributeBonus itemAttributeBonus3 = new ItemAttributeBonus(weapon1, "Vitality", 6, false,
        null);
    itemAttributeBonus3 = itemAttributeBonusDao.create(itemAttributeBonus3);

    Job job1 = new Job(0, "Warrior");
    Job job2 = new Job(0, "Mage");
    job1 = jobDao.create(job1);
    job2 = jobDao.create(job2);
    ItemEquipableJob equipableJob1 = new ItemEquipableJob(item1, job1);
    ItemEquipableJob equipableJob2 = new ItemEquipableJob(item2, job2);
    equipableJob1 = itemEquipableJobDao.create(equipableJob1);
    equipableJob2 = itemEquipableJobDao.create(equipableJob2);
    CharacterJob characterJob1 = new CharacterJob(character1, job1, 10, 1000);
    CharacterJob characterJob2 = new CharacterJob(character2, job2, 5, 500);
    characterJob1 = characterJobDao.create(characterJob1);
    characterJob2 = characterJobDao.create(characterJob2);

    InventorySlot inventorySlot1 = new InventorySlot(character1, consumable1, 10, 50);
    inventorySlot1 = inventorySlotDao.create(inventorySlot1);
    //Equip weapon and gear
    CharacterEquipment ce1 = new CharacterEquipment(character1, gear1.getSlotType(), gear1);
    ce1 = characterEquipmentDao.create(ce1);
    System.out.println("Equipped Gear to Character: " + ce1);
    CharacterEquipment ce2 = new CharacterEquipment(character1, weapon1.getSlotType(), weapon1);
    ce2 = characterEquipmentDao.create(ce2);
    System.out.println("Equipped Weapon to Character: " + ce2);
    // create records to Currency, CharacterCurrency, and CharacterAttribute.
    Currency currency1 = new Currency("Currency1", 1000, null);
    currency1 = currencyDao.create(currency1);
    Currency currency2 = new Currency("Currency2", 2000, 500);
    currency2 = currencyDao.create(currency2);
    Currency currency3 = new Currency("Currency3", 3000, 1000);
    currency3 = currencyDao.create(currency3);
    CharacterCurrency characterCurrency1 = new CharacterCurrency(character1, currency1);
    characterCurrency1 = characterCurrencyDao.create(characterCurrency1);
    CharacterCurrency characterCurrency2 = new CharacterCurrency(character2, currency2, 100, 20);
    characterCurrency2 = characterCurrencyDao.create(characterCurrency2);
    CharacterCurrency characterCurrency3 = new CharacterCurrency(character3, currency3, 2000, 100);
    characterCurrency3 = characterCurrencyDao.create(characterCurrency3);
    CharacterAttribute characterAttribute1 = new CharacterAttribute(character1, "Strength", 10);
    characterAttribute1 = characterAttributeDao.create(characterAttribute1);
    CharacterAttribute characterAttribute2 = new CharacterAttribute(character2, "Dexterity", 20);
    characterAttribute2 = characterAttributeDao.create(characterAttribute2);
    CharacterAttribute characterAttribute3 = new CharacterAttribute(character3, "Intelligence", 30);
    characterAttribute3 = characterAttributeDao.create(characterAttribute3);

    // READ.
    // Read CharacterEquipment by characterID and itemID
    CharacterEquipment readCE1 = characterEquipmentDao.getCharacterEquipmentByCharacterAndSlotType(
        ce1.getCharacter(), ce1.getSlotType());
    System.out.println("Reading CharacterEquipment by CharacterID and ItemID: " + readCE1);
    // Read all CharacterEquipments of Character1
    List<CharacterEquipment> ceList = characterEquipmentDao.getCharacterEquipmentByCharacter(
        character1);
    System.out.println("All Equipments of Character1:");
    for (CharacterEquipment ce : ceList) {
      System.out.println(ce);
    }
    // get Gear
    Gear resultGear = gearDao.getGearByID(5);
    System.out.println("Reading Gear:" + resultGear);
    //get Weapon
    Weapon resultWeapon = weaponDao.getWeaponByID(6);
    System.out.println("Reading Weapon: " + resultWeapon);

    Player readPlayer1 = playerDao.getPlayerByID(1);
    System.out.println("Reading Player: " + readPlayer1);
    List<Player> readPlayerList = playerDao.getPlayerByStatus("active");
    for (Player player : readPlayerList) {
      System.out.println("Looping player: " + player);
    }
    Character readCharacter = characterDao.getCharacterByID(1);
    System.out.println("Reading Character: " + readCharacter);
    List<Character> readCharacterList = characterDao.getCharacterByPlayer(player1);
    for (Character character : readCharacterList) {
      System.out.println("Looping character: " + character);
    }
    Item readItem = itemDao.getItemByID(1);
    System.out.println("Reading item: " + readItem);
    // get ItemAttributeBonus
    ItemAttributeBonus read_ItemAttributeBonus1 = itemAttributeBonusDao.getItemAttributeBonusByItemIDAndAttrName(
        gear1, "Strength");
    List<ItemAttributeBonus> read_ItemAttributeBonusList = itemAttributeBonusDao.getItemAttributeBonusesByItem(
        weapon1);
    System.out.println("Reading ItemAttributeBonus: " + read_ItemAttributeBonus1.toString());
    for (ItemAttributeBonus itemAttributeBonus : read_ItemAttributeBonusList) {
      System.out.println("Looping ItemAttributeBonusList: " + itemAttributeBonus.toString());
    }
    // get Consumable
    Consumable read_consumable1 = consumableDao.getConsumableByID(consumable1.getItemID());
    System.out.print("Reading Consumable: " + read_consumable1.toString() + "\n");
    // Get Job by ID
    Job fetchedJob = jobDao.getJobByID(job1.getJobID());
    System.out.println("Fetched Job: " + fetchedJob.getName());
    // Get ItemEquipableJob
    ItemEquipableJob fetchedEquipableJob = itemEquipableJobDao.getItemEquipableJobByItemAndJob(
        equipableJob1.getItem(), equipableJob1.getJob());
    System.out.println(
        "Fetched ItemEquipableJob: " + fetchedEquipableJob.getItem().getName() + " -> "
            + fetchedEquipableJob.getJob().getName());
    // Get CharacterJob
    CharacterJob fetchedCharacterJob = characterJobDao.getCharacterJobByCharacterAndJob(
        characterJob1.getCharacter(), characterJob1.getJob());
    System.out.println(
        "Fetched CharacterJob: " + fetchedCharacterJob.getCharacter().getFirstName() + " -> "
            + fetchedCharacterJob.getJob().getName() + " (Level: " + fetchedCharacterJob.getLevel()
            + ", XP: " + fetchedCharacterJob.getExperiencePoint() + ")");
    // Get InventorySlot
    InventorySlot read_InventorySlot1 = inventorySlotDao.getInventorySlotByID(
        inventorySlot1.getSlotID());
    System.out.println("Reading InventorySlot: " + read_InventorySlot1.toString());
    // read records from Currency, CharacterCurrency, and CharacterAttribute.
    Currency read_currency1 = currencyDao.getCurrencyByID(currency1.getCurrentcyID());
    System.out.print("Reading Currency: " + read_currency1.toString() + "\n");
    CharacterCurrency read_characterCurrency1 = characterCurrencyDao.getCharacterCurrencyByCharacterAndCurrency(
        characterCurrency1.getCharacter(), characterCurrency1.getCurrency());
    System.out.print("Reading CharacterCurrency: " + read_characterCurrency1.toString() + "\n");
    CharacterAttribute read_characterAttribute1 = characterAttributeDao.getCharacterAttributeByCharacterAndAttrName(
        characterAttribute1.getCharacter(), characterAttribute1.getAttrName());
    System.out.print("Reading CharacterAttribute: " + read_characterAttribute1.toString() + "\n");

    // UPDATE.
    playerDao.updateName(player1, "ALice_New");
    Gear updatedGear1 = gearDao.updateItemLevel(gear1, 6);
    System.out.println("Updated Gear: " + updatedGear1);
    Job updatedJob = jobDao.updateJobName(job1, "Knight");
    System.out.println("Updated Job Name: " + updatedJob.getName());
    CharacterJob updatedCharacterJob = characterJobDao.updateLevel(characterJob1, 15);
    System.out.println(
        "Updated CharacterJob Level: " + updatedCharacterJob.getCharacter().getFirstName()
            + " -> Level " + updatedCharacterJob.getLevel());
    Consumable update_consumable1 = consumableDao.updateItemLevel(consumable1, 6);
    System.out.println("Update Consumable Result: " + update_consumable1.toString());
    InventorySlot update_InventorySlot1 = inventorySlotDao.updateItemCount(inventorySlot1, 60);
    System.out.println("Updated InventorySlot Result: " + update_InventorySlot1.toString());
    // update records in Currency, CharacterCurrency, and CharacterAttribute.
    Currency update_currency2 = currencyDao.updateMaxAmount(currency2, 2100);
    update_currency2 = currencyDao.updateMaxAmount(currency2, 550);
    System.out.print("Update Currency Result: " + update_currency2.toString() + "\n");
    CharacterCurrency update_characterCurrency2 = characterCurrencyDao.updateAmount(
        characterCurrency2, 200);
    update_characterCurrency2 = characterCurrencyDao.updateEarnedWithinWeek(characterCurrency2, 50);
    System.out.print(
        "Update CharacterCurrency Result: " + update_characterCurrency2.toString() + "\n");
    CharacterAttribute update_characterAttribute1 = characterAttributeDao.updateValue(
        characterAttribute1, 15);
    System.out.print(
        "Update CharacterAttribute Result: " + update_characterAttribute1.toString() + "\n");

    // DELETE.
    playerDao.delete(player4);
    characterDao.delete(character6);
    itemDao.delete(item4);
    weaponDao.delete(weapon1);
    System.out.println("Deleted Weapon: " + weapon1);
    itemEquipableJobDao.delete(equipableJob2);
    System.out.println("Deleted ItemEquipableJob for: " + equipableJob2.getItem().getName() + " -> "
        + equipableJob2.getJob().getName());
  }

}
