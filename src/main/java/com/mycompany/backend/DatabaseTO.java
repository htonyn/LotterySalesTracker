/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
    
    public static List<Game> getGames(){
        Gson gson = new Gson();
        BufferedReader bufferedReader = null;
        List<Game> games = new ArrayList<>();
        try{
            bufferedReader = new BufferedReader(new FileReader("Games.json"));
            games = gson.fromJson(bufferedReader, new TypeToken<List<Game>>(){}.getType());
        }catch(FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }finally{
            if(bufferedReader != null){
                try{
                    bufferedReader.close();
                }catch(IOException ioe){
                    ioe.printStackTrace();
                }
            }
        }
        return games;
    }
    
    public static List<Ticket> getTickets(){
        Gson gson = new Gson();
        BufferedReader bufferedReader = null;
        List<Ticket> tickets = new ArrayList<>();
        try{
            bufferedReader = new BufferedReader(new FileReader("Tickets.json"));
            tickets = gson.fromJson(bufferedReader, new TypeToken<List<Ticket>>(){}.getType());
        }catch(FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }finally{
            if(bufferedReader != null){
                try{
                    bufferedReader.close();
                }catch(IOException ioe){
                    ioe.printStackTrace();
                }
            }
        }
        return tickets;
    }
    
    public static void updateGameList(List<Game> games){
        try(Writer writer = new FileWriter("Game.json") {}){
            Gson gson = new Gson();
            gson.toJson(games, writer);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    
    public static void createDailyReport(List<Ticket> tickets){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        try(Writer writer = new FileWriter("Tickets" + dateFormat.format(date)  + ".json"){}){
            Gson gson = new Gson();
            gson.toJson(tickets, writer);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
}
