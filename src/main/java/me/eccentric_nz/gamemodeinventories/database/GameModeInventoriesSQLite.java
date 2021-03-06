/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.gamemodeinventories.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import me.eccentric_nz.gamemodeinventories.GameModeInventories;

/**
 *
 * @author eccentric_nz
 */
public class GameModeInventoriesSQLite {

    private Connection connection = null;
    private Statement statement = null;
    private final GameModeInventories plugin;

    public GameModeInventoriesSQLite(GameModeInventories plugin) {
        this.plugin = plugin;
    }

    public void createTables() {
        try {
            connection = GameModeInventoriesConnectionPool.dbc();
            statement = connection.createStatement();
            String queryInventories = "CREATE TABLE IF NOT EXISTS inventories (id INTEGER PRIMARY KEY NOT NULL, uuid TEXT, player TEXT, gamemode TEXT, inventory TEXT, xp REAL, armour TEXT, enderchest TEXT, attributes TEXT, armour_attributes TEXT)";
            statement.executeUpdate(queryInventories);
            // update inventories if there is no uuid column
            String queryUUID = "SELECT sql FROM sqlite_master WHERE tbl_name = 'inventories' AND sql LIKE '%uuid TEXT%'";
            ResultSet rsUUID = statement.executeQuery(queryUUID);
            if (!rsUUID.next()) {
                String queryAlterU = "ALTER TABLE inventories ADD uuid TEXT";
                statement.executeUpdate(queryAlterU);
                System.out.println("[GameModeInventories] Adding UUID to database!");
            }
            // update inventories if there is no xp column
            String queryXP = "SELECT sql FROM sqlite_master WHERE tbl_name = 'inventories' AND sql LIKE '%xp REAL%'";
            ResultSet rsXP = statement.executeQuery(queryXP);
            if (!rsXP.next()) {
                String queryAlter = "ALTER TABLE inventories ADD xp REAL";
                statement.executeUpdate(queryAlter);
                System.out.println("[GameModeInventories] Adding xp to database!");
            }
            // update inventories if there is no armour column
            String queryArmour = "SELECT sql FROM sqlite_master WHERE tbl_name = 'inventories' AND sql LIKE '%armour TEXT%'";
            ResultSet rsArmour = statement.executeQuery(queryArmour);
            if (!rsArmour.next()) {
                String queryAlter2 = "ALTER TABLE inventories ADD armour TEXT";
                statement.executeUpdate(queryAlter2);
                System.out.println("[GameModeInventories] Adding armour to database!");
            }
            // update inventories if there is no enderchest column
            String queryEnder = "SELECT sql FROM sqlite_master WHERE tbl_name = 'inventories' AND sql LIKE '%enderchest TEXT%'";
            ResultSet rsEnder = statement.executeQuery(queryEnder);
            if (!rsEnder.next()) {
                String queryAlter3 = "ALTER TABLE inventories ADD enderchest TEXT";
                statement.executeUpdate(queryAlter3);
                System.out.println("[GameModeInventories] Adding enderchest to database!");
            }
            // update inventories if there is no attributes column
            String queryAttr = "SELECT sql FROM sqlite_master WHERE tbl_name = 'inventories' AND sql LIKE '%attributes TEXT%'";
            ResultSet rsAttr = statement.executeQuery(queryAttr);
            if (!rsAttr.next()) {
                String queryAlter4 = "ALTER TABLE inventories ADD attributes TEXT";
                statement.executeUpdate(queryAlter4);
                String queryAlter5 = "ALTER TABLE inventories ADD armour_attributes TEXT";
                statement.executeUpdate(queryAlter5);
                System.out.println("[GameModeInventories] Adding attributes to database!");
            }
            // add blocks table
            String queryBlocks = "CREATE TABLE IF NOT EXISTS blocks (id INTEGER PRIMARY KEY NOT NULL, worldchunk TEXT, location TEXT)";
            statement.executeUpdate(queryBlocks);
            // update inventories if there is no attributes column
            String queryWorld = "SELECT sql FROM sqlite_master WHERE tbl_name = 'blocks' AND sql LIKE '%worldchunk TEXT%'";
            ResultSet rsWorld = statement.executeQuery(queryWorld);
            if (!rsWorld.next()) {
                String queryAlter6 = "ALTER TABLE blocks ADD worldchunk TEXT";
                statement.executeUpdate(queryAlter6);
                System.out.println("[GameModeInventories] Adding new fields to database!");
            }

            // add stands table
            String queryStands = "CREATE TABLE IF NOT EXISTS stands (uuid TEXT PRIMARY KEY NOT NULL)";
            statement.executeUpdate(queryStands);
            statement.close();
        } catch (SQLException e) {
            plugin.getServer().getConsoleSender().sendMessage(plugin.MY_PLUGIN_NAME + "SQLite create table error: " + e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null && GameModeInventoriesConnectionPool.isIsMySQL()) {
                    connection.close();
                }
            } catch (SQLException e) {
                plugin.getServer().getConsoleSender().sendMessage(plugin.MY_PLUGIN_NAME + "SQLite close statement error: " + e);
            }
        }
    }
}
