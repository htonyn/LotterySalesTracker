/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.lotterysalestracker.Model.DayResult;
import com.mycompany.lotterysalestracker.Model.Game;
import com.mycompany.lotterysalestracker.Model.Ticket;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseTO {
    // ====================================================================== //
    // Uses Gson Library to read a Gson-generated json file containing data
    // about the list of games currently in the system for the client
    // ====================================================================== //
    public static List<Game> getGames(){
        Gson gson = new Gson();
        BufferedReader bufferedReader = null;
        List<Game> games = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new FileReader("Game.json"));
            games = gson.fromJson(bufferedReader, new TypeToken<List<Game>>(){}.getType());
        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace(System.out);
        } finally {
            if(bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch(IOException ioe) {
                    ioe.printStackTrace(System.out);
                }
            }
        }
        return games;
    }
    // ====================================================================== //
    // Uses Gson library to read a Gson-generated json file specifically
    // pertaining to a list of tickets. Accepts a String that is used as
    // a path towards locating a file to open.
    // ====================================================================== //
    public static List<Ticket> getTickets(String filePath){
        Gson gson = new Gson();
        BufferedReader bufferedReader = null;
        List<Ticket> tickets = new ArrayList<>();
        
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            tickets = gson.fromJson(bufferedReader, new TypeToken<List<Ticket>>(){}.getType());
        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace(System.out);
            
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace(System.out);
                }
            }
        }
        return tickets;
    }
    // ====================================================================== //
    // Uses Gson library to read a Gson-generated json file specifically
    // pertaining to a list of results. Accepts a String that is used as
    // a path towards locating a file to open.
    // ====================================================================== //
    public static List<DayResult> getResults(String filePath){
        Gson gson = new Gson();
        BufferedReader bufferedReader = null;
        List<DayResult> results = new ArrayList<>();
        
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            results = gson.fromJson(bufferedReader, new TypeToken<List<DayResult>>(){}.getType());
        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace(System.out);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace(System.out);
                }
            }
        }
        return results;
    }
    
    // ====================================================================== //
    // While the program is in use, the list of games may or may not change.
    // In the event any games are added or removed, the game list should reflect
    // that change so that any further use of the application will be running
    // with the updated list.
    // ====================================================================== //
    public static void updateGameList(List<Game> games){
        try(Writer writer = new FileWriter("Game.json")) {
            Gson gson = new Gson();
            gson.toJson(games, writer);
        } catch(IOException ioe) {
            ioe.printStackTrace(System.out);
        }
    }
    // ====================================================================== //
    // Generates a daily report upon completing stats for the current day.
    // Currently, the application will overwrite the file if a report has
    // already been generated.
    // ====================================================================== //
    public static void createDailyReport(List<Ticket> tickets, List<DayResult> results){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try (Writer writer = new FileWriter("Tickets" + dateFormat.format(date)  + ".json"){}) {
            Gson gson = new Gson();
            gson.toJson(tickets, writer);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        try (Writer writer = new FileWriter("Results" + dateFormat.format(date)  + ".json"){}) {
            Gson gson = new Gson();
            gson.toJson(results, writer);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
