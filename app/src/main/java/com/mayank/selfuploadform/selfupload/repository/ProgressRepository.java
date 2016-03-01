package com.mayank.selfuploadform.selfupload.repository;

import com.mayank.selfuploadform.models.PropertyModel;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by rahulchandnani on 01/03/16
 */
public class ProgressRepository {

    public static int getCommercialProgress(PropertyModel propertyModel) {
        ArrayList<Object> list = getMandatoryCommercial(propertyModel);
        int total = list.size();
        int completed = 0;
        for (Object obj : list) {
            if (null != obj) {
                completed++;
            }
        }
        return (int) (((double) completed * 100) / (double) total);
    }

    private static ArrayList<Object> getMandatoryCommercial(PropertyModel model) {
        return new ArrayList<Object>(Arrays.asList(model.getPrice(), model.getBrokerage(), model.getBuiltUpArea(),
                model.getCarpetArea(), model.getSocietyCharges(), model.getIsPriceNegotiable(), model
                        .getAvailableFrom()));
    }

    public static int getDetailsProgress(PropertyModel propertyModel) {
        ArrayList<Object> list = getMandatoryDetails(propertyModel);
        int total = list.size();
        int completed = 0;
        for (Object obj : list) {
            if (null != obj) {
                completed++;
            }
        }
        return (int) (((double) completed * 100) / (double) total);
    }

    private static ArrayList<Object> getMandatoryDetails(PropertyModel model) {
        return new ArrayList<Object>(Arrays.asList(model.getPropertyType(), model.getBhkType(), model
                .getBathroomCount(), model.getBalconyCount(), model.getEntranceFacing(), model.getLocalityId(), model
                .getBuildingId(), model.getFloorNumber(), model.getTotalFloors(), model.getAgeOfProperty(), model
                .getReservedParking(), model.getCupboards(), model.getPipelineGas()));
    }

}