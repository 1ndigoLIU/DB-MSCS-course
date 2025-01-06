package org.database5700.teamproject.servlet;

import com.mysql.cj.util.StringUtils;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.database5700.teamproject.dal.*;
import org.database5700.teamproject.model.*;
import org.database5700.teamproject.model.Character;

@WebServlet("/characters")
public class FindCharactersByPlayer extends HttpServlet {

  protected PlayerDao playerDao;
  protected CharacterDao characterDao;
  protected CharacterEquipmentDao characterEquipmentDao;
  protected CharacterAttributeDao characterAttributeDao;
  protected GearDao gearDao;
  protected WeaponDao weaponDao;
  protected ConsumableDao consumableDao;
  protected InventorySlotDao inventorySlotDao;

  @Override
  public void init() throws ServletException {
    playerDao = PlayerDao.getInstance();
    characterDao = CharacterDao.getInstance();
    characterEquipmentDao = CharacterEquipmentDao.getInstance();
    characterAttributeDao = CharacterAttributeDao.getInstance();
    gearDao = GearDao.getInstance();
    weaponDao = WeaponDao.getInstance();
    consumableDao = ConsumableDao.getInstance();
    inventorySlotDao = InventorySlotDao.getInstance();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);

    List<Character> characters = new ArrayList<Character>();

    // Retrieve and validate playerId.
    String playerIdStr = req.getParameter("id");
    if (playerIdStr == null || playerIdStr.trim().isEmpty()) {
      messages.put("success", "Please enter a valid player ID.");
    } else {
      try {
        int playerId = Integer.parseInt(playerIdStr);
        Player player = playerDao.getPlayerByID(playerId);
        if (player != null) {
          characters = characterDao.getCharacterByPlayer(player);
          messages.put("success", "Displaying results for Player ID: " + playerId);
        } else {
          messages.put("success", "Player not found for ID: " + playerId);
        }
      } catch (NumberFormatException e) {
        messages.put("success", "Invalid player ID format.");
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }
    req.setAttribute("characters", characters);
    req.getRequestDispatcher("/FindCharactersByPlayer.jsp").forward(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // Determine which form was submitted by using the 'action' parameter
    String action = req.getParameter("action");

    if ("viewEquipments".equals(action)) {
      handleViewEquipments(req, resp);
    } else if ("viewAttributes".equals(action)) {
      handleViewAttributes(req, resp);
    } else if ("viewInventory".equals(action)) {
      handleViewInventory(req, resp);
    } else if ("updateItemCount".equals(action)) {
      handleUpdateItemCount(req, resp);
    } else {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action");
    }
  }

  // Handle View Equipments
  private void handleViewEquipments(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);

    List<Character> characters = new ArrayList<Character>();

    // Retrieve and validate playerId.
    String playerIdStr = req.getParameter("id");
    if (playerIdStr == null || playerIdStr.trim().isEmpty()) {
      messages.put("success", "Please enter a valid player ID.");
    } else {
      try {
        int playerId = Integer.parseInt(playerIdStr);
        Player player = playerDao.getPlayerByID(playerId);
        if (player != null) {
          characters = characterDao.getCharacterByPlayer(player);
          messages.put("success", "Displaying results for Player ID: " + playerId);

          // Retrieve equipments for a specific character if requested
          String characterIdStr = req.getParameter("characterId");
          if (characterIdStr != null && !characterIdStr.trim().isEmpty()) {
            int characterId = Integer.parseInt(characterIdStr);
            Character selectedCharacter = characterDao.getCharacterByID(characterId);
            if (selectedCharacter != null) {
              List<CharacterEquipment> equipments = characterEquipmentDao.getCharacterEquipmentByCharacter(
                  selectedCharacter);
              req.setAttribute("selectedCharacter", selectedCharacter);
              Weapon weapon = null;
              List<Gear> gears = new ArrayList<>();
              for (CharacterEquipment equipment : equipments) {
                if (equipment.getSlotType().equals("Main Hand")) {
                  weapon = weaponDao.getWeaponByID(equipment.getItem().getItemID());
                } else {
                  gears.add(gearDao.getGearByID(equipment.getItem().getItemID()));
                }
              }
              req.setAttribute("weapon", weapon);
              req.setAttribute("gears", gears);
            }
          }
        } else {
          messages.put("success", "Player not found for ID: " + playerId);
        }
      } catch (NumberFormatException e) {
        messages.put("success", "Invalid player ID format.");
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }
    req.setAttribute("characters", characters);
    req.setAttribute("action", "viewEquipments");
    req.getRequestDispatcher("/FindCharactersByPlayer.jsp").forward(req, resp);
  }

  private void handleViewAttributes(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);

    List<Character> characters = new ArrayList<Character>();

    // Retrieve and validate playerId.
    String playerIdStr = req.getParameter("id");
    if (playerIdStr == null || playerIdStr.trim().isEmpty()) {
      messages.put("success", "Please enter a valid player ID.");
    } else {
      try {
        int playerId = Integer.parseInt(playerIdStr);
        Player player = playerDao.getPlayerByID(playerId);
        if (player != null) {
          characters = characterDao.getCharacterByPlayer(player);
          messages.put("success", "Displaying results for Player ID: " + playerId);

          // Retrieve equipments for a specific character if requested
          String characterIdStr = req.getParameter("characterId");
          if (characterIdStr != null && !characterIdStr.trim().isEmpty()) {
            int characterId = Integer.parseInt(characterIdStr);
            Character selectedCharacter = characterDao.getCharacterByID(characterId);
            if (selectedCharacter != null) {
              List<CharacterAttribute> attributes = characterAttributeDao.getCharacterAttributesByCharacter(
                  selectedCharacter);
              req.setAttribute("selectedCharacter", selectedCharacter);
              req.setAttribute("selectedAttributes", attributes);
            }
          }
        } else {
          messages.put("success", "Player not found for ID: " + playerId);
        }
      } catch (NumberFormatException e) {
        messages.put("success", "Invalid player ID format.");
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }
    req.setAttribute("characters", characters);
    req.setAttribute("action", "viewAttributes");
    req.getRequestDispatcher("/FindCharactersByPlayer.jsp").forward(req, resp);
  }

  private void handleViewInventory(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);
    List<Character> characters = new ArrayList<Character>();
    // Retrieve and validate playerId.
    String playerIdStr = req.getParameter("id");
    if (playerIdStr == null || playerIdStr.trim().isEmpty()) {
      messages.put("success", "Please enter a valid player ID.");
    } else {
      try {
        int playerId = Integer.parseInt(playerIdStr);
        Player player = playerDao.getPlayerByID(playerId);
        if (player != null) {
          characters = characterDao.getCharacterByPlayer(player);
          messages.put("success", "Displaying results for Player ID: " + playerId);

          // Retrieve equipments for a specific character if requested
          String characterIdStr = req.getParameter("characterId");
          if (characterIdStr != null && !characterIdStr.trim().isEmpty()) {
            int characterId = Integer.parseInt(characterIdStr);
            Character selectedCharacter = characterDao.getCharacterByID(characterId);
            if (selectedCharacter != null) {
              List<InventorySlot> inventorySlotList = inventorySlotDao.getInventorySlotsByCharacter(
                  selectedCharacter);
              Map<Integer, InventorySlot> inventorySlots = new HashMap<>();
              Map<Integer, Weapon> weapons = new HashMap<>();
              Map<Integer, Gear> gears = new HashMap<>();
              Map<Integer, Consumable> consumables = new HashMap<>();
              for (InventorySlot inventorySlot : inventorySlotList) {
                inventorySlots.put(inventorySlot.getSlotID(), inventorySlot);
                Weapon weapon = weaponDao.getWeaponByID(inventorySlot.getItem().getItemID());
                if (weapon != null) {
                  weapons.put(inventorySlot.getSlotID(), weapon);
                }
                Gear gear = gearDao.getGearByID(inventorySlot.getItem().getItemID());
                if (gear != null) {
                  gears.put(inventorySlot.getSlotID(), gear);
                }
                Consumable consumable = consumableDao.getConsumableByID(
                    inventorySlot.getItem().getItemID());
                if (consumable != null) {
                  consumables.put(inventorySlot.getSlotID(), consumable);
                }
              }
              req.setAttribute("selectedCharacter", selectedCharacter);
              req.setAttribute("inventorySlots", inventorySlots);
              req.setAttribute("inventoryWeapons", weapons);
              req.setAttribute("inventoryGears", gears);
              req.setAttribute("inventoryConsumables", consumables);
            }
          }
        } else {
          messages.put("success", "Player not found for ID: " + playerId);
        }
      } catch (NumberFormatException e) {
        messages.put("success", "Invalid player ID format.");
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }
    req.setAttribute("characters", characters);
    req.setAttribute("action", "viewInventory");
    req.getRequestDispatcher("/FindCharactersByPlayer.jsp").forward(req, resp);
  }

  private void handleUpdateItemCount(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<>();
    req.setAttribute("messages", messages);

    // Retrieve parameters from the form submission
    String slotIdStr = req.getParameter("inventorySlotID");
    String newCountStr = req.getParameter("newCount");

    if (slotIdStr != null && newCountStr != null) {
      try {
        int slotId = Integer.parseInt(slotIdStr);
        int newCount = Integer.parseInt(newCountStr);
        InventorySlot inventorySlot = inventorySlotDao.getInventorySlotByID(slotId);
        if (newCount > 0) {
          // Call the DAO layer to update the item count in inventory
          inventorySlotDao.updateItemCount(inventorySlot, newCount);
          messages.put("updateSuccess", "Item count updated successfully.");
        } else if (newCount == 0) {
          inventorySlotDao.delete(inventorySlot);
          messages.put("updateSuccess", "Item in inventory deleted successfully.");
        }
      } catch (NumberFormatException | SQLException e) {
        e.printStackTrace();
        messages.put("error", "Failed to update item count. Please try again.");
      }
    }

    // Forward the request back to the same JSP page to display updated inventory
    List<Character> characters = new ArrayList<Character>();
    // Retrieve and validate playerId.
    String playerIdStr = req.getParameter("id");
    if (playerIdStr == null || playerIdStr.trim().isEmpty()) {
      messages.put("success", "Please enter a valid player ID.");
    } else {
      try {
        int playerId = Integer.parseInt(playerIdStr);
        Player player = playerDao.getPlayerByID(playerId);
        if (player != null) {
          characters = characterDao.getCharacterByPlayer(player);
        }
      } catch (NumberFormatException e) {
        messages.put("success", "Invalid player ID format.");
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }
    req.setAttribute("characters", characters);
    req.setAttribute("action", "updateItemCount");
    req.getRequestDispatcher("/FindCharactersByPlayer.jsp").forward(req, resp);
  }
}