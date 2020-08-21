package com.example.houseit.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class House {

    private String houseCode;
    private List<List<String>> lists;       // lists containing
    private int totalUsers = 1;             // tracks # of users, init = 1
    private String headofHouse;             // creator of household
    private ArrayList<String> housemates = new ArrayList<>();


    //public String[] housemates;

    public House(){
        //need an empty constructor
    }

    public House(String userId){
        // user is creating a house

            headofHouse = userId;

            housemates.add(userId);



    }



    public List<List<String>> getLists() {
        return lists;
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public String getHeadofHouse() {
        return headofHouse;
    }

    public ArrayList<String> getHousemates() {
        return housemates;
    }

    public String getHouseCode(){
        return houseCode;
    }
    public void setHouseCode(String enteredCode){

        houseCode = enteredCode;
    }

    public void addUser(String userId){
        totalUsers++;
        housemates.add(userId);

    }
    public void genCode(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 2;         // for now the length is 2 to understand how duplicates are handled! make it 4
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        houseCode = buffer.toString();
    }

}
