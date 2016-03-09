package com.mayank.selfuploadform.selfupload.repository;

import android.content.Context;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.models.PropertyModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

/**
 * Created by rahulchandnani on 01/03/16
 */
public class ProgressRepository {

    private static final String COMMERCIAL_PREFIX_TEXT_FORMAT = "%s%.0f %s, ";
    private static final String COMMERCIAL_POSTFIX_TEXT_FORMAT = "%.0f%s %s, ";
    private Context context;

    public ProgressRepository(Context context) {
        this.context = context;
    }

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

    public String getCommercialText(PropertyModel model) {
        String finalString = "";
        if (null != model.getPrice()) {
            String[] priceArray = context.getResources().getStringArray(R.array.price_entries);
            String priceText = null;
            for (String str : priceArray) {
                String[] splitArray = str.split(",");
                Double conversionFactor = Double.parseDouble(splitArray[1]);
                if (conversionFactor.equals(model.getPriceFactor())) {
                    priceText = splitArray[0];
                    break;
                }
            }
            finalString = finalString.concat(String
                    .format(Locale.getDefault(), COMMERCIAL_PREFIX_TEXT_FORMAT, context.getString(R.string.rupee_sign),
                            model.getPrice(), priceText));
        }
        if (null != model.getBrokerage()) {
            finalString = finalString.concat(String
                    .format(Locale.getDefault(), COMMERCIAL_POSTFIX_TEXT_FORMAT, model.getBrokerage(),
                            context.getString(R.string.percentage), context.getString(R.string.brokerage)));
        }
        if (null != model.getBuiltUpArea() || null != model.getCarpetArea()) {
            String[] areaArray = context.getResources().getStringArray(R.array.area_entries);
            String[] areaNameArray = new String[areaArray.length];
            Double[] areaFactorArray = new Double[areaArray.length];
            int i = 0;
            for (String str : areaArray) {
                String[] splitArray = str.split(",");
                areaNameArray[i] = splitArray[0];
                areaFactorArray[i] = Double.parseDouble(splitArray[1]);
                i++;
            }
            if (null != model.getBuiltUpArea()) {
                String builtUpAreaText = null;
                i = 0;
                for (String name : areaNameArray) {
                    if (model.getBuiltUpAreaFactor().equals(areaFactorArray[i])) {
                        builtUpAreaText = name;
                        break;
                    }
                    i++;
                }
                finalString = finalString.concat(String
                        .format(Locale.getDefault(), COMMERCIAL_POSTFIX_TEXT_FORMAT, model.getBuiltUpArea(),
                                builtUpAreaText, context.getString(R.string.built_up_area_title)));
            }
            if (null != model.getCarpetArea()) {
                String carpetAreaText = null;
                i = 0;
                for (String name : areaNameArray) {
                    if (model.getCarpetAreaFactor().equals(areaFactorArray[i])) {
                        carpetAreaText = name;
                        break;
                    }
                    i++;
                }
                finalString = finalString.concat(String
                        .format(Locale.getDefault(), COMMERCIAL_POSTFIX_TEXT_FORMAT, model.getCarpetArea(),
                                carpetAreaText, context.getString(R.string.carpet_area_title)));
            }
        }
        if (0 < finalString.length()) {
            finalString = finalString.substring(0, finalString.length() - 2);
        }
        return finalString;
    }

    public String getDetailsText(PropertyModel propertyModel) {
        return null;
    }

}