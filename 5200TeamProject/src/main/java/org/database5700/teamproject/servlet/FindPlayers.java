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
import org.database5700.teamproject.dal.PlayerDao;
import org.database5700.teamproject.model.Player;

// http://localhost:8080/TeamProject_war_exploded/findplayers
@WebServlet("/findplayers")
public class FindPlayers extends HttpServlet {

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
    List<Player> players = new ArrayList<>();

    // Retrieve and validate name
    // name is retrieved from the URL query string
    String name = req.getParameter("username");
    if (StringUtils.isNullOrEmpty(name)) {
      messages.put("success", "Please enter a valid name");
    } else {
      // Retrieve Players, and store as a message
      try {
        players = playerDao.getPlayerByName(name);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
      messages.put("success", "Displaying results for " + name);
      messages.put("previousUsername", name);
    }
    req.setAttribute("players", players);
    req.getRequestDispatcher("/FindPlayers.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // Map for storing messages
    Map<String, String> messages = new HashMap<>();
    req.setAttribute("messages", messages);
    List<Player> players = new ArrayList<>();

    // Retrieve and validate name
    // name is retrieved from the URL query string
    String name = req.getParameter("username");
    if (StringUtils.isNullOrEmpty(name)) {
      messages.put("success", "Please enter a valid name");
    } else {
      // Retrieve Players, and store as a message
      try {
        players = playerDao.getPlayerByName(name);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
      messages.put("success", "Displaying results for " + name);
      messages.put("previousUsername", name);
    }
    req.setAttribute("players", players);
    req.getRequestDispatcher("/FindPlayers.jsp").forward(req, resp);
  }
}
