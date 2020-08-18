package com.example.houseit;

import java.util.Random;

public class House {

    public String houseCode;
    public int totalUsers = 1;           // tracks # of users, init = 1
    public String headofHouse;      // creator of household
    //public String[] housemates;
    public House(String userId, boolean create){
        // user is creating a house
        if(create){
            headofHouse = userId;
            //housemates.
        }
        else {
            totalUsers++;

        }

    }
    public String getHouseCode(){
        return houseCode;
    }
    public void setHouseCode(String enteredCode){

        houseCode = enteredCode;
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
