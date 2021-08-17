package club.lucien2714.hungergamesspeedrun.saveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class saveData {

    public File f = new File("data.json");

    public boolean saveData(String PlayerName, int type) {//save data use
        playerdata p = new playerdata();
        p.career = type;
        p.player = PlayerName;
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String rawData = readToString("data.json");
        Gson gson = new Gson();
        boolean isInList = false;
        List<playerdata> json;
        if (!Objects.equals(rawData, "")) {
            json = gson.fromJson(rawData, new TypeToken<List<playerdata>>() {
            }.getType());
            for (playerdata temp : json
            ) {
                if (Objects.equals(temp.player, PlayerName)) {
                    temp.career = type;
                    isInList = true;
                }
            }
        } else {
            json = new ArrayList<playerdata>();
        }
        if (!isInList)
            json.add(p);
        System.out.println(gson.toJson(json));
        if (writeToFile(gson.toJson(json))) {
            return true;
        } else {
            return false;
        }
    }

    private boolean writeToFile(String str) {//write data to file
        try (FileWriter writer = new FileWriter(f);
             BufferedWriter out = new BufferedWriter(writer)
        ) {
            out.write(str);
            out.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String readToString(String fileName) {//read file
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

    public List<playerdata> getPlayers() {//gets the player data stored in the json file
        String rawData = readToString("data.json");
        Gson gson = new Gson();
        return gson.fromJson(rawData, new TypeToken<List<playerdata>>() {
        }.getType());
    }

}

