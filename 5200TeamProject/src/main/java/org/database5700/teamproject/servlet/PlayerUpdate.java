package org.database5700.teamproject.servlet;

import com.mysql.cj.util.StringUtils;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.database5700.teamproject.dal.PlayerDao;
import org.database5700.teamproject.model.Player;

@WebServlet("/playerupdate")
public class PlayerUpdate extends HttpServlet {

  protected PlayerDao playerDao;

  @Override
  public void init() throws ServletException {
    playerDao = PlayerDao.getInstance();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // Map for storing messages
    Map<String, String> messages = new HashMap<>();
    req.setAttribute("messages", messages);

    // Retrieve user and validate
    String idStr = req.getParameter("id");
    if (StringUtils.isNullOrEmpty(idStr)) {
      messages.put("success", "Please entoer a valid accountID");
    } else {
      try {
        Player player = playerDao.getPlayerByID(Integer.parseInt(idStr));
        if (player == null) {
          messages.put("success", "accountID does not exist");
        }
        req.setAttribute("player", player);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }
    req.getRequestDispatcher("/PlayerUpdate.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // Map for storing messages
    Map<String, String> messages = new HashMap<>();
    req.setAttribute("messages", messages);

    // Retrieve user and validate
    String idStr = req.getParameter("id");
    if (StringUtils.isNullOrEmpty(idStr)) {
      messages.put("success", "Please entoer a valid accountID");
    } else {
      try {
        Player player = playerDao.getPlayerByID(Integer.valueOf(idStr));
        if (player == null) {
          messages.put("success", "accountID does not exist");
        } else {
          String newName = req.getParameter("username");
          player = playerDao.updateName(player, newName);
          messages.put("success", "Successfully updated to " + newName);
        }
        req.setAttribute("player", player);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }
    req.getRequestDispatcher("/PlayerUpdate.jsp").forward(req, resp);
  }
}
