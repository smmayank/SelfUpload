package com.mayank.selfuploadform.models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rahulchandnani on 22/02/16
 */
public class PhotoModel {

    private HashMap<String, ArrayList<PhotoObject>> map;

    public HashMap<String, ArrayList<PhotoObject>> getMap() {
        return map;
    }

    public void setMap(HashMap<String, ArrayList<PhotoObject>> map) {
        this.map = map;
    }

    public static class PhotoObject {

        private Integer id;
        private String path;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

    }

}
